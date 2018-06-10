package de.htwg.se.mastermind.controller

import de.htwg.se.mastermind.model
import de.htwg.se.mastermind.model.Solver
import de.htwg.se.mastermind.util.Command
import de.htwg.se.mastermind.controller.GameStatus._

class SolveCommand(controller: Controller) extends Command {

  var memento: model.Board = controller.board

  override def doStep(): Unit = {
    memento = controller.board
    val newBoard = new Solver(controller.board).solve
    controller.gameStatus = SOLVED
    controller.board = newBoard
  }

  override def undoStep(): Unit = {
    val new_memento = controller.board
    controller.board = memento
    memento = new_memento
  }

  override def redoStep(): Unit = {
    val new_memento = controller.board
    controller.board = memento
    memento = new_memento
  }
}