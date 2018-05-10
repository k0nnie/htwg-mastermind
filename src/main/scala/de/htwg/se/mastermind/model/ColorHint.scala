package de.htwg.se.mastermind.model

case class ColorHint(name: String) {

  def this() = this("0") // empty value

  def getAvailableColors: Seq[String] = Seq(
    "rightColAndPos", "rightCol", "0"
  )

  override def toString: String = name match {
    case "rightColAndPos" => "+"
    case "rightCol" => "o"
    case _ => " "
  }

  def isValidColor(name: String) : Boolean = getAvailableColors.contains(name)
}