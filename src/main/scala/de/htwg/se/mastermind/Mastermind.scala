package de.htwg.se.mastermind

import de.htwg.se.mastermind.aview.Tui
import de.htwg.se.mastermind.model.Grid

import scala.io.StdIn.readLine

object Mastermind {
  var grid = new Grid(9)
  val tui = new Tui

  def main(args: Array[String]): Unit = {
    var input: String = ""

    do {
      println("Grid : " + grid.toString)
      input = readLine()
      grid = tui.processInputLine(input, grid)
    } while (input != "q")
  }
}