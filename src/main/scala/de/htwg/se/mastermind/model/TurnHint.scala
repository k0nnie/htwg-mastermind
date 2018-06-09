package de.htwg.se.mastermind.model

case class TurnHint(pegs: Vector[Peg[Hint]]) {

  def this() = this(Vector.fill(Board.NumberOfPegs)(new Peg[Hint](new Hint())))

  val size: Int = pegs.size

  def replaceHintColors(colVec: Vector[Hint]): TurnHint = {
    var newPegs = Vector.empty[Peg[Hint]]
    colVec.foreach(color => newPegs = newPegs :+ Peg(color))
    copy(newPegs)
  }
}