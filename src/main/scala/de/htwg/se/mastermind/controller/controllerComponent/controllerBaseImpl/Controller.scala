package de.htwg.se.mastermind.controller.controllerComponent.controllerBaseImpl

import de.htwg.se.mastermind.controller.controllerComponent.GameStatus._
import de.htwg.se.mastermind.controller.controllerComponent.{ControllerInterface, GameStatus, PegChanged}
import de.htwg.se.mastermind.model.boardComponent.BoardInterface
import de.htwg.se.mastermind.model.boardComponent.boardBaseImpl.{Board, Color, Hint}
import de.htwg.se.mastermind.util.UndoManager

import scala.swing.Publisher

class Controller(var board: BoardInterface) extends ControllerInterface with Publisher {

  var gameStatus: GameStatus = IDLE

  private val undoManager = new UndoManager
  val numberOfRounds: Int = Board.NumberOfRounds
  val numberOfPegs: Int = Board.NumberOfPegs
  val solution: Vector[Color] = board.solution

  val availableGUIColors = Vector(
    java.awt.Color.PINK,
    java.awt.Color.BLUE,
    java.awt.Color.CYAN,
    java.awt.Color.GREEN,
    java.awt.Color.YELLOW,
    java.awt.Color.ORANGE,
    java.awt.Color.RED,
    java.awt.Color.MAGENTA)

  val availableGUIHintColors = Vector(
    java.awt.Color.BLACK,
    java.awt.Color.WHITE
  )

  def createEmptyBoard(): Unit = {
    board = new Board()
    gameStatus = NEW
    publish(new PegChanged)
  }

  def boardToString: String = board.toString

  def clearRound(index: Int): BoardInterface = {
    board.emptyRound(index)
  }

  def getCurrentRoundIndex: Int = {
    val index = board.rounds.indices.iterator.find(index => !board.rounds(index).isSet).getOrElse(-1)
    index
  }

  def solutionToString(): String = board.solutionToString

  def roundIsSolved(index: Int): Boolean = this.board.rounds(index).turnHint.pegs.toString().equals("Vector(+, +, +, +)")

  def set(roundIndex: Int, colors: Vector[Color]): Unit = {
    undoManager.doStep(new SetCommand(roundIndex,this, colors))
    gameStatus = SET
    publish(new PegChanged)
  }

  def mapFromGuiColor(color: java.awt.Color): Color = {
    val colors = new Color().getAvailableColors
    var foundColor: Color = new Color()

    for (i <- availableGUIColors.indices) {
      if (availableGUIColors(i).equals(color)) {
        foundColor = Color(colors(i))
        return foundColor
      }
    }
    foundColor
  }

  def mapToGuiColor(color: Color): java.awt.Color = {
    val colors = new Color().getAvailableColors
    var foundColor: java.awt.Color = java.awt.Color.GRAY

    for (i <- colors.indices) {
      if (colors(i). equals(color.toString)) {
        foundColor = availableGUIColors(i)
        return foundColor
      }
    }
    foundColor
  }

  def mapHintToGuiHint(hintColor: Hint): java.awt.Color = {
    val hints = new Hint().getAvailableHints
    var foundHint: java.awt.Color = java.awt.Color.LIGHT_GRAY

    for (i <- hints.indices) {
      if (hints(i).equals(hintColor.name.toString)) {
        foundHint = availableGUIHintColors(i)
        return foundHint
      }
    }
    foundHint
  }

  def getGuessColor(rowIndex: Int, columnIndex: Int): java.awt.Color = {
    var foundColor: java.awt.Color = java.awt.Color.GRAY

    if (!board.rounds(rowIndex).turn.pegs(columnIndex).emptyColor) {
      foundColor = mapToGuiColor(board.rounds(rowIndex).turn.pegs(columnIndex).color)
    }
    foundColor
  }

  def getHintColor(rowIndex: Int, columnIndex: Int): java.awt.Color = {
    var foundColor: java.awt.Color = java.awt.Color.LIGHT_GRAY

    if (!board.rounds(rowIndex).turn.containsEmptyColor) {
      foundColor = mapHintToGuiHint(board.rounds(rowIndex).turnHint.pegs(columnIndex).color)
    }
    foundColor
  }

  def undo(): Unit = {
    undoManager.undoStep()
    gameStatus = UNDO
    publish(new PegChanged)
  }

  def redo(): Unit = {
    undoManager.redoStep()
    gameStatus = REDO
    publish(new PegChanged)
  }

  def solve(): Unit = {
    undoManager.doStep(new SolveCommand(this))
    gameStatus = SOLVED
    publish(new PegChanged)
  }

  def statusText: String = GameStatus.message(gameStatus)

}
