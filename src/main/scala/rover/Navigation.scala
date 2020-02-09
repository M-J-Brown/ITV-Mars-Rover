package rover

import cats.implicits._
import mouse.boolean.booleanSyntaxMouse
import rover.Rover.{Command, MoveForward, RotateAntiClockwise, RotateClockwise}
import world.Direction.{East, North, South, West}
import world.{Direction, Grid}

object Navigation {
  type Coordinates = (Int, Int)

  implicit class CoordinateOps(position: Coordinates) {
    def directions: (Direction, Direction) = position.bimap(
      (x: Int) => if (x < 0) West else East,
      (y: Int) => if (y < 0) South else North
    )

    def xDirection: Direction = directions._1

    def yDirection: Direction = directions._2

    def x: Int = position._1

    def y: Int = position._2
  }

  /**
   * If the next position is passable, return it.
   */
  def tryStep(direction: Direction, position: Coordinates, grid: Grid): Option[Coordinates] = {
    val nextPosition = grid.resolve(position |+| direction.unit)
    grid.isPassable(nextPosition).option(nextPosition)
  }


  /**
   * Always travels X then Y
   */
  def shortestRouteIgnoringMountainsAndTurnTime(facing: Direction,
                                                starting: Coordinates,
                                                grid: Grid,
                                                to: Coordinates): List[Command] = {
    val toTravel = bestTravelVector(starting, to, grid)
    turnTo(facing, toTravel.xDirection)
      .appendedAll(List.fill(Math.abs(toTravel.x))(MoveForward))
      .appendedAll(turnTo(toTravel.xDirection, toTravel.yDirection))
      .appendedAll(List.fill(Math.abs(toTravel.y))(MoveForward))
  }

  def bestTravelVector(from: Coordinates, to: Coordinates, grid: Grid): Coordinates = {
    (grid.resolve(to) |-| grid.resolve(from)).bimap(optimise(_, grid.width), optimise(_, grid.height))
  }

  /**
   * Given going off one side of the grid puts you back on the other side, the best route might be over the edge!
   */
  private def optimise(distance: Int, size: Int): Int =
    if (Math.abs(distance) <= size / 2) distance
    else if (distance > 0) distance - size
    else distance + size

  def turnTo(starting: Direction, ending: Direction): List[Command] = {
    if (starting == ending) Nil
    else if (starting.turnClockwise == ending) List(RotateClockwise)
    else if (starting.turnAntiClockwise == ending) List(RotateAntiClockwise)
    else List(RotateClockwise, RotateClockwise) //Must be opposite!
  }

  def facingRightX(direction: Direction, xCoord: Int): Boolean = if (xCoord < 0) direction == West else direction == East

  def facingRightY(direction: Direction, yCoord: Int): Boolean = if (yCoord < 0) direction == South else direction == North

}