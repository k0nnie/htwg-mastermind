package de.htwg.se.mastermind.util

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class UndoManagerSpec extends WordSpec with Matchers {

  "An UndoManager" should {
    val undoManager = new UndoManager

    "have a do, undo and redo" in {
      val command = new incrCommand
      command.state should be(0)
      undoManager.doStep(command)
      command.state should be(1)
      undoManager.undoStep()
      command.state should be(0)
      undoManager.redoStep()
      command.state should be(1)
    }

    "handle multiple undo steps correctly" in {
      val command = new incrCommand
      command.state should be(0)
      undoManager.doStep(command)
      command.state should be(1)
      undoManager.doStep(command)
      command.state should be(2)
      undoManager.undoStep()
      command.state should be(1)
      undoManager.undoStep()
      command.state should be(0)
      undoManager.redoStep()
      command.state should be(1)
    }
    "do nothing when stack is empty" in {
      val command = new incrCommand
      command.state should be(0)
      undoManager.undoStep()
      command.state should be(0)
      undoManager.redoStep()
      command.state should be(0)
    }
  }
}