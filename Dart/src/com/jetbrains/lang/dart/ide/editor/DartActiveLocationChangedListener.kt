// Copyright 2000-2025 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package com.jetbrains.lang.dart.ide.editor

import com.google.gson.JsonObject
import com.intellij.openapi.editor.event.CaretEvent
import com.intellij.openapi.editor.event.CaretListener
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.project.Project
import com.jetbrains.lang.dart.ide.toolingDaemon.DartToolingDaemonService
import java.nio.file.InvalidPathException
import java.nio.file.Paths

class DartActiveLocationChangedListener(private val project: Project) : CaretListener {

  override fun caretPositionChanged(event: CaretEvent) {
    val line = event.caret?.logicalPosition?.line ?: return
    val column = event.caret?.logicalPosition?.column ?: return
    val virtualFile = FileDocumentManager.getInstance().getFile(event.editor.document) ?: return
    val fileUri = toFileUri(virtualFile.path) ?: return

    println("Caret moved to line: $line, column: $column in $fileUri")
    sendActiveLocationChangedEvent(fileUri, line, column)
  }

  private fun sendActiveLocationChangedEvent(fileUri: String, line: Int, column: Int) {
    // Construct the event.
    val params = JsonObject()
    params.addProperty("eventKind", "activeLocationChanged")
    params.addProperty("streamId", "Editor")
    val eventData = JsonObject()
    // TODO: add event data
    params.add("eventData", eventData)

    // Send the event.
    val dtdService = DartToolingDaemonService.getInstance(project)
    dtdService.sendRequest("postEvent", params, false
    ) {
      // TODO: handle response
    }
  }

  /**
   * Converts a file path string into a file URI string (e.g., "file:///path/to/file").
   * This method handles platform differences and encodes special characters.
   *
   * @param filePath The raw file path string (e.g., "/Users/user/file.txt").
   * @return The file URI string, or null if the input path is invalid.
   */
  private fun toFileUri(filePath: String?): String? {
    if (filePath == null || filePath.trim { it <= ' ' }.isEmpty()) {
      return null
    }
    try {
      val path = Paths.get(filePath)
      val uri = path.toUri()
      return uri.toString()
    }
    catch (e: InvalidPathException) {
      return null
    }
    catch (e: Exception) {
      return null
    }
  }
}
