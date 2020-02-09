package world

sealed trait Direction {

  def unit: (Int, Int)

  def turnClockwise: Direction

  def turnAntiClockwise: Direction
}

object Direction {

  case object North extends Direction {
    override def turnClockwise: Direction = East

    override def turnAntiClockwise: Direction = West

    override def unit: (Int, Int) = (0, 1)
  }

  case object East extends Direction {
    override def turnClockwise: Direction = South

    override def turnAntiClockwise: Direction = North

    override def unit: (Int, Int) = (1, 0)
  }

  case object South extends Direction {
    override def turnClockwise: Direction = West

    override def turnAntiClockwise: Direction = East

    override def unit: (Int, Int) = (0, -1)
  }

  case object West extends Direction {
    override def turnClockwise: Direction = North

    override def turnAntiClockwise: Direction = South

    override def unit: (Int, Int) = (-1, 0)
  }
}
