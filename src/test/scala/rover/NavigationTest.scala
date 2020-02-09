package rover

import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec
import rover.Rover.{MoveForward, RotateAntiClockwise, RotateClockwise}
import world.Direction.North
import world.Grid

class NavigationTest extends AnyWordSpec with Matchers {

  "shortestRouteIgnoringMountains" should {
    "work for a simple grid" in {
      val testGrid = Grid(5, 5)
      Navigation.shortestRouteIgnoringMountainsAndTurnTime(North, (0, 0), testGrid, (2, 2)) mustBe List(
        RotateClockwise,
        MoveForward, MoveForward,
        RotateAntiClockwise,
        MoveForward, MoveForward
      )
    }

    "consider going off the grid" in {
      val testGrid = Grid(5, 5)
      Navigation.shortestRouteIgnoringMountainsAndTurnTime(North, (0, 0), testGrid, (4, 4)) mustBe List(
        RotateAntiClockwise,
        MoveForward,
        RotateAntiClockwise,
        MoveForward
      )
    }
  }
}
