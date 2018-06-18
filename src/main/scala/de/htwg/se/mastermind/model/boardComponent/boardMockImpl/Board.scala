package de.htwg.se.mastermind.model.boardComponent.boardMockImpl

import de.htwg.se.mastermind.model.boardComponent.boardBaseImpl.{Color, Hint, Peg, Round}
import de.htwg.se.mastermind.model.boardComponent.{BoardInterface, PegInterface}

class Board(var rounds: Vector[Round], var solution: Vector[Color]) extends BoardInterface {

  def replaceRound(index: Int, colVec: Vector[Color]): BoardInterface = this

  def emptyRound(index: Int): BoardInterface = this

  def set(roundIndex: Int, color: Int): BoardInterface = this

  def undoSetPeg(roundIndex: Int): BoardInterface = this

  def redoSetPeg(roundIndex: Int, color: Int): BoardInterface = this

  def createHints(solution: Vector[Color], colVec: Vector[Color]): Vector[Hint] = Vector[Hint](new Hint())

  def isSolved(rowIndex: Int): Boolean = false

  def isSolved: Boolean = false

  def solve: BoardInterface = this
}

object EmptyPeg extends PegInterface {

  override def emptyColor: Boolean = true

  override def updateColor(color: Color): Peg[Color] = Peg(new Color())

}