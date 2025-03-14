// Copyright 2000-2025 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package com.jetbrains.lang.dart.ide.toolingDaemon

import com.intellij.openapi.components.ProjectComponent
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.fileEditor.FileEditorManagerEvent
import com.intellij.openapi.fileEditor.FileEditorManagerListener
import com.intellij.openapi.project.Project
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.Disposable

class LocationChangedListener(private val project: Project) : ProjectComponent, Disposable {

  private val fileEditorManagerListener = object : FileEditorManagerListener {
    override fun selectionChanged(event: FileEditorManagerEvent) {
      println("selection was changed")
      val newEditor: Editor? = event.newEditor?.let { FileEditorManager.getInstance(project).getSelectedTextEditor() }
      val oldEditor: Editor? = event.oldEditor?.let { FileEditorManager.getInstance(project).getSelectedTextEditor() }

      // Example: Display a message in the console with the file name
      if (newEditor != null) {
        println("Selected editor changed to: ${newEditor.virtualFile.name}")
        // Example: Get the current line number
        val caretModel = newEditor.caretModel
        val lineNumber = caretModel.logicalPosition.line
        println("Current line number: $lineNumber")

        //Example: Get the selected text
        val selectedText = newEditor.selectionModel.selectedText
        if (selectedText != null) {
          println("Selected text: $selectedText")
        }
      } else {
        println("No editor selected")
      }

      if(oldEditor != null){
        println("Previous editor was: ${oldEditor.virtualFile.name}")
      }
    }
  }

  override fun projectOpened() {
    println("Project was opened!!")
    project.messageBus.connect(this).subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, fileEditorManagerListener)
  }

  override fun projectClosed() {}

  override fun dispose() {}

  override fun initComponent() {}

  override fun disposeComponent() {}

  override fun getComponentName(): String {
    return "LocationChangedListener"
  }
}