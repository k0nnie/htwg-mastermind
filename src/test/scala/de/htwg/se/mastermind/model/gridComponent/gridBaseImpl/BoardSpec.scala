package de.htwg.se.mastermind.model.gridComponent.gridBaseImpl

import de.htwg.se.mastermind.model.boardComponent.boardBaseImpl
import de.htwg.se.mastermind.model.boardComponent.boardBaseImpl.{Board, Color, Round}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class BoardSpec extends WordSpec with Matchers {
  "A Board is the playingfield of Mastermind. A Board" when {
    "newly created" should {
      val emptyBoard = new Board(4,10)
      "contain a solution" in {
        emptyBoard.solution.nonEmpty should be(true)
      }
      "have a solution of 4 random pegs" in {
        emptyBoard.solution.size should be(4)
      }
      "check number of pegs" in {
        Board.checkNumOfPegs(4) should be(4)
        Board.checkNumOfPegs(5) should be(5)
        Board.checkNumOfPegs(6) should be(6)
        Board.checkNumOfPegs(7) should be(7)
        Board.checkNumOfPegs(8) should be(8)
        Board.checkNumOfPegs(9) should be(8)
        Board.checkNumOfPegs(10) should be(8)
      }
    }
    "played first round" should {
      val emptyBoard = new Board(4, 10)
      val colVec = Vector[Color](Color("3"), Color("3"), Color("3"), Color("3"))
      val nonEmptyBoard = emptyBoard.replaceRound(0, colVec)
      "be replaced with a round with a vector of 4 pegs for a given round" in {
        nonEmptyBoard.rounds(0).turn.toString should be("Turn(Vector(3, 3, 3, 3))")
      }
    }
    "called with a given solution" should {
      val solution = Vector[Color](Color("1"), Color("2"), Color("5"), Color("6"))
      val boardWithSolution = boardBaseImpl.Board(Vector.fill(10)(new Round(4)), solution, 0)
      val colVec = Vector[Color](Color("2"), Color("2"), Color("2"), Color("2"))
      val newBoard = boardWithSolution.replaceRound(0, colVec)
      "have this solution" in {
        boardWithSolution.solution.toString() should be("Vector(1, 2, 5, 6)")
      }
      "give back these hints" in {
        newBoard.createHints(solution, colVec).toString() should be("Vector(+,  ,  ,  )")
      }
      "display the right hint if same colors are inputted more than once" in {
        val newInput = Vector[Color](Color("1"), Color("1"), Color("1"), Color("1"))
        val newRound = boardWithSolution.replaceRound(1, newInput)
        newRound.rounds(1).turnHint.pegs.toString() should be("Vector(+,  ,  ,  )")
      }
    }
    "with default constructor" should {
      val round = new Round(4)
      val rounds = Vector(round, round, round, round)
      val solution = Vector[Color](Color("1"), Color("2"), Color("3"), Color("4"))
      val board = boardBaseImpl.Board(rounds, solution, 0)
      "have four rounds" in {
        board.rounds.size should be(4)
      }
      "have a solution with four pegs" in {
        board.solution.size should be(4)
      }
    }
    "printed on console" should {
      val board = new Board(4, 10)
      "start with this output" in {
        board.toString.startsWith("\n+---------+---------+") should be(true)
      }
    }
    "tested for game logic" should {
      val solution = Vector[Color](Color("5"), Color("6"), Color("4"), Color("8"))
      val boardWithSolution = boardBaseImpl.Board(Vector.fill(8)(new Round(4)), solution, 0)
      "give back these hints when one color and position is guessed correctly" in {
        val colVec = Vector[Color](Color("5"), Color("5"), Color("5"), Color("5"))
        val newBoard = boardWithSolution.replaceRound(0, colVec)
        newBoard.createHints(solution, colVec).toString() should be("Vector(+,  ,  ,  )")
      }
      "give back these hints when two colors and positions are guessed correctly" in {
        val colVec = Vector[Color](Color("5"), Color("6"), Color("5"), Color("6"))
        val newBoard = boardWithSolution.replaceRound(0, colVec)
        newBoard.createHints(solution, colVec).toString() should be("Vector(+, +,  ,  )")
      }
      "give back these hints when two pegs have correct color and position and two pegs have correct color" in {
        val colVec = Vector[Color](Color("8"), Color("6"), Color("4"), Color("5"))
        val newBoard = boardWithSolution.replaceRound(0, colVec)
        newBoard.createHints(solution, colVec).toString() should be("Vector(+, +, o, o)")
      }
      "give back these hints when one color and position are guessed correctly" in {
        val colVec = Vector[Color](Color("2"), Color("2"), Color("4"), Color("4"))
        val newBoard = boardWithSolution.replaceRound(0, colVec)
        newBoard.createHints(solution, colVec).toString() should be("Vector(+,  ,  ,  )")
      }
      "give back these hints two colors have correct color and position" in {
        val colVec = Vector[Color](Color("2"), Color("2"), Color("4"), Color("8"))
        val newBoard = boardWithSolution.replaceRound(0, colVec)
        newBoard.createHints(solution, colVec).toString() should be("Vector(+, +,  ,  )")
      }
    }
    "solved before last round" should {
      val solution = Vector[Color](Color("1"), Color("2"), Color("3"), Color("4"))
      var boardWithSolution = boardBaseImpl.Board(Vector.fill(solution.size)(new Round(4)), solution, 0)
      val colVec = Vector[Color](Color("1"), Color("2"), Color("3"), Color("4"))
      val newBoard = boardWithSolution.replaceRound(0, colVec)
      "have this solution" in {
        boardWithSolution.solution.toString() should be("Vector(1, 2, 3, 4)")
      }
      "have four hints for right color and position" in {
        newBoard.createHints(solution, colVec).toString() should be("Vector(+, +, +, +)")
      }
      "set pegs correctly" in {
        var board = boardWithSolution.set(0, 5)
        board.rounds(0).turn.pegs.toString should be("Vector(5,  ,  ,  )")
        board = board.set(0, 5)
        board.rounds(0).turn.pegs.toString should be("Vector(5, 5,  ,  )")
      }
      "return true if peg with given round and column is empty" in {
        var board = boardWithSolution.set(0, 0)
        board.rounds(0).turn.pegs(0).emptyColor should be(true)
      }
      "return false if peg with given round and column is empty" in {
        var board = boardWithSolution.set(0, 5)
        board.rounds(0).turn.pegs(0).emptyColor should be(false)
      }
      "do nothing if index is -1 (index is out of bounds)" in {
        var board = new Board(1, 1)
        board.rounds(0).turn.pegs.toString() should be("Vector( )")
        var testVal = ""
        try {
          board = boardWithSolution.set(-1, 5)
        } catch {
          case e: IndexOutOfBoundsException => testVal = "do nothing in this case"
        }
        testVal should be("do nothing in this case")
        board.rounds(0).turn.pegs.toString() should be("Vector( )")
      }
    }
  }
}
