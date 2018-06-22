package de.htwg.se.mastermind.model.boardComponent.boardBaseImpl

case class Round(turn: Turn, turnHint: TurnHint) {

  def this(numberOfPegs: Int) = this(new Turn(numberOfPegs), new TurnHint(numberOfPegs))

  def turnHintSize: Int = turnHint.size
  def turnSize: Int = turn.size

  def replacePegs(colVec: Vector[Color], hints: Vector[Hint]): Round = copy(turn.replaceColors(colVec), turnHint.replaceHintColors(hints))

  def isSet: Boolean = !turn.containsEmptyColor
}
