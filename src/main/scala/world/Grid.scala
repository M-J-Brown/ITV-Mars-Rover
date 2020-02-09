package world

import mouse.boolean.booleanSyntaxMouse
import rover.Navigation.{CoordinateOps, Coordinates}

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
  def resolve(position: Coordinates): Coordinates = {
    val canonicalX = if (width == 0) 0 else Math.floorMod(position.x, width)
    val canonicalY = if (height == 0) 0 else Math.floorMod(position.y, height)
    canonicalX -> canonicalY
  }

  def isPassable(position: Coordinates): Boolean = {
    val (realX, realY) = resolve(position)
    underlying(realX)(realY)
  }
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
