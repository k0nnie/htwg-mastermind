package de.htwg.se.mastermind.model.fileIoComponent.fileIoJsonImpl

import com.google.inject.Guice
import com.google.inject.name.Names
import net.codingwell.scalaguice.InjectorExtensions._
import de.htwg.se.mastermind.MastermindModule
import de.htwg.se.mastermind.model.fileIoComponent.FileIOInterface
import de.htwg.se.mastermind.model.boardComponent.BoardInterface
import play.api.libs.json._

import scala.io.Source
import java.io._

import play.api.libs.json.Json._

class FileIO extends FileIOInterface {

  override def read: BoardInterface = {
    var board: BoardInterface = null
    val source: String = Source.fromFile("board.json").getLines.mkString
    val json: JsValue = parse(source)
    val numberOfRounds = (json \ "board" \ "numberOfRounds").get.toString.toInt
    val numberOfPegs = (json \ "board" \ "numberOfPegs").get.toString.toInt
    val injector = Guice.createInjector(new MastermindModule)
    numberOfRounds match {
      case 12 => board = injector.instance[BoardInterface](Names.named("easy"))
      case 10 => board = injector.instance[BoardInterface](Names.named("normal"))
      case 8 => board = injector.instance[BoardInterface](Names.named("hard"))
      case _ =>
    }
    for (index <- 0 until numberOfRounds * numberOfPegs) {
      val roundIdx = (json \\ "roundIdx") (index).as[Int]
      val pegIdx = (json \\ "pegIdx") (index).as[Int]
      var color = 0
      val emptyColor = (json \\ "emptyColor") (index).as[Boolean]
      if (!emptyColor) color = (json \\ "color") (index).as[String].toInt
      board = board.set(roundIdx, color)
    }
    board
  }

  override def write(board: BoardInterface): Unit = {
    val pw = new PrintWriter(new File("board.json"))
    pw.write(prettyPrint(boardToJson(board)))
    pw.close()
  }

  def boardToJson(board: BoardInterface): JsObject = {
    obj("board" ->
      obj("numberOfRounds" -> JsNumber(board.rounds.size),
        "numberOfPegs" -> JsNumber(board.solution.size),
        "pegs" -> toJson(
          for {roundIdx <- board.rounds.indices
               pegIdx <- board.solution.indices} yield {
            obj("roundIdx" -> JsNumber(roundIdx),
              "pegIdx" -> JsNumber(pegIdx),
              "color" -> toJson(board.rounds(roundIdx).turn.pegs(pegIdx).color.toString),
              "emptyColor" -> toJson(board.rounds(roundIdx).turn.pegs(pegIdx).emptyColor)
            )
          }
        )
      )
    )
  }
}
