package MyJourney.Day5;

// Correct for exmaple input, but incorrect for original output.

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.lang.Math;

class Interval{
        public long start;
        public long end;

        Interval(long start, long end){
            this.start = start;
            this.end = end;
        }

    @Override
    public String toString() {
        return "Interval{" +
                "start=" + this.start +
                ", end=" + this.end +
                '}';
    }
}

public class Day5Part2 {

    static List<AlmanacMap> ListMaker(BufferedReader reader) throws IOException {
        List<AlmanacMap> x = new ArrayList<>();
        String line;
        while(!(line = reader.readLine()).isEmpty()){
            List<Long> temp  =Arrays.stream(line.split("\\s+")).map(Long::parseLong).toList();
            x.add(new AlmanacMap(temp.get(1), temp.get(1) + temp.get(2) - 1, temp.get(0) - temp.get(1)));
        }
        Comparator<AlmanacMap> comp = Comparator.comparing(record -> record.sourceRangeStart);
        x.sort(comp);
        return x;
    }
    public static void main(String[] args) {
        List<AlmanacMap> seed_soil = null;
        List<AlmanacMap> soil_fertilizer = null;
        List<AlmanacMap> fertilizer_water = null;
        List<AlmanacMap> water_light = null;
        List<AlmanacMap> light_temperature = null;
        List<AlmanacMap> temperature_humidity = null;
        List<AlmanacMap> humidity_location = null;


        try {
            FileInputStream fileInputStream = new FileInputStream("MyJourney/Day5/puzzle_input.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();


            long[] seeds = Arrays.stream(line.split(":\\s+")[1]
                            .split("\\s+"))
                            .mapToLong(Long::parseLong)
                            .toArray();

            List<Interval> inputs = new ArrayList<>();

            for(int i=0;i<seeds.length;i=i+2){
                inputs.add(new Interval(seeds[i], seeds[i]+seeds[i+1]-1));
            }
            inputs.forEach(System.out::println);



            while ((line = reader.readLine()) != null){

                if(Headers.SEED_TO_SOIL.value.equals(line)) {
                    seed_soil = ListMaker(reader);
//                    seed_soil.forEach(System.out::println);
                }
                else if(Headers.SOIL_TO_FERTILIZER.value.equals(line)) {
                    soil_fertilizer = ListMaker(reader);
//                    soil_fertilizer.forEach(System.out::println);
                }
                else if(Headers.FERTILIZER_TO_WATER.value.equals(line)) {
                    fertilizer_water = ListMaker(reader);
//                    fertilizer_water.forEach(System.out::println);
                }
                else if(Headers.WATER_TO_LIGHT.value.equals(line)) {
                    water_light = ListMaker(reader);
//                    water_light.forEach(System.out::println);
                }
                else if(Headers.LIGHT_TO_TEMPERATURE.value.equals(line)) {
                    light_temperature = ListMaker(reader);
//                    light_temperature.forEach(System.out::println);
                }
                else if(Headers.TEMPERATURE_TO_HUMIDITY.value.equals(line)) {
                    temperature_humidity = ListMaker(reader);
//                    temperature_humidity.forEach(System.out::println);
                }
                else if(Headers.HUMIDITY_TO_LOCATION.value.equals(line)) {
                    humidity_location = ListMaker(reader);
//                    humidity_location.forEach(System.out::println);
                }

            }

            List<List<AlmanacMap>> filters = List.of (
                                            seed_soil,
                                            soil_fertilizer,
                                            fertilizer_water,
                                            water_light,
                                            light_temperature,
                                            temperature_humidity,
                                            humidity_location
                                            );


             for (List<AlmanacMap> remap : filters){
                 List<Interval> newList = new ArrayList<>();

                while(!inputs.isEmpty()){
                    var input = inputs.remove(0);
                    int flag = 0;
                    for (int i=0;i<remap.size();i++){
                        if (remap.get(i).destinationRangeStart>=input.start && remap.get(i).sourceRangeStart<=input.end){
                            if(i==0 && remap.get(i).sourceRangeStart>input.start){
                                newList.add(new Interval(input.start,remap.get(i).sourceRangeStart-1));
                            }
                            if (i==(remap.size()-1) && remap.get(i).destinationRangeStart<input.end){
                                newList.add(new Interval(remap.get(i).destinationRangeStart+1,input.end));
                            }
                            if (i!=0 && remap.get(i-1).destinationRangeStart<input.end && remap.get(i).sourceRangeStart>input.start && remap.get(i-1).destinationRangeStart<remap.get(i).sourceRangeStart){
                                var s = Math.max(remap.get(i-1).destinationRangeStart+1,input.start);
                                var e = Math.min(remap.get(i).sourceRangeStart-1,input.end);
                                newList.add(new Interval(s,e)); 
                            }
                            var s = Math.max(remap.get(i).sourceRangeStart,input.start)+remap.get(i).range;
                            var e = Math.min(remap.get(i).destinationRangeStart,input.end)+remap.get(i).range;
                            newList.add(new Interval(s,e));
                            flag = 1;
                        }
                    }
                    if (flag==0) newList.add(new Interval(input.start,input.end));
                }
                inputs = newList;
                inputs.forEach(r -> System.out.print(r+" "));
                System.out.println();
             }

            System.out.println("Minimum : "+ Collections.min(inputs, Comparator.comparing(interval -> interval.start)));


            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
