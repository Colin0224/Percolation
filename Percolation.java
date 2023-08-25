import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[][] arrayGrid;
    private WeightedQuickUnionUF UF;
    private int n;
    private int counter = 0;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Its too low idiot");
        }
        arrayGrid = new int[n][n];
        UF = new WeightedQuickUnionUF(n * n + 2);
        this.n = n;
        for (int i = 1; i <= n + 1; i++) {
            UF.union(0, i);
        }
        for (int i = 2; i <= n; i++) {
            UF.union(n * (n - 1) + i, n * n + 1);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row <= 0 || row > n || col <= 0 || col > n) {
            throw new IllegalArgumentException("Row and col must be within acceptable range");
        }

        row -= 1;
        col -= 1;
        if (arrayGrid[row][col] != 1) {
            this.counter += 1;
        }
        int translate = row * n + col;

        arrayGrid[row][col] = 1;
        if (row - 1 >= 0 && arrayGrid[row - 1][col] == 1) {
            UF.union(translate - n + 2, translate + 2);
        }
        if (row + 1 < n && arrayGrid[row + 1][col] == 1) {
            UF.union(translate + 2, translate + n + 2);
        }
        if (col + 1 < n && arrayGrid[row][col + 1] == 1) {
            UF.union(translate + 3, translate + 2);
        }
        if (col - 1 >= 0 && arrayGrid[row][col - 1] == 1) {
            UF.union(translate + 1, translate + 2);
        }
    }


    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || col < 1 || row > this.n || col > this.n) {
            throw new IllegalArgumentException(
                    "row and col must be valid indices in the grid");
        }
        return this.arrayGrid[row - 1][col - 1] == 1;
    }


    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row <= 0 || row > n || col <= 0 || col > n) {
            throw new IllegalArgumentException("Row and col must be within acceptable range");
        }
        return UF.find(0) == UF.find((row - 1) * n + col + 1) && arrayGrid[row - 1][col - 1] == 1;
    }


    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.counter;
    }

    // does the system percolate?
    public boolean percolates() {
        return UF.find(0) == UF.find(n * n + 1);
    }


    // test client (optional)
    public static void main(String[] args) {

    }
}
