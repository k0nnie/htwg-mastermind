package de.htwg.se.mastermind

import de.htwg.se.mastermind.aview.Tui
import de.htwg.se.mastermind.controller.Controller
import de.htwg.se.mastermind.model._
import scala.io.StdIn._

object Mastermind {

  val controller = new Controller(new Board())
  val tui: Tui = new Tui(controller)
  controller.notifyObservers

  def main(args: Array[String]): Unit = {
    println("This is Mastermind.")
    println("Please choose 4 colors to start!")
    println("Available colors: 1, 2, 3, 4, 5, 6, 7, 8")
    println("Each color occurs only once in a solution.")
    var input: String = ""
    var index = 0

    do {
      var validInput = true
      while (validInput && index < Board.NumberOfRounds) {
        input = readLine()
        validInput = tui.processInputLine(input, index)
        if (validInput) {
          index += 1
        }
      }
    } while (input != "q" && index < Board.NumberOfRounds)
  }
}
