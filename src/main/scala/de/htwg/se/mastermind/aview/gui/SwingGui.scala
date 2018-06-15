package de.htwg.se.mastermind.aview.gui

import scala.swing._
import scala.swing.event._
import de.htwg.se.mastermind.controller.controllerComponent.{ColorSelected, PegChanged}
import de.htwg.se.mastermind.controller.controllerComponent.controllerBaseImpl.Controller

class SwingGui(controller: Controller) extends MainFrame {
  title = "HTWG Mastermind"
  preferredSize = new Dimension(480, 640)

  listenTo(controller)

  //val statusline = new TextField("status text", 20)

  def colorPickerPanel: FlowPanel = new FlowPanel {
    for {index <- 0 until controller.numberOfPegs * 2} {
      val button = new Button("") {
      }
      button.preferredSize_=(new Dimension(30, 30))
      button.enabled = true
      button.background = controller.availableGUIColors(index)
      contents += button
      listenTo(button.mouse.clicks)
    }

    reactions += {
      case MouseClicked(src, pt, mod, clicks, pops) =>
        controller.publish(ColorSelected(src.background))
        this.repaint()
    }
  }

  def guessPanel(rowIndex: Int): FlowPanel = new FlowPanel {
    for {columnIndex <- 0 until controller.numberOfPegs} {
      val button = Button("") {
      }
      button.preferredSize_= (new Dimension(30, 30))
      button.enabled = false
      button.background = controller.getGuessColor(rowIndex, columnIndex)
      contents += button
      listenTo(button)
    }
  }

  def hintPanel(rowIndex: Int): FlowPanel = new FlowPanel {
    for {columnIndex <- 0 until controller.numberOfPegs} {
      val button = Button("") {
      }
      button.preferredSize_=(new Dimension(20, 20))
      button.enabled = false
      button.background = controller.getHintColor(rowIndex, columnIndex)
      contents += button
      listenTo(button)
    }
  }

  def roundPanel(rowIndex: Int): FlowPanel = new FlowPanel {
    contents += guessPanel(rowIndex)
    contents += hintPanel(rowIndex)
  }

  def boardPanel: GridPanel = new GridPanel(controller.numberOfRounds, 1) {
    for (i <- 0 until controller.numberOfRounds) {
      contents += roundPanel(i)
    }
  }

  contents = new BorderPanel {
    add(boardPanel, BorderPanel.Position.Center)
    add(colorPickerPanel, BorderPanel.Position.South)
  }

  menuBar = new MenuBar {
    contents += new Menu("File") {
      mnemonic = Key.F
      contents += new MenuItem(Action("New") { controller.createEmptyBoard() })
      contents += new MenuItem(Action("Quit") { System.exit(0) })
    }
    contents += new Menu("Edit") {
      mnemonic = Key.E
      contents += new MenuItem(Action("Undo") { controller.undo() })
      contents += new MenuItem(Action("Redo") { controller.redo() })
    }
    contents += new Menu("Solve") {
      mnemonic = Key.S
      contents += new MenuItem(Action("Solve") { controller.solve() })
    }
  }

  visible = true

  reactions += {
    case event: PegChanged    => redraw()
    case event: ColorSelected => redraw(event.color)
  }

  def redraw(): Unit = {
    contents = new BorderPanel {
      add(boardPanel, BorderPanel.Position.Center)
      add(colorPickerPanel, BorderPanel.Position.South)
    }
    repaint()
  }

  def redraw(color: Color): Unit = {
    controller.set(controller.getCurrentRoundIndex, Vector(controller.mapFromGuiColor(color)))
    contents = new BorderPanel {
      add(boardPanel, BorderPanel.Position.Center)
      add(colorPickerPanel, BorderPanel.Position.South)
    }
    repaint()
  }
}
