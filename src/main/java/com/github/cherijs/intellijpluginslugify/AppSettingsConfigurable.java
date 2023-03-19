package com.github.cherijs.intellijpluginslugify;

import com.github.cherijs.intellijpluginslugify.AppSettingsState;
import com.intellij.openapi.options.Configurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
/**
 * Provides controller functionality for application settings.
 */
public class AppSettingsConfigurable implements Configurable {

    private AppSettingsComponent slugifySettingsComponent;

    // A default constructor with no arguments is required because this implementation
    // is registered as an applicationConfigurable EP

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "Slugify Settings";
    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        return slugifySettingsComponent.getPreferredFocusedComponent();
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        slugifySettingsComponent = new AppSettingsComponent();
        return slugifySettingsComponent.getPanel();
    }

    @Override
    public boolean isModified() {
        AppSettingsState settings = AppSettingsState.getInstance();
        boolean modified = !slugifySettingsComponent.getApiKeyText().equals(settings.apiKey);
        modified |= slugifySettingsComponent.getEnabled() != settings.enabled;
        modified |= slugifySettingsComponent.getLanguage() != settings.language;
        return modified;
    }

    @Override
    public void apply() {
        AppSettingsState settings = AppSettingsState.getInstance();
        settings.apiKey = slugifySettingsComponent.getApiKeyText();
        settings.enabled = slugifySettingsComponent.getEnabled();
        settings.language = slugifySettingsComponent.getLanguage();
    }

    @Override
    public void reset() {
        AppSettingsState settings = AppSettingsState.getInstance();
        slugifySettingsComponent.setApiKeyText(settings.apiKey);
        slugifySettingsComponent.setEnabled(settings.enabled);
        slugifySettingsComponent.setLanguage(settings.language);
    }

    @Override
    public void disposeUIResources() {
        slugifySettingsComponent = null;
    }

}