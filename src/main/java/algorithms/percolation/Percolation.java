package algorithms.percolation;

/**
 * @author Cui
 * @create 2020-02-05 13:13
 **/

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    /**
     * 避免backwash问题：
     * 因为所有底层site都与bottom相连
     * 所以只要一个底层site为full，其他底层site均变为full状态
     */
    private final WeightedQuickUnionUF sandwichUF; //申
    private final WeightedQuickUnionUF umbrellaUF; //由
    private final int topSite;
    private final int bottomSite;
    private final int edge;
    /**
     * store open state
     */
    private boolean[] openMatrix;
    private int openNum = 0;
    private final int[][] directions = {{0,1}, {0,-1}, {1,0}, {-1, 0}};

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n){
        if(n<=0){
            throw new IllegalArgumentException("size " + n + " out of bounds");
        }
        sandwichUF = new WeightedQuickUnionUF(n*n+2);
        umbrellaUF = new WeightedQuickUnionUF(n*n+1);
        topSite =n*n;
        bottomSite =n*n+1;
        edge=n;
        openMatrix = new boolean[n*n];
    }

    /**
     * row and col is in [1,n]
     * @param row
     * @param col
     */
    // opens the site (row, col) if it is not open already
    public void open(int row, int col){
        if(isOpen(row, col)){
            return;
        }
        /**
         * a new open site
         */
        int idx = getIdx(row, col);
        openMatrix[idx]=true;
        openNum++;
        /**
         * union top or bottom
         */
        if(row==1){
            sandwichUF.union(idx, topSite);
            umbrellaUF.union(idx, topSite);
        }
        if(row==edge){
            sandwichUF.union(idx, bottomSite);
        }
        /**
         * union neighbour which valid and open
         */
        for(int[] dir : directions){
            int r = row+dir[0];
            int c = col+dir[1];
            if(r>=1 && r<=edge && c>=1 && c<=edge && isOpen(r, c)){
                sandwichUF.union(idx, getIdx(r, c));
                umbrellaUF.union(idx, getIdx(r, c));
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col){
        validate(row, col);
        int idx = getIdx(row, col);
        return openMatrix[idx];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col){
        validate(row, col);
        int idx = getIdx(row, col);
        if(isOpen(row, col)) {
            return umbrellaUF.find(idx) == umbrellaUF.find(topSite);
        }
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites(){
        return openNum;
    }

    // does the system percolate?
    public boolean percolates(){
        return sandwichUF.find(topSite)== sandwichUF.find(bottomSite);
    }

    // test client (optional)
    public static void main(String[] args){

    }

    private void validate(int row, int col){
        if (row<1 || row>edge || col<1 || col>edge){
            throw new IllegalArgumentException("wrong coordinate 【" + row + "," + col + "】");
        }
    }

    private int getIdx(int row, int col){
        return edge*(row-1) + col-1;
    }
}
