package de.htwg.se.mastermind.model.fileIoComponent.fileIoXmlImpl

import com.google.inject.Guice
import com.google.inject.name.Names
import net.codingwell.scalaguice.InjectorExtensions._
import de.htwg.se.mastermind.MastermindModule
import de.htwg.se.mastermind.model.fileIoComponent.FileIOInterface
import de.htwg.se.mastermind.model.boardComponent.BoardInterface

import scala.xml.{Elem, PrettyPrinter}

class FileIO extends FileIOInterface {

  override def read: BoardInterface = {
    var board: BoardInterface = null
    val file = scala.xml.XML.loadFile("board.xml")
    val numOfPegsAttr = file \\ "board" \ "@numberOfPegs"
    val numOfRoundsAttr = file \\ "board" \ "@numberOfRounds"

    val numOfPegs = numOfPegsAttr.text.toInt
    val numOfRounds = numOfRoundsAttr.text.toInt

    val injector = Guice.createInjector(new MastermindModule)
    numOfRounds match {
      case 12 => board = injector.instance[BoardInterface](Names.named("easy"))
      case 10 => board = injector.instance[BoardInterface](Names.named("normal"))
      case 8 => board = injector.instance[BoardInterface](Names.named("hard"))
      case _ =>
    }

    val pegNodes = file \\ "peg"
    for (peg <- pegNodes) {
      val roundIdx: Int = (peg \ "@roundIdx").text.toInt
      val pegIdx: Int = (peg \ "@pegIdx").text.toInt
      val isEmpty = (peg \ "@isEmpty").text.toBoolean
      var color = 0
      if (!isEmpty) color = peg.text.trim.toInt
      board = board.set(roundIdx, color)
    }
    board
  }

  def write(board: BoardInterface): Unit = writeString(board)

  def writeXML(board: BoardInterface): Unit = {
    scala.xml.XML.save("board.xml", boardToXml(board))
  }

  def writeString(board: BoardInterface): Unit = {
    import java.io._
    val pw = new PrintWriter(new File("board.xml"))
    val prettyPrinter = new PrettyPrinter(120, 4)
    val xml = prettyPrinter.format(boardToXml(board))
    pw.write(xml)
    pw.close()
  }

  def boardToXml(board: BoardInterface): Elem = {
    <board numberOfPegs={board.rounds(0).turnSize.toString} numberOfRounds={board.rounds.size.toString}>
      {for {
      roundIdx <- board.rounds.indices
      pegIdx <- 0 until board.rounds(0).turnSize
    } yield pegToXml(board, roundIdx, pegIdx)}
    </board>
  }

  def pegToXml(board: BoardInterface, roundIdx: Int, pegIdx: Int): Elem = {
    <peg pegIdx={pegIdx.toString} roundIdx={roundIdx.toString} isEmpty={board.rounds(roundIdx).turn.pegs(pegIdx).emptyColor.toString}>
      {board.rounds(roundIdx).turn.pegs(pegIdx).color}
    </peg>
  }
}
