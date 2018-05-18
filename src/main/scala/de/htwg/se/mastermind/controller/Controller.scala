package de.htwg.se.mastermind.controller

import de.htwg.se.mastermind.model.{Board, Color}
import de.htwg.se.mastermind.util.Observable

class Controller(var board: Board) extends Observable {

  var gameSolved = false

  def createEmptyBoard(): Unit = {
    board = new Board()
    notifyObservers
  }

  def boardToString: String = board.toString

  def replaceRound(index: Int, colVec: Vector[Color]): Unit = {
    board = board.replaceRound(index, colVec)
    notifyObservers
  }

  def checkInput(index: Int, colVec: Vector[Color]): Boolean = {
    var isValid = true
    val validNumOfChars = Board.NumberOfPegs

    for (color <- colVec) {
      if (!color.isValidColor) {
        isValid = false
      }
    }
    if (isValid) {
      board = board.replaceRound(index, colVec)
      notifyObservers
    } else {
      println("Wrong console input. Try again!")
      println("Available colors: r, b, y, g, w, p, o, v")
    }
    isValid
  }


  def solutionToString(): String = board.solutionToString

  def gameSolved(index: Int): Unit = {
    board.solutionToString
    println("game solved after " + (index-1) + " rounds!")
    gameSolved = true
  }

  def isSolved(index: Int): Boolean = this.board.rounds(index).turnHint.pegs.toString().equals("Vector(+, +, +, +)")
}
