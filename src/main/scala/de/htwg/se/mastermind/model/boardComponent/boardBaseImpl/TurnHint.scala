package de.htwg.se.mastermind.model.boardComponent.boardBaseImpl

case class TurnHint(pegs: Vector[Peg[Hint]]) {

  //def this() = this(Vector.fill(Board.NumberOfPegs)(new Peg[Hint](new Hint())))

  def this(numberOfPegs: Int) = this(Vector.fill(numberOfPegs)(new Peg[Hint](new Hint())))

  def size: Int = pegs.size

  def replaceHintColors(hintVec: Vector[Hint]): TurnHint = {
    var newPegs = Vector.empty[Peg[Hint]]
    hintVec.foreach(hint => newPegs = newPegs :+ Peg(hint))
    copy(newPegs)
  }

  def hintVectorSolved: TurnHint = {
    val solvedVec = Vector.fill(size)(Hint("rightColAndPos"))
    var newPegs = Vector.empty[Peg[Hint]]

    solvedVec.foreach(hint => newPegs = newPegs :+ Peg(hint))
    copy(newPegs)
  }
}