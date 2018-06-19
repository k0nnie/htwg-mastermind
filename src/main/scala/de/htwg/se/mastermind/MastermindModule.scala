package de.htwg.se.mastermind

import com.google.inject.AbstractModule
import com.google.inject.name.Names
import net.codingwell.scalaguice.ScalaModule
import de.htwg.se.mastermind.controller.controllerComponent._
import de.htwg.se.mastermind.model.boardComponent.BoardInterface
import de.htwg.se.mastermind.model.boardComponent.boardBaseImpl.Board

class MastermindModule extends AbstractModule with ScalaModule {

  val numberOfRounds: Int = 10
  val numberOfPegs: Int = 4

  def configure(): Unit = {
    bindConstant().annotatedWith(Names.named("NumberOfRounds")).to(numberOfRounds)
    bindConstant().annotatedWith(Names.named("NumberOfPegs")).to(numberOfPegs)
    bind[BoardInterface].annotatedWithName("default").toInstance(new Board())

    bind[BoardInterface].to[Board]
    bind[ControllerInterface].to[controllerBaseImpl.Controller]
  }
}
