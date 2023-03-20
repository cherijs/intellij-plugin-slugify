package org.intellij.sdk.liveTemplates;

import com.github.cherijs.intellijpluginslugify.AppSettingsState;
import com.intellij.codeInsight.template.*;
import com.intellij.codeInsight.template.macro.MacroBase;
import com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;


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
        if (text.equals(settings.lastSearch) && settings.lastSlug != null) {
            return new TextResult(settings.lastSlug);
        }

        settings.lastSearch = text;
        text = slugifyText(text);

        return new TextResult(text);
    }

    @NotNull
    public static String slugifyText(String text) {

        // Detect script type based on the presence of Cyrillic characters
        boolean isCyrillic = text.matches(".*[А-Яа-яЁё].*");

        // Convert Cyrillic characters to Latin if necessary
        if (isCyrillic) {
            text = convertCyrillicToLatin(text);
        }


        // Convert Unicode characters to Latin characters
        text = Normalizer.normalize(text, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");

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

    public static String convertCyrillicToLatin(String cyrillic) {
        Map<Character, String> translitMap = new HashMap<>();
        translitMap.put('А', "A");
        translitMap.put('Б', "B");
        translitMap.put('В', "V");
        translitMap.put('Г', "G");
        translitMap.put('Д', "D");
        translitMap.put('Е', "E");
        translitMap.put('Ё', "YO");
        translitMap.put('Ж', "ZH");
        translitMap.put('З', "Z");
        translitMap.put('И', "I");
        translitMap.put('Й', "Y");
        translitMap.put('К', "K");
        translitMap.put('Л', "L");
        translitMap.put('М', "M");
        translitMap.put('Н', "N");
        translitMap.put('О', "O");
        translitMap.put('П', "P");
        translitMap.put('Р', "R");
        translitMap.put('С', "S");
        translitMap.put('Т', "T");
        translitMap.put('У', "U");
        translitMap.put('Ф', "F");
        translitMap.put('Х', "H");
        translitMap.put('Ц', "TS");
        translitMap.put('Ч', "CH");
        translitMap.put('Ш', "SH");
        translitMap.put('Щ', "SCH");
        translitMap.put('Ъ', "");
        translitMap.put('Ы', "Y");
        translitMap.put('Ь', "");
        translitMap.put('Э', "E");
        translitMap.put('Ю', "YU");
        translitMap.put('Я', "YA");
        translitMap.put('а', "a");
        translitMap.put('б', "b");
        translitMap.put('в', "v");
        translitMap.put('г', "g");
        translitMap.put('д', "d");
        translitMap.put('е', "e");
        translitMap.put('ё', "yo");
        translitMap.put('ж', "zh");
        translitMap.put('з', "z");
        translitMap.put('и', "i");
        translitMap.put('й', "y");
        translitMap.put('к', "k");
        translitMap.put('л', "l");
        translitMap.put('м', "m");
        translitMap.put('н', "n");
        translitMap.put('о', "o");
        translitMap.put('п', "p");
        translitMap.put('р', "r");
        translitMap.put('с', "s");
        translitMap.put('т', "t");
        translitMap.put('у', "u");
        translitMap.put('ф', "f");
        translitMap.put('х', "h");
        translitMap.put('ц', "ts");
        translitMap.put('ч', "ch");
        translitMap.put('ш', "sh");
        translitMap.put('щ', "sch");
        translitMap.put('ъ', "");
        translitMap.put('ы', "y");
        translitMap.put('ь', "");
        translitMap.put('э', "e");
        translitMap.put('ю', "yu");
        translitMap.put('я', "ya");

        StringBuilder builder = new StringBuilder();
        System.out.println(cyrillic.toCharArray());
        for (char c : cyrillic.toCharArray()) {
            if (translitMap.containsKey(c)) {
                builder.append(translitMap.get(c));
            } else {
                builder.append(c);
            }
        }
        return builder.toString();
    }

    @Override
    public boolean isAcceptableInContext(TemplateContextType context) {
        // Might want to be less restrictive in future
        return true;
    }

}
