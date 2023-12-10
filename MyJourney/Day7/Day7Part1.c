#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<math.h>
#include "dict.h"
#define MAX_LENGTH 1024
#define FOR_EACH(array, element) for (int i = 0;i < sizeof(array) / sizeof(element); i++)
#define FOR_EACHSTR(array, element) for (int i = 0;i < strlen(array); i++)
#define FOR_EACH2D(array, element) for (int i = 0; array[i] != NULL; i++)

char** split(char c[], char delimiter){
    size_t length = strlen(c);
    int ti=0;
    int ri=0;
    char* buffer = calloc(length,sizeof(char));
    char** result = (char**) calloc(length/2+1,sizeof(char*));
    for(int i=0;i<length;i++){
        if(c[i]==delimiter){
            while(c[i+1]==delimiter) i++;    // Helps in \s+
            buffer[ti++] = '\0';
            result[ri++] = buffer;
            buffer = calloc(length, sizeof(char));
            ti = 0;
        }
        else{
            buffer[ti++] = c[i];
        }
    }
    
    result[ri] = strdup(c[length - 1] != delimiter ? buffer : "");
    return result;
}


typedef struct 
{
    char* cards;
    int bid;
} Hand ;

Dictionary* dict;


double priority_calc(Hand* hand){
    int array[13] = {[0 ... 12] = 4}, order = 0;
    long long rank = 1;
    char* cards = hand->cards;
    FOR_EACHSTR(cards,char){
        char str[2];
        str[0] = cards[i];
        str[1] = '\0';

        int* index_ptr = get_element_label(dict, str);

        if (index_ptr != NULL) {
            int index = *index_ptr;
            array[index]+=1;
            order += pow(14,5-i)*(index+1);
        } else {
            // Handle the case when the card is not found in the dictionary
            fprintf(stderr, "None entry at given label: %s\n", str);
        }
    }
    FOR_EACH(array,int){
        if(array[i]>5){
            rank+= (long long) pow(array[i],array[i]);
            
        }
    }
    return rank+(double)order/1000;    
} 

int compare(const void*a, const void *b){
    Hand* hand1 = *(Hand**)a;
    Hand* hand2 = *(Hand**)b;
    double f = priority_calc(hand1);
    double s = priority_calc(hand2);
    if(f > s){
        return 1;
    }
    else if (f == s)
    {
        return 0;
    }
    else{
        return -1;
    }
}


int main(){
    
    FILE* file = fopen("puzzle_input.txt","r");
    if (file == NULL) {
        perror("Error opening file");
        return 1;
    }

    dict = create_dict();
    add_item_label(dict,"2",&(int){0});
    add_item_label(dict,"3",&(int){1});
    add_item_label(dict,"4",&(int){2});
    add_item_label(dict,"5",&(int){3});
    add_item_label(dict,"6",&(int){4});
    add_item_label(dict,"7",&(int){5});
    add_item_label(dict,"8",&(int){6});
    add_item_label(dict,"9",&(int){7});
    add_item_label(dict,"T",&(int){8});
    add_item_label(dict,"J",&(int){9});
    add_item_label(dict,"Q",&(int){10});
    add_item_label(dict,"K",&(int){11});
    add_item_label(dict,"A",&(int){12});
   
    printf("%d\n", *((int *)get_element_label(dict, "A")));
  



    char line[MAX_LENGTH];
    // Hand** array = malloc(sizeof(Hand*)*MAX_LENGTH);
    Hand* game[MAX_LENGTH];
    int index=0;
    while (fgets(line, sizeof(line), file) != NULL) {
        
        char** input = split(line,' ');
        game[index] = malloc(sizeof(Hand*));
        game[index]->cards = strdup(input[0]);
        game[index++]->bid = atoi(input[1]);


        FOR_EACH2D(input,char*){
            free(input[i]);
        }
        free(input);
        
        
    }
    qsort(game,1000,sizeof(Hand*),compare);
    
    FOR_EACH2D(game,Hand*){
        if(i==1000) break;
        printf("%s : %d\n",game[i]->cards,game[i]->bid);
        printf("Priority : %f\n",priority_calc(game[i]));
        printf("%d * %d = %d\n",game[i]->bid,i+1,(i+1)*game[i]->bid);
    }

    long sum = 0;
    FOR_EACH2D(game,Hand*){
        if(i==1000) break;
        sum += (i+1)*game[i]->bid;
    }
    printf("Total Sum : %ld\n",sum);

    FOR_EACH2D(game,Hand*){
        if(i==1000) break;
        free(game[i]);
    }

    return 0;
}
