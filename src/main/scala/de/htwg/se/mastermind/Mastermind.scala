package de.htwg.se.mastermind

import de.htwg.se.mastermind.aview.Tui
import de.htwg.se.mastermind.controller.Controller
import de.htwg.se.mastermind.model._
import scala.io.StdIn._

object Mastermind {

  val controller = new Controller(new Board())
  val tui = new Tui(controller)
  controller.notifyObservers

  def main(args: Array[String]): Unit = {
    println("This is Mastermind.")
    println("Available colors: r, b, y, g, w, p, o, v")
    println("Each color occurs only once in a solution.")
    var input: String = ""
    var index = 0
    var list = input.toList.filter(c => c != ' ').map(c => c.toString)

    do {

      input = readLine()
      tui.processInputLine(input, index)
      index += 1
    } while (input != "q" && index < Board.NumberOfRounds)
  }
}