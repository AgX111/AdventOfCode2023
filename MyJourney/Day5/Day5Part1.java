package MyJourney.Day5;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Almanac{

    public long value;
    Almanac(long value){
        this.value = value;
    }

    public Almanac process(List<AlmanacMap> almanacMap){
        for (AlmanacMap alm : almanacMap ){
            if (alm.check(this.value)){
                return new Almanac(alm.convert(this.value));
            }
        }
        return new Almanac(this.value);
    }
}

class AlmanacMap{
    Long destinationRangeStart;
    Long sourceRangeStart;
    Long range;

    public AlmanacMap(List<Long> input) {
        this.destinationRangeStart = input.get(0);
        this.sourceRangeStart = input.get(1);
        this.range = input.get(2);
    }

    boolean check(long value){
        return (value >= sourceRangeStart) && (value-sourceRangeStart) < range;
    }
    long convert(long value){
        long offset = value-sourceRangeStart;
        return destinationRangeStart + offset;
    }

    @Override
    public String toString() {
        return "AlmanacMap{" +
                "destinationRangeStart=" + destinationRangeStart +
                ", sourceRangeStart=" + sourceRangeStart +
                ", range=" + range +
                '}';
    }
}
public class Day5Part1 {
    static List<AlmanacMap> ListMaker(BufferedReader reader) throws IOException {
        List<AlmanacMap> x = new ArrayList<>();
        String line;
        while(!(line = reader.readLine()).isEmpty()){
            x.add(new AlmanacMap(Arrays.stream(line.split("\\s+")).map(Long::parseLong).toList()));
        }
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



            long[] seeds = Arrays.stream(line.split(":\\s+")[1].split("\\s+")).mapToLong(Long::parseLong).toArray();
            Arrays.stream(seeds).forEach(System.out::println);

            while ((line = reader.readLine()) != null){

                if(Headers.SEED_TO_SOIL.value.equals(line)) {
                    seed_soil = ListMaker(reader);
                    seed_soil.forEach(System.out::println);
                }
                else if(Headers.SOIL_TO_FERTILIZER.value.equals(line)) {
                    soil_fertilizer = ListMaker(reader);
                    soil_fertilizer.forEach(System.out::println);
                }
                else if(Headers.FERTILIZER_TO_WATER.value.equals(line)) {
                    fertilizer_water = ListMaker(reader);
                    fertilizer_water.forEach(System.out::println);
                }
                else if(Headers.WATER_TO_LIGHT.value.equals(line)) {
                    water_light = ListMaker(reader);
                    water_light.forEach(System.out::println);
                }
                else if(Headers.LIGHT_TO_TEMPERATURE.value.equals(line)) {
                    light_temperature = ListMaker(reader);
                    light_temperature.forEach(System.out::println);
                }
                else if(Headers.TEMPERATURE_TO_HUMIDITY.value.equals(line)) {
                    temperature_humidity = ListMaker(reader);
                    temperature_humidity.forEach(System.out::println);
                }
                else if(Headers.HUMIDITY_TO_LOCATION.value.equals(line)) {
                    humidity_location = ListMaker(reader);
                    humidity_location.forEach(System.out::println);
                }

            }
            long min = Long.MAX_VALUE;
            for(long seed : seeds){
                Almanac test = new Almanac(seed)
                        .process(seed_soil)
                        .process(soil_fertilizer)
                        .process(fertilizer_water)
                        .process(water_light)
                        .process(light_temperature)
                        .process(temperature_humidity)
                        .process(humidity_location);
                System.out.printf("Location : %d\n",test.value);
                min = Long.min(min,test.value);
            }
            System.out.printf("Minimum : %d\n",min);


            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
