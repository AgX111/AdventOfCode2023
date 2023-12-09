package MyJourney.Day6;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Day6Part2 {
    public static void main(String[] args) {
        try {
            FileInputStream fileInputStream = new FileInputStream("MyJourney/Day6/puzzle_input.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            
            long time = Long.parseLong(String.join("", reader.readLine().split(":\\s+")[1].split("\\s+")));
            long record = Long.parseLong(String.join("", reader.readLine().split(":\\s+")[1].split("\\s+")));

            Race race = new Race(time,record);
            
            System.out.println("Possible No. of ways to win are : "+Race.possibleOutcomes(race));
            reader.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
