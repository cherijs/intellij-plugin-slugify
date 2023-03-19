package com.github.cherijs.intellijpluginslugify;

import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * Supports creating and managing a {@link JPanel} for the Settings Dialog.
 */
public class AppSettingsComponent {

    private final JPanel settingsMainPanel;
    private final JBTextField apiKeyText = new JBTextField();
    private final JBCheckBox enabledCheckBox = new JBCheckBox("Enabled");
    private final ComboBox<Language> languageDropdown = new ComboBox<Language>();

    private Language[] languages = {
            new Language("af", "Afrikaans"),
            new Language("ak", "Akan"),
            new Language("sq", "Albanian"),
            new Language("am", "Amharic"),
            new Language("ar", "Arabic"),
            new Language("hy", "Armenian"),
            new Language("as", "Assamese"),
            new Language("ay", "Aymara"),
            new Language("az", "Azerbaijani"),
            new Language("bm", "Bambara"),
            new Language("eu", "Basque"),
            new Language("be", "Belarusian"),
            new Language("bn", "Bengali"),
            new Language("bho", "Bhojpuri"),
            new Language("bs", "Bosnian"),
            new Language("bg", "Bulgarian"),
            new Language("ca", "Catalan"),
            new Language("ceb", "Cebuano"),
            new Language("ny", "Chichewa"),
            new Language("zh", "Chinese (Simplified)"),
            new Language("zh-TW", "Chinese (Traditional)"),
            new Language("co", "Corsican"),
            new Language("hr", "Croatian"),
            new Language("cs", "Czech"),
            new Language("da", "Danish"),
            new Language("dv", "Divehi"),
            new Language("doi", "Dogri"),
            new Language("nl", "Dutch"),
            new Language("en", "English"),
            new Language("eo", "Esperanto"),
            new Language("et", "Estonian"),
            new Language("ee", "Ewe"),
            new Language("tl", "Filipino"),
            new Language("fi", "Finnish"),
            new Language("fr", "French"),
            new Language("fy", "Frisian"),
            new Language("gl", "Galician"),
            new Language("lg", "Ganda"),
            new Language("ka", "Georgian"),
            new Language("de", "German"),
            new Language("gom", "Goan Konkani"),
            new Language("el", "Greek"),
            new Language("gn", "Guarani"),
            new Language("gu", "Gujarati"),
            new Language("ht", "Haitian Creole"),
            new Language("ha", "Hausa"),
            new Language("haw", "Hawaiian"),
            new Language("iw", "Hebrew"),
            new Language("hi", "Hindi"),
            new Language("hmn", "Hmong"),
            new Language("hu", "Hungarian"),
            new Language("is", "Icelandic"),
            new Language("ig", "Igbo"),
            new Language("ilo", "Iloko"),
            new Language("id", "Indonesian"),
            new Language("ga", "Irish"),
            new Language("it", "Italian"),
            new Language("ja", "Japanese"),
            new Language("jw", "Javanese"),
            new Language("kn", "Kannada"),
            new Language("kk", "Kazakh"),
            new Language("km", "Khmer"),
            new Language("rw", "Kinyarwanda"),
            new Language("ko", "Korean"),
            new Language("kri", "Krio"),
            new Language("ku", "Kurdish (Kurmanji)"),
            new Language("ckb", "Kurdish (Sorani)"),
            new Language("ky", "Kyrgyz"),
            new Language("lo", "Lao"),
            new Language("la", "Latin"),
            new Language("lv", "Latvian"),
            new Language("ln", "Lingala"),
            new Language("lt", "Lithuanian"),
            new Language("lb", "Luxembourgish"),
            new Language("mk", "Macedonian"),
            new Language("mai", "Maithili"),
            new Language("mg", "Malagasy"),
            new Language("ms", "Malay"),
            new Language("ml", "Malayalam"),
            new Language("mt", "Maltese"),
            new Language("mni-Mtei", "Manipuri (Meitei Mayek)"),
            new Language("mi", "Maori"),
            new Language("mr", "Marathi"),
            new Language("lus", "Mizo"),
            new Language("mn", "Mongolian"),
            new Language("my", "Myanmar (Burmese)"),
            new Language("ne", "Nepali"),
            new Language("nso", "Northern Sotho"),
            new Language("no", "Norwegian"),
            new Language("or", "Odia (Oriya)"),
            new Language("om", "Oromo"),
            new Language("ps", "Pashto"),
            new Language("fa", "Persian"),
            new Language("pl", "Polish"),
            new Language("pt", "Portuguese"),
            new Language("pa", "Punjabi"),
            new Language("qu", "Quechua"),
            new Language("ro", "Romanian"),
            new Language("ru", "Russian"),
            new Language("sm", "Samoan"),
            new Language("sa", "Sanskrit"),
            new Language("gd", "Scots Gaelic"),
            new Language("sr", "Serbian"),
            new Language("st", "Sesotho"),
            new Language("sn", "Shona"),
            new Language("sd", "Sindhi"),
            new Language("si", "Sinhala"),
            new Language("sk", "Slovak"),
            new Language("sl", "Slovenian"),
            new Language("so", "Somali"),
            new Language("es", "Spanish"),
            new Language("su", "Sundanese"),
            new Language("sw", "Swahili"),
            new Language("sv", "Swedish"),
            new Language("tg", "Tajik"),
            new Language("ta", "Tamil"),
            new Language("tt", "Tatar"),
            new Language("te", "Telugu"),
            new Language("th", "Thai"),
            new Language("ti", "Tigrinya"),
            new Language("ts", "Tsonga"),
            new Language("tr", "Turkish"),
            new Language("tk", "Turkmen"),
            new Language("uk", "Ukrainian"),
            new Language("ur", "Urdu"),
            new Language("ug", "Uyghur"),
            new Language("uz", "Uzbek"),
            new Language("vi", "Vietnamese"),
            new Language("cy", "Welsh"),
            new Language("xh", "Xhosa"),
            new Language("yi", "Yiddish"),
            new Language("yo", "Yoruba"),
            new Language("zu", "Zulu"),
            new Language("he", "Hebrew"),
            new Language("jv", "Javanese"),
            new Language("zh-CN", "Chinese (Simplified)")
    };

    public AppSettingsComponent() {
        for (Language language : languages) {
            languageDropdown.addItem(language);
        }


//        enabledCheckBox.addChangeListener(e -> {
//            apiKeyText.setEnabled(enabledCheckBox.isSelected());
//        });

        settingsMainPanel = FormBuilder.createFormBuilder()
                .addComponent(enabledCheckBox, 1)
                .addLabeledComponent(new JBLabel("Enter google translate api key: "), apiKeyText, 4, true)
                .addLabeledComponent(new JBLabel("Language: "), languageDropdown, 4, true)
                .addComponentFillVertically(new JPanel(), 0)
                .getPanel();

    }

    public JPanel getPanel() {
        return settingsMainPanel;
    }

    public JComponent getPreferredFocusedComponent() {
        return apiKeyText;
    }

    @NotNull
    public String getApiKeyText() {
        return apiKeyText.getText();
    }

    public void setApiKeyText(@NotNull String newText) {
        apiKeyText.setText(newText);
    }

    public boolean getEnabled() {
        return enabledCheckBox.isSelected();
    }

    public void setEnabled(boolean newStatus) {
        enabledCheckBox.setSelected(newStatus);
    }

    public String getLanguage() {
        Language selectedLanguage = (Language) languageDropdown.getSelectedItem();
        return selectedLanguage.getCode();
    }

    public void setLanguage(String code) {
        int index = -1;
        for (int i = 0; i < languages.length; i++) {
            if (languages[i].getCode().equals(code)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            System.out.println("Index of ak language: " + index);
            languageDropdown.setSelectedItem(languages[index]);
        } else {
            System.out.println("ak language not found in the languages array.");
        }

    }


}