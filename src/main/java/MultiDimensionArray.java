public class MultiDimensionArray {

    public void MultiDimensionArray()
    {
        int a[][] = new int[3][3];
        int k =1 ;

        for( int i = 0 ; i < a.length ; i++)
        {
            for( int j = 0 ; j < a.length ; j++){

                a[i][j] = k;
                k++;

                System.out.print(a[i][j] + " ");
            }


            System.out.println("");

        }





    }







}
