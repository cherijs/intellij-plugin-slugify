package org.intellij.sdk.liveTemplates;

import com.github.cherijs.intellijpluginslugify.AppSettingsState;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.intellij.codeInsight.template.*;
import com.intellij.codeInsight.template.macro.MacroBase;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;


public class TranslateMacro extends MacroBase {
    private static final Logger LOG = Logger.getInstance(TranslateMacro.class);

    private static final String API_BASE_URL = "https://translation.googleapis.com/language/translate/v2?key=";

    public TranslateMacro() {
        super("translate", "translate(String)");
    }

    /**
     * Strictly to uphold contract for constructors in base class.
     */
    private TranslateMacro(String name, String description) {
        super(name, description);
    }

    private static final AppSettingsState settings = AppSettingsState.getInstance();

    @Override
    protected Result calculateResult(Expression @NotNull [] params, ExpressionContext context, boolean quick) {


        // Retrieve the text from the macro or selection, if any is available.
        String text = getTextResult(params, context, true);


        if (text == null) {
            return null;
        }
        if (text.equals(settings.lastSearch)  && settings.lastTranslation != null) {
            return new TextResult(settings.lastTranslation);
        }
        settings.lastSearch = text;

        // Translate text to English using Google Translate API
        text = translateText(text);

        return new TextResult(text);
    }

    public static String translateText(String text) {

        if (settings.apiKey == null || settings.apiKey.isEmpty() || !settings.enabled) {
            // Return the original text if the API key is not set
            return text;
        }

        JsonObject json;

        try {
            // Build the URL for the API request
            String url = API_BASE_URL + settings.apiKey
                    + "&q=" + URLEncoder.encode(text, StandardCharsets.UTF_8)
                    + "&target=" + settings.language;

            // Create an HTTP client and request
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();

            // Send the request and parse the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

//            System.out.println(response.body());

            json = JsonParser.parseString(response.body()).getAsJsonObject();

        } catch (Exception e) {
            if (e.getMessage() != null) {
                // Create a notification with the exception message
                Notification notification = new Notification(
                        "Slugify",
                        "Translation error",
                        e.getMessage(),
                        NotificationType.ERROR
                );
                // Show the notification
                Notifications.Bus.notify(notification);
                LOG.error(e.getMessage());
            }

            // Log any errors and return the original text
//            e.printStackTrace();

            return text;
        }


        try {
            String translatedText = json.getAsJsonObject("data")
                    .getAsJsonArray("translations")
                    .get(0)
                    .getAsJsonObject()
                    .get("translatedText")
                    .getAsString();
            settings.lastTranslation = translatedText;
            return translatedText;
        } catch (Exception e) {
            String message = json.getAsJsonObject("error")
                    .get("message")
                    .getAsString();
            if (message != null) {
                Notification notification = new Notification(
                        "Slugify",
                        "Translation error",
                        message,
                        NotificationType.ERROR
                );
                Notifications.Bus.notify(notification);
                LOG.error(message);
            } else {
                // Log any errors and return the original text
//                e.printStackTrace();
                LOG.error(e.getMessage());
            }
            return text;
        }
    }


    @Override
    public boolean isAcceptableInContext(TemplateContextType context) {
        // Might want to be less restrictive in future
        return true;
    }

}
