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
      val pegs = Vector(Peg(Color("8")), Peg(Color("4")), Peg(Color("6")), Peg(Color("5")))
      val turn = Turn(pegs)
      "replace empty pegs" in {
        turn.replaceColors(Vector(Color("2"), Color("3"), Color("8"), Color("7")))
        turn.pegs.toString() should be("Vector(8, 4, 6, 5)")
      }
    }
  }
}
