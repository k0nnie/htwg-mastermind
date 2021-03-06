package de.htwg.se.mastermind.model.gridComponent.gridBaseImpl

import de.htwg.se.mastermind.model.boardComponent.boardBaseImpl.{Color, Peg, Turn}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class TurnSpec extends WordSpec with Matchers {

  "A Turn " when {
    "empty " should {
      val emptyTurn = new Turn(4)
      "have a vector with four empty pegs" in {
        emptyTurn.pegs.toString() should be("Vector( ,  ,  ,  )")
      }
      "should contain an empty color" in {
        emptyTurn.containsEmptyColor should be(true)
      }
    }
    "set to a valid peg color" should {
      val nonEmptyTurn = Turn(Vector(Peg[Color](Color("g"))))
      "return that vector" in {
        nonEmptyTurn.pegs.toString() should be("Vector(g)")
      }
    }
    "replacing colors " should {
      val pegs = Vector(Peg(Color("8")), Peg(Color("4")), Peg(Color("6")), Peg(Color("5")))
      val turn = Turn(pegs)
      "replace empty pegs" in {
        turn.replaceColors(Vector(Color("2"), Color("3"), Color("8"), Color("7")))
        turn.pegs.toString() should be("Vector(8, 4, 6, 5)")
      }
      "not contain an empty color" in {
        turn.containsEmptyColor should be(false)
      }
    }
  }
}
