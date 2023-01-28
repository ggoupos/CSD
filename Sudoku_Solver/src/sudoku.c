#include "sudoku.h"
#include <assert.h>
#include <ctype.h>
#include <string.h>

Grid_T sudoku_read(void) {
  Grid_T grid;
  int i = 0;
  int j = 0;
  int number = 0;
  int num_of_elts = 0;
  char value;
  while (num_of_elts != 81) {
    value = getchar();
    if (value == ' ')
      continue;
    else if (value == '\n') {
      i++;
      j = 0;
    } else {
      number = value - '0';
      grid_update_value(&grid, i, j, number);
      j++;
      num_of_elts++;
    }
  }
  assert(num_of_elts == 81);
  return grid;
}

void sudoku_print(FILE *s, Grid_T g) {
  int i, j;
  for (i = 0; i < 9; i++) {
    for (j = 0; j < 9; j++) {
      /*if it's the last row we don't print the extra ' ' at the end*/
      if (j == 8)
        fprintf(s, "%d", grid_read_value(g, i, j));
      else
        fprintf(s, "%d ", grid_read_value(g, i, j));
    }
    /*change line*/
    fprintf(s, "\n");
  }
  return;
}

void sudoku_print_errors(Grid_T g) {
  int i, j, k, l, m, n;
  int value = 0;
  int value2 = 0;
  int counter = 0;
  int row = 0;
  int col = 0;
  int should_break = 0;
  for (i = 0; i < 9; i++) {
    for (j = 0; j < 9; j++) {
      value = grid_read_value(g, i, j);
      /*checking if the above element found more than 1 in the same row */
      for (k = 0; k < 9; k++) {
        value2 = grid_read_value(g, i, k);
        if (value == value2 && value != 0)
          counter++;
        if (counter == 2) {
          fprintf(stderr,
                  "Error: The element with position (%d,%d) and value %d found "
                  "more than 1 time in the same row!\n",
                  i, j, value);
          break;
        }
      }
      counter = 0;
      /*checking if the above element found more than 1 in the same column*/
      for (l = 0; l < 9; l++) {
        value2 = grid_read_value(g, l, j);
        if (value == value2 && value != 0)
          counter++;
        if (counter == 2) {
          fprintf(stderr,
                  "Error: The element with position (%d,%d) and value %d found "
                  "more than 1 time in the same column!\n",
                  i, j, value);
          break;
        }
      }
      counter = 0;
      /*checking if the above element found more than 1 in the same 3x3
       * sub_space*/
      row = i - i % 3;
      col = j - j % 3;
      for (m = 0; m < 3; m++) {
        for (n = 0; n < 3; n++) {
          value2 = grid_read_value(g, m + row, n + col);
          if (value == value2 && value != 0)
            counter++;
          if (counter == 2) {
            fprintf(stderr,
                    "Error: The element with position (%d,%d) and value %d "
                    "found more than 1 time in the same 3x3 sub_grid!\n",
                    i, j, value);
            should_break = 1;
            break;
          }
        }
        if (should_break == 1) {
          should_break = 0;
          break;
        }
      }
      counter = 0;
    }
  }
}

int sudoku_is_correct(Grid_T g) {
  int i, j, k, l, m, n;
  int value = 0;
  int value2 = 0;
  int counter = 0;
  int row = 0;
  int col = 0;
  for (i = 0; i < 9; i++) {
    for (j = 0; j < 9; j++) {
      value = grid_read_value(g, i, j);
      /*checking if the above element found more than 1 in the same row */
      for (k = 0; k < 9; k++) {
        value2 = grid_read_value(g, i, k);
        if (value == value2 && value != 0)
          counter++;
        if (counter == 2) {
          return 0;
        }
      }
      counter = 0;
      /*checking if the above element found more than 1 in the same column*/
      for (l = 0; l < 9; l++) {
        value2 = grid_read_value(g, l, j);
        if (value == value2 && value != 0)
          counter++;
        if (counter == 2) {
          return 0;
        }
      }
      counter = 0;
      /*checking if the above element found more than 1 in the same 3x3
       * sub_space*/
      row = i - i % 3;
      col = j - j % 3;
      for (m = 0; m < 3; m++) {
        for (n = 0; n < 3; n++) {
          value2 = grid_read_value(g, m + row, n + col);
          if (value == value2 && value != 0)
            counter++;
          if (counter == 2) {
            return 0;
          }
        }
      }
      counter = 0;
    }
  }
  /* if we made it till this point, our sudoku is correct */
  return 1;
}

static void sudoku_init_choices(Grid_T *g) {
  int i, j, k, m, n, value, row, col;
  /* Blindly, for each element:
   * we set the count to 9,
   * we make the choice 0 invalid,
   * and finally we make all other choices valid
   */
  for (i = 0; i < 9; i++) {
    for (j = 0; j < 9; j++) {
      grid_set_count(g, i, j);
      grid_clear_choice(g, i, j, 0);
      for (k = 1; k <= 9; k++) {
        grid_set_choice(g, i, j, k);
      }
    }
  }
  /* Right now, each element has 9 choices, even
   * the elements that alerady have a value in them. (non-zero element)
   * When we encounter these elements we set their count to 0
   * make all their choices invalid
   * and make the other elements' of the same row, column and 3x3 sub_space
   * choice with the value of the non-zero element invalid
   */
  for (i = 0; i < 9; i++) {
    for (j = 0; j < 9; j++) {
      value = grid_read_value(*g, i, j);
      /* case we encounter the elements mentioned above */
      if (value != 0) {
        grid_clear_count(g, i, j);
        for (k = 1; k <= 9; k++)
          grid_clear_choice(g, i, j, k);

        for (k = 0; k < 9; k++) {
          /* remove the choice with the value of
           * the non-zero elemnent in all the row
           */
          grid_remove_choice(g, i, k, value);
          /* remove the choice with the value of
           * the non-zero elemnent in all the column
           */
          grid_remove_choice(g, k, j, value);
        }
        /* remove the choice with the value of the
         * non-zero element in all the 3x3 sub_space
         */
        row = i - i % 3;
        col = j - j % 3;
        for (m = 0; m < 3; m++) {
          for (n = 0; n < 3; n++)
            grid_remove_choice(g, m + row, n + col, value);
        }
        continue;
      }
      /* case we encounter an element which value is 0
       * we do nothing
       */
      else
        continue;
    }
  }
  return;
}
static int sudoku_try_next(Grid_T g, int *row, int *col) {
  /* We want to read the count of each element and see which
   * element has the smallest possible number of choices.
   */
  int i, j;
  int k, l;
  int value = 0;
  int num_of_choices;
  int min_num_of_choices = 9;
  /* We start from a random element and we will
   * continue to examine all elements
   */
  srand(getpid());
  /* Completely random element */
  i = rand() % 9;
  j = rand() % 9;
  for (k = 0; k < 9; k++) {
    for (l = 0; l < 9; l++) {
      value = grid_read_value(g, i, j);
      if (value == 0) {
        num_of_choices = grid_read_count(g, i, j);
        if (num_of_choices < min_num_of_choices) {
          min_num_of_choices = num_of_choices;
          *row = i;
          *col = j;
        }
        /* Case an element has the value 0 and has no possible choice
         * we return 0 because there are not available options
         */
        if (num_of_choices == 0)
          return 0;
      }
      j++;
      if (j == 9)
        j = 0;
    }
    i++;
    if (i == 9)
      i = 0;
  }
  /* So, right now, we have found the smallest possible number of choices
   * a specific element of the sudoku has. Maybe more elements has the same
   * number of choices, which is fine.
   */

  /* Now we will take the last element which has the smallest possible number
   * of choice and return the first value that is valid to that element,
   * and because each time we start from a different position
   * (remember i and j at the first step take random values) we will reach
   * to a different ending.
   */
  i = 1;
  while (i < 10) {
    if (grid_choice_is_valid(g, *row, *col, i))
      return i;
    i++;
  }

  return 0;
}

static int sudoku_update_choice(Grid_T *g, int i, int j, int n) {
  int counts = grid_read_count(*g, i, j);
  grid_remove_choice(g, i, j, n);
  /* return the number of counts before we remove the choice */
  return counts;
}

static void sudoku_eliminate_choice(Grid_T *g, int r, int c, int n) {
  int k, m, l, row, col;
  /* remove choice with the value n from the entire row */
  for (k = 0; k < 9; k++)
    grid_remove_choice(g, r, k, n);
  /* remove choice with the value n from the entire column */
  for (k = 0; k < 9; k++)
    grid_remove_choice(g, k, c, n);
  /* remove choice with the value n from the entire 3x3 sub_space */
  row = r - r % 3;
  col = c - c % 3;
  for (m = 0; m < 3; m++) {
    for (l = 0; l < 3; l++)
      grid_remove_choice(g, m + row, l + col, n);
  }
  return;
}

/* A function that returns 1 if the grid has zeroes
 * and returns 0 if it has not
 */
int has_zeros(Grid_T g) {
  int i, j;
  int counter = 0;
  for (i = 0; i < 9; i++) {
    for (j = 0; j < 9; j++) {
      if (grid_read_value(g, i, j) == 0)
        counter++;
    }
  }
  return counter;
}

/* Same (not exactly) as sudoku_solve()... (Read the comments on
 * sudoku_solve)... Used to somehow break the recursive repetition in
 * sudoku_solve
 */
Grid_T sudoku_solve_x(Grid_T g) {
  int i, j;
  int value, num_of_choice;
  Grid_T tmp, solution;
  sudoku_init_choices(&g);

  while ((value = sudoku_try_next(g, &i, &j))) {

    num_of_choice = grid_read_count(g, i, j);

    if (num_of_choice != 1) {
      tmp = g;
      grid_update_value(&tmp, i, j, value);
      solution = sudoku_solve(tmp);
      if (sudoku_is_correct(solution) && !has_zeros(solution)) {
        return solution;
      }

      sudoku_update_choice(&g, i, j, value);

    } else {
      grid_update_value(&g, i, j, value);
      sudoku_eliminate_choice(&g, i, j, value);
    }
  }
  return g;
}

Grid_T sudoku_solve(Grid_T g) {
  int i, j;
  int value, value2, num_of_choice;
  Grid_T tmp, solution, solution2;
  sudoku_init_choices(&g);
  /* We run our code until sudoku_try_next returns 0, which means that
   * the elements of our puzzle are out of choices which mean either we
   * solved the puzzle or the puzzle is impossible to solve
   */
  while ((value = sudoku_try_next(g, &i, &j))) {
    /* We read how many choices the specific element has.
     * If it has exactly one choice then we just
     * update the value of the element with the remaining choice
     * and eliminate this choice from all the elements in the same row, column
     * and 3x3 sub_grid
     */
    num_of_choice = grid_read_count(g, i, j);

    /* In case that our element has not exatcly one choice...
     * We copy our puzzle to a new grid(temp) and we suppose that the value
     * sudoku_try_next has returned is the correct one(we do not know it yet).
     * So, we update and eliminate this value TO THE NEW GRID(TEMP) and call
     * again sudoku_solve to try to solve the temp(not our original) with the
     * new value that we updated a second ago. This step may happen a lot of
     * times but eventually we will check if the final grid that reached to a
     * solution is correct(if it is correct we just return it). If it is not
     * correct, we update the choice(NOT the value) to our ORIGINAL grid(meaning
     * the grid from which the temp was created) to tell it that this value you
     * tried at the first place isn't working, try again with a different value
     * (that will be provided from sudoku_try_next).
     *
     * (It is like we follow a road with lots of crossways, and each time WE
     * decide if we want to go left or right, and if at the end the destination
     * is wrong, then we go to the previous crossway and we make the choice that
     * we previoulsy ignored and repeat this process until we arrive at the
     * correct destination)
     */
    if (num_of_choice != 1) {
      tmp = g;
      /* Everything here occurs to the "new"(temp) grid, not the original */
      grid_update_value(&tmp, i, j, value);
      solution = sudoku_solve(tmp);

      /* If we reach at point where we have used grid_clear_unique(), then our
       * solution is not unique and we should not try to examine each possible
       * solution, so we will just return the one we found (read below...)
       */
      if (!grid_read_unique(solution))
        return solution;

      if (sudoku_is_correct(solution) && !has_zeros(solution)) {
        /* In case that we have found a correct solution, we should also
         * check if it is a unique solution. We will take a another possible
         * choice for the element the sudoky_try_next has returned(but NOT the
         * value the sudoku_try_next has returned) and we try to solve it again
         * with a new valid value. We call sudoku_solve_x (same as sudoku_solve,
         * just need to exit the recursive process) to see if can come up with
         * another solution. If we have found another solution, then the puzzle
         * has not unique solution
         */

        /* Here, we take the other valid choice */
        for (value2 = 1; value2 <= 9; value2++) {
          if (grid_choice_is_valid(g, i, j, value2) && value2 != value)
            break;
        }
        /* We apply it to the original grid( not the temp, because it is already
         * solved at this point) and try to solve it */
        grid_update_value(&g, i, j, value2);
        /* Inside the sudoku_solve_x, again we will use recursion and make
         * copies of our grid etc,etc,etc... just like in sudoku_solve...
         */
        solution2 = sudoku_solve_x(g);
        /* If the second solution is correct, then the puzzle has not unique
         * solution*/
        if (sudoku_is_correct(solution2) && !has_zeros(solution2)) {
          grid_clear_unique(&solution);
          return solution;
        }
        /* Otherwise, it has unique solution */
        else {
          grid_set_unique(&solution);
          return solution;
        }
      }

      /* If a path we followed failed, we remove the choice that lead us to
       * failure and try again. (We do not need to change any value to our
       * original grid because we have never changed it*/
      sudoku_update_choice(&g, i, j, value);
      /*grid_update_value(&g,i,j,0);*/

    }
    /* Exactly one choice, so we just pass this value to the grid
     * and make sure to eliminate this value from repeating itself
     * in the same row, column and 3x3 sub_grid
     */
    else {
      grid_update_value(&g, i, j, value);
      sudoku_eliminate_choice(&g, i, j, value);
    }
  }
  /* If our final return is here, then the solution will be unique */
  grid_set_unique(&g);
  return g;
}

int sudoku_solution_is_unique(Grid_T g) { return grid_read_unique(g); }

static Grid_T sudoku_generate_complete(void) {
  Grid_T g;
  int ready_puzzle[81] = {1, 5, 6, 7, 8, 9, 2, 3, 4, 7, 8, 9, 2, 3, 4, 1, 5,
                          6, 2, 3, 4, 1, 5, 6, 7, 8, 9, 3, 4, 1, 6, 7, 2, 8,
                          9, 5, 8, 7, 5, 9, 1, 3, 4, 6, 2, 6, 9, 2, 5, 4, 8,
                          3, 1, 7, 4, 1, 7, 3, 6, 5, 9, 2, 8, 5, 2, 3, 8, 9,
                          7, 6, 4, 1, 9, 6, 8, 4, 2, 1, 5, 7, 3};
  int i, j;
  int times;
  int value = 0;
  int element = 0;
  sudoku_init_choices(&g);
  /* We will try to generete a completely new sudoku.
   * With try next we will take possible values and apply them
   * to the puzzle. We will try 10 times, because it is not certain
   * if can generate a complete solvable puzzle. If we can not we
   * will return the above ready_puzzle
   */
  for (times = 0; times < 10; times++) {
    while ((value = sudoku_try_next(g, &i, &j))) {
      grid_update_value(&g, i, j, value);
      sudoku_update_choice(&g, i, j, value);
      sudoku_eliminate_choice(&g, i, j, value);
    }
    if (sudoku_is_correct(g) && !has_zeros(g))
      return g;
  }

  /* If we did not manage to generate a new puzzle out of nothing
   * we will return the above ready puzzle
   */
  for (i = 0; i < 9; i++) {
    for (j = 0; j < 9; j++) {
      grid_update_value(&g, i, j, ready_puzzle[element++]);
    }
  }

  return g;
}

Grid_T sudoku_generate(int nelts, int u) {
  Grid_T g, sol, temp;
  int i, j;
  int counter = 81;
  int value;
  g = sudoku_generate_complete();
  srand(getpid());
  while (counter != nelts) {
    i = rand() % 9;
    j = rand() % 9;
    value = grid_read_value(g, i, j);
    if (value != 0) {
      if (u == 1) {
        temp = g;
        grid_update_value(&temp, i, j, 0);
        sol = sudoku_solve(temp);
        if (grid_read_unique(sol) == 1) {
          grid_update_value(&g, i, j, 0);
          counter--;
        }
      } else {
        grid_update_value(&g, i, j, 0);
        counter--;
      }
    }
  }
  return g;
}

void print_usage() {
  printf("Usage1: ./sudoku\n");
  printf("Usage2: ./sudoku -c\n");
  printf("Usage3: ./sudoku -g [NUM_OF_NELTS]\n");
  printf("Usage4: ./sudoku -g [NUM_OF_NELTS] -u\n");
}

int main(int argc, char **argv) {
  Grid_T g;
  switch (argc) {
  default:
    fprintf(stderr, "New puzzle:\n");
    g = sudoku_read();
    sudoku_print(stderr, g);
    g = sudoku_solve(g);
    if (sudoku_is_correct(g) && has_zeros(g)) {
      fprintf(stderr, "Puzzle has no solution\nIt was close, but too far at "
                      "the same time...\n");
      sudoku_print(stdout, g);
    } else {
      fprintf(stderr, "Puzzle has a ");
      if (grid_read_unique(g))
        fprintf(stderr, "unique ");
      fprintf(stderr, "solution:\n");
    }
    sudoku_print(stdout, g);
    break;
  case 2:
    if (strcmp(argv[1], "-c")) {
      print_usage();
      return -2;
    }
    g = sudoku_read();
    sudoku_print(stderr, g);
    fprintf(stderr, "Sudoku is ");
    if (!sudoku_is_correct(g)) {
      fprintf(stderr, "not correct\n");
      sudoku_print_errors(g);
    } else
      fprintf(stderr, "correct\n");
    break;
  case 3:
    if (strcmp(argv[1], "-g")) {
      print_usage();
      return -3;
    } else if (atoi(argv[2]) < 0 || atoi(argv[2]) > 81) {
      print_usage();
      printf("Invalid number of nelts.\n");
      printf("(Nelts must be between 0 and 81)\n");
      return -1;
    }
    g = sudoku_generate(atoi(argv[2]), 0);
    sudoku_print(stdout, g);
    break;
  case 4:
    if (strcmp(argv[1], "-g") && strcmp(argv[3], "-u")) {
      print_usage();
      return -4;
    } else if (atoi(argv[2]) < 0 || atoi(argv[2]) > 81) {
      print_usage();
      printf("Invalid number of nelts.\n");
      printf("(Nelts must be between 0 and 81)\n");
      return -1;
    }
    g = sudoku_generate(atoi(argv[2]), 1);
    sudoku_print(stdout, g);
    break;
  }

  return 0;
}
