package de.htwg.se.mastermind.util

class UndoManager {
  private var undoStack: List[Command] = Nil
  private var redoStack: List[Command] = Nil

  def doStep(command: Command): Unit = {
    undoStack = command :: undoStack
    command.doStep()
  }

  def undoStep(): Unit = {
    undoStack match {
      case Nil =>
      case head :: stack =>
        head.undoStep()
        undoStack = stack
        redoStack = head :: redoStack
    }
  }

  def redoStep(): Unit = {
    redoStack match {
      case Nil =>
      case head :: stack =>
        head.redoStep()
        redoStack = stack
        undoStack = head :: undoStack
    }
  }

//  def undoStep(): Unit = {
//    undoStack match {
//      case Nil =>
//      case color1 :: color2 :: color3 :: color4 :: stack =>
//        color1.undoStep()
//        redoStack = color1 :: redoStack
//        color2.undoStep()
//        redoStack = color2 :: redoStack
//        color3.undoStep()
//        redoStack = color3 :: redoStack
//        color4.undoStep()
//        redoStack = color4 :: redoStack
//        undoStack = stack
//    }
//  }
//
//  def redoStep(): Unit = {
//    redoStack match {
//      case Nil =>
//      case color1 :: color2 :: color3 :: color4 :: stack =>
//        color1.undoStep()
//        undoStack = color1 :: undoStack
//        color2.undoStep()
//        undoStack = color2 :: undoStack
//        color3.undoStep()
//        undoStack = color3 :: undoStack
//        color4.undoStep()
//        undoStack = color4 :: undoStack
//        redoStack = stack
//    }
//  }
}
