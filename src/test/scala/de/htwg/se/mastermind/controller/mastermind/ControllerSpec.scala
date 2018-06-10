package de.htwg.se.mastermind.controller.mastermind

import de.htwg.se.mastermind.controller.Controller
import de.htwg.se.mastermind.model.{Board, Color, Round}
import de.htwg.se.mastermind.util.Observer
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class ControllerSpec extends WordSpec with Matchers {
  "A Controller" when {
    "observed by an Observer" should {
      val solution = Vector[Color](Color("5"), Color("6"), Color("7"), Color("8"))
      val rounds = Vector.fill(Board.NumberOfRounds)(new Round())
      val board = Board(rounds, solution)
      val controller = new Controller(board)
      val observer = new Observer {
        var updated: Boolean = false

        def isUpdated: Boolean = updated

        override def update(): Unit = updated = true
      }
      "print out this solution on console" in {
        controller.solutionToString() should be("solution: 5, 6, 7, 8")
      }
      "notify its Observer after replacing a round" in {
        val colVec = Vector[Color](Color("1"), Color("1"), Color("1"), Color("1"))
        controller.checkInputAndSetRound(0, colVec)
        controller.board.rounds(0).turn.pegs.toString() should be("Vector(1, 1, 1, 1)")
      }
      "return false if game is not solved yet" in {
        val colVec = Vector[Color](Color("5"), Color("6"), Color("7"), Color("8"))
        controller.checkInputAndSetRound(1, colVec)
        controller.gameSolved should be(false)
        controller.gameSolved(1)
        controller.gameSolved should be(true)
      }
      "clear a round if needed" in {
        controller.clearRound(0).rounds(0).turn.pegs.toString() should be("Vector( ,  ,  ,  )")
      }
      "adding a color on GUI" in {
        controller.clearRound(0)
        controller.clearRound(1)
        controller.addColor(java.awt.Color.BLUE)
        controller.board.rounds(2).turn.pegs.toString should be("Vector(2,  ,  ,  )")
      }
      "mapping a GUI color to color" in {
        val guiColor = java.awt.Color.BLUE
        val color = controller.mapFromGuiColor(guiColor)
        color should be(Color("2"))
      }
      "mapping a color to GUI color" in {
        val color = Color("2")
        val guiColor = controller.mapToGuiColor(color)
        guiColor should be(java.awt.Color.BLUE)
      }
      "getting a guessed color" in {
        controller.getGuessColor(0,0).toString should be("java.awt.Color[r=255,g=175,b=175]")
      }
      "getting the color for a hint" in {
        controller.getHintColor(0,0).toString should be("java.awt.Color[r=192,g=192,b=192]")
      }
      "solving a board" in {
        controller.solve()
        controller.gameSolved should be(true)
      }
      "give back a board as string" in {
        controller.boardToString should startWith("\n+---------+---------+")
      }
    }
  }
}
