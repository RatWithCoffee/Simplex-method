import java.util.Arrays;

public class DualSimplexTest {

    public static void main(String[] args) {
        double[][] standardized = {
                {0, 0, -1, 0, 0, -3, 0, -9},
                {1, 0, 0, 0, 0, 1, 0, 10},
                {0, 0, -0.5, 1, 0, -1.5, 0, 0.5},
                {0, 1, -0.25, 0, 0, -0.25, 0, 2.75},
                {0, 0, 0.25, 0, 1, 0.25, 0, 0.25},
                {0, 0, -0.75, 0, 0, -0.75, 1, -0.75}
        };
        String[] basicVars = {"x1", "y2", "x2", "y3", "s1"};
        String[] allVars = {"x1", "x2", "y1", "y2", "y3", "y4", "s1"};
        DualSimplex simplex = new DualSimplex(standardized, basicVars, allVars);
        simplex.solve();
    }

    static double[][] getSt(double[][] solution, double[] newRestriction) {
        double[][] st2 = new double[solution.length + 1][solution[0].length + 1];
        for (int i = 0; i < solution.length; i++) {
            System.arraycopy(solution[i], 0, st2[i], 0, solution[0].length - 1);
            st2[i][st2[0].length - 1] = solution[i][solution[0].length - 1];
        }

        st2[st2.length - 1] = newRestriction;

        return st2;
    }

}