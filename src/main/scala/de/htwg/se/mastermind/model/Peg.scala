package de.htwg.se.mastermind.model

case class Peg[A](color: A) {

  def emptyColor(color: Color): Boolean = color.name.equals(" ")

  override def toString: String = color.toString.replace('0', ' ')

  def updateColor(color: A): Peg[A] = Peg(color)
}