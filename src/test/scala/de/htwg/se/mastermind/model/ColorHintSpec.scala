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
      val emptyCol = ColorHint("0")
      "return that value" in {
        emptyCol.fullName should be("empty")
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
      val notValidColor = Color("notvalid")
      "not be valid" in {
        notValidColor.isValidColor(notValidColor.name) should be(false)
      }
      "return an empty string" in {
        notValidColor.fullName should be("")
      }
    }
  }
}