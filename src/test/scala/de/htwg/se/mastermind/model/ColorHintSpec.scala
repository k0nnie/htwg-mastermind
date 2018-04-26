package de.htwg.se.mastermind.model

import org.scalatest.{Matchers, WordSpec}

class ColorHintSpec extends WordSpec with Matchers {
  "A Color" when {
    "have a name from the sequence" should {
      val rightColor = ColorHint("rightCol")
      "return that value" in {
        rightColor.colName should contain oneOf("rightCol", "rightColAndPos")
      }
    }
  }
}
