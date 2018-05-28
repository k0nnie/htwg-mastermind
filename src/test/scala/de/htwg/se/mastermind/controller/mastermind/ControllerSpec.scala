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
      controller.add(observer)
      "notify its Observer after creation" in {
        controller.createEmptyBoard()
        observer.updated should be(true)
        controller.board.rounds.size should be(Board.NumberOfRounds)
      }
      "notify its Observer after replacing a round" in {
        val colVec = Vector[Color](Color("1"), Color("1"), Color("1"), Color("1"))
        controller.checkInputAndSetRound(0, colVec)
        observer.updated should be(true)
        controller.board.rounds(0).turn.pegs.toString() should be("Vector(1, 1, 1, 1)")
      }
      "return false if game is not solved yet" in {
        val colVec = Vector[Color](Color("5"), Color("6"), Color("7"), Color("8"))
        controller.checkInputAndSetRound(1, colVec)
        controller.gameSolved should be (false)
        controller.gameSolved(1)
        controller.gameSolved should be (true)
      }
      "clear a round if needed" in {
        controller.clearRound(0).rounds(0).turn.pegs.toString() should be("Vector( ,  ,  ,  )")
      }
      "remove an observer" in {
        controller.remove(observer)
        controller.subscribers.contains(observer) should be(false)
      }
    }
  }
}
