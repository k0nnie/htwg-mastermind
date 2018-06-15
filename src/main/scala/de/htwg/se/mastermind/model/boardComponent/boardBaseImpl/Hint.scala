package de.htwg.se.mastermind.model.boardComponent.boardBaseImpl

case class Hint(name: String) {

  def this() = this("0") // empty value

  def getAvailableHints: Seq[String] = Seq(
    "rightColAndPos", "rightCol"
  )

  override def toString: String = name match {
    case "rightColAndPos" => "+"
    case "rightCol" => "o"
    case _ => " "
  }

  def isValidColor(name: String) : Boolean = getAvailableHints.contains(name)
}