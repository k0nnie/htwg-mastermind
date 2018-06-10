package de.htwg.se.mastermind.aview

import de.htwg.se.mastermind.controller.{Controller, GameStatus, PegChanged}
import de.htwg.se.mastermind.model.{Board, Color}

import scala.swing.Reactor

class Tui(controller: Controller) extends Reactor {

  listenTo(controller)

  def numberOfPegs: Int = controller.numberOfPegs * 2

  def numberOfRounds: Int = controller.numberOfRounds

  def processInputLine(input: String, index: Int): Boolean = {

    var isValid = true

    input match {
      case "n" => controller.createEmptyBoard()
      case "z" => isValid = controller.undo()
      case "y" => isValid = controller.redo()
      case _ =>
        input.toList.filter(c => c != ' ').map(c => c.toString) match {
          case color1 :: color2 :: color3 :: color4 :: Nil => isValid = controller.checkInputAndSetRound(index, Vector(Color(color1), Color(color2), Color(color3), Color(color4)))
          case _ => isValid = false
        }
    }
    if (index == Board.NumberOfRounds - 1) {
      println(controller.solutionToString())
    }
    isValid
  }

  reactions += {
    case event: PegChanged => printTui()
  }

  def printTui(): Unit = {
    println(controller.boardToString)
    println(GameStatus.message(controller.gameStatus))
  }
}
