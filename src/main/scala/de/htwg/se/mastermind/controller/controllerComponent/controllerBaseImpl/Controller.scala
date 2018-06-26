package de.htwg.se.mastermind.controller.controllerComponent.controllerBaseImpl

import com.google.inject.name.Names
import com.google.inject.{Guice, Inject, Injector}
import net.codingwell.scalaguice.InjectorExtensions._
import de.htwg.se.mastermind.MastermindModule
import de.htwg.se.mastermind.controller.controllerComponent.GameStatus._
import de.htwg.se.mastermind.controller.controllerComponent.{BoardSizeChanged, ControllerInterface, GameStatus, PegChanged}
import de.htwg.se.mastermind.model.boardComponent.BoardInterface
import de.htwg.se.mastermind.model.fileIoComponent.FileIOInterface
import de.htwg.se.mastermind.util.UndoManager

import scala.swing.Publisher

class Controller @Inject()(var board: BoardInterface) extends ControllerInterface with Publisher {

  var gameStatus: GameStatus = IDLE
  private val undoManager = new UndoManager
  val injector: Injector = Guice.createInjector(new MastermindModule)
  val fileIo: FileIOInterface = injector.instance[FileIOInterface]


  val availableGUIColors = Vector(
    java.awt.Color.PINK,
    java.awt.Color.BLUE,
    java.awt.Color.CYAN,
    java.awt.Color.GREEN,
    java.awt.Color.YELLOW,
    java.awt.Color.ORANGE,
    java.awt.Color.RED,
    java.awt.Color.MAGENTA)

  val availableGUIHintColors = Vector(
    java.awt.Color.BLACK,
    java.awt.Color.WHITE
  )

  def numberOfRounds: Int = board.rounds.size

  def numberOfPegs: Int = board.solution.size

  def createEmptyBoard(): Unit = {
    board.rounds.size match {
      case 12 => board = injector.instance[BoardInterface](Names.named("easy"))
      case 10 => board = injector.instance[BoardInterface](Names.named("normal"))
      case 8 => board = injector.instance[BoardInterface](Names.named("hard"))
      case _ =>
    }
    gameStatus = NEW
    publish(new PegChanged)
  }

  def resize(newNumberOfPegs: Int, newNumberOfRounds: Int): Unit = {
    newNumberOfRounds match {
      case 12 => board = injector.instance[BoardInterface](Names.named("easy"))
      case 10 => board = injector.instance[BoardInterface](Names.named("normal"))
      case 8 => board = injector.instance[BoardInterface](Names.named("hard"))
      case _ =>
    }
    gameStatus = RESIZE
    publish(BoardSizeChanged(numberOfPegs, numberOfRounds))
  }

  val availableColors: Vector[String] = board.rounds(0).turn.pegs(0).color.getAvailableColors.toVector
  val availableHints: Vector[String] = board.rounds(0).turnHint.pegs(0).color.getAvailableHints.toVector

  def boardToString: String = board.toString

  def getCurrentRoundIndex: Int = board.rounds.indices.iterator.find(index => !board.rounds(index).isSet).getOrElse(-1)

  def set(roundIndex: Int, colors: Int): Unit = {
    if (roundIndex != -1) {
      undoManager.doStep(new SetCommand(roundIndex, this, colors))
      gameStatus = SET
      publish(new PegChanged)
    }
  }

  def mapFromGuiColor(color: java.awt.Color): Int = {
    var foundColor: Int = 0
    val idx = availableGUIColors.indices.toStream.find(i => availableGUIColors(i).equals(color)).getOrElse(-1)

    if (idx != -1) foundColor = availableColors(idx).toInt

    foundColor
  }

  def mapToGuiColor(color: Int): java.awt.Color = {
    var foundColor: java.awt.Color = java.awt.Color.GRAY
    val idx = availableGUIColors.indices.toStream.find(i => availableColors(i).equals(color.toString)).getOrElse(-1)

    if (idx != -1) foundColor = availableGUIColors(idx)

    foundColor
  }

  def mapHintToGuiHint(hintColor: String): java.awt.Color = {
    var foundHint: java.awt.Color = java.awt.Color.LIGHT_GRAY
    val idx = availableHints.indices.toStream.find(i => availableHints(i).equals(hintColor)).getOrElse(-1)

    if (idx != -1) foundHint = availableGUIHintColors(idx)

    foundHint
  }

  def guessColor(rowIndex: Int, columnIndex: Int): java.awt.Color = {
    var foundColor: java.awt.Color = java.awt.Color.GRAY

    if (!board.rounds(rowIndex).turn.pegs(columnIndex).emptyColor) {
      foundColor = mapToGuiColor(board.rounds(rowIndex).turn.pegs(columnIndex).color.toString.toInt)
    }
    foundColor
  }

  def hintColor(rowIndex: Int, columnIndex: Int): java.awt.Color = {
    var foundColor: java.awt.Color = java.awt.Color.LIGHT_GRAY

    if (!board.rounds(rowIndex).turn.containsEmptyColor) {
      foundColor = mapHintToGuiHint(board.rounds(rowIndex).turnHint.pegs(columnIndex).color.name)
    }
    foundColor
  }

  def undo(): Unit = {
    undoManager.undoStep()
    gameStatus = UNDO
    publish(new PegChanged)
  }

  def redo(): Unit = {
    undoManager.redoStep()
    gameStatus = REDO
    publish(new PegChanged)
  }

  def solve(): Unit = {
    undoManager.doStep(new SolveCommand(this))
    gameStatus = SOLVED
    publish(new PegChanged)
  }

  def statusText: String = GameStatus.message(gameStatus)

  def save(): Unit = {
    fileIo.write(board)
    gameStatus = SAVED
    publish(new PegChanged)
  }

  def load(): Unit = {
    board = fileIo.read
    gameStatus = LOADED
    publish(new PegChanged)
  }

}