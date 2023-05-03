import examples1.InterfaceExample;

public class ExampleInter implements InterfaceExample {


    @Override
    public void Red() {

        System.out.println("REd");

    }

    @Override
    public void Green() {

        System.out.println("Green");

    }


    public void Yellow() {


        System.out.println("Yellow");
    }


    public void NonInterfaceMethod()
    {

        System.out.println("NonInterfaceMethod");
    }
}
