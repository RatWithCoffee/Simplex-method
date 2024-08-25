import java.util.Arrays;

// находит максимальное значение целевой функции
public class Simplex {
    private final int rows;
    private final int cols;
    private final double[][] table;
    private final String[] basicVars;
    private final String[] allVars;

    private int count = 1;

    public enum SOLUTION {
        NOT_OPTIMAL,
        IS_OPTIMAL,
        UNBOUNDED
    }


    // numOfConstraints - количество ограничений
    // numOfUnknowns - количество неизвестных
    public Simplex(double[][] standardized, String[] basicVars, String[] allVars) {
        rows = standardized.length;
        cols = standardized[0].length;
        table = Arrays.copyOf(standardized, rows);

        this.basicVars = Arrays.copyOf(basicVars, basicVars.length);
        this.allVars = Arrays.copyOf(allVars, allVars.length);
    }

    public void solve() {
        System.out.println("Начальная таблица");
        printTable();

        int counter = 0;
        boolean quit = false;
        while (!quit) {
            SOLUTION solution = compute();

            if (solution == SOLUTION.IS_OPTIMAL) {
                System.out.println("Решение");
                printTable();
                quit = true;
            } else if (solution == SOLUTION.UNBOUNDED) {
                System.out.println("Нет решения");
                quit = true;
            } else if (counter == 20) {
                System.out.println("Количество итераций больше 20");
                break;
            }

            counter++;
        }
    }


    // меняем местами свободную и базисную переменную
    public void swapVars(int row, int col) {
        String temp = allVars[col];
        basicVars[row] = temp;
        allVars[col] = basicVars[row];
    }

    // вывод таблицы в консоль
    public void printTable() {
        System.out.print("\t\t");
        for (String allVar : allVars) {
            System.out.print(allVar + "\t\t\t");
        }
        System.out.print("b");
        System.out.println();
        for (int i = 0; i < rows; i++) {
            if (i == 0) {
                System.out.print("f\t\t");
            } else {
                System.out.print(basicVars[i - 1] + "\t\t");
            }
            for (int j = 0; j < cols; j++) {
                String value = String.format("%.2f", table[i][j]);
                System.out.print(value + "\t\t");
            }
            System.out.println();
        }
        System.out.println();
    }

    // подсчитывает значения в таблице
    // необходимо использовать в цикле до нахождения оптимального решения
    public SOLUTION compute() {
        // проверим, является ли решение оптимальным
        if (isOptimal()) {
            return SOLUTION.IS_OPTIMAL;
        }

        // найдем разрешающий столбец (переменную для включения в базис)
        int pivotColumn = findPivotColumn();
        System.out.println("Разрешающий столбец: " + pivotColumn);

        // найдем разрешающую строку (переменная для исключения из базиса)
        int pivotRow = findPivotRow(pivotColumn);
        if (pivotRow == -1) {
            return SOLUTION.UNBOUNDED;
        }
        System.out.println("Разрешающая строка: " + pivotRow);

        // меняем обозначения свободной и базисной переменных
        swapVars(pivotRow - 1, pivotColumn);

        // составим следующую таблицу
        formNextTable(pivotRow, pivotColumn);
        System.out.println("Итерация номер: " + count);
        count++;
        printTable();

        return SOLUTION.NOT_OPTIMAL;
    }

    // составляет следующую таблицу
    private void formNextTable(int pivotRow, int pivotColumn) {
        double pivotValue = table[pivotRow][pivotColumn];
        System.out.println("Разрешающий элемент: " + pivotValue);

        double[] pivotRowVals = new double[cols];
        double[] pivotColumnVals = new double[cols];
        double[] rowNew = new double[cols];

        // копируем элементы из разрешающей строки в отдельный массив
        System.arraycopy(table[pivotRow], 0, pivotRowVals, 0, cols);

        // копируем элементы из разрешающего столбца в отдельный массив
        for (int i = 0; i < rows; i++)
            pivotColumnVals[i] = table[i][pivotColumn];

        for (int i = 0; i < cols; i++)
            rowNew[i] = pivotRowVals[i] / pivotValue;

        for (int i = 0; i < rows; i++) {
            if (i != pivotRow) {
                for (int j = 0; j < cols; j++) {
                    double c = pivotColumnVals[i];
                    table[i][j] = table[i][j] - (c * rowNew[j]);
                }
            }
        }

        // копируем в разрешающую строку rowNew
        System.arraycopy(rowNew, 0, table[pivotRow], 0, rowNew.length);
    }

    // находим индекс разрешающей строки (переменная для исключения из базиса)
    private int findPivotRow(int pivotCol) {
        double minVal = Double.MAX_VALUE; // минимальное значение, получаемое при решении уравнений
        int indexMinVal = -1; // индекс новой свободной переменной
        double partition;
        for (int i = 1; i < rows; i++) {
            partition = table[i][cols - 1] / table[i][pivotCol];
            if (partition > 0 && partition < minVal) {
                minVal = partition;
                indexMinVal = i;
            } else if (partition == 0 && table[i][pivotCol] > 0) {
                indexMinVal = i;
                return indexMinVal;
            }
        }

        return indexMinVal;
    }


    // находит минимальное значение среди коэффициентов в целевой функции
    // функция возвращает его индекс
    private int findPivotColumn() {
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

    // проверяет, является ли найденное решение оптимальным
    public boolean isOptimal() {
        for (int i = 0; i < cols - 1; i++) {
            if (table[0][i] < 0) {
                return false;
            }
        }

        return true;
    }

}