package de.htwg.se.mastermind.controller.controllerComponent.controllerBaseImpl

import de.htwg.se.mastermind.model.boardComponent.boardBaseImpl.Color
import de.htwg.se.mastermind.util.Command

class SetCommand(roundIndex: Int, controller: Controller, colors: Vector[Color]) extends Command {
  override def doStep(): Unit = controller.board = controller.board.set(roundIndex, colors)

  override def undoStep(): Unit = controller.board = controller.board.unsetRound(roundIndex, new Color().emptyColVec)

  override def redoStep(): Unit = controller.board = controller.board.setRound(roundIndex, colors)
}
