package de.htwg.se.mastermind.model

case class Round(turn: Turn, turnHint: TurnHint) {
  def this() = this(new Turn(), new TurnHint())

  val turnHintSize: Int = turnHint.size
  val turnSize: Int = turn.size

  def replacePegs(colVec: Vector[Color], hints: Vector[ColorHint]): Round = {
    copy(turn.replaceColors(colVec), turnHint.replaceHintColors(hints))
  }
}
