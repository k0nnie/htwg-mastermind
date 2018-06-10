package de.htwg.se.mastermind.controller

import de.htwg.se.mastermind.model.{Board, Color, Hint}
import de.htwg.se.mastermind.util.UndoManager

import scala.swing.Publisher

class Controller(var board: Board) extends Publisher {

  val numberOfRounds: Int = Board.NumberOfRounds
  val numberOfPegs: Int = Board.NumberOfPegs
  val solution: Vector[Color] = board.solution

  var gameSolved = false
  private val undoManager = new UndoManager

  def createEmptyBoard(): Unit = {
    board = new Board()
    publish(new PegChanged)
  }

  def boardToString: String = board.toString

  def clearRound(index: Int): Board = {
    board.emptyRound(index)
  }

  def checkInputAndSetRound(index: Int, colVec: Vector[Color]): Boolean = {
    var isValid = true
    val validNumOfChars = Board.NumberOfPegs

    for (color <- colVec) {
      if (!color.isValidColor) {
        isValid = false
      }
    }
    if (isValid) {
      undoManager.doStep(new SetCommand(index, colVec, this))
      publish(new PegChanged)
    } else {
      println("Wrong console input. Try again!")
      println("Available colors: 1, 2, 3, 4, 5, 6, 7, 8")
    }
    isValid
  }

  def solutionToString(): String = board.solutionToString

  def gameSolved(index: Int): Unit = {
    board.solutionToString
    println("game solved after " + (index + 1) + " rounds!")
    gameSolved = true
  }

  def isSolved(index: Int): Boolean = this.board.rounds(index).turnHint.pegs.toString().equals("Vector(+, +, +, +)")

  def undo(): Boolean = {
    undoManager.undoStep()
    publish(new PegChanged)
    false
  }

  def redo(): Boolean = {
    undoManager.redoStep()
    publish(new PegChanged)
    false
  }

  def addColor(color: java.awt.Color): Unit = {

    for (rowIndex <- 0 until this.numberOfRounds) {
      var colVec = Vector[Color](new Color(), new Color(), new Color(), new Color())
      for (columnIndex <- 0 until this.numberOfPegs) {
        if (board.rounds(rowIndex).turn.pegs(columnIndex).emptyColor) {
          colVec = colVec.updated(columnIndex, mapFromGuiColor(color))
          this.board = board.replaceRound(rowIndex, colVec)
          return
        } else {
          colVec = colVec.updated(columnIndex, board.rounds(rowIndex).turn.pegs(columnIndex).color)
        }
      }
    }
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

  def solve(): Unit = {
    undoManager.doStep(new SolveCommand(this))
    //gameStatus = SOLVED
    publish(new PegChanged)
  }
}
