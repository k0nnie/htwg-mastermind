package de.htwg.se.mastermind.controller

object GameStatus extends Enumeration {
  type GameStatus = Value
  val IDLE, SET, NEW, UNDO, REDO, SOLVED = Value

  val map = Map[GameStatus, String](
    IDLE -> "",
    NEW -> "A new game was created",
    SET -> "A cell was set",
    UNDO -> "Undone one round",
    REDO -> "Redone one round",
    SOLVED -> "Game succesfully solved"
  )

  def message(gameStatus: GameStatus) = {
    map(gameStatus)
  }

}
