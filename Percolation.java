/* *************************************************************************************************
 *  Compilation : javac Percolation.java
 *  Dependencies : None
 *
 *  Models a percolation system. All sites are initially blocked. Calls to open(int row, int col)
 *  are used open a particular site. Provides methods to check if a site is open, full or if the
 *  system percolates. Used in PercolationStats.java to find the percolation threshold.
 ************************************************************************************************ */

public class Percolation {

    // uses union - find data type to model the sites
    private final UF id;
    // to note whether a site is open or blocked
    private boolean[][] status;
    // to denote the size of our grid
    private final int n;
    // to denote number of opensites
    private int opensites;

    // Weighted Union Find implementation by Robert Sedgewick and Kevin Wayne @ Algorithms 4e
    private static class UF {
        private int[] parent;   // parent[i] = parent of i
        private int[] size;     // size[i] = number of elements in subtree rooted at i
        private int count;      // number of components

        public UF(int n) {
            count = n;
            parent = new int[n];
            size = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        public int count() {
            return count;
        }

        public int find(int p) {
            while (p != parent[p])
                p = parent[p];
            return p;
        }

        public boolean connected(int p, int q) {
            return find(p) == find(q);
        }

        public void union(int p, int q) {
            int rootP = find(p);
            int rootQ = find(q);
            if (rootP == rootQ) return;

            // make smaller root point to larger one
            if (size[rootP] < size[rootQ]) {
                parent[rootP] = rootQ;
                size[rootQ] += size[rootP];
            }
            else {
                parent[rootQ] = rootP;
                size[rootP] += size[rootQ];
            }
            count--;
        }
    }

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n < 1) throw new IllegalArgumentException(n + " must be greater than zero.");
        id = new UF(n * n + 2);
        status = new boolean[n][n];
        this.n = n;
        opensites = 0;
    }

    // returns address of (row, col) in id's integer array
    private int address(int row, int col) {
        if (row == 0) return 0;
        if (row == n + 1) return n * n + 1;
        return (row - 1) * n + col;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || col < 1 || row > n || col > n) {
            throw new IllegalArgumentException("Value not between 1 and " + n);
        }

        if (status[row - 1][col - 1]) return;

        opensites++;

        status[row - 1][col - 1] = true;
        
        if (row == 1) {
        	id.union(0, address(row, col));
	    }
        if (row > 1 && status[row - 2][col - 1]) {
        	id.union(address(row, col), address(row - 1, col));
	    }
        if (row < n && status[row][col - 1]) {
        	id.union(address(row, col), address(row + 1, col));
	    }
        if (col > 1 && status[row - 1][col - 2]) {
            id.union(address(row, col), address(row, col - 1));
        }
        if (col < n && status[row - 1][col]) {
            id.union(address(row, col), address(row, col + 1));
        }
        if (row == n) {
            id.union(n * n + 1, address(row, col));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || col < 1 || row > n || col > n) {
            throw new IllegalArgumentException("Value should be greater than zero");
        }

        return status[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || col < 1 || row > n || col > n) {
            throw new IllegalArgumentException("Value should be greater than zero");
        }

        if (!status[row - 1][col - 1])
            return false;

        return id.find(address(row, col)) == id.find(0);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return opensites;
    }

    // does the system percolate?
    public boolean percolates() {
        return id.find(n * n + 1) == id.find(0);
    }
}

