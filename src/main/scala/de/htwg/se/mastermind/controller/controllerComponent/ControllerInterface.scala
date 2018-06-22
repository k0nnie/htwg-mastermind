package de.htwg.se.mastermind.controller.controllerComponent

import de.htwg.se.mastermind.controller.controllerComponent.GameStatus._
import de.htwg.se.mastermind.model.boardComponent.BoardInterface

import scala.swing.Publisher

trait ControllerInterface extends Publisher{

  def createEmptyBoard(): Unit
  def boardToString: String
  def getCurrentRoundIndex: Int
  def set(roundIndex: Int, colors: Int): Unit
  def mapFromGuiColor(color: java.awt.Color): Int
  def mapToGuiColor(color: Int): java.awt.Color
  def mapHintToGuiHint(hintColor: String): java.awt.Color
  def guessColor(rowIndex: Int, columnIndex: Int): java.awt.Color
  def hintColor(rowIndex: Int, columnIndex: Int): java.awt.Color
  def undo(): Unit
  def redo(): Unit
  def solve(): Unit
  def statusText: String
  def gameStatus: GameStatus
  def numberOfPegs: Int
  def numberOfRounds: Int
  def availableGUIColors: Vector[java.awt.Color]
  def resize(numberOfPegs: Int, numberOfRounds: Int): Unit
  //def updateNumOfRoundsAndPegs(board: BoardInterface): Unit
}



import scala.swing.event.Event

class PegChanged extends Event
case class ColorSelected(color: java.awt.Color) extends Event
case class BoardSizeChanged(numberOfPegs: Int, numberOfRounds: Int) extends Event
