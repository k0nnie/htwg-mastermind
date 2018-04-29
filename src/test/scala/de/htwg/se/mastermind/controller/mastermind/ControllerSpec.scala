package de.htwg.se.mastermind.controller.mastermind

import de.htwg.se.mastermind.controller.Controller
import de.htwg.se.mastermind.model.{Board, Color}
import de.htwg.se.mastermind.util.Observer
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class ControllerSpec extends WordSpec with Matchers {
  "A Controller" when {
    "observed by an Observer" should {
      val board = new Board()
      val controller = new Controller(board)
      val observer = new Observer {
        var updated: Boolean = false

        def isUpdated: Boolean = updated

        override def update: Unit = updated = true

      }
      controller.add(observer)
      "notify its Observer after creation" in {
        controller.createEmptyBoard()
        observer.updated should be(true)
        controller.board.rounds.size should be(8)
      }
      "notify its Observer after replacing a round" in {
        val colVec = Vector[Color](Color("r"), Color("r"), Color("r"), Color("r"))
        controller.replaceRound(0, colVec)
        observer.updated should be(true)
        controller.board.rounds(0).turn.pegString should be("Vector(r, r, r, r)")
      }
    }
  }
}
