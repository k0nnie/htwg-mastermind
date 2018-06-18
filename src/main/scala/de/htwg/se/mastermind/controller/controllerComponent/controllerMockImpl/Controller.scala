package de.htwg.se.mastermind.controller.controllerComponent.controllerMockImpl

import de.htwg.se.mastermind.controller.controllerComponent.GameStatus._
import de.htwg.se.mastermind.controller.controllerComponent.{ControllerInterface, GameStatus}
import de.htwg.se.mastermind.model.boardComponent.BoardInterface
import de.htwg.se.mastermind.model.boardComponent.boardBaseImpl.{Color, Hint}

class Controller(var board: BoardInterface) extends ControllerInterface {

  createEmptyBoard()

  override def createEmptyBoard(): Unit = {}

  override def boardToString: String = board.toString

  override def getCurrentRoundIndex: Int = 0

  override def set(roundIndex: Int, colors: Vector[Color]): Unit = {}


  override def solutionToString(): String = solutionToString()

  override def mapFromGuiColor(color: java.awt.Color): Color = Color("1")

  override def mapToGuiColor(color: Color): java.awt.Color = java.awt.Color.PINK

  override def mapHintToGuiHint(hintColor: Hint): java.awt.Color = java.awt.Color.BLACK

  override def getGuessColor(rowIndex: Int, columnIndex: Int): java.awt.Color = java.awt.Color.PINK

  override def getHintColor(rowIndex: Int, columnIndex: Int): java.awt.Color = java.awt.Color.WHITE

  override def undo(): Unit = {}

  override def redo(): Unit = {}

  override def solve(): Unit = {}

  override def gameStatus: GameStatus = IDLE

  override def statusText: String = GameStatus.message(gameStatus)

  override def availableGUIColors: Vector[java.awt.Color] = Vector[java.awt.Color](java.awt.Color.PINK)

  override def numberOfPegs: Int = 1

  override def numberOfRounds: Int = 1
}