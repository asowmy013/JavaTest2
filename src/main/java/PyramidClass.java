public class PyramidClass {


    public void Pyramid(int pyr, int num) {

        int k = 1;

        for(int i = 0 ; i < pyr  ;i++)
        {

            for(int j = 1 ; j <= pyr-i ; j++ )
            {

                System.out.print(k + " ");

                k++;
            }
            System.out.println("\t ");



        }



    }





}
