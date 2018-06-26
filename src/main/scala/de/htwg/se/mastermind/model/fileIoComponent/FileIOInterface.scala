package de.htwg.se.mastermind.model.fileIoComponent

import de.htwg.se.mastermind.model.boardComponent.BoardInterface


trait FileIOInterface {

  def read: BoardInterface
  def write(board: BoardInterface):Unit

}
