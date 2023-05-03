public class SmallestNumberMatrix {

    public void PrintSmallestNumber()
    {
        int a[][] = {{2,4,5},{3,0,7},{1,2,9}};

        int min  = a[0][0];
        int i = 0 ; int  j = 0;
        int max = 0;

        for(i = 0 ; i < a.length ; i++)
        {
            for( j= 0 ; j < a.length ; j++)
            {
                if(a[i][j] < min)
                {
                    min = a[i][j];
                    max = j;

                }
            }
        }
        System.out.println(min);
             System.out.println(FindMaximum(max,a));

    }


    public int  FindMaximum(int j , int a[][])
    {

        int max = 0;

        for(int i = 0 ; i < a.length ; i++)
        {
             if( a[i][j] > max)
            {
                max = a[i][j];
            }
        }
        return  max;

    }

}
