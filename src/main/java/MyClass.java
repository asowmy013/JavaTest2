import CollectionsProgram.ArrayListExample;
import CollectionsProgram.HashMapExample;
import CollectionsProgram.HashSetExample;
import ProgramForPractice.leapYear;
import ProgramForPractice.program2;
import ProgramForPractice.reverseString;
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

        PyramidClass obj =  new PyramidClass();
       obj.Pyramid(3,3);
      // obj.LeapYear2(2024);




    }
}
