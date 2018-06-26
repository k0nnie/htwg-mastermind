package de.htwg.se.mastermind.model.boardComponent

import de.htwg.se.mastermind.model.boardComponent.boardBaseImpl._

trait BoardInterface {

  def replaceRound(index: Int, colVec: Vector[Color]): BoardInterface
  def set(roundIndex: Int, color: Int): BoardInterface
  def undoPeg(roundIndex: Int): BoardInterface
  def redoPeg(roundIndex: Int, color: Int): BoardInterface
  def createHints(solution: Vector[Color], colVec: Vector[Color]): Vector[Hint]
  def isSolved: Boolean
  def solution: Vector[Color]
  def rounds: Vector[Round]
  def solve: BoardInterface
  def createEmptyBoard(newNumberOfPegs: Int, newNumberOfRounds: Int): BoardInterface
}
