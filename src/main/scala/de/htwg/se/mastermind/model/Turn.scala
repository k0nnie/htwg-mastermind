package de.htwg.se.mastermind.model

case class Turn(pegs: Vector[Peg[Color]]) {

  def this() = this(Vector.fill(Board.NumberOfPegs)(new Peg[Color](new Color())))

  val size: Int = pegs.size

  def replaceColors(colVec: Vector[Color]): Turn = {
    var newPegs = Vector.empty[Peg[Color]]
    colVec.foreach(color => newPegs = newPegs :+ Peg(color))
    copy(newPegs)
  }

  def isEmpty: Boolean = {
    if (pegs.nonEmpty) {
      false
    }
    true
  }

  def containsEmptyColor: Boolean = {
    if (pegs.contains(Peg(new Color()))) {
      return true
    }
    false
  }
}
