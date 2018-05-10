package de.htwg.se.mastermind.aview

import de.htwg.se.mastermind.controller.Controller
import de.htwg.se.mastermind.model.Board
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
    "set a turn on input 'rgbp'" in {
      tui.processInputLine("rgbp", 0)
      controller.board.rounds(0).turn.pegs.toString() should be("Vector(r, g, b, p)")
    }
    "for now accept wrong console input of four or more chars" in {
      tui.processInputLine("ssss", 0)
      controller.board.rounds(0).turn.pegs.toString() should be("Vector(s, s, s, s)")
      tui.processInputLine("sssss", 0)
      controller.board.rounds(0).turn.pegs.toString() should be("Vector(s, s, s, s)")
    }
    "for now do nothing with wrong console input of less than four chars" in {
      tui.processInputLine("s", 1)
      controller.board.rounds(1).turn.pegs.toString() should be("Vector( ,  ,  ,  )")
      tui.processInputLine("ss", 1)
      controller.board.rounds(1).turn.pegs.toString() should be("Vector( ,  ,  ,  )")
      tui.processInputLine("sss", 1)
      controller.board.rounds(1).turn.pegs.toString() should be("Vector( ,  ,  ,  )")

    }
  }
}
