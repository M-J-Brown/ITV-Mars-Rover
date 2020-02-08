package world

import mouse.boolean.booleanSyntaxMouse
import rover.Navigation.Position

/**
 * A repeating grid of cells which may or may not be passable. Should be rectangular (i.e. all 2nd dimension vectors
 * should be the same length). Using the provided apply methods ensures this.
 */
case class Grid(underlying: Vector[Vector[Boolean]]) {
  require(underlying.headOption.forall(firstY => underlying.forall(_.size == firstY.size)))

  lazy val width: Int = underlying.size
  lazy val height: Int = underlying.headOption.fold(0)(_.size)

  /**
   * The grid repeats - what is the actual position you will be in given these coordinates (actually in range for the
   * underlying collection)
   */
  def resolve(position: Position): Position = {
    val caconicalX = if (width == 0) 0 else Math.floorMod(position._1, width)
    val caconicalY = if (height == 0) 0 else Math.floorMod(position._2, height)
    caconicalX -> caconicalY
  }

  def isPassable(position: Position): Boolean = {
    val (realX, realY) = resolve(position)
    underlying(realX)(realY)
  }

  def inRange(position: Position): Boolean = resolve(position) == position
}

object Grid {
  /**
   * Creates an completely passable grid of the given size.
   */
  def apply(x: Int, y: Int): Grid = new Grid(Vector.fill(x, y)(true))

  /**
   * Returns None if the underlying data is not rectangular, a new grid otherwise.
   */
  def apply(underlying: Vector[Vector[Boolean]]): Option[Grid] =
    underlying.headOption.flatMap { firstY =>
      underlying.forall(_.size == firstY.size).option(new Grid(underlying))
    }
}
