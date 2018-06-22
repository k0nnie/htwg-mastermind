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
      val solution = Vector[Color](Color("5"), Color("6"))
      val rounds = Vector.fill(6)(new Round(2))
      val board = Board(rounds, solution, 0)
      val controller = new Controller(board)
      val observer = new Observer {
        var updated: Boolean = false

        def isUpdated: Boolean = updated

        override def update(): Unit = updated = true
      }
      "notify its Observer after replacing a round" in {
        controller.set(0, 1)
        controller.set(0, 1)
        controller.board.rounds(0).turn.pegs.toString() should be("Vector(1, 1)")
        controller.statusText should be("A peg was set")
      }
      "adding a color on GUI" in {
        controller.set(2, controller.mapFromGuiColor(java.awt.Color.BLUE))
        controller.board.rounds(2).turn.pegs.toString should be("Vector(2,  )")
      }
      "mapping a GUI color to color" in {
        val guiColor = java.awt.Color.BLUE
        val color = controller.mapFromGuiColor(guiColor)
        color should be(2)
      }
      "mapping a color to GUI color" in {
        val color = 2
        val guiColor = controller.mapToGuiColor(color)
        guiColor should be(java.awt.Color.BLUE)
      }
      "not mapping a wrong color to GUI color" in {
        val color = 9
        val guiColor = controller.mapToGuiColor(color)
        guiColor should be(java.awt.Color.GRAY)
      }
      "get a guessed color" in {
        controller.guessColor(0,0).toString should be("java.awt.Color[r=255,g=175,b=175]")
      }
      "get the default color if there is no hint" in {
        controller.hintColor(0,0).toString should be("java.awt.Color[r=192,g=192,b=192]")
      }
      "get the right hint color (black) if a peg has right color and position" in {
        controller.set(3, 5)
        controller.set(3, 5)
        controller.hintColor(3,0).toString should be("java.awt.Color[r=0,g=0,b=0]")
      }
      "get the right hint color (white) if a peg has right color and wrong position" in {
        controller.set(4, 1)
        controller.set(4, 6)
        controller.hintColor(4,0).toString should be("java.awt.Color[r=0,g=0,b=0]")
      }
      "get the default color if turn is not completely set" in {
        controller.set(5, 5)
        controller.board.rounds(5).turn.pegs.toString should be("Vector(5,  )")
        controller.hintColor(5,0).toString should be("java.awt.Color[r=192,g=192,b=192]")
        controller.hintColor(5,1).toString should be("java.awt.Color[r=192,g=192,b=192]")
      }
      "solve a board" in {
        controller.solve()
        controller.board.isSolved should be(true)
      }
      "give back a board as string" in {
        controller.boardToString should startWith("\n+-----+-----+")
      }
      "give back current round index" in {
        var board2 = new Board(1,2)
        val controller2 = new Controller(board2)
        controller2.getCurrentRoundIndex should be (0)
        controller2.set(0, 1)
        controller2.getCurrentRoundIndex should be (1)
      }
    }
    "empty" should {
      val controller = new Controller(new Board(2, 2))
      "handle undo/redo of solving a grid correctly" in {
        controller.board.rounds(0).turn.containsEmptyColor should be(true)
        controller.board.isSolved should be(false)
        controller.solve()
        controller.board.rounds(controller.numberOfRounds-1).turn.containsEmptyColor should be(false)
        controller.board.isSolved should be(true)
        controller.undo()
        controller.board.rounds(controller.numberOfRounds-1).turn.containsEmptyColor should be(true)
        controller.board.isSolved should be(false)
        controller.redo()
        controller.board.rounds(controller.numberOfRounds-1).turn.containsEmptyColor should be(false)
        controller.board.isSolved should be(true)
      }
      "print out a message of game status" in {
        controller.createEmptyBoard()
        GameStatus.message(controller.gameStatus) should be("A new game was created")
      }
    }
    "resizing board" should {
      val controller = new Controller(new Board(4, 10))
      "resize board correctly" in {
        controller.board.rounds.size should be(10)
        controller.board.rounds(0).turnSize should be(4)
        controller.resize(6, 8)
        controller.board.rounds.size should be(8)
        controller.board.rounds(0).turnSize should be(6)
        controller.resize(4, 12)
        controller.board.rounds.size should be(12)
        controller.board.rounds(0).turnSize should be(4)
        controller.resize(7, 7)
        controller.board.rounds.size should be(12)
        controller.board.rounds(0).turnSize should be(4)
        controller.resize(4, 10)
        controller.board.rounds.size should be(10)
        controller.board.rounds(0).turnSize should be(4)
      }
    }
    "create empty board" should {
      val controller = new Controller(new Board(4, 10))
      "create default empty board correctly" in {
        controller.createEmptyBoard()
        controller.board.rounds.size should be(10)
        controller.board.rounds(0).turnSize should be(4)
      }
      val controllerEasy = new Controller(new Board(4, 12))
      "create easy empty board correctly" in {
        controllerEasy.createEmptyBoard()
        controllerEasy.board.rounds.size should be(12)
        controllerEasy.board.rounds(0).turnSize should be(4)
      }
      val controllerHard = new Controller(new Board(6, 8))
      "create hard empty board correctly" in {
        controllerHard.createEmptyBoard()
        controllerHard.board.rounds.size should be(8)
        controllerHard.board.rounds(0).turnSize should be(6)
      }
      val controllerNA = new Controller(new Board(7, 7))
      "create not offered empty board correctly" in {
        controllerNA.createEmptyBoard()
        controllerNA.board.rounds.size should be(7)
        controllerNA.board.rounds(0).turnSize should be(7)
      }
    }
  }
}
