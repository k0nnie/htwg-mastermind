package de.htwg.se.mastermind.controller.controllerComponent.controllerMockImpl

import de.htwg.se.mastermind.controller.controllerComponent.GameStatus._
import de.htwg.se.mastermind.controller.controllerComponent.{ControllerInterface, GameStatus}
import de.htwg.se.mastermind.model.boardComponent.BoardInterface
import de.htwg.se.mastermind.model.boardComponent.boardMockImpl.Board
import de.htwg.se.mastermind.model.boardComponent.boardBaseImpl.{Color, Hint, Round}

class Controller(var board: BoardInterface) extends ControllerInterface {

  board = new Board(Vector[Round](new Round()), Vector[Color](new Color()))

  override def createEmptyBoard(): Unit = {}

  override def boardToString: String = board.toString

  override def clearRound(index: Int): BoardInterface = board.emptyRound(0)

  override def getCurrentRoundIndex: Int = 0

  override def set(roundIndex: Int, colors: Vector[Color]): Unit = {}

  override def solutionToString(): String = solutionToString()

  override def roundIsSolved(index: Int): Boolean = false

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
}
