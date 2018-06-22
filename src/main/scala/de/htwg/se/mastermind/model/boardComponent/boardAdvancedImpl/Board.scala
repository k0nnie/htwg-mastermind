package de.htwg.se.mastermind.model.boardComponent.boardAdvancedImpl

import com.google.inject.Inject
import com.google.inject.name.Named
import de.htwg.se.mastermind.model.boardComponent.BoardInterface
import de.htwg.se.mastermind.model.boardComponent.boardBaseImpl.{Round, Board => BaseBoard}

class Board @Inject() ( @Named("DefaultNumberOfPegs") numberOfPegs: Int, @Named("DefaultNumberOfRounds") numberOfRounds: Int) extends BaseBoard(numberOfPegs, numberOfRounds) {

  def createEmptyBoard: BoardInterface = BaseBoard(Vector.fill(numberOfRounds)(new Round(numberOfPegs)), BaseBoard.randomSolution(numberOfPegs), 0)
}