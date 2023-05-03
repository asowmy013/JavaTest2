package CollectionsProgram;

import java.util.*;

public class HashMapExample {

    public void HashMapExample()
    {

        HashMap<Integer , String> hash = new  HashMap<Integer , String>();
        hash.put(1,"Laptop");
        hash.put(2,"Mouse");
        hash.put(3,"Keyboard");
        hash.put(4,"HardDisk");
        hash.put(3,"Keyboard");
        hash.put(4 , "");

        for(int i =1 ; i <= 4 ; i++)
        {

            System.out.println(hash.get(i));
            if(hash.get(i) == "")
            {
                //hash.remove(i);
                hash.size();

            }
            System.out.println(hash.get(i));

        }

        HashMap<Integer , String> hash1 = new HashMap<Integer , String>();

        hash1.put(1,"abc");
        hash1.put(2,"def");
        hash1.put(3,"hij");


        Set sn  = hash1.entrySet();
        Iterator it = sn.iterator();


        while (it.hasNext()){


            Map.Entry mp = (Map.Entry)it.next();
            System.out.println(mp.getKey());
            System.out.println(mp.getValue());


        }








    }

    public void CountOccurance()
    {

        int count = 1 ;
        String a = "Tthis is a String";
        a = a.toLowerCase();

        char[] b = a.toCharArray();


        HashMap<String,Integer> occ = new HashMap<String,Integer>();



        for(int i = 0 ; i < b.length; i++)
        {

            String c = String.valueOf(b[i]);
            System.out.println(b[i]);

           if(occ.containsKey(c) == false){

               occ.put(c,count);
               System.out.println("Value is updated in occ");


           }
           else
           {
            occ.replace(c,count+1);
           }


        }

        for(Map.Entry<String , Integer> mp : occ.entrySet() )
        {
            System.out.println(mp.getKey() + "  occurance of   " + mp.getValue());
        }






    }



    public void ArrayElements()
    {
        int a[] = {4,4,4,5,5,5,4,6,6,9,4};
        int count = 1;

   HashMap<Integer , Integer> aa = new HashMap<Integer, Integer>();

   for(int i = 0 ; i< a.length ; i++)
   {
       int  l = a[i];
       if(aa.containsKey(a[i])==false)
       {
           aa.put(l, count);
       }

       else

       {

           aa.replace(l, aa.get(l)+ 1  );
       }



   }
        for(Map.Entry<Integer,Integer> mp : aa.entrySet()){

            System.out.println(mp.getKey() + " count is " + mp.getValue());
        }




    }



}
