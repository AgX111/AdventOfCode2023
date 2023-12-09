package MyJourney.Day6;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

class Race{
    public long T;
    long R;

    Race(long t,long r){
        T = t;
        R = r;
    }

    @Override
    public String toString(){
        return String.format("(%d,%d)",T,R);
    }

    static long possibleOutcomes(Race race){
        long T = race.T;
        long R = race.R;
        double root1 = (T - Math.sqrt((Math.pow(T, 2)-4*R)))/2;
        double root2 = (T + Math.sqrt((Math.pow(T, 2)-4*R)))/2;
        System.out.println(root1+" "+root2);
        return 1 + (long) (Math.floor(root2-0.0000001) - Math.ceil(root1+0.0000001));
    }
}

public class Day6Part1 {

    public static void main(String[] args) {
        try{
            FileInputStream fileInputStream = new FileInputStream("MyJourney/Day6/puzzle_input.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            
            long[] times = Arrays.stream(reader.readLine().split(":\\s+")[1].split("\\s+")).mapToLong(Long::parseLong).toArray();
            long[] records = Arrays.stream(reader.readLine().split(":\\s+")[1].split("\\s+")).mapToLong(Long::parseLong).toArray();

            long prod = 1;
            for (int i=0;i<times.length;i++){
                Race input = new Race(times[i], records[i]);
                prod *= Race.possibleOutcomes(input);
            }
            
            
            System.out.println("Product of all outcomes: "+prod);
            reader.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
