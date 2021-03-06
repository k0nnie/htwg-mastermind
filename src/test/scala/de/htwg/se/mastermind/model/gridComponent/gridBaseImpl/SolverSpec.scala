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
      val emptyBoard = new Board(4, 10)
      "solve a board without any problems" in {
        emptyBoard.isSolved should be(false)
        val solvedBoard = new Solver(emptyBoard).solve
        solvedBoard.isSolved should be(true)
      }
    }
  }
  "board is not empty" should {
    val solution = Vector[Color](Color("1"), Color("2"), Color("5"), Color("6"))
    val boardWithSolution = boardBaseImpl.Board(Vector.fill(solution.size)(new Round(4)), solution, 0)
    val colVec = Vector[Color](Color("2"), Color("2"), Color("2"), Color("2"))
    val newBoard = boardWithSolution.replaceRound(0, colVec)
    "solve a board without any problems" in {
      newBoard.isSolved should be(false)
      val solvedBoard = new Solver(newBoard).solve
      solvedBoard.isSolved should be(true)
    }
  }
}
