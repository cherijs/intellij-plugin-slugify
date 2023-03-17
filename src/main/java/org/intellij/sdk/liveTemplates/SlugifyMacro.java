package org.intellij.sdk.liveTemplates;

import com.github.cherijs.intellijpluginslugify.SlugifySettings;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.intellij.codeInsight.template.*;
import com.intellij.codeInsight.template.macro.MacroBase;
import com.intellij.openapi.util.text.StringUtil;

import java.net.http.HttpClient;

import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SlugifyMacro extends MacroBase {

    private static final String API_BASE_URL = "https://translation.googleapis.com/language/translate/v2?key=";

    public SlugifyMacro() {
        super("slugify", "slugify(String)");
    }

    /**
     * Strictly to uphold contract for constructors in base class.
     */
    private SlugifyMacro(String name, String description) {
        super(name, description);
    }

    @Override
    protected Result calculateResult(Expression @NotNull [] params, ExpressionContext context, boolean quick) {

        // Retrieve the text from the macro or selection, if any is available.
        String text = getTextResult(params, context, true);


        if (text == null) {
            return null;
        }
        String lastText = SlugifySettings.getInstance().getLastText();
        String lastTranslatedText = SlugifySettings.getInstance().getLastTranslation();
        if (text.equals(lastText) || text.equals(lastTranslatedText)) {
            return new TextResult(lastTranslatedText);
        }
        String translatedText = text;

        // Translate text to English using Google Translate API
        translatedText = translateText(translatedText);

        // Slugify translated text
        if (translatedText.length() > 0) {
            translatedText = StringUtil.toLowerCase(translatedText);
            translatedText = translatedText.replaceAll("[^a-z0-9]+", "-"); // replace any non-alphanumeric characters with "-"
            translatedText = translatedText.replaceAll("-+", "-"); // replace any consecutive dashes with a single dash
            translatedText = translatedText.replaceAll("^-|-$", ""); // remove leading and trailing dashes
        }
        SlugifySettings.getInstance().setLastText(text);
        SlugifySettings.getInstance().setLastTranslation(translatedText);
        return new TextResult(translatedText);
    }

    private String translateText(String text) {
        String apiKey = SlugifySettings.getInstance().getApiKey();
        String language = SlugifySettings.getInstance().getLanguage();

        if (apiKey == null || apiKey.isEmpty()) {
            // Return the original text if the API key is not set
            return text;
        }

        try {
            // Build the URL for the API request
            String url = API_BASE_URL + apiKey
                    + "&q=" + URLEncoder.encode(text, "UTF-8")
                    + "&target=" + language;

            // Create an HTTP client and request
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();

            // Send the request and parse the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());

            JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
            String translatedText = json.getAsJsonObject("data")
                    .getAsJsonArray("translations")
                    .get(0)
                    .getAsJsonObject()
                    .get("translatedText")
                    .getAsString();

            return translatedText;
        } catch (Exception e) {
            // Log any errors and return the original text
            e.printStackTrace();
            return text;
        }
    }


    @Override
    public boolean isAcceptableInContext(TemplateContextType context) {
        // Might want to be less restrictive in future
        return true;
    }

}
