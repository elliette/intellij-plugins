package com.intellij.terraform.template

import com.intellij.openapi.application.PathManager
import com.intellij.testFramework.fixtures.BasePlatformTestCase

class TftplFoldingTest : BasePlatformTestCase() {
  override fun getTestDataPath(): String {
    return PathManager.getHomePath() + "/contrib/terraform/terraform-template/test-data/folding"
  }

  fun testIfFolding() {
    myFixture.testFolding("${testDataPath}/if.tftpl")
  }

  fun testForFolding() {
    myFixture.testFolding("${testDataPath}/for.tftpl")
  }

  fun testNestedFolding() {
    myFixture.testFolding("${testDataPath}/nested.tftpl")
  }
}