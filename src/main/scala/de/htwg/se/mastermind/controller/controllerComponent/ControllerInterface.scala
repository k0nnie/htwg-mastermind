package de.htwg.se.mastermind.controller.controllerComponent

import de.htwg.se.mastermind.controller.controllerComponent.GameStatus._
import de.htwg.se.mastermind.model.boardComponent.boardBaseImpl.{Color, Hint}

import scala.swing.Publisher

trait ControllerInterface extends Publisher{

  def createEmptyBoard(): Unit
  def boardToString: String
  def getCurrentRoundIndex: Int
  def set(roundIndex: Int, colors: Vector[Color]): Unit
  def solutionToString(): String
  def mapFromGuiColor(color: java.awt.Color): Color
  def mapToGuiColor(color: Color): java.awt.Color
  def mapHintToGuiHint(hintColor: Hint): java.awt.Color
  def getGuessColor(rowIndex: Int, columnIndex: Int): java.awt.Color
  def getHintColor(rowIndex: Int, columnIndex: Int): java.awt.Color
  def undo(): Unit
  def redo(): Unit
  def solve(): Unit
  def statusText: String
  def gameStatus: GameStatus
  def numberOfPegs: Int
  def numberOfRounds: Int
  def availableGUIColors: Vector[java.awt.Color]
}



import scala.swing.event.Event

class PegChanged extends Event
case class ColorSelected(color: java.awt.Color) extends Event