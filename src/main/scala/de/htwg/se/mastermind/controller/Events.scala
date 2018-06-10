package de.htwg.se.mastermind.controller

import scala.swing.event.Event

class PegChanged extends Event
case class ColorSelected(color: java.awt.Color) extends Event