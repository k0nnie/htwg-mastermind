package de.htwg.se.mastermind.model.gridComponent.gridBaseImpl

import de.htwg.se.mastermind.model.boardComponent.boardBaseImpl
import de.htwg.se.mastermind.model.boardComponent.boardBaseImpl.{Board, Color, Round, Solver}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class SolverSpec extends WordSpec with Matchers {

  "A Solver" when {
    "a board is empty" should {
      val emptyBoard = new Board()
      "solve a board without any problems" in {
        val solvedBoard = new Solver(emptyBoard).solve
        solvedBoard.isSolved should be(true)
      }
    }
  }
  "Board is not empty" should {
    val solution = Vector[Color](Color("1"), Color("2"), Color("5"), Color("6"))
    val boardWithSolution = boardBaseImpl.Board(Vector.fill(Board.NumberOfRounds)(new Round()), solution)
    val colVec = Vector[Color](Color("2"), Color("2"), Color("2"), Color("2"))
    val newBoard = boardWithSolution.replaceRound(0, colVec)
    "solve a board without any problems" in {
      newBoard.isSolved should be(false)
      val solvedBoard = new Solver(newBoard).solve
      solvedBoard.isSolved should be(true)
    }
  }
}
