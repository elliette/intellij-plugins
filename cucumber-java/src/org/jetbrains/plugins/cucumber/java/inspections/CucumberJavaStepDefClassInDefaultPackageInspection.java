// Copyright 2000-2024 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package org.jetbrains.plugins.cucumber.java.inspections;

import com.intellij.codeInspection.AbstractBaseJavaLocalInspectionTool;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.cucumber.java.CucumberJavaBundle;
import org.jetbrains.plugins.cucumber.java.CucumberJavaUtil;

public final class CucumberJavaStepDefClassInDefaultPackageInspection extends AbstractBaseJavaLocalInspectionTool {

  @Override
  public @NotNull String getShortName() {
    return "CucumberJavaStepDefClassInDefaultPackage";
  }

  @Override
  public @NotNull PsiElementVisitor buildVisitor(final @NotNull ProblemsHolder holder, boolean isOnTheFly) {
    return new CucumberJavaStepDefClassIsPublicVisitor(holder);
  }

  static class CucumberJavaStepDefClassIsPublicVisitor extends JavaElementVisitor {
    final ProblemsHolder holder;

    CucumberJavaStepDefClassIsPublicVisitor(final ProblemsHolder holder) {
      this.holder = holder;
    }

    @Override
    public void visitClass(@NotNull PsiClass aClass) {
      if (!CucumberJavaUtil.isStepDefinitionClass(aClass)) {
        return;
      }
      PsiFile containingFile = aClass.getContainingFile();
      if (containingFile instanceof PsiClassOwner javaFile) {
        final String packageName = javaFile.getPackageName();
        if (StringUtil.isEmpty(packageName)) {
          PsiElement elementToHighlight = aClass.getNameIdentifier();
          if (elementToHighlight != null) {
            holder.registerProblem(elementToHighlight,
                                   CucumberJavaBundle.message("cucumber.java.inspections.step.def.class.in.default.package.message")
            );
          }
        }
      }
    }
  }
}
