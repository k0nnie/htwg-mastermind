package de.htwg.se.mastermind.model

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class RoundSpec extends WordSpec with Matchers {

  "A Round " when {
    "empty " should {
      val emptyRound = Round(new Turn(), new TurnHint())
      "have an empty turn" in {
        emptyRound.turn.pegString should be("Vector( ,  ,  ,  )")
      }
      "have that turn size" in {
        emptyRound.turnSize should be(4)
      }
      "have an empty Vector of turnHint" in {
        emptyRound.turnHint.pegString should be("Vector( ,  ,  ,  )")
      }
      "have that turnHint size" in {
        emptyRound.turnHintSize should be(4)
      }
    }
    "set to a valid peg color" should {
      val nonEmptyTurn = Turn(Vector(Peg[Color](Color("g"))))
      "return that vector" in {
        nonEmptyTurn.pegString should be("Vector(g)")
      }
    }
  }
}
