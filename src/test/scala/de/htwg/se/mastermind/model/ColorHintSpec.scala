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
      "have a full name" in {
        new ColorHint().fullName should be("empty")
      }
      "have zero as name" in {
        new ColorHint().name should be("0")
      }
    }
    "is valid" should {
      val valid = ColorHint("rightColAndPos")
      "be true" in {
        valid.isValidColor(valid.name)
      }
    }
    "is a valid color" should {
      val rightCol = ColorHint("rightCol")
      "should be displayed as this string" in {
        rightCol.toString should be("o")
      }
    }
    "when not valid" should {
      val notValidColor = ColorHint("notvalid")
      "not be valid" in {
        notValidColor.isValidColor(notValidColor.name) should be(false)
      }
      "return a whitespace as name when formatted as string" in {
        notValidColor.toString should be(" ")
      }
      "return an empty string as full name" in {
        notValidColor.fullName should be("")
      }
    }
  }
}