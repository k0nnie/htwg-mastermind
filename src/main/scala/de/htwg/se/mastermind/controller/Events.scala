package de.htwg.se.mastermind.controller

import scala.swing.event.Event

case class ColorSelected(color: java.awt.Color) extends Event
class PegChanged extends Event
case class GridSizeChanged(newSize: Int) extends Event
class CandidatesChanged extends Event