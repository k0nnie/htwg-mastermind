package de.htwg.se.mastermind.model.boardComponent.boardBaseImpl

import de.htwg.se.mastermind.model.boardComponent.BoardInterface

case class Board(rounds: Vector[Round], solution: Vector[Color], offset: Int) extends BoardInterface {

  def this(numberOfPegs: Int, numberOfRounds: Int) = this(Vector.fill(numberOfRounds)(new Round(numberOfPegs)), Board.randomSolution(numberOfPegs), 0)

  def numOfRounds: Int = rounds.size
  def numOfPegs: Int = solution.size

  def set(roundIndex: Int, colors: Int): Board = {
    var newColVec = Vector.fill(numOfPegs)(new Color())
    val alreadySetPegs = rounds(roundIndex).turn.pegs.filter(peg => !peg.emptyColor)
    alreadySetPegs.indices.foreach(i => newColVec = newColVec.updated(i, alreadySetPegs(i).color))

    if (newColVec.contains(new Color())) {
      val i = newColVec.indexOf(new Color())
      val color = Vector[Color](Color(colors.toString))
      newColVec = newColVec.updated(i, color.head)
    }
    replaceRound(roundIndex, newColVec)
  }

  def undoPeg(roundIndex: Int): Board = {
    var newColVec = Vector.fill(numOfPegs)(new Color())
    var alreadySetPegs = rounds(roundIndex).turn.pegs.filter(peg => !peg.emptyColor)

    alreadySetPegs = alreadySetPegs.dropRight(1)
    alreadySetPegs.indices.foreach(i => newColVec = newColVec.updated(i, alreadySetPegs(i).color))

    replaceRound(roundIndex, newColVec)
  }

  def redoPeg(roundIndex: Int, color: Int): Board = {
    var newColVec = Vector.fill(numOfPegs)(new Color())
    var alreadySetPegs = rounds(roundIndex).turn.pegs.filter(peg => !peg.emptyColor)

    alreadySetPegs.indices.foreach(i => newColVec = newColVec.updated(i, alreadySetPegs(i).color))

    val colorVec = Vector[Color](Color(color.toString))

    newColVec = newColVec.updated(alreadySetPegs.size, colorVec.head)

    replaceRound(roundIndex, newColVec)
  }

  def replaceRound(index: Int, colVec: Vector[Color]): Board = {
    var hints = Vector.fill(numOfPegs)(new Hint())

    if (!colVec.contains(new Color())) hints = createHints(solution, colVec)

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
    while (hints.size.<(numOfPegs)) {
      hints = hints :+ new Hint()
    }
    hints
  }

  override def toString: String = {
    val lineSeparator = ("+-" + ("--" * numOfPegs)) + "+-" + ("--" * numOfPegs) + "+\n"
    val line = ("| " + ("x " * numOfPegs)) + ("| " + ("x " * numOfPegs)) + "|\n"
    var box = "\n" + (lineSeparator + line) * numOfRounds + lineSeparator

    for {
      row <- rounds.indices
      col <- 0 until numOfPegs * 2
    } {
      if (col < numOfPegs) {
        box = box.replaceFirst("x", rounds(row).turn.pegs(col).color.toString)
      } else {
        box = box.replaceFirst("x", rounds(row).turnHint.pegs(col - numOfPegs).color.toString)
      }
    }
    box
  }

  def isSolved: Boolean = rounds.indices.exists(i => this.rounds(i).turnHint.equals(rounds(i).turnHint.hintVectorSolved))

  def solve: BoardInterface = new Solver(this).solve

  override def createEmptyBoard(newNumberOfPegs: Int, newNumberOfRounds: Int): BoardInterface = this
}

object Board {

  def randomSolution(numberOfPegs: Int): Vector[Color] = addColorRec(numberOfPegs)

  def addColorRec(numberOfPegs: Int): Vector[Color] = {
    val newSet = Set.empty[Color]
    addColorToSet(newSet, numberOfPegs).toVector
  }

  def addColorToSet(set: Set[Color], numberOfPegs: Int): Set[Color] = {
    var newSet = set
    val color = Color(new Color().randomColorString())

    if (newSet.size.equals(numberOfPegs)) {
      newSet
    } else {
      newSet += color
      addColorToSet(newSet, numberOfPegs)
    }
  }
}