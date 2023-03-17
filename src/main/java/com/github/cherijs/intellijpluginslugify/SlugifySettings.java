package com.github.cherijs.intellijpluginslugify;

public class SlugifySettings {
    private static SlugifySettings instance;
    private String apiKey = null;
    private String language = "en";
    private String lastText = null;
    private String lastTranslation = null;

    private SlugifySettings() {
    }

    public static SlugifySettings getInstance() {
        if (instance == null) {
            instance = new SlugifySettings();
        }
        return instance;
    }

    public String getApiKey() {
        return this.apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }


    public String getLastText() {
        return this.lastText;
    }

    public void setLastText(String lastText) {
        this.lastText = lastText;
    }

    public String getLastTranslation() {
        return this.lastTranslation;
    }

    public void setLastTranslation(String lastTranslation) {
        this.lastTranslation = lastTranslation;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
