package de.htwg.se.mastermind.model

case class TurnHint(pegs: Vector[Peg[ColorHint]]) {

  def this() = this(Vector.fill(4)(new Peg[ColorHint](new ColorHint())))

  val size: Int = pegs.size

  val pegString : String = pegs.toString()

  def replaceHintColors(colVec: Vector[ColorHint]) : TurnHint = {
    var newPegs = Vector.empty[Peg[ColorHint]]
    colVec.foreach(color => newPegs = newPegs :+ Peg(color))
    copy(newPegs)
  }
}