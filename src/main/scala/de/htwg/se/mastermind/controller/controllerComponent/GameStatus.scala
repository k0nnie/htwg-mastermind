package de.htwg.se.mastermind.controller.controllerComponent

object GameStatus extends Enumeration {
  type GameStatus = Value
  val IDLE, SET, NEW, UNDO, REDO, SOLVED, RESIZE = Value

  val map: Map[GameStatus, String] = Map[GameStatus, String](
    IDLE -> "",
    NEW -> "A new game was created",
    SET -> "A peg was set",
    UNDO -> "Undone one round",
    REDO -> "Redone one round",
    SOLVED -> "Game successfully solved",
    RESIZE -> "Game resized"
  )

  def message(gameStatus: GameStatus): String = {
    map(gameStatus)
  }
}
