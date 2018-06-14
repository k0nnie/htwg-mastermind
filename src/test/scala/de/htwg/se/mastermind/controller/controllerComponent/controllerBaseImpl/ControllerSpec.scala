package de.htwg.se.mastermind.controller.controllerComponent.controllerBaseImpl

import de.htwg.se.mastermind.controller.controllerComponent.GameStatus
import de.htwg.se.mastermind.model.boardComponent.boardBaseImpl.{Board, Color, Round}
import de.htwg.se.mastermind.util.Observer
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class ControllerSpec extends WordSpec with Matchers {
  "A Controller" when {
    "observed by an Observer" should {
      val solution = Vector[Color](Color("5"), Color("6"), Color("7"), Color("8"))
      val rounds = Vector.fill(Board.NumberOfRounds)(new Round())
      val board = Board(rounds, solution)
      val controller = new Controller(board)
      val observer = new Observer {
        var updated: Boolean = false

        def isUpdated: Boolean = updated

        override def update(): Unit = updated = true
      }
      "print out this solution on console" in {
        controller.solutionToString() should be("solution: 5, 6, 7, 8")
      }
      "notify its Observer after replacing a round" in {
        controller.set(0, Vector[Color](Color("1")))
        controller.set(0, Vector[Color](Color("1")))
        controller.set(0, Vector[Color](Color("1")))
        controller.set(0, Vector[Color](Color("1")))
        controller.board.rounds(0).turn.pegs.toString() should be("Vector(1, 1, 1, 1)")
      }
      "return correctly if game is solved or not solved yet" in {
        controller.set(1, Vector[Color](Color("5")))
        controller.set(1, Vector[Color](Color("6")))
        controller.set(1, Vector[Color](Color("7")))
        controller.set(1, Vector[Color](Color("8")))
        controller.roundIsSolved(0) should be(false)
        controller.roundIsSolved(1) should be(true)
      }
      "clear a round if needed" in {
        controller.clearRound(0).rounds(0).turn.pegs.toString() should be("Vector( ,  ,  ,  )")
      }
      "adding a color on GUI" in {
        controller.clearRound(0)
        controller.clearRound(1)
        controller.set(2, Vector[Color](controller.mapFromGuiColor(java.awt.Color.BLUE)))
        controller.board.rounds(2).turn.pegs.toString should be("Vector(2,  ,  ,  )")
      }
      "mapping a GUI color to color" in {
        val guiColor = java.awt.Color.BLUE
        val color = controller.mapFromGuiColor(guiColor)
        color should be(Color("2"))
      }
      "mapping a color to GUI color" in {
        val color = Color("2")
        val guiColor = controller.mapToGuiColor(color)
        guiColor should be(java.awt.Color.BLUE)
      }
      "getting a guessed color" in {
        controller.getGuessColor(0,0).toString should be("java.awt.Color[r=255,g=175,b=175]")
      }
      "getting the color for a hint" in {
        controller.getHintColor(0,0).toString should be("java.awt.Color[r=192,g=192,b=192]")
      }
      "solve a board" in {
        controller.solve()
        controller.board.isSolved should be(true)
      }
      "give back a board as string" in {
        controller.boardToString should startWith("\n+---------+---------+")
      }
      "give back current round index" in {
        var board2 = new Board()
        val controller2 = new Controller(board2)

        controller2.getCurrentRoundIndex should be (0)

        controller2.set(0, Vector[Color](Color("1")))
        controller2.set(0, Vector[Color](Color("1")))
        controller2.set(0, Vector[Color](Color("1")))
        controller2.set(0, Vector[Color](Color("1")))
        controller2.getCurrentRoundIndex should be (1)

        controller2.set(1, Vector[Color](Color("5")))
        controller2.set(1, Vector[Color](Color("6")))
        controller2.set(1, Vector[Color](Color("7")))
        controller2.set(1, Vector[Color](Color("8")))
        controller2.getCurrentRoundIndex should be (2)
      }
    }
    "empty" should {
      val controller = new Controller(new Board())
      "handle undo/redo of solving a grid correctly" in {
        controller.board.rounds(0).turn.containsEmptyColor should be(true)
        controller.board.isSolved should be(false)
        controller.solve()
        controller.board.rounds(controller.numberOfRounds-1).turn.containsEmptyColor should be(false)
        controller.board.isSolved should be(true)
        controller.board.isSolved(controller.numberOfRounds-1) should be(true)
//        controller.undo()
//        controller.board.rounds(controller.numberOfRounds-1).turn.containsEmptyColor should be(true)
//        controller.board.isSolved should be(false)
//        controller.board.isSolved(controller.numberOfRounds-1) should be(false)
//        controller.redo()
//        controller.board.rounds(controller.numberOfRounds-1).turn.containsEmptyColor should be(false)
//        controller.board.isSolved should be(true)
//        controller.board.isSolved(controller.numberOfRounds-1) should be(true)
      }
      "print out a message of game status" in {
        controller.createEmptyBoard()
        GameStatus.message(controller.gameStatus) should be("A new game was created")
      }
    }
  }
}
