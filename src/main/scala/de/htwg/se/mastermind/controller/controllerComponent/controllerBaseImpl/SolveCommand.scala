package de.htwg.se.mastermind.controller.controllerComponent.controllerBaseImpl

import de.htwg.se.mastermind.controller.controllerComponent.GameStatus._
import de.htwg.se.mastermind.model.boardComponent.BoardInterface
import de.htwg.se.mastermind.util.Command

class SolveCommand(controller: Controller) extends Command {

  var memento: BoardInterface = controller.board

  override def doStep(): Unit = {
    memento = controller.board
    val newBoard = controller.board.solve
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