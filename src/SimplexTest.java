
//        // example standardized form
//        float[][] standardized = {
//                {1,1,1,0,4},
//                {1,3,0,1,6},
//                {-3,-5,0,0,0}
//          };

public class SimplexTest {

    public static void main(String[] args) {

        boolean quit = false;

        // Example problem:
        // maximize 3x + 5y
        // subject to x +  y = 4 and
        //            x + 3y = 6
//        double[][] standardized = {
//                {8, 12, -1, 0, 0, 0, 0, 0},
//                {2, 4, -2, 1, 0, 0, 0, 400},
//                {0.6, 0.2, 0, 0, 1, 0, 0, 65},
//                {2, 4, 0, 0, 0, 1, 0, 1000},
//                {0, 0, 1, 0, 0, 0, 1, 250}
//
//        };
        double m = 10000;
//        double[][] standardized = {
//                {-6 * m + 400, -0.8 * m + 65, -6 * m + 1000, 250, m, m, 0, 0, 0, -20 * m},
//                {2, 0.6, 2, 0, -1, 0, 0, 1, 0, 8},
//                {4, 0.2, 4, 0, 0, -1, 0, 0, 1, 12},
//                {2, 0, 0, -1, 0, 0, 1, 0, 0, 1},
//        };
        // метод штрафов
        double[][] standardized = {
                {-4 - m, -1 - 4 * m, m, 0, 0, 0, 0, -4 * m},
                {1, 4, -1, 0, 0, 0, 1, 4},
                {1, -2, 0, 1, 0, 0, 0, 8},
                {0, 1, 0, 0, 1, 0, 0, 3},
                {1, 0, 0, 0, 0, 1, 0, 10}

        };
//
//        // row and column do not include
//        // right hand side values
//        // and objective row
//        Simplex simplex = new Simplex(4, 6);

//        double[][] stand = {
//                {2, 4, -2, 1, 0, 0, 0, 400},
//                {0.6f, 0.2f, 0, 0, 1, 0,0 ,65},
//                {2, 4, 0, 0, 0, 1, 0,1000},
//                {0, 0, 1, 0, 0, 0, 1, 250},
//                {8, 12, -1, 0, 0, 0, 0, 0}
//
//        };

        // row and column do not include
        // right hand side values
        // and objective row
        Simplex simplex = new Simplex(standardized.length - 1, standardized[0].length - 1);


        simplex.fillTable(standardized);

        // print it out
        System.out.println("Начальная таблица");
        simplex.print();

        int c = 0;
        while (!quit) {
            Simplex.ERROR err = simplex.compute();

            if (err == Simplex.ERROR.IS_OPTIMAL) {
                System.out.println("Решение");
                simplex.print();
                quit = true;
            } else if (err == Simplex.ERROR.UNBOUNDED) {
                System.out.println("Нет решения");
                quit = true;
            }


            c++;
            if (c == 20) {
                System.out.println("Количество итераций больше 20");
                break;
            }
        }
    }
}