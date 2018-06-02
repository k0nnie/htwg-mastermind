package de.htwg.se.mastermind.controller

import de.htwg.se.mastermind.controller.GameStatus._
import de.htwg.se.mastermind.model.{Board, Color}
import de.htwg.se.mastermind.util.{Observable, UndoManager}

class Controller(var board: Board) extends Observable {

  var gameStatus: GameStatus = IDLE
  var gameSolved = false
  private val undoManager = new UndoManager

  def createEmptyBoard(): Unit = {
    board = new Board()
    gameStatus = NEW
    notifyObservers()
  }

  def boardToString: String = board.toString

  def clearRound(index: Int): Board = {
    board.emptyRound(index)
  }

  def checkInputAndSetRound(index: Int, colVec: Vector[Color]): Boolean = {
    var isValid = true
    gameStatus = SET
    val validNumOfChars = Board.NumberOfPegs

    for (color <- colVec) {
      if (!color.isValidColor) {
        isValid = false
      }
    }
    if (isValid) {
      undoManager.doStep(new SetCommand(index, colVec, this))
      notifyObservers()
    } else {
      println("Wrong console input. Try again!")
      println("Available colors: 1, 2, 3, 4, 5, 6, 7, 8")
    }
    isValid
  }

  def solutionToString(): String = board.solutionToString

  def gameSolved(index: Int): Unit = {
    board.solutionToString
    gameStatus = SOLVED
    println("game solved after " + (index + 1) + " rounds!")
    gameSolved = true
  }

  def isSolved(index: Int): Boolean = this.board.rounds(index).turnHint.pegs.toString().equals("Vector(+, +, +, +)")

  def undo(): Boolean = {
    undoManager.undoStep()
    gameStatus = UNDO
    notifyObservers()
    false
  }

  def redo(): Boolean = {
    undoManager.redoStep()
    gameStatus = REDO
    notifyObservers()
    false
  }
}
