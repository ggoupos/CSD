#include "grid.h"
#include <assert.h>

/* update value of i,j to n */
void grid_update_value(Grid_T *g, int i, int j, int n){
	assert(n>=0 && n<=9);
	g->elts[i][j].val = n;
	return;
}

/* return value of i,j */
int grid_read_value(Grid_T g, int i, int j){
	return g.elts[i][j].val;
}

/* set (to 1) choice n for elt i,j */
void grid_set_choice(Grid_T *g, int i, int j, int n){
	g->elts[i][j].choices.num[n] = 1;
	return;
}

/* set (to 0) choice n for elt i,j */
void grid_clear_choice(Grid_T *g, int i, int j, int n){
	g->elts[i][j].choices.num[n] = 0;
	return;
}

/* true if choice n for elt i.j is valid */
int grid_choice_is_valid(Grid_T g, int i, int j, int n){
	assert(n>=0 && n<=9);
	return g.elts[i][j].choices.num[n];
}

/* remove n from choices of elt i,j and adjust count only if n is a valid choice for elt i,j */
void grid_remove_choice(Grid_T *g, int i, int j, int n){
	if(grid_choice_is_valid(*g,i,j,n)){
		g->elts[i][j].choices.num[n] = 0;
		g->elts[i][j].choices.count--;
	}
	return;
}

/* return value of count */
int grid_read_count(Grid_T g, int i, int j){
	return g.elts[i][j].choices.count;
}

/* set value of count to 9 */
void grid_set_count(Grid_T *g, int i, int j){
	g->elts[i][j].choices.count = 9;
	return;
}

/* set value of count to 0 */
void grid_clear_count(Grid_T *g, int i, int j){
	g->elts[i][j].choices.count = 0;
	return;
}

/*return value of unique */
int grid_read_unique(Grid_T g){
	return g.unique;
}

/*set value of unique to 1 */
void grid_set_unique(Grid_T *g){
	g->unique = 1;
	return;
}

/*set value of unique to 0 */
void grid_clear_unique(Grid_T *g){
	g->unique = 0;
	return;
}
