package de.htwg.se.mastermind.model

import scala.util.Random

case class Color(name: String) {

  def this() = this("0") // empty value

  def getAvailableColors: Seq[String] = Seq(
    "1", "2", "3", "4", "5", "6", "7", "8"
  )

  def isValidColor: Boolean = getAvailableColors.contains(name)

  override def toString: String = name match {
    case "0" => " "
    case value => value
  }

  def randomColorString() : String = {
    val colors = this.getAvailableColors
    val rand = new Random(System.currentTimeMillis())
    val random_index = rand.nextInt(colors.length)
    val result = colors(random_index)
    result.toString
  }
}