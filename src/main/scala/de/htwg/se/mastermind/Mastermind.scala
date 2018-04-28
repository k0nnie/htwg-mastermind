package de.htwg.se.mastermind

import de.htwg.se.mastermind.aview.Tui
import de.htwg.se.mastermind.model._
import scala.io.StdIn._

object Mastermind {

  var testGrid = new Board() // test solution
  val tui = new Tui

  def main(args: Array[String]): Unit = {
    println("This is Mastermind.")
    println("Available colors: r, b, y, g, w, p, o, v")
    println("Each color occurs only once in a solution.")
    var input: String = ""
    var index = 0
    do {
      println("Grid : " + testGrid.toString)
      input = readLine()
      testGrid = tui.processInputLine(input, testGrid, index)
      index += 1
    } while (input != "q" && index < 7)
  }
}