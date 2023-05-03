import CollectionsProgram.ArrayListExample;
import CollectionsProgram.HashMapExample;
import CollectionsProgram.HashSetExample;
import StreamandLambda.StreamLambda;
import examples1.NewChild;

public class MyClass {

    public String CheckMethod() {
        String a = "This is the new world";
        return a;


    }

    public void CheckWhileloop() {
        int i = 0;
        while (i <= 10) {

            i = i + 1;
        }


    }

    //pring the number from 20 to 30
    public void DoWhileLoop() {
        int i = 20;
        do {
            System.out.println("printing  i from 20 to 30 " + i);

            i++;
        } while (i < 30);


    }

    public static void main(String[] args) {

        StreamLambda obj =  new StreamLambda();
       obj.StreamExample();




        System.out.println("Hello");
//println will provide new line
        System.out.println("This is the new changes");

        System.out.println("a");


    }
}
