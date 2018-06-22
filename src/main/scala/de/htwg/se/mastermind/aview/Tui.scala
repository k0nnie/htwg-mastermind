package de.htwg.se.mastermind.aview

import de.htwg.se.mastermind.controller.controllerComponent._
import scala.swing.Reactor

class Tui(controller: ControllerInterface) extends Reactor {

  listenTo(controller)

  def processInputLine(input: String): Unit = {

    input match {
      case "q" => System.exit(0)
      case "n" => controller.createEmptyBoard()
      case "z" => controller.undo()
      case "y" => controller.redo()
      case "s" => controller.solve()
      case "+" => controller.resize(6, 8)
      case "-" => controller.resize(4, 12)
      case "*" => controller.resize(4, 10)
      case _ =>
        input.toList.filter(c => c != ' ').map(c => c.toString) match {
          case color1 :: Nil => controller.set(controller.getCurrentRoundIndex, color1.toInt)
          case _ =>
        }
    }
  }

  reactions += {
    case event: PegChanged => printTui()
    case event: ColorSelected =>
    case event: BoardSizeChanged => printTui()
  }

  def printTui(): Unit = {
    println(controller.boardToString)
    println(GameStatus.message(controller.gameStatus))
  }
}
