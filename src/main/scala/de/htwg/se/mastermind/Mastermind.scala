package de.htwg.se.mastermind

import com.google.inject.{Guice, Injector}
import de.htwg.se.mastermind.aview.Tui
import de.htwg.se.mastermind.aview.gui.SwingGui
import de.htwg.se.mastermind.controller.controllerComponent.ControllerInterface

import scala.io.StdIn._

object Mastermind {

  val injector: Injector = Guice.createInjector(new MastermindModule)
  val controller: ControllerInterface = injector.getInstance(classOf[ControllerInterface])
  val tui: Tui = new Tui(controller)
  val gui = new SwingGui(controller)
  controller.createEmptyBoard()

  def main(args: Array[String]): Unit = {
    println("This is Mastermind.")
    println("Please choose 4 colors to start!")
    println("Available colors: 1, 2, 3, 4, 5, 6, 7, 8")
    println("Each color occurs only once in a solution.")
    var input: String = ""

    do {
      input = readLine()
      tui.processInputLine(input)
    }
    while (input != "q")
  }
}
