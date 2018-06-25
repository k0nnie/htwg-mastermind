package de.htwg.se.mastermind.model.boardComponent.boardBaseImpl

import de.htwg.se.mastermind.model.boardComponent.BoardInterface

class Solver(var board: BoardInterface) {

  def solve: BoardInterface = {
      board = board.replaceRound(board.rounds.size - 1, board.solution)
    board
  }
}
