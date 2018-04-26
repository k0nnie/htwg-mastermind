package de.htwg.se.mastermind.model

case class ColorHint(colName: String) {

  def checkName(colName: String) : String = colName match {
    case "x" => "rightColAndPos"
    case "o" => "rightCol"
    case "0" => "0"
    case _ => ""
  }

  def getAvailableColors: Seq[String] = Seq(
    "rightColAndPos", "rightCol", "0"
  )

  def isValidHintColor(name: String) : Boolean = getAvailableColors.contains(name)

  val name: String = checkName(colName)

  isValidHintColor(name)
}