package de.htwg.se.mastermind.model

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class TurnSpec extends WordSpec with Matchers {

  "A Turn " when {
    "empty " should {
      val emptyTurn = new Turn()
      "have a vector with four empty pegs" in {
        emptyTurn.pegs.toString() should be("Vector( ,  ,  ,  )")
      }
    }
    "set to a valid peg color" should {
      val nonEmptyTurn = Turn(Vector(Peg[Color](Color("g"))))
      "return that vector" in {
        nonEmptyTurn.pegs.toString() should be("Vector(g)")
      }
    }
    "replacing colors " should {
      val pegs = Vector(Peg(Color("y")), Peg(Color("v")), Peg(Color("o")), Peg(Color("w")))
      val turn = Turn(pegs)
      "replace empty pegs" in {
        turn.replaceColors(Vector(Color("g"), Color("b"), Color("y"), Color("p")))
        turn.pegs.toString() should be("Vector(y, v, o, w)")
      }
    }
  }
}
