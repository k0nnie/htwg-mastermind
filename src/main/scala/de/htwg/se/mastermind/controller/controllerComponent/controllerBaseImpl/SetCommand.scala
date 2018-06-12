package de.htwg.se.mastermind.controller.controllerComponent.controllerBaseImpl

import de.htwg.se.mastermind.model.boardComponent.boardBaseImpl.Color
import de.htwg.se.mastermind.util.Command

class SetCommand(index: Int, colVec: Vector[Color], controller: Controller) extends Command {
  override def doStep(): Unit = controller.board = controller.board.replaceRound(index, colVec)

  override def undoStep(): Unit = controller.board = controller.clearRound(index)

  override def redoStep(): Unit = controller.board = controller.board.replaceRound(index, colVec)
}
