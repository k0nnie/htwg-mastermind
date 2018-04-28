package de.htwg.se.mastermind.aview

import de.htwg.se.mastermind.model.{Board, Color}

class Tui {
  def processInputLine[A](input: String, board:Board, index: Int):Board = {
    input match {
      case "q" => board
      case _ => {
        input.toList.filter(c => c != ' ').map(c => c.toString) match {
          case color1 :: color2 :: color3 :: color4 :: Nil => board.replaceRound(index, Vector(Color(color1),Color(color2),Color(color3),Color(color4)))
          case _ => board
        }
      }
    }
  }
}
