package de.htwg.se.mastermind.aview

import de.htwg.se.mastermind.controller.Controller
import de.htwg.se.mastermind.model.{Board, Color}
import de.htwg.se.mastermind.util.Observer
import scala.collection.mutable.ListBuffer

class Tui(controller: Controller) extends Observer {

  controller.add(this)

  val availableColor = Set("r", "b", "y", "g", "w", "p", "o", "v")

  def processInputLine(input: String, index: Int): Unit = {
    //index + 1
    input match {
      case "n" => controller.createEmptyBoard()
      case "q" =>
      case _ =>
        var list = input.toList.filter(c => c != ' ').map(c => c.toString)
        if (!(list.size == 4)) {
          println("Bitte geben Sie 4 Farbe")
          if (index > 0) index - 1
          return
        }
        val list1 = new ListBuffer[String]()
        for (col <- list) {
          if (availableColor.contains(col)) {
            list1 += col
          } else {
            println("Bitte geben Sie nur gültige Farbe")
            if (index > 0) index - 1
            return

          }
        }
        controller.replaceRound(index, Vector(Color(list1(0)), Color(list1(1)), Color(list1(2)), Color(list1(3))))
    }
    if (index == Board.NumberOfRounds - 1) {
      println(controller.solutionToString())
    }
  }

  override def update: Unit = println(controller.boardToString)

//  def handleInput(input: List[String]): Boolean = {
//    input match {
//      case input.length == 4 => if (input forall (availableColor contains)) {
//                                    return true
//                                    } else {
//                                    println("Bitte geben Sie nur gültige Farbe")
//                                    return false
//                                    }
//      case input.length != 4
//    }
//    if (input.length == 4) {
//
//      true
//    }
//
//    println("Bitte geben Sie 4 Farbe")
//    false
//  }
}