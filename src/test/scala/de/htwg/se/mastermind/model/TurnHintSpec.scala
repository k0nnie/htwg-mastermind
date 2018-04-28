package de.htwg.se.mastermind.model

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class TurnHintSpec extends WordSpec with Matchers {

  "A TurHint " when {
    "empty " should {
      val emptyTurnHint = new TurnHint()
      "have a vector with four empty pegs" in {
        emptyTurnHint.pegString should be("Vector( ,  ,  ,  )")
      }
    }
    "set to a valid peg color and position" should {
      val nonEmptyTurnHint = TurnHint(Vector(Peg[ColorHint](ColorHint("rightColAndPos"))))
      "return that vector" in {
        nonEmptyTurnHint.pegString should be("Vector(+)")
      }
    }
    "set to a valid color with wrong position" should {
      val rightCol = TurnHint(Vector(Peg[ColorHint](ColorHint("rightCol"))))
      "return a \"o\"" in {
        rightCol.pegs.toString should be("Vector(o)")
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
