package de.htwg.se.mastermind.controller.controllerComponent

import de.htwg.se.mastermind.controller.controllerComponent.GameStatus._
import de.htwg.se.mastermind.model.boardComponent.BoardInterface
import de.htwg.se.mastermind.model.boardComponent.boardBaseImpl.{Board, Color, Hint}

import scala.swing.Publisher

trait ControllerInterface extends Publisher{

  def createEmptyBoard(): Unit
  def boardToString: String
  def clearRound(index: Int): BoardInterface
  def checkInputAndSetRound(index: Int, colVec: Vector[Color]): Boolean
  def solutionToString(): String
  def roundIsSolved(index: Int): Boolean
  def addColor(color: java.awt.Color): Unit
  def mapFromGuiColor(color: java.awt.Color): Color
  def mapToGuiColor(color: Color): java.awt.Color
  def mapHintToGuiHint(hintColor: Hint): java.awt.Color
  def getGuessColor(rowIndex: Int, columnIndex: Int): java.awt.Color
  def getHintColor(rowIndex: Int, columnIndex: Int): java.awt.Color
  def undo(): Boolean
  def redo(): Boolean
  def solve(): Boolean
  def statusText: String
  def gameStatus: GameStatus
}



import scala.swing.event.Event

class PegChanged extends Event
case class ColorSelected(color: java.awt.Color) extends Event
