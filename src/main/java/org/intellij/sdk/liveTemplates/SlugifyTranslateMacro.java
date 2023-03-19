package org.intellij.sdk.liveTemplates;

import com.github.cherijs.intellijpluginslugify.AppSettingsState;
import com.intellij.codeInsight.template.*;
import com.intellij.codeInsight.template.macro.MacroBase;
import org.jetbrains.annotations.NotNull;


public class SlugifyTranslateMacro extends MacroBase {

    public SlugifyTranslateMacro() {
        super("translate_slugify", "translate_slugify(String)");
    }

    /**
     * Strictly to uphold contract for constructors in base class.
     */
    private SlugifyTranslateMacro(String name, String description) {
        super(name, description);
    }

    private final AppSettingsState settings = AppSettingsState.getInstance();

    @Override
    protected Result calculateResult(Expression @NotNull [] params, ExpressionContext context, boolean quick) {


        // Retrieve the text from the macro or selection, if any is available.
        String text = getTextResult(params, context, true);


        if (text == null) {
            return null;
        }
        if ((text.equals(settings.lastSearch) || text.equals(settings.lastTranslationSlug)) && settings.lastTranslationSlug != null) {
            return new TextResult(settings.lastTranslationSlug);
        }

        settings.lastSearch = text;

        text = TranslateMacro.translateText(text);
        text = SlugifyMacro.slugifyText(text);

        return new TextResult(text);
    }

    @Override
    public boolean isAcceptableInContext(TemplateContextType context) {
        // Might want to be less restrictive in future
        return true;
    }

}
