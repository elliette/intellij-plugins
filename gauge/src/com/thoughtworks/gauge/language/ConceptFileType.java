/*
 * Copyright (C) 2020 ThoughtWorks, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.thoughtworks.gauge.language;

import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.LanguageFileType;
import com.thoughtworks.gauge.GaugeBundle;
import com.thoughtworks.gauge.GaugeConstants;
import icons.GaugeIcons;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public final class ConceptFileType extends LanguageFileType {
  public static final FileType INSTANCE = new ConceptFileType();

  private ConceptFileType() {
    super(Concept.INSTANCE);
  }

  @Override
  public @NotNull String getName() {
    return "Concept";
  }

  @Override
  public @NotNull String getDescription() {
    return GaugeBundle.message("filetype.gauge.concept.description");
  }

  @Override
  public @NotNull String getDefaultExtension() {
    return GaugeConstants.CONCEPT_EXTENSION;
  }

  @Override
  public Icon getIcon() {
    return GaugeIcons.Gauge;
  }
}
