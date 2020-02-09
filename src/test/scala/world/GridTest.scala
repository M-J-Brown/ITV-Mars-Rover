package world

import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec

class GridTest extends AnyWordSpec with Matchers {

  "grid creation" should {
    "create an empty grid" in {
      Grid(2, 2).underlying mustBe Vector(
        Vector(true, true),
        Vector(true, true)
      )
    }

    "give empty for negatives" in {
      Grid(-1, -1).underlying mustBe Vector.empty
    }

    "not allow non-square grids" in {
      Grid(Vector(
        Vector(true, true),
        Vector(true)
      )) mustBe None
    }
  }

  "resolve" should {
    "resolve a negative" in {
      val testGrid = Grid(3, 3)
      testGrid.resolve(-1, -1) mustBe(2, 2)
    }
    "not change coordinates in range" in {
      val testGrid = Grid(3, 3)
      testGrid.resolve(2, 2) mustBe(2, 2)
    }
    "resolve out of range coordinates" in {
      val testGrid = Grid(3, 3)
      testGrid.resolve(3, 3) mustBe(0, 0)
    }
  }
}
