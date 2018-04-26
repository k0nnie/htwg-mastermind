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
        emptyPeg.emptyColor(Color("g")) should be(false)
      }
    }
    "set to a specific value" should {
      val nonEmptyPeg = Peg("g")
      "return that value" in {
        nonEmptyPeg.color should be("g")
      }
      "be set" in {
        nonEmptyPeg.emptyColor(Color(" ")) should be(true)
      }
    }
  }

}
