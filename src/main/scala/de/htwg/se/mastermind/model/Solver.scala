package de.htwg.se.mastermind.model

class Solver(var board: Board) {
  var solved = false

  def solve: Board = {
      if (!board.isSolved) {
        board = board.replaceRound(Board.NumberOfRounds - 1, board.solution)
      }
    board
  }
}
