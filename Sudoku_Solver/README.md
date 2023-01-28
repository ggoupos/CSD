## Assignment 4: Sudoku checker, solver, and generator

Read the makefile to compile properly.

#Usage:

- ./sudoku
  1. Reads a puzzle from stdin
  2. Prints the input puzzle in stderr
  3. Prints if the puzzle has unique, more, or none solution in stderr
  4. Prints a solution if exists or some possible solution (if it is unsolved) with all the faults the puzzle has in stdout
  
  Example: ./sudoku < .. puzzles/s.7 (input from the puzzles folder)

- ./sudoku -c
  1. Reads a puzzle from stdin
  2. Checks if the puzzle is correct or not
  3. Prints a message saying if the puzzle is correct or not and its faults in stderr

  Example: ./sudoku -c < .. puzzles/s.1 (input from the puzzles folder)

- ./sudoku -g nelts -u
  1. Produce a puzzle that contains nelts elements (no zero elements)
  2. Prints the puzzle at stdout
  3. Without -u parameter, the generated puzzle might not have a unique solution
  4. With -u parameter, generated puzzle will have a unique solution

  Example: ./sudoku -g 50 -u | ./sudoku | ./sudoku -c 
