package de.htwg.se.mastermind.model

import org.junit.runner.RunWith
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class BoardSpec extends WordSpec with Matchers {
  "A Board is the playingfield of Mastermind. A Board" when {
    "newly created" should {
      val emptyBoard = new Board()
      "contain a solution" in {
        emptyBoard.solution.nonEmpty should be(true)
      }
      "have a solution of 4 random pegs" in {
        emptyBoard.solution.size should be(4)
      }
    }
    "played first round" should {
      val emptyBoard = new Board()
      val colVec = Vector[Color](Color("g"), Color("g"), Color("g"), Color("g"))
      val nonEmptyBoard = emptyBoard.replaceRound(0, colVec)
      "be replaced with a round with a vector of 4 pegs for a given round" in {
        nonEmptyBoard.rounds(0).turn.toString should be("Turn(Vector(g, g, g, g))")
      }
    }
    "called with a given solution" should {
      val solution = Vector[Color](Color("r"), Color("g"), Color("b"), Color("v"))
      val boardWithSolution = Board(Vector.fill(8)(new Round()), solution)
      val colVec = Vector[Color](Color("g"), Color("g"), Color("g"), Color("g"))
      val newBoard = boardWithSolution.replaceRound(0, colVec)
      "have this solution" in {
        boardWithSolution.solution.toString() should be("Vector(r, g, b, v)")
      }
      "give back these hints" in {
        newBoard.createHints(solution, colVec).toString() should be("Vector(o,  ,  ,  )")
      }
      "should display the right hint if same colors are inputted more than once" in {
        val newInput = Vector[Color](Color("r"), Color("r"), Color("r"), Color("r"))
        val newRound = boardWithSolution.replaceRound(1, newInput)
        newRound.rounds(1).turnHint.pegs.toString() should be("Vector(+,  ,  ,  )")
      }
    }
    "with rounds and solution constructor params" should {
      val round = new Round()
      val rounds = Vector(round, round, round, round)
      val board = new Board(rounds)
      "have four rounds" in {
        board.rounds.size should be(4)
      }
    }
    "with default constructor" should {
      val round = new Round()
      val rounds = Vector(round, round, round, round)
      val solution = Vector[Color](Color("r"), Color("g"), Color("b"), Color("v"))
      val board = Board(rounds, solution)
      "should have four rounds" in {
        board.rounds.size should be(4)
      }
      "have a solution with four pegs" in {
        board.solution.size should be(4)
      }
    }
    "printed on console" should {
      val board = new Board()
      "have this output" in {
        board.toString.startsWith("\n+---------+---------+") should be(true)
      }
    }
  }
}
