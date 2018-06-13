package de.htwg.se.mastermind.aview

import de.htwg.se.mastermind.controller.controllerComponent.{ColorSelected, GameStatus, PegChanged}
import de.htwg.se.mastermind.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.mastermind.model.boardComponent.boardBaseImpl.Color

import scala.swing.Reactor

class Tui(controller: Controller) extends Reactor {

  listenTo(controller)

  def numberOfPegs: Int = controller.numberOfPegs * 2

  def numberOfRounds: Int = controller.numberOfRounds

  def processInputLine(input: String): Unit = {

    input match {
      case "q" =>
      case "n" => controller.createEmptyBoard()
      case "z" => controller.undo()
      case "y" => controller.redo()
      case "s" => controller.solve()
      case _ =>
        input.toList.filter(c => c != ' ').map(c => c.toString) match {
          case color1 :: Nil => controller.set(controller.getCurrentRoundIndex, Vector[Color](Color(color1)))
          case _ =>
        }
    }
  }

  reactions += {
    case event: PegChanged => printTui()
    case event: ColorSelected => printTui()
  }

  def printTui(): Unit = {
    println(controller.boardToString)
    println(GameStatus.message(controller.gameStatus))
  }
}
