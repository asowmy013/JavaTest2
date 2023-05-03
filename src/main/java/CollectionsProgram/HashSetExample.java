package CollectionsProgram;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class HashSetExample {


    //Hashset,treeset ,LinkedHashSet implements set interface
    //This will not accept the duplicate values
    //The order opf the values stores can not be predicted in hash set
    //here we don't have option to set the value in the particular index

    //--- Array List --
    //when we want add the duplicate values then we we need use atray list and linked list for example any e-commerce website
    //we have option to set/get/remove the values on any index


    public void HashSetExample()
    {

        HashSet<String> a =  new HashSet<String>();
        a.add("karnataka");
        a.add("Kerala");
        a.add("New Delhi");
        a.add("Kerala");
        a.add("TamilNadu");


        System.out.println(a);

        Iterator<String>  it = a.iterator();

        System.out.println(it.next());
        while (it.hasNext())
        {
            System.out.println(it.next());

        }



    }



    


}
