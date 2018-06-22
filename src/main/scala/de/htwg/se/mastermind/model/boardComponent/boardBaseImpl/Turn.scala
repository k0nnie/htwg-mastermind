package de.htwg.se.mastermind.model.boardComponent.boardBaseImpl

case class Turn(pegs: Vector[Peg[Color]]) {

  //def this() = this(Vector.fill(Board.NumberOfPegs)(new Peg[Color](new Color())))

  def this(numberOfPegs: Int) = this(Vector.fill(numberOfPegs)(new Peg[Color](new Color())))

  def size: Int = pegs.size

  def replaceColors(colVec: Vector[Color]): Turn = {
    var newPegs = Vector.empty[Peg[Color]]
    colVec.foreach(color => newPegs = newPegs :+ Peg(color))
    copy(newPegs)
  }

  def containsEmptyColor: Boolean = if (pegs.contains(Peg(new Color()))) true else false // todo: add
}
