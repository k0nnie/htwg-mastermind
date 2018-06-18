package de.htwg.se.mastermind.aview

import de.htwg.se.mastermind.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.mastermind.model.boardComponent.boardBaseImpl
import de.htwg.se.mastermind.model.boardComponent.boardBaseImpl.{Board, Color, Hint, Round}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class TuiSpec extends WordSpec with Matchers {
  "A Mastermind Tui" should {
    val controller = new Controller(new Board())
    val tui = new Tui(controller)
    "create and empty Mastermind on input 'n'" in {
      tui.processInputLine("n")
      controller.board.rounds should be(new Board().rounds)
    }
    "set a turn on input '1'" in {
      tui.processInputLine("1")
      controller.board.rounds(0).turn.pegs.toString() should be("Vector(1,  ,  ,  )")
    }
    "undo a step back on input 'z'" in {
      tui.processInputLine("1")
      tui.processInputLine("1")
      tui.processInputLine("1")
      controller.board.rounds(0).turn.pegs.toString() should be("Vector(1, 1, 1, 1)")
      controller.undo()
      controller.board.rounds(0).turn.pegs.toString() should be("Vector(1, 1, 1,  )")
      controller.undo()
      controller.board.rounds(0).turn.pegs.toString() should be("Vector(1, 1,  ,  )")
      controller.undo()
      controller.board.rounds(0).turn.pegs.toString() should be("Vector(1,  ,  ,  )")
      controller.undo()
      controller.board.rounds(0).turn.pegs.toString() should be("Vector( ,  ,  ,  )")
    }
    "redo a step back on input 'y'" in {
      controller.redo()
      controller.board.rounds(0).turn.pegs.toString() should be("Vector(1,  ,  ,  )")
      controller.redo()
      controller.board.rounds(0).turn.pegs.toString() should be("Vector(1, 1,  ,  )")
      controller.redo()
      controller.board.rounds(0).turn.pegs.toString() should be("Vector(1, 1, 1,  )")
      controller.redo()
      controller.board.rounds(0).turn.pegs.toString() should be("Vector(1, 1, 1, 1)")
    }
    "display solution after last round" in {
      val solution = Vector[Color](Color("5"), Color("6"), Color("7"), Color("8"))
      val rounds = Vector.fill(Board.NumberOfRounds)(new Round())
      val controller2 = new Controller(boardBaseImpl.Board(rounds, solution))
      val tui2 = new Tui(controller2)
      tui2.processInputLine("5")
      tui2.processInputLine("6")
      tui2.processInputLine("7")
      tui2.processInputLine("8")
      controller2.solutionToString() should be("solution: 5, 6, 7, 8")
    }
    "for now do nothing with wrong console input or more than one char" in {
      tui.processInputLine("s")
      controller.board.rounds(2).turn.pegs.toString() should be("Vector( ,  ,  ,  )")
      tui.processInputLine("ss")
      controller.board.rounds(2).turn.pegs.toString() should be("Vector( ,  ,  ,  )")
      tui.processInputLine("sss")
      controller.board.rounds(2).turn.pegs.toString() should be("Vector( ,  ,  ,  )")
    }
    "have this params given" in {
      controller.numberOfRounds should be(10)
      controller.numberOfPegs should be(4)
    }
    "map a hint to a GUI hint" in {
      val hint = "rightColAndPos"
      controller.mapHintToGuiHint(hint).toString should be("java.awt.Color[r=0,g=0,b=0]")
    }
    "get a guess color" in {
      controller.guessColor(0,0).toString should be("java.awt.Color[r=255,g=175,b=175]")
    }
  }
}
