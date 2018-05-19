package de.htwg.se.mastermind.model

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class PegSpec extends WordSpec with Matchers {

  "A Peg" when {
    "not set to any value " should {
      val emptyPeg = Peg("0")
      "be an empty string" in {
        emptyPeg.color should be("0")
      }
      "not be set" in {
        emptyPeg.emptyColor(Color("2")) should be(false)
      }
      "should contain a free space when using toString" in {
        emptyPeg.toString should be(" ")
      }
    }
    "set to a specific value" should {
      val nonEmptyPeg = Peg("2")
      "return that value" in {
        nonEmptyPeg.color should be("2")
      }
      "be set" in {
        nonEmptyPeg.emptyColor(Color(" ")) should be(true)
      }
      "updatd with a new color" in {
        val updatedPeg = nonEmptyPeg.updateColor("7")
        updatedPeg.color should be("7")
      }
    }
  }

}
