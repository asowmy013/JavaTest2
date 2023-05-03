import java.util.ArrayList;
import java.util.List;

public class SortingArray {



    public void ArrayDeclaration()
    {

        String[] a = new String[3];
        a[0]= "abc";
        //convert string to array

        String data1 = "This is a string";

        char b[] = data1.toCharArray();

        ArrayList<String> abc = new ArrayList<String>();

        abc.add("TEst1");
        abc.add("TEst2");
        abc.add("TEst3");
        abc.add("TEst4");

        List<String> k1 = new ArrayList<String>();
        k1.addAll(abc);

        List<String> k2 = new ArrayList<String>();
        k2.addAll(abc);

        String[] k4 =k2.toArray(new String[k2.size()]);

        ArrayList<List> k3 = new ArrayList<List>();

        k3.add(k1);
        k3.add(k2);



    }
}
