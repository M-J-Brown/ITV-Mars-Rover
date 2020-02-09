package rover

import java.io.PrintStream

import rover.Navigation.Coordinates
import rover.Rover._
import world.Direction.North
import world.{Direction, Grid}

/**
 * Called Rover, but really it's the state of the problem.
 */
case class Rover(facing: Direction, position: Coordinates, grid: Grid) {
  def command(command: Command): Either[String, Rover] = command match {
    case RotateClockwise => Right(rotateClockwise)
    case RotateAntiClockwise => Right(rotateAntiClockwise)
    case MoveForward => step.toRight("Cannot go there!")
  }

  /**
   * Attemps to follow the commands. Returns the final state of the rover, and an error if it bumped into a mountain.
   */
  def process(commands: List[Command]): (Rover, Option[String]) =
    commands.foldLeft(this -> Option.empty[String]) { case ((rover, maybeError), command) =>
      maybeError match {
        case Some(error) => rover -> Some(error)
        case None => rover.command(command) match {
          case Left(error) => rover -> Some(error)
          case Right(nextRover) => nextRover -> None
        }
      }
    }

  def print(outputStream: PrintStream): Unit = {
    (grid.height to(0, -1))
      .foreach { y =>
        outputStream.println()
        (0 to grid.width)
          .foreach { x =>
            outputStream.print {
              if (position == x -> y) symbol(facing) else if (grid.isPassable(x, y)) 'o' else 'x'
            }
          }
      }
  }

  private def step: Option[Rover] =
    Navigation.tryStep(facing, position, grid).map(newPos => copy(position = newPos))

  private def rotateClockwise: Rover = copy(facing = facing.turnClockwise)

  private def rotateAntiClockwise: Rover = copy(facing = facing.turnAntiClockwise)
}

object Rover {

  def apply(grid: Grid): Rover = new Rover(North, (0, 0), grid)

  sealed trait Command

  case object MoveForward extends Command

  case object RotateClockwise extends Command

  case object RotateAntiClockwise extends Command

  private def symbol(direction: Direction): Char = direction match {
    case Direction.North => '^'
    case Direction.East => '>'
    case Direction.South => 'v'
    case Direction.West => '<'
  }

}
