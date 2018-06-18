package de.htwg.se.mastermind.model.boardComponent.boardBaseImpl

import de.htwg.se.mastermind.model.boardComponent.BoardInterface
import scala.collection.immutable.VectorBuilder
import scala.util.Random

case class Board(rounds: Vector[Round], solution: Vector[Color]) extends BoardInterface {

  def this() = this(Vector.fill(Board.NumberOfRounds)(new Round()), Board.randomSolution)

  def this(rounds: Vector[Round]) = this(rounds, Board.randomSolution)

  def set(roundIndex: Int, colors: Int): Board = {
    var newColVec = Vector.fill(Board.NumberOfPegs)(new Color())
    val alreadySetPegs = rounds(roundIndex).turn.pegs.filter(peg => !peg.emptyColor)
    alreadySetPegs.indices.foreach(i => newColVec = newColVec.updated(i, alreadySetPegs(i).color))

    if (newColVec.contains(new Color())) {
      val i = newColVec.indexOf(new Color())
      val color = Vector[Color](Color(colors.toString))
      newColVec = newColVec.updated(i, color.head)
    }
    replaceRound(roundIndex, newColVec)
  }

  def undoSetPeg(roundIndex: Int): Board = {
    var newColVec = Vector.fill(Board.NumberOfPegs)(new Color())
    var alreadySetPegs = rounds(roundIndex).turn.pegs.filter(peg => !peg.emptyColor)

    alreadySetPegs = alreadySetPegs.dropRight(1)
    alreadySetPegs.indices.foreach(i => newColVec = newColVec.updated(i, alreadySetPegs(i).color))

    replaceRound(roundIndex, newColVec)
  }

  def redoSetPeg(roundIndex: Int, color: Int): Board = {
    var newColVec = Vector.fill(Board.NumberOfPegs)(new Color())
    var alreadySetPegs = rounds(roundIndex).turn.pegs.filter(peg => !peg.emptyColor)

    alreadySetPegs.indices.foreach(i => newColVec = newColVec.updated(i, alreadySetPegs(i).color))

    val colorVec = Vector[Color](Color(color.toString))

    newColVec = newColVec.updated(alreadySetPegs.size, colorVec.head)

    replaceRound(roundIndex, newColVec)
  }

  def replaceRound(index: Int, colVec: Vector[Color]): Board = {
    var hints = Vector[Hint](new Hint(), new Hint(), new Hint(), new Hint())

    if (!colVec.contains(new Color())) hints = createHints(solution, colVec)

    copy(rounds.updated(index, rounds(index).replacePegs(colVec, hints)), solution)
  }

  def emptyRound(index: Int): Board = {
    var emptyColVec = for (i <- 1 to solution.size) yield new Color()
    val hints = createHints(solution, emptyColVec.toVector)

    copy(rounds.updated(index, rounds(index).replacePegs(emptyColVec.toVector, hints)), solution)
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
      row <- rounds.indices
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

  def solutionToString: String = "solution: " + solution.mkString(", ") // can be removed

  def isSolved(rowIndex: Int): Boolean = rounds(rowIndex).turnHint.equals(rounds(rowIndex).turnHint.hintVectorSolved)

  def isSolved: Boolean = rounds.indices.exists(i => this.isSolved(i))

  override def solve: BoardInterface = new Solver(this).solve
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