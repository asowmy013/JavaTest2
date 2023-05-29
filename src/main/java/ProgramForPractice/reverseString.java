package ProgramForPractice;


import java.lang.reflect.Array;
import java.util.HashMap;

public class reverseString {

    public void ReverseString()
    {
        String a = "Automation";

        String res = "" ;

        for(int i = a.length() -1   ; i >= 0 ; i--)
        {
            res = a.charAt(i) +  res ;


        }

    System.out.println(res);

    }

    public void CountNumberOfWordsInaString(){

        String a = "This is a Automation Script";

        String[] b =  a.split(" ");

        int count = b.length ;

        System.out.println(count);




    }

    public void FindDuplicates()
    {
        String a = "This is a string";
        int count = 0 ;

        char[] b = a.toCharArray();

        for(int i = 0 ; i <= a.length()-1 ; i++)
        {
            for(int j = i + 1 ; j <=  a.length() -1; j++)
            {
                if(b[i]== b[j])
                {
                   System.out.println(b[i]);
                }

            }
        }


    }

    public void FindDuplicate2() {

        String a = "This is a Automation Program";

        char[] b = a.toCharArray();
        HashMap<String, Integer> value = new HashMap<String, Integer>();

        for (int i = 0; i <= a.length() - 1; i++) {
            int count = 0;
            for (int j = i + 1; j <= a.length() - 1; j++) {
                if (b[i] == b[j]) {
                    count = count + 1;
                }
            }
            if (count > 0) {
                value.put(String.valueOf(b[i]), count);
            }
        }

        // Print the duplicate characters and their counts
        for (String key : value.keySet()) {
            System.out.println("Character: " + key + ", Count: " + value.get(key));
        }
    }


    public void OccuranceofEachCharacter()
    {
        String a = "Test Automation";
        HashMap<String , Integer> occurance = new HashMap<>();

        char[] arr = a.toCharArray();

        for (char b:arr  ) {
            if (b != ' ') {

                String g = String.valueOf(arr[b]);
                if (occurance.containsKey(g)) {

                    occurance.put(String.valueOf(b), occurance.get(b) + 1);

                } else {
                    occurance.put(String.valueOf(b), 1);
                }
            }

        }


        for (String  n : occurance.keySet()  ) {

            int c = occurance.get(n);
            if(c > 1){

                System.out.println(occurance.get(n));
            }



        }




    }
    public void Occurance(){
        {
            String a = "This is a Automation Program";

            HashMap<Character, Integer> charCountMap = new HashMap<>();

            // Convert the string to char array
            char[] charArray = a.toCharArray();

            // Iterate through each character
            for (char c : charArray) {
                // Ignore whitespace
                if (c != ' ') {
                    // Check if the character already exists in the map
                    if (charCountMap.containsKey(c)) {
                        // Increment the count by 1
                        charCountMap.put(c, charCountMap.get(c) + 1);
                    } else {
                        // Add the character to the map with count 1
                        charCountMap.put(c, 1);
                    }
                }
            }

            // Print the duplicate characters and their counts
            for (char c : charCountMap.keySet()) {
                int count = charCountMap.get(c);
                if (count > 1) {
                    System.out.println("Character: " + c + ", Count: " + count);
                }
            }
        }
    }


}

