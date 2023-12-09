package MyJourney.Day1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Day1 {

    final static HashMap<String,Integer> numbers;
    static {
        numbers = new HashMap<>();
        numbers.put("zero",0);
        numbers.put("one",1);
        numbers.put("two",2);
        numbers.put("three",3);
        numbers.put("four",4);
        numbers.put("five",5);
        numbers.put("six",6);
        numbers.put("seven",7);
        numbers.put("eight",8);
        numbers.put("nine",9);
    }

    public static void main(String[] args) {
        // Specify the URL you want to read data from
        String urlString = "https://adventofcode.com/2023/day/1/input";

        try {
            // Create a URL object
            InputStream inputStream = getInputStream(urlString);

            // Read data from the input stream
//            int data = AocDay1Part1(inputStream);
            int data = AocDay1Part2(inputStream);

            // Print the data
            System.out.println("Data from URL:");
            System.out.println(data);

            // Close the input stream
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private static InputStream getInputStream(String urlString) throws IOException {
        URL url = new URL(urlString);

        // Open a connection to the URL
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setRequestProperty("Cookie","session=53616c7465645f5f2e061303e05b2433e6962e62df700fd2db301698f01dcc8176ee2927772b555fec65f13f4e7fb3234afb5e545b8ad1530ebc880ddf16becb" );

        // Get the input stream from the connection
        return urlConnection.getInputStream();
    }

    private static int AocDay1Part1(InputStream inputStream) throws IOException {
        // Use a BufferedReader to read data from the input stream
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//        StringBuilder stringBuilder = new StringBuilder();

        String line;
        int sum = 0;

        // Read each line from the input stream and append it to the StringBuilder
        while ((line = reader.readLine()) != null) {
//            stringBuilder.append(line).append("\n");
            int i,j;
            for (i=0;i<line.length();i++){
                if (48 <= line.charAt(i) && line.charAt(i)<=57) break;
            }
            for (j=line.length()-1;j>0;j--){
                if (48 <= line.charAt(j) && line.charAt(j)<=57) break;
            }
            sum+= (line.charAt(i)-48)*10+(line.charAt(j)-48);
        }

        // Close the BufferedReader
        reader.close();

        // Return the content as a string
        return sum;
    }

    public static int AocDay1Part2(InputStream inputStream) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        int sum = 0;
        while((line = br.readLine()) != null){
            int flagL=-1, flagR=-1, temp, i;
            for(i=0;i<line.length();i++){
                if(Character.isDigit(line.charAt(i))){
                    if(flagL==-1)
                        flagL = line.charAt(i)-48;
                    flagR = line.charAt(i)-48;
                }
                else if((temp = match(line,i)) != -1) {
                    if (flagL == -1)
                        flagL = temp;
                    flagR = temp;
                }
            }
            sum += flagL*10 + flagR;
            System.out.printf("%s\n%d %d%n",line,flagL,flagR);
        }
        return sum;
    }
    public static int match(String line,int index){

        for (Map.Entry<String, Integer> entry : numbers.entrySet()) {
            if (index<=(line.length()-entry.getKey().length()) && entry.getKey().equals(line.substring(index, index + entry.getKey().length()))) {
                return entry.getValue();
            }
        }

        return -1;
    }
}
