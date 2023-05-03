public class PyramidClass {


    public void Pyramid(int pyr, int num) {

        int k = 1;

        for(int i = 0 ; i < 4  ;i++)
        {

            for(int j = 1 ; j <= 4-i ; j++ )
            {

                System.out.print(k + " ");

                k++;
            }
            System.out.println("\t ");



        }



    }





}
