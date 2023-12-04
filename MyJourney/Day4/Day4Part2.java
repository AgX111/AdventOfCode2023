package MyJourney.Day4;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day4Part2{

    public static void main(String[] args){
        String path = "MyJourney/Day4/puzzle_input.txt";
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line;
            int[] arr = new int[186];
            Arrays.fill(arr,1);
            int index = 0;
            while((line = reader.readLine()) != null){
                Pattern regex1 = Pattern.compile(":\\s*([\\d\\s]+)\\s*\\|");
                Pattern regex2 = Pattern.compile("\\|\\s*(.+)");
                Matcher matcher1 = regex1.matcher(line);
                Matcher matcher2 = regex2.matcher(line);

                if(matcher1.find() && matcher2.find()){
                    List<String> str_num1 = List.of(matcher1.group(1).split("\\s+"));
                    List<String> str_num2 = List.of(matcher2.group(1).split("\\s+"));
                    List<Integer> nums1 = str_num1.stream()
                            .map(Integer::parseInt)
                            .toList();
                    HashSet<Integer> nums2 = str_num2.stream()
                            .map(Integer::parseInt)
                            .collect(Collectors.toCollection(HashSet::new));
                    int copy = 0;
                    for(Integer num : nums1){
                        if(nums2.contains(num))
                            copy++;
                    }
                    for (int j = 1;j<=copy;j++){
                        arr[index+j] += arr[index];
                    }
                    System.out.printf("Cards %d : %d\n",index+1,arr[index++]);

                }
            }
            reader.close();
            fileInputStream.close();
            System.out.printf("Total Cards: %d",Arrays.stream(arr).sum());
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
