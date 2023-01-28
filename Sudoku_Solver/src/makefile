CFLAGS = -ansi -pedantic -Wall

sudoku: sudoku.o grid.o
	gcc $(CFLAGS) sudoku.o grid.o -o sudoku

grid.o: grid.c grid.h
	gcc $(CFLAGS) grid.c -c -o grid.o

sudoku.o : sudoku.c sudoku.h
	gcc $(CFLAGS) sudoku.c -c -o sudoku.o

clean: 
	rm -rf *.o sudoku
