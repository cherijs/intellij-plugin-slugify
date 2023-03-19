package com.github.cherijs.intellijpluginslugify;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import org.intellij.sdk.liveTemplates.TranslateMacro;

public class TranslateAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent event) {
        Editor editor = event.getData(CommonDataKeys.EDITOR);
        String selectedText = editor != null ? editor.getSelectionModel().getSelectedText() : null;
        if (selectedText != null) {
            // Translate the selected text
            String translatedText = translate(selectedText);
            // Replace the selected text with the translated text
            Runnable replaceAction = () -> editor.getDocument().replaceString(
                    editor.getSelectionModel().getSelectionStart(),
                    editor.getSelectionModel().getSelectionEnd(),
                    translatedText
            );
            WriteCommandAction.runWriteCommandAction(editor.getProject(), replaceAction);
        } else {
            if (editor != null) {
                // Translate the current line
                int caretOffset = editor.getCaretModel().getOffset();
                int lineNumber = editor.getDocument().getLineNumber(caretOffset);
                int startOffset = editor.getDocument().getLineStartOffset(lineNumber);
                int endOffset = editor.getDocument().getLineEndOffset(lineNumber);
                String currentLineText = editor.getDocument().getText().substring(startOffset, endOffset);
                String translatedText = translate(currentLineText);
                // Replace the current line with the translated text
                Runnable replaceAction = () -> editor.getDocument().replaceString(startOffset, endOffset, translatedText);
                WriteCommandAction.runWriteCommandAction(editor.getProject(), replaceAction);
            }
        }
    }


    private String translate(String text) {
//        Messages.showInfoMessage(translatedText, "Translated Line");
        return TranslateMacro.translateText(text);
    }

}
