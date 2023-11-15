
//        // example standardized form
//        float[][] standardized = {
//                {1,1,1,0,4},
//                {1,3,0,1,6},
//                {-3,-5,0,0,0}
//          };

public class SimplexTest {

    public static void main(String[] args) {
//        penaltyMethod();
        dualMethod1();
        dualMethod2();

    }

//    / {-4 - m, -1 - 4 * m, m, 0, 0, 0, 0, -4 * m},

    static void penaltyMethod() {
        double m = 10000;
        double[][] standardized = {
                {-2 - m, 4 - 4 * m, m, 0, 0, 0, 0, -4 * m},
                {1, 4, -1, 0, 0, 0, 1, 4},
                {1, -2, 0, 1, 0, 0, 0, 8},
                {0, 1, 0, 0, 1, 0, 0, 3},
                {1, 0, 0, 0, 0, 1, 0, 10}

        };
        String[] basicVars = {"r1", "y2", "y3", "y4"};
        String[] allVars = {"x1", "x2", "y1", "y2", "y3", "y4", "r1"};
        Simplex simplex = new Simplex(standardized, basicVars, allVars);
        simplex.solve();
    }

    static void dualMethod1() {
        System.out.println("Первый шаг");
        double[][] standardized = {
                {-1, -4, 1, 0, 0, 0, 0, -4},
                {1, 4, -1, 0, 0, 0, 1, 4},
                {1, -2, 0, 1, 0, 0, 0, 8},
                {0, 1, 0, 0, 1, 0, 0, 3},
                {1, 0, 0, 0, 0, 1, 0, 10}

        };
        String[] basicVars = {"r1", "y2", "y3", "y4"};
        String[] allVars = {"x1", "x2", "y1", "y2", "y3", "y4", "r1"};
        Simplex simplex = new Simplex(standardized, basicVars, allVars);
        simplex.solve();

    }

    static void dualMethod2() {
        System.out.println("Второй шаг");
        double[][] standardized = {
                {-3, 0, 1, 0, 0, 0, -4},
                {0.25, 1, -0.25, 0, 0, 0, 1},
                {1.5, 0, -0.5, 1, 0, 0, 10},
                {-0.25, 0, 0.25, 0, 1, 0, 2},
                {1, 0, 0, 0, 0, 1, 10}

        };
        String[] basicVars = {"x2", "y2", "y3", "y4"};
        String[] allVars = {"x1", "x2", "y1", "y2", "y3", "y4"};
        Simplex simplex = new Simplex(standardized, basicVars, allVars);
        simplex.solve();
    }
}