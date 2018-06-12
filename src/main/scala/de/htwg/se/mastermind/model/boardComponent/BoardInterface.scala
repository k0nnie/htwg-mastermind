package de.htwg.se.mastermind.model.boardComponent

import de.htwg.se.mastermind.model.boardComponent.boardBaseImpl._

trait BoardInterface {

  def replaceRound(index: Int, colVec: Vector[Color]): BoardInterface
  def emptyRound(index: Int): BoardInterface
  def createHints(solution: Vector[Color], colVec: Vector[Color]): Vector[Hint]
  def solutionToString: String
  def isSolved(rowIndex: Int): Boolean
  def isSolved: Boolean

  def solution: Vector[Color]
  def rounds:Vector[Round]

  //def NumberOfPegs: Int
  //def NumberOfRounds: Int
  //def randomSolution: Vector[Color]


}

trait PegInterface {
  def updateColor(color: Color): Peg[Color]
  def emptyColor: Boolean
}

trait BoardCompanion[T <: Board] {
  // these methods replace constructors in your example
  def apply(): T
  def apply(rounds: Vector[Round]): T
}