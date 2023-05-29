package ProgramForPractice;

public class program2 {

public void findLargestElementArray()
{
    int[] a = {1,2,6,4,30,4,7,8};
    int max = a[0] ;

    for(int i = 1 ; i < a.length ; i++)
    {
       if(a[i] > max)
       {
            max = a[i];
       }


    }
    System.out.println(max);



}



}
