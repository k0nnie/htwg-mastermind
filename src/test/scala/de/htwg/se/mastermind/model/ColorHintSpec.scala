package de.htwg.se.mastermind.model

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class ColorHintSpec extends WordSpec with Matchers {
  "A ColorHint" when {
    "having a valid name" should {
      val rightCol = ColorHint("rightCol")
      "be in the list of available colors" in {
        rightCol.getAvailableColors should contain(rightCol.name)
      }
    }
    "is empty" should {
      "have zero as name" in {
        new ColorHint().name should be("0")
      }
    }
    "is valid color and position" should {
      val rightColAndPos = ColorHint("rightColAndPos")
      "be true" in {
        rightColAndPos.isValidColor(rightColAndPos.name)
      }
    }
    "is a valid color but wrong position" should {
      val rightCol = ColorHint("rightCol")
      "be displayed as as right color hint" in {
        rightCol.toString should be("o")
      }
    }
    "not valid" should {
      val notValidColor = ColorHint("notvalid")
      "not be valid" in {
        notValidColor.isValidColor(notValidColor.name) should be(false)
      }
      "return a whitespace as name when formatted as string" in {
        notValidColor.toString should be(" ")
      }
    }
  }
}