package de.htwg.se.mastermind.model.gridComponent.gridBaseImpl

import de.htwg.se.mastermind.model.boardComponent.boardBaseImpl._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class RoundSpec extends WordSpec with Matchers {

  "A Round " when {
    "empty " should {
      val emptyRound = Round(new Turn(4), new TurnHint(4))
      "have an empty turn" in {
        emptyRound.turn.pegs.toString() should be("Vector( ,  ,  ,  )")
      }
      "have that turn size" in {
        emptyRound.turnSize should be(4)
      }
      "have an empty Vector of turnHint" in {
        emptyRound.turnHint.pegs.toString() should be("Vector( ,  ,  ,  )")
      }
      "have that turnHint size" in {
        emptyRound.turnHintSize should be(4)
      }
    }
    "set to a valid peg color" should {
      val nonEmptyTurn = Turn(Vector(Peg[Color](Color("2"))))
      "return that vector" in {
        nonEmptyTurn.pegs.toString() should be("Vector(2)")
      }
    }
  }
}
