package ProgramForPractice;

public class leapYear {


    public void LeapYear(int year)
    {
        //leap year is divisible by 4
        boolean a = false,b = false,c = false;

        if(year % 4 == 0){
            a = true;
        }

        if(year % 100 == 0){
            b = true;
        }

        if(year % 400 == 0){
            c = true;
        }


        if(a && (!b || c))
        {
            System.out.println(year);
            System.out.println("This is a leap year");

        }
        else
        {
            System.out.println("This is not a leap year");
        }


    }


    public void LeapYear2(int year)
    {

        boolean divisibleBy4 = (year % 4 == 0);

        // Leap year is not divisible by 100, except if it is divisible by 400
        boolean divisibleBy100 = (year % 100 == 0);
        boolean divisibleBy400 = (year % 400 == 0);

        // Check if it meets the leap year conditions
        if (divisibleBy4 && (!divisibleBy100 || divisibleBy400)) {
            System.out.println(year);
            System.out.println("This is a leap year");

        } else {
            System.out.println("This is not a leap year");
        }
    }
}
