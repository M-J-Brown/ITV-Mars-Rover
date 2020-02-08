package rover

import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec
import rover.Rover.{MoveForward, RotateAntiClockwise, RotateClockwise}
import world.Direction.{East, North, West}
import world.Grid

class RoverTest extends AnyWordSpec with Matchers {
  "A Rover" when {
    "starting out" should {
      "start at 0,0, facing north" in {
        val testWorld = Grid(5, 5)
        Rover(testWorld) mustBe Rover(North, (0, 0), testWorld)
      }

      "commanded" should {
        "move forward" in {
          val testWorld = Grid(5, 5)
          Rover(testWorld).command(MoveForward) mustBe Rover(North, (0, 1), testWorld)
        }
        "rotate clockwise" in {
          val testWorld = Grid(5, 5)
          Rover(testWorld).command(RotateClockwise) mustBe Rover(East, (0, 0), testWorld)
        }
        "rotate anticlockwise" in {
          val testWorld = Grid(5, 5)
          Rover(testWorld).command(RotateAntiClockwise) mustBe Rover(West, (0, 0), testWorld)
        }
      }
    }
  }

}
