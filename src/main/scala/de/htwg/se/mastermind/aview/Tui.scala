package de.htwg.se.mastermind.aview

import de.htwg.se.mastermind.controller.Controller
import de.htwg.se.mastermind.model.Color
import de.htwg.se.mastermind.util.Observer

class Tui(controller: Controller) extends Observer {

  controller.add(this)

  def processInputLine(input: String, index: Int): Unit = {
    input match {
      case "n" => controller.createEmptyBoard()
      case _ =>
        input.toList.filter(c => c != ' ').map(c => c.toString) match {
          case color1 :: color2 :: color3 :: color4 :: Nil => controller.replaceRound(index, Vector(Color(color1), Color(color2), Color(color3), Color(color4)))
          case _ =>
        }
    }
  }

  override def update: Unit = println(controller.boardToString)
}
