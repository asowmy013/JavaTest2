package StreamandLambda;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamLambda {

    ArrayList<String> arr = new ArrayList<String>();

    ArrayList<String> arr1 = new ArrayList<String>();

    public void StreamExample()
    {
        arr.add("America");
        arr.add("Austrilia");
        arr.add("Brazila");
        arr.add("Europe");
        arr1.add("Sweden");
        arr1.add("Denmark");
        arr1.add("Srilanka");
        arr1.add("Nepal");
        arr1.add("America");
        arr1.add("Austrilia");




        System.out.println(arr.stream().filter(s -> arr.contains("Srilanka")).count());

        //print all the name

        arr.stream().filter(s -> s.length() > 4 ).limit(2).forEach(s-> System.out.println(s));

        arr.stream().filter(s-> s.startsWith("S")).map(s-> s.replace("S" , "This")).forEach(s-> System.out.println(s));

        arr.stream().map(s-> s.toLowerCase()).sorted().forEach(s-> System.out.println(s));

        Stream<String> newStream =  Stream.concat(arr.stream(),arr1.stream());

        System.out.println(newStream.anyMatch(s -> s.equalsIgnoreCase("Srilanka")));

        Stream<String> newStream1 =  Stream.concat(arr.stream(),arr1.stream());
        List<String> abc = newStream1.filter(s-> s.startsWith("S")).collect(Collectors.toList());

        for (String li : abc){

            System.out.println(li);



        }


        }


        }



