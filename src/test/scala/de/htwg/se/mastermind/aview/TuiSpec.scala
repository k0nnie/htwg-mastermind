package de.htwg.se.mastermind.aview

import de.htwg.se.mastermind.controller.Controller
import de.htwg.se.mastermind.model.{Board, Color, Round}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class TuiSpec extends WordSpec with Matchers {
  "A Mastermind Tui" should {
    val controller = new Controller(new Board())
    val tui = new Tui(controller)
    "create and empty Mastermind on input 'n'" in {
      tui.processInputLine("n", 0)
      controller.board.rounds should be(new Board().rounds)
    }
    "set a turn on input '1234'" in {
      tui.processInputLine("1234", 0)
      controller.board.rounds(0).turn.pegs.toString() should be("Vector(1, 2, 3, 4)")
    }
    "undo a step back on input 'z'" in {
      tui.processInputLine("2345", 1)
      controller.undo()
      controller.board.rounds(1).turn.pegs.toString() should be("Vector( ,  ,  ,  )")
    }
    "redo a step back on input 'y'" in {
      controller.redo()
      controller.board.rounds(1).turn.pegs.toString() should be("Vector(2, 3, 4, 5)")
    }
    "display solution after last round" in {
      val solution = Vector[Color](Color("5"), Color("6"), Color("7"), Color("8"))
      val rounds = Vector.fill(Board.NumberOfRounds)(new Round())
      val controller2 = new Controller(Board(rounds, solution))
      val tui2 = new Tui(controller2)
      tui2.processInputLine("5678", Board.NumberOfRounds - 1)
      controller2.solutionToString() should be("solution: 5, 6, 7, 8")
    }
    "for now accept wrong console input of four or more chars" in {
      tui.processInputLine("ssss", 0)
      controller.board.rounds(0).turn.pegs.toString() should be("Vector(1, 2, 3, 4)")
      tui.processInputLine("sssss", 0)
      controller.board.rounds(0).turn.pegs.toString() should be("Vector(1, 2, 3, 4)")
    }
    "for now do nothing with wrong console input of less than four chars" in {
      tui.processInputLine("s", 2)
      controller.board.rounds(2).turn.pegs.toString() should be("Vector( ,  ,  ,  )")
      tui.processInputLine("ss", 2)
      controller.board.rounds(2).turn.pegs.toString() should be("Vector( ,  ,  ,  )")
      tui.processInputLine("sss", 2)
      controller.board.rounds(2).turn.pegs.toString() should be("Vector( ,  ,  ,  )")
    }
  }
}
