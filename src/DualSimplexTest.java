
public class DualSimplexTest {

    public static void main(String[] args) {
//        double[][] standardized = {
//                {400, 65, 1000, 250, 0, 0, 0, 0},
//                {-2, -0.6, -2, 0, 1, 0, 0, -8},
//                {-4, -0.2, -4, 0, 0, 1, 0, -12},
//                {2, 0, 0, -1, 0, 0, 1, 1}
//        };
        double[][] standardized = {
                {2, 8, 5, 0, 0, 0},
                {-2, -1, 1, 1, 0, 1},
                {-1, -2, -1, 0, 1, -1}
        };
        String[] basicVars = {"y1", "y2"};
        String[] allVars = {"l1", "l2", "l3", "y1", "y2"};
        DualSimplex simplex = new DualSimplex(standardized, basicVars, allVars);
        simplex.solve();
    }


}