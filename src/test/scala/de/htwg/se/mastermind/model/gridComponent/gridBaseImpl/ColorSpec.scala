package de.htwg.se.mastermind.model.gridComponent.gridBaseImpl

import de.htwg.se.mastermind.model.boardComponent.boardBaseImpl.Color
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class ColorSpec extends WordSpec with Matchers {
  "A Color" when {
    "having a valid name" should {
      val colorOne = Color("1")
      "return that value" in {
        colorOne.getAvailableColors should contain("1")
      }
    }
    "is valid" should {
      val colorOne = Color("1")
      "be true" in {
        colorOne.isValidColor
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
    }
    "being initialized" should {
      val color = Color("8")
      "have a name" in {
        color.name should be("8")
      }
    }
  }
}