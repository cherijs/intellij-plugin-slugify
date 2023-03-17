package com.github.cherijs.intellijpluginslugify;

import com.intellij.openapi.options.Configurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

public class SlugifySettingsProvider implements Configurable {

    private JPanel settingsPanel;
    private JTextField apiKeyTextField;
    private JLabel apiKeyLabel;
    private GridBagConstraints constraints;
    private JSeparator separator;

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "Slugify Settings";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        // Create the UI panel for the settings
        settingsPanel = new JPanel(new GridBagLayout());

        // Create the API key label and text field
        apiKeyLabel = new JLabel("API Key:");
        apiKeyTextField = new JTextField();

        // Set the API key label to its minimum width and add right padding
        apiKeyLabel.setMinimumSize(apiKeyLabel.getPreferredSize());
        apiKeyLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

        // Add the API key label and text field to the panel
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0.0;
        constraints.fill = GridBagConstraints.NONE;
        settingsPanel.add(apiKeyLabel, constraints);

        constraints.gridx = 1;
        constraints.weightx = 1.0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        settingsPanel.add(apiKeyTextField, constraints);

        // Set the size of each half of the panel
        settingsPanel.setPreferredSize(new Dimension(500, 50));
        settingsPanel.setMinimumSize(settingsPanel.getPreferredSize());

        // Set the anchor of the panel to the top of the parent container
        GridBagConstraints parentConstraints = new GridBagConstraints();
        parentConstraints.gridx = 0;
        parentConstraints.gridy = 0;
        parentConstraints.weightx = 1.0;
        parentConstraints.weighty = 0.0;
        parentConstraints.fill = GridBagConstraints.HORIZONTAL;
        parentConstraints.anchor = GridBagConstraints.NORTH; // set the anchor to north

        JPanel parentPanel = new JPanel(new BorderLayout());
        parentPanel.add(settingsPanel, BorderLayout.NORTH);
        parentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        return parentPanel;
    }


    @Override
    public boolean isModified() {
        // Check if the settings have been modified
        String apiKey = apiKeyTextField.getText();
        return !apiKey.equals(SlugifySettings.getInstance().getApiKey());
    }

    @Override
    public void apply() {
        // Save the settings
        String apiKey = apiKeyTextField.getText();
        // Save the API key to the plugin settings
        SlugifySettings.getInstance().setApiKey(apiKey);
    }

    @Override
    public void reset() {
        // Load the settings from the plugin settings
        String apiKey = SlugifySettings.getInstance().getApiKey();
        apiKeyTextField.setText(apiKey);
    }

}
