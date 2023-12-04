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

public class Day4Part1{

	public static void main(String[] args){
		String path = "MyJourney/Day4/puzzle_input.txt";
		try {
			FileInputStream fileInputStream = new FileInputStream(path);
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
			BufferedReader reader = new BufferedReader(inputStreamReader);
			String line;
			int sum = 0;
			int index = 1;
			while((line = reader.readLine()) != null){
				Pattern regex1 = Pattern.compile(":\\s*([\\d\\s]+)\\s*\\|");
				Pattern regex2 = Pattern.compile("\\|\\s*(.+)");
				Matcher matcher1 = regex1.matcher(line);
				Matcher matcher2 = regex2.matcher(line);
//				System.out.println(index);
				if(matcher1.find() && matcher2.find()){
					List<String> str_num1 = List.of(matcher1.group(1).split("\\s+"));
					List<String> str_num2 = List.of(matcher2.group(1).split("\\s+"));
					List<Integer> nums1 = str_num1.stream()
							.map(Integer::parseInt)
							.toList();
					HashSet<Integer> nums2 = str_num2.stream()
							.map(Integer::parseInt)
							.collect(Collectors.toCollection(HashSet::new));
					int points = 0;
					for(Integer num : nums1){
						if(nums2.contains(num)){
							if (points == 0)
								points = 1;
							else{
								points *= 2;
							}
						}
					}
					System.out.printf("Points %d : %d\n",index++,points);
					sum += points;
				}
			}
			reader.close();
			fileInputStream.close();
			System.out.println("Total Points Worth: "+sum);
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
}
