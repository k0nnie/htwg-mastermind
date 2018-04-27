package de.htwg.se.mastermind.model

import org.junit.runner.RunWith
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class BoardSpec extends WordSpec with Matchers {
  "A Board is the playingfield of Mastermind. A Board" when {
    "when newly created" should {
      val emptyBoard = new Board(Vector(Color("r"), Color("g"), Color("b"), Color("p")))
      "consist of a vector with 7 empty rounds" in {
        emptyBoard.rounds should be(Vector.fill(7)(new Round()))
      }
      "should contain a solution" in {
        emptyBoard.solution.nonEmpty should be(true)
      }
      "should have a solution of 4 pegs" in {
        emptyBoard.solution.size should be(4)
      }
      "should have the solution" in {
        emptyBoard.solution.toString should be("Vector(Color(r), Color(g), Color(b), Color(p))")
      }
    }
    "when played first round" should {
      val emptyBoard = new Board(Vector(Color("r"), Color("g"), Color("b"), Color("p")))
      val colVec = Vector[Color](Color("g"), Color("g"), Color("g"), Color("g"))
      val nonEmptyBoard = emptyBoard.replaceRound(0, colVec)
      "can be replaced with a round with a vector of 4 pegs for a given round" in {
        nonEmptyBoard.rounds(0).turn.toString should be("Turn(Vector(Color(g), Color(g), Color(g), Color(g)))")
      }
//      "when replaced, should have hints" in {
//        nonEmptyBoard.rounds(0).turnHint.toString should be("TurnHint(Vector(ColorHint( ), ColorHint(rightColAndPos), ColorHint( ), ColorHint( )))")
//      }
    }
  }
}
