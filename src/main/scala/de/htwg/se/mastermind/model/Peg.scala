package de.htwg.se.mastermind.model

case class Peg(color: Color, position: Int) {
  def isSet: Boolean = color.isInstanceOf[Color] && position != 0
  //override def toString: String = value.toString.replace('0', ' ')
  def col : Color = color
  def pos : Int = position
}