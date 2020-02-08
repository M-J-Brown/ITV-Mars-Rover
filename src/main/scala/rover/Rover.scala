package rover

import cats.implicits._
import rover.Navigation.Position
import rover.Rover._
import world.Direction.North
import world.{Direction, Grid}

case class Rover(facing: Direction, position: Position, grid: Grid) {
  def command(command: Command): Rover = command match {
    case MoveForward => step
    case RotateClockwise => rotateClockwise
    case RotateAntiClockwise => rotateAntiClockwise
  }

  private def step: Rover = copy(position = Navigation.step(facing, position, grid))

  private def rotateClockwise: Rover = copy(facing = facing.turnClockwise)

  private def rotateAntiClockwise: Rover = copy(facing = facing.turnAntiClockwise)
}

object Rover {

  def apply(grid: Grid): Rover = new Rover(North, (0, 0), grid)

  sealed trait Command

  case object MoveForward extends Command

  case object RotateClockwise extends Command

  case object RotateAntiClockwise extends Command

}

object Navigation {
  type Position = (Int, Int)

  def step(direction: Direction, position: Position, grid: Grid): Position = grid.resolve(position |+| direction.unit)

  def moveOnGrid(direction: Direction, starting: Position, distance: Int, grid: Grid): Position = {
    val (realDirection, realDistance) = if (distance < 0) direction.turnAntiClockwise.turnAntiClockwise -> distance * -1 else direction -> distance
    grid.resolve(List.fill(realDistance)(realDirection.unit).combineAll |+| starting)
  }
}
