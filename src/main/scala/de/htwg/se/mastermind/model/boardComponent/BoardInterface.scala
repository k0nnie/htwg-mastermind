package de.htwg.se.mastermind.model.boardComponent

import de.htwg.se.mastermind.model.boardComponent.boardBaseImpl._

trait BoardInterface {

  def replaceRound(index: Int, colVec: Vector[Color]): BoardInterface
  def emptyRound(index: Int): BoardInterface
  def set(roundIndex: Int, color: Int): BoardInterface
  def undoSetPeg(roundIndex: Int): BoardInterface
  def redoSetPeg(roundIndex: Int, color: Int): BoardInterface
  def createHints(solution: Vector[Color], colVec: Vector[Color]): Vector[Hint]
  def solutionToString: String
  def isSolved(rowIndex: Int): Boolean
  def isSolved: Boolean
  def solution: Vector[Color]
  def rounds:Vector[Round]
  def solve: BoardInterface
}

trait PegInterface {
  def updateColor(color: Color): Peg[Color]
  def emptyColor: Boolean
}