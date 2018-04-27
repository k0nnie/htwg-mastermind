package de.htwg.se.mastermind.model

case class Color(shortColName: String) {

  def getAvailableColors: Seq[String] = Seq(
    "r", "b", "y", "g", "w", "b", "p", "0"
  )

  def isValidColor(name: String): Boolean = getAvailableColors.contains(name)

  val colorName : String = shortColName match {
    case "r" => "red"
    case "b" => "blue"
    case "y" => "yellow"
    case "g" => "green"
    case "w" => "white"
    case "p" => "pink"
    case "0" => "empty"
    case _ => ""
  }
}