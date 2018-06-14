package de.htwg.se.mastermind.model.boardComponent.boardBaseImpl

import de.htwg.se.mastermind.model.boardComponent.BoardInterface

import scala.collection.immutable.VectorBuilder
import scala.util.Random

case class Board(rounds: Vector[Round], solution: Vector[Color]) extends BoardInterface {

  def this() = this(Vector.fill(Board.NumberOfRounds)(new Round()), Board.randomSolution)

  def this(rounds: Vector[Round]) = this(rounds, Board.randomSolution)

  def set(roundIndex: Int, colors: Vector[Color]): Board = {

    var newColVec = Vector.fill(Board.NumberOfPegs)(new Color())
    val alreadySetPegs = rounds(roundIndex).turn.pegs.filter(peg => !peg.emptyColor)

    for (i <- alreadySetPegs.indices) {
      newColVec = newColVec.updated(i, alreadySetPegs(i).color)
    }

    var x = 0
    for (i <- alreadySetPegs.size until Board.NumberOfPegs) while (x < colors.size) {
      if (newColVec.contains(new Color())) {
        newColVec = newColVec.updated(i, colors(x))
        x = x + 1
      }
    }

    val board = replaceRound(roundIndex, newColVec)
    board
  }

  def undoSetPeg(roundIndex: Int): Board = {
    var newColVec = Vector.fill(Board.NumberOfPegs)(new Color())
    var alreadySetPegs = rounds(roundIndex).turn.pegs.filter(peg => !peg.emptyColor)

    alreadySetPegs = alreadySetPegs.dropRight(1)

    for (i <- alreadySetPegs.indices) {
      newColVec = newColVec.updated(i, alreadySetPegs(i).color)
    }

    val board = replaceRound(roundIndex, newColVec)
    board
  }

  def redoSetPeg(roundIndex: Int, color: Vector[Color]): Board = {
    var newColVec = Vector.fill(Board.NumberOfPegs)(new Color())
    var alreadySetPegs = rounds(roundIndex).turn.pegs.filter(peg => !peg.emptyColor)

    for (i <- alreadySetPegs.indices) {
      newColVec = newColVec.updated(i, alreadySetPegs(i).color)
    }
    newColVec = newColVec.updated(alreadySetPegs.size, color(0))

    val board = replaceRound(roundIndex, newColVec)
    board
  }

  def replaceRound(index: Int, colVec: Vector[Color]): Board = {
    var hints = Vector[Hint](new Hint(), new Hint(), new Hint(), new Hint())

    if (!colVec.contains(new Color())) {
      hints = createHints(solution, colVec)
    }
    copy(rounds.updated(index, rounds(index).replacePegs(colVec, hints)), solution)
  }

  def emptyRound(index: Int): Board = {
    val colVec = new Color().emptyColVec
    val hints = createHints(solution, colVec)
    copy(rounds.updated(index, rounds(index).replacePegs(colVec, hints)), solution)
  }

  def createHints(solution: Vector[Color], colVec: Vector[Color]): Vector[Hint] = {
    var hints = Vector.empty[Hint]
    var hintSet = Set.empty[String]

    for {i <- colVec.indices} {
      if (solution(i).name == colVec(i).name) {
        hints = Hint("rightColAndPos") +: hints
        hintSet = hintSet + colVec(i).name
      }
    }
    for {i <- colVec.indices} {
      if (!hintSet.contains(colVec(i).name) && solution.contains(colVec(i))) {
        hintSet = hintSet + colVec(i).name
        hints = hints :+ Hint("rightCol")
      }
    }
    while (hints.size.<(Board.NumberOfPegs)) {
      hints = hints :+ new Hint()
    }
    hints
  }

  override def toString: String = {
    val lineSeparator = ("+-" + ("--" * Board.NumberOfPegs)) + "+-" + ("--" * Board.NumberOfPegs) + "+\n"
    val line = ("| " + ("x " * Board.NumberOfPegs)) + ("| " + ("x " * Board.NumberOfPegs)) + "|\n"
    var box = "\n" + (lineSeparator + line) * Board.NumberOfRounds + lineSeparator

    for {
      row <- 0 until Board.NumberOfRounds
      col <- 0 until Board.NumberOfPegs * 2
    } {
      if (col < Board.NumberOfPegs) {
        box = box.replaceFirst("x", rounds(row).turn.pegs(col).color.toString)
      } else {
        box = box.replaceFirst("x", rounds(row).turnHint.pegs(col - Board.NumberOfPegs).color.toString)
      }
    }
    box
  }

  def solutionToString: String = {
    val solutionString = "solution: " + solution.mkString(", ")
    solutionString
  }

  def isSolved(rowIndex: Int): Boolean = {
    var solved = false

    var x: Vector[String] = Vector.empty[String]

    if (rounds(rowIndex).turnHint.equals(rounds(rowIndex).turnHint.hintVectorSolved)) {
      solved = true
    }
    solved
  }

  def isSolved: Boolean = {
    for (i <- 0 until Board.NumberOfRounds) {
      if (this.isSolved(i)) {
        return true
      }
    }
    false
  }
}

object Board {

  val NumberOfPegs = 4
  val NumberOfRounds = 10

  def randomSolution: Vector[Color] = {
    val random = new Random
    var colors = new VectorBuilder[Color]
    var set = Set.empty[String]

    do {
      val color = Color(new Color().randomColorString())
      if (!set.contains(color.name)) {
        colors += color
        set += color.name
      }
    } while (set.size < NumberOfPegs)

    colors.result()
  }
}