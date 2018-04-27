package de.htwg.se.mastermind.model

case class Turn(pegs: Vector[Peg[Color]]) {

  def this() = this(Vector.fill(4)(new Peg[Color](Color("0"))))

  val size: Int = pegs.size

  val pegString : String = pegs.toString()

  def replaceColors(colVec: Vector[Color]) : Turn = {
    var newPegs = Vector.empty[Peg[Color]]
    colVec.foreach(color => newPegs = newPegs :+ Peg(color))
    copy(newPegs)
  }
}
