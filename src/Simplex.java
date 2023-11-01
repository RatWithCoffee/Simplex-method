// находит максимальное значение целевой функции
public class Simplex {
    private final int rows;
    private final int cols;
    private final double[][] table;

    private int count = 1;
//    private boolean solutionIsUnbounded = false;

    public static enum ERROR {
        NOT_OPTIMAL,
        IS_OPTIMAL,
        UNBOUNDED
    }


    // numOfConstraints - количество ограничений
    // numOfUnknowns - количество неизвестных
    public Simplex(int numOfConstraints, int numOfUnknowns) {
        rows = numOfConstraints + 1;
        cols = numOfUnknowns + 1;
        // инициализация таблицы
        table = new double[rows][];
        for (int i = 0; i < rows; i++) {
            table[i] = new double[cols];
        }
    }

    public void solve() {

    }

    // вывод таблицы в консоль
    public void print() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                String value = String.format("%.2f", table[i][j]);
                System.out.print(value + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }

    // заполнение таблицы начальными коэффициентами
    public void fillTable(double[][] data) {
        for (int i = 0; i < table.length; i++) {
            System.arraycopy(data[i], 0, this.table[i], 0, data[i].length);
        }
    }

    // подсчитывает значения в таблице
    // необходимо использовать в цикле до нахождения оптимального решения
    public ERROR compute() {
        // найдем оптимальное решение
        if (isOptimalMax()) {
            return ERROR.IS_OPTIMAL;
        }

        // найдем разрешающий столбец (переменная для включения в базис)
        int pivotColumn = findPivotColumnMax();
        System.out.println("Pivot Column: " + pivotColumn);

        // step 3
        // найдем разрешающую строку (переменная для исключения из базиса)
        int pivotRow = pivotRow(pivotColumn);
        System.out.println("Pivot row: " + pivotRow);

        // составим следующую таблицу
        formNextTableau(pivotRow, pivotColumn);
        System.out.println("Итерация номер: " + count);
        count++;
        print();
        // since we formed a new table so return NOT_OPTIMAL
        return ERROR.NOT_OPTIMAL;
    }

    // составляет следующую таблицу
    private void formNextTableau(int pivotRow, int pivotColumn) {
        double pivotValue = table[pivotRow][pivotColumn];
        System.out.println(pivotValue);
        double[] pivotRowVals = new double[cols];
        double[] pivotColumnVals = new double[cols];
        double[] rowNew = new double[cols];

        // divide all entries in pivot row by entry in pivot column
        // get entry in pivot row
        System.arraycopy(table[pivotRow], 0, pivotRowVals, 0, cols);

        // get entry inpivot colum
        for (int i = 0; i < rows; i++)
            pivotColumnVals[i] = table[i][pivotColumn];

        // divide values in pivot row by pivot value
        for (int i = 0; i < cols; i++)
            rowNew[i] = pivotRowVals[i] / pivotValue;

        // subtract from each of the other rows
        for (int i = 0; i < rows; i++) {
            if (i != pivotRow) {
                for (int j = 0; j < cols; j++) {
                    double c = pivotColumnVals[i];
                    table[i][j] = table[i][j] - (c * rowNew[j]);
                }
            }
        }

        // replace the row
        System.arraycopy(rowNew, 0, table[pivotRow], 0, rowNew.length);
    }

    // находим индекс разрешающей строку (переменная для исключения из базиса)
    private int pivotRow(int pivotCol) {
        double minVal = Double.MAX_VALUE; // минимальное знечение, получаемое при решении уравнений
        int indexMinVal = -1; // индекс новой свободной переменной
//        int negativeCount = 0;
        double partition;
        for (int i = 1; i < rows; i++) {
            partition = table[i][cols - 1] / table[i][pivotCol];
            if (partition > 0 && partition < minVal) {
                minVal = partition;
                indexMinVal = i;
            } else if (partition == 0 && table[i][pivotCol] > 0) {
                indexMinVal = i;
                break;
            }
        }

        return indexMinVal;
    }


    // находит максимальное значение среди коэффициентов в целевой функции
    // возращает его индекс
    private int findPivotColumnMax() {
        double min = Double.MAX_VALUE;
        int index = -1;
        for (int i = 0; i < cols - 1; i++) {
            if (table[0][i] < min) {
                min = table[0][i];
                index = i;
            }
        }

        return index;
    }

    private int findPivotColumnMin() {
        double max = Double.MIN_VALUE;
        int index = -1;
        for (int i = 0; i < cols - 1; i++) {
            if (table[0][i] > max) {
                max = table[0][i];
                index = i;
            }
        }

        return index;
    }

    // проверяет, является ли найденное решение оптимальным
    public boolean isOptimalMax() {
        for (int i = 0; i < cols - 1; i++) {
            if (table[0][i] < 0) {
                return false;
            }
        }

        return true;
    }

    public boolean isOptimalMin() {
        for (int i = 0; i < cols - 1; i++) {
            if (table[0][i] > 0) {
                return false;
            }
        }

        return true;
    }

    public double[][] getTable() {
        return table;
    }
}