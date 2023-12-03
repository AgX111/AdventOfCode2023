#include <stdio.h>
#include <ctype.h>
#include <string.h>
#include <stdbool.h>
#include <stdlib.h>

#define dot 46
#define MAX_ROWS 1000
#define MAX_COLS 1000

int main(void)
{
	
    FILE *file = fopen("puzzle_input.txt", "r");

    if (file == NULL) {
        return 1;
    }

    char array[MAX_ROWS][MAX_COLS];
    int numRows = 0;

    // Making a 2D Char array from the inpurt file
    while (fgets(array[numRows], MAX_COLS, file) != NULL) {
        size_t len = strlen(array[numRows]);
        if (len > 0 && array[numRows][len - 1] == '\n') {
            array[numRows][len - 1] = '\0';
        }

        numRows++;

        if (numRows >= MAX_ROWS) {
            fprintf(stderr, "Too many rows in the file.\n");
            break;
        }
    }
    int numCols = strlen(array[0]);

    
    bool check(char array[MAX_ROWS][MAX_COLS],int i,int j){
        if((0<=i-1 && 0<=j-1) && !isdigit(array[i-1][j-1]) && array[i-1][j-1] != dot && array[i-1][j-1] )
            return true;
        if((0<=j-1) && !isdigit(array[i][j-1]) && array[i][j-1] != dot && array[i][j-1] )
            return true;
        if((0<=j-1) && !isdigit(array[i+1][j-1]) && array[i+1][j-1] != dot && array[i+1][j-1] )
            return true;
        if((0<=i-1) && !isdigit(array[i-1][j+1]) && array[i-1][j+1] != dot && array[i-1][j+1] )
            return true;
        if(!isdigit(array[i][j+1]) && array[i][j+1] != dot && array[i+1][j+1] )
            return true;
        if(!isdigit(array[i+1][j+1]) && array[i+1][j+1] != dot && array[i+1][j+1] )
            return true;
        return false;
    }
   

    int sum = 0;
    char buffer[10];
    for(int i=0;i<numRows;i++){
        memset(buffer,'\0',sizeof(buffer));
        int flag = 0;
        int index = 0;
        for(int j=0;j<numCols;j++){
            if(isdigit(array[i][j])){
                buffer[index++] = array[i][j];
                if(flag==0){
                    flag = check(array,i,j);
                }
                if(!isdigit(array[i][j+1])){
                    if(flag==1){
                        sum += atoi(buffer);
                    }
                    memset(buffer,'\0',sizeof(buffer));
                    index = 0;
                    flag = 0;
                }
            }
        }
    }
    printf("Sum : %d\n",sum);
    fclose(file);
	return 0;
}