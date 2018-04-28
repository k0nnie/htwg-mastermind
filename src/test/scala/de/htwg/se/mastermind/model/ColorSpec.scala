package de.htwg.se.mastermind.model

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class ColorSpec extends WordSpec with Matchers {
  "A Color" when {
    "having a valid name" should {
      val redColor = Color("r")
      "return that value" in {
        redColor.getAvailableColors should contain("r")
      }
    }
    "is valid" should {
      val redColor = Color("r")
      "be true" in {
        redColor.isValidColor("r")
      }
    }
    "is a valid color" should {
      val pinkColor = Color("p")
      "have a long color name" in {
        pinkColor.fullName should be("pink")
      }
    }
    "getting a random color" should {
      val randomColor = Color("0").randomColorString()
      "not be empty anymore" in {
        randomColor should not be "0"
      }
    }
    "being empty" should {
      val emptyColor = new Color()
      "contain a zero as value" in {
        emptyColor.name should be("0")
      }
      "have a full name \"empty\""  in {
        emptyColor.fullName should be("empty")
      }
    }
    "being initialized" should {
      val color = Color("y")
      "have a name" in {
        color.name should be("y")
      }
    }
  }
}