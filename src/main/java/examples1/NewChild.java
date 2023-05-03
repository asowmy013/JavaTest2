package examples1;

public class NewChild extends NewParent{

    String a = "Srikanth";

    public void SuperKeyword()
    {
        System.out.println(super.a);
        System.out.println("Child string" + a );

    }

    public void Super1()
    {
        System.out.println("Super1 child ");
        super.Super1();
    }


}
