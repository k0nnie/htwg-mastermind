package de.htwg.se.mastermind.model

case class Board(rounds: Vector[Round], solution: Vector[Color]) {

  def this(solution: Vector[Color]) = this(Vector.fill(7)(new Round()), solution)

  def replaceRound(index: Int, colVec: Vector[Color]) : Board = {
    val hints = createHints(solution, colVec)
    copy(rounds.updated(index, rounds(index).replacePegs(colVec, hints)), solution)
  }

  def createHints(solution: Vector[Color], colVec: Vector[Color]): Vector[ColorHint] = {
    var hints = Vector[ColorHint](ColorHint("0"), ColorHint("0"), ColorHint("0"), ColorHint("0"))
    for {
      i <- colVec.indices
      j <- solution.indices
    } {
      val isEqual = colVec(i).shortColName.equals(solution(j).shortColName)
      if (isEqual) {
        if (i == j) {
          hints = hints.updated(i, ColorHint("rightColAndPos"))
        } else {
          hints = hints.updated(i, ColorHint("rightCol"))
        }
      }
    }
    hints
  }

//  override def toString: String = {
//    val size = 8
//    val tries = 7
//    val numberOfPegs = 4
//    val numberOfPegsHint = 4
//    val lineseparator = ("+-" + ("--" * numberOfPegsHint)) + "+-" + ("--" * numberOfPegs) + "+\n"
//
//    val line = ("| " + ("x " * numberOfPegsHint)) + ("| " + ("x " * numberOfPegs)) + "|\n"
//    var box = "\n" + (lineseparator + line) * tries + lineseparator
//    for {
//      row <- 0 until 2 * tries
//      col <- 0 until 6
//    } box = box.replaceFirst("x", "y")
//    box
//  }
}


