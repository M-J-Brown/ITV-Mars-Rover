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
          Rover(testWorld).command(MoveForward) mustBe Right(Rover(North, (0, 1), testWorld))
        }
        "rotate clockwise" in {
          val testWorld = Grid(5, 5)
          Rover(testWorld).command(RotateClockwise) mustBe Right(Rover(East, (0, 0), testWorld))
        }
        "rotate anticlockwise" in {
          val testWorld = Grid(5, 5)
          Rover(testWorld).command(RotateAntiClockwise) mustBe Right(Rover(West, (0, 0), testWorld))
        }
      }

      "print" should {
        "work" in {
          //Prime candidate for cats.State!
          val startingRover = Rover(Grid(5, 5))
          for {
            r1 <- startingRover.command(MoveForward)
            r2 <- r1.command(RotateClockwise)
            r3 <- r2.command(MoveForward)
          } r3.print(Console.out)
        }
      }
    }
  }
}
