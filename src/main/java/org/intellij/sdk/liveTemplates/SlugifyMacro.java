package org.intellij.sdk.liveTemplates;

import com.github.cherijs.intellijpluginslugify.AppSettingsState;
import com.intellij.codeInsight.template.*;
import com.intellij.codeInsight.template.macro.MacroBase;
import com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.annotations.NotNull;


public class SlugifyMacro extends MacroBase {

    public SlugifyMacro() {
        super("slugify", "slugify(String)");
    }

    /**
     * Strictly to uphold contract for constructors in base class.
     */
    private SlugifyMacro(String name, String description) {
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
        if ((text.equals(settings.lastSearch) || text.equals(settings.lastSlug)) && settings.lastSlug != null) {
            return new TextResult(settings.lastSlug);
        }

        settings.lastSearch = text;
        text = slugifyText(text);

        return new TextResult(text);
    }

    @NotNull
    public static String slugifyText(String text) {
        // Slugify translated text
        if (text.length() > 0) {
            text = StringUtil.toLowerCase(text);
            text = text.replaceAll("[^a-z0-9]+", "-"); // replace any non-alphanumeric characters with "-"
            text = text.replaceAll("-+", "-"); // replace any consecutive dashes with a single dash
            text = text.replaceAll("^-|-$", ""); // remove leading and trailing dashes
            settings.lastSlug = text;
        }
        return text;
    }

    @Override
    public boolean isAcceptableInContext(TemplateContextType context) {
        // Might want to be less restrictive in future
        return true;
    }

}
