package CollectionsProgram;


import java.util.ArrayList;
import java.util.List;

public class ArrayListExample {

    public void ArrayListMethod(){

        ArrayList<String> a = new ArrayList<String>();

        a.add("Sowmya");
        a.add("Srikanth");
        a.add("Anke");

        ArrayList<String> b = new ArrayList<String>();

        b.addAll(a);

        System.out.println(b);

        b.addAll(1,a);

        if(a.contains("Sowmya"))
        {
            System.out.println("Checked Contains Stmt");
        }
        System.out.println(b);


        for(int i = 0 ; i< b.size() ; i++){
            System.out.println(b.get(i));

        }

        ArrayList<Integer> c = new ArrayList<Integer>();

        boolean checkArray = c.isEmpty();

        System.out.println(checkArray);

        c.add(100);
        c.add(200);
        c.add(300);
        c.add(400);

        System.out.println(c.indexOf(200));

        c.remove(0);

        System.out.println(c);


        List<Integer> d = new ArrayList<Integer>();

        d.add(123);
        d.add(2);
        d.add(5);
        d.add(70);


        int temp = 0;
        for(int k = 0 ; k <d.size()-1 ; k++)
        {
            for(int l = 0 ; l < d.size()-1 ; l++ )
            {
                if(d.get(l) <  d.get(l+1 ))
                {
                    temp = d.get(l);
                    d.set( l ,d.get(l+1));
                    d.set(l+1 , temp);


                }
        }
        System.out.println(d);

        }




//        add(E element): adds an element to the end of the list.
//         add(int index, E element): adds an element at a specific index in the list.
//        addAll(Collection<? extends E> c): adds all the elements of the specified collection to the end of the list.
//        addAll(int index, Collection<? extends E> c): adds all the elements of the specified collection starting from the specified index.
//         clear(): removes all the elements from the list.
//                contains(Object o): returns true if the list contains the specified element.
//        get(int index): returns the element at the specified index in the list.
//        indexOf(Object o): returns the index of the first occurrence of the specified element in the list, or -1 if it is not found.
//        isEmpty(): returns true if the list is empty.
//        remove(Object o): removes the first occurrence of the specified element from the list.
//                remove(int index): removes the element at the specified index from the list.
//        size(): returns the number of elements in the list.


    }


}
