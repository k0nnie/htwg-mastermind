package de.htwg.se.mastermind.controller

import de.htwg.se.mastermind.model.{Board, Color}
import de.htwg.se.mastermind.util.Observable

class Controller(var board: Board) extends Observable {
  def createEmptyBoard(): Unit = {
    board = new Board()
    notifyObservers
  }

  def boardToString: String = board.toString

  def replaceRound(index: Int, colVec: Vector[Color]): Unit = {
    board = board.replaceRound(index, colVec)
    notifyObservers
  }

  def solutionToString(): String = board.solutionToString

  def gameSolved(index: Int): Unit = {
    board.solutionToString
    println("game solved!")
    System.exit(0)
  }
}
