package de.htwg.se.mastermind.model

import scala.collection.immutable.VectorBuilder
import scala.util.Random

case class Board(rounds: Vector[Round], solution: Vector[Color]) {

  def this() = this(Vector.fill(8)(new Round()), Board.randomSolution)

  def this(rounds: Vector[Round]) = this(rounds, Board.randomSolution)

  def replaceRound(index: Int, colVec: Vector[Color]) : Board = {
    val hints = createHints(solution, colVec)
    copy(rounds.updated(index, rounds(index).replacePegs(colVec, hints)), solution)
  }

  def createHints(solution: Vector[Color], colVec: Vector[Color]): Vector[ColorHint] = {
    var hints = Vector.empty[ColorHint]
    var set = Set.empty[String]

    for {
      i <- colVec.indices
      j <- solution.indices
    } {
      val isEqual = colVec(i).name.equals(solution(j).name)
      if (isEqual) {
        if (i == j) {
          if (!set.contains(colVec(i).name)) {
            hints = hints :+ ColorHint("rightColAndPos")
            set = set + colVec(i).name
          }
        } else {
          if (!set.contains(colVec(i).name)) {
            hints = hints :+ ColorHint("rightCol")
            set = set + colVec(i).name
          }
        }
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
      row <- 0 to Board.NumberOfRounds
      col <- 0 to Board.NumberOfRounds
    } {
      if (col < Board.NumberOfPegs) {
        box = box.replaceFirst("x", rounds(row).turn.pegs(col).color.toString)
      } else {
        box = box.replaceFirst("x", rounds(row).turnHint.pegs(col - Board.NumberOfPegs).color.toString)
      }
    }
    box
  }
}

object Board {

  val NumberOfPegs = 4
  val NumberOfRounds = 7

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