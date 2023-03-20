# Translate and Slugify Expressions

![Build](https://github.com/cherijs/intellij-plugin-slugify/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/21270.svg)](https://plugins.jetbrains.com/plugin/21270)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/21270.svg)](https://plugins.jetbrains.com/plugin/21270)

<!-- Plugin description -->
Easily convert and translate text on-the-fly, making it a valuable tool for any developer who needs to work with international content.

This plugin provides several useful features for developers.

It includes the following liveTemplate expressions: `slugify(String)`, `translate(String)`, and `translate_slugify(String)`.

The `slugify` expression converts the selected text to a URL-friendly format, while the `translate` expression utilizes the Google Translate API to translate the selected text to any language.

The `translate_slugify` expression combines both functionalities from translate and slugify to produce URL-friendly translated text.

In addition to the liveTemplate expressions, this plugin also adds `slugify`, `translate_slugify`, and `translate` entries to the **Preferences | Editor | Live Templates | HTML/XML**.

And now, with the new update, you can also use the shortcut `ctrl+shift+T` to quickly translate the selected text to any language.

<!-- Plugin description end -->

## Installation

- Using IDE built-in plugin system:
  
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "Translate and Slugify Expressions"</kbd> >
  <kbd>Install Plugin</kbd>
  
- Manually:

  Download the [latest release](https://github.com/cherijs/intellij-plugin-slugify/releases/latest) and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>

## Setup translation

- To enable google translation expression, go to <kbd>Preferences</kbd> | <kbd>Tools</kbd> | <kbd>Slugify Settings</kbd>, enable translation, add **API-KEY** and select target language.

---
Plugin based on the [IntelliJ Platform Plugin Template][template].

[template]: https://github.com/JetBrains/intellij-platform-plugin-template
[docs:plugin-description]: https://plugins.jetbrains.com/docs/intellij/plugin-user-experience.html#plugin-description-and-presentation