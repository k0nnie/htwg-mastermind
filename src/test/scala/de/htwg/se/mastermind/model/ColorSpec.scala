package de.htwg.se.mastermind.model

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class ColorSpec extends WordSpec with Matchers {
  "A Color" when {
    "have a name from the sequence" should {
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
        pinkColor.colorName should be("pink")
      }
    }
  }
}