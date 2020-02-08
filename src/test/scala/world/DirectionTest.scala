package world

import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec
import world.Direction._

class DirectionTest extends AnyWordSpec with Matchers {

  "North" when {
    "turning clockwise" should {
      "face East when turning" in {
        North.turnClockwise mustBe East
      }
      "face South when turning 2 times" in {
        North.turnClockwise.turnClockwise mustBe South
      }

      "face West when turning 3 times" in {
        North.turnClockwise.turnClockwise.turnClockwise mustBe West
      }

      "get back to North when turning 4 times" in {
        North.turnClockwise.turnClockwise.turnClockwise.turnClockwise mustBe North
      }
    }

    "turning anticlockwise" should {
      "face East when turning" in {
        North.turnAntiClockwise mustBe West
      }
      "face South when turning 2 times" in {
        North.turnAntiClockwise.turnAntiClockwise mustBe South
      }

      "face West when turning 3 times" in {
        North.turnAntiClockwise.turnAntiClockwise.turnAntiClockwise mustBe East
      }

      "get back to North when turning 4 times" in {
        North.turnAntiClockwise.turnAntiClockwise.turnAntiClockwise.turnAntiClockwise mustBe North
      }
    }
  }
}
