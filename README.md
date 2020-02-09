# ITV-Mars-Rover

## Thoughts
- Processing a list of commands is almost certainly a good candiate for cats.State. StateT presumably lets you combine it with either as error handling?


### - Part 1: Basic Movement
- [x] 1. The Mars Rover operates on a grid of arbitrary size.
- [x] 2. You can only issue three commands: Move forward, rotate clockwise, and rotate anticlockwise.
- [x] 3. If the rover moves off the grid, it reappears on the opposite side of the grid.


### - Part 2: Autopilot
- [x] 1. Devise a simple process for determining the shortest possible path from one position on the grid to another.
  - Ignores turn time!
- [ ] 2. Improve the solution so that it can avoid mountain ranges that occupy a number of inconvenient grid squares scattered around the map.


### - Part 3: Putting it all together
- [ ] Output all the instructions and moves carried out by the rover to get from one grid square to another. The output can take any form e.g rows of text, JSON data, or something graphical.
  - Got a print method for printing the state of the rover on the world, not hooked up to anything.
