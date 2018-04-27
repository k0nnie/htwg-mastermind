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
        emptyTurn.pegString should be("Vector(Color( ), Color( ), Color( ), Color( ))")
      }
    }
    "set to a valid peg color" should {
      val nonEmptyTurn = Turn(Vector(Peg[Color](Color("g"))))
      "return that vector" in {
        nonEmptyTurn.pegString should be("Vector(Color(g))")
      }
    }
//    "replace pegs " should {
//      val turn = new Turn(4)
//      "replace empty pegs" in {
//        turn.replacePegs(Vector(Color("g"), Color("b"), Color("y"), Color("p"))) should be("Vector(Color(g), Color(b), Color(y), Color(p))")
//      }
//    }
  }
}
