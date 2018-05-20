package de.htwg.se.mastermind.model

import scala.collection.immutable.VectorBuilder
import scala.util.Random

case class Board(rounds: Vector[Round], solution: Vector[Color]) {

  def this() = this(Vector.fill(Board.NumberOfRounds)(new Round()), Board.randomSolution)

  def this(rounds: Vector[Round]) = this(rounds, Board.randomSolution)

  def replaceRound(index: Int, colVec: Vector[Color]): Board = {
    val hints = createHints(solution, colVec)
    copy(rounds.updated(index, rounds(index).replacePegs(colVec, hints)), solution)
  }

  def emptyRound(index: Int): Board = {
    val colVec = new Color().emptyColVec
    val hints = createHints(solution, colVec)
    copy(rounds.updated(index, rounds(index).replacePegs(colVec, hints)), solution)
  }

  def createHints(solution: Vector[Color], colVec: Vector[Color]): Vector[ColorHint] = {
    var hints = Vector.empty[ColorHint]
    var hintSet = Set.empty[String]

    for {i <- colVec.indices} {
      if (solution(i).name == colVec(i).name) {
        hints = ColorHint("rightColAndPos") +: hints
        hintSet = hintSet + colVec(i).name
      }
    }
    for {i <- colVec.indices} {
      if (!hintSet.contains(colVec(i).name) && solution.contains(colVec(i))) {
        hintSet = hintSet + colVec(i).name
        hints = hints :+ ColorHint("rightCol")
      }
    }
    while (hints.size.<(Board.NumberOfPegs)) {
      hints = hints :+ new ColorHint()
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