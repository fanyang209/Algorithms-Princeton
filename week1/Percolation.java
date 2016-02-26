import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Created by richard on 9/13/2015.
 */
public class Percolation {


    private int n;
    private  WeightedQuickUnionUF uf;

    private int[][] squre;

    private  boolean[][] blocked;
    private int virtualtop;
    private int virtualbottom;

    public Percolation(int N) {
//       boolean[][] blocked= new boolean[N][N];

        try {


            blocked = new boolean[N + 1][N + 1];
            squre = new int[N + 1][N + 1];
            this.n = N;
            uf = new WeightedQuickUnionUF(N * N + 2);
            virtualtop = N * N;
            virtualbottom = N * N + 1;


            for (int i = 1; i <= N; i++)
                for (int j = 1; j <= N; j++) {
                    squre[i][j] = j - 1 + (i - 1) * N;
//                System.out.println(squre[i][j]);
                }

            for (int i = 1; i <= N; i++) {
                for (int j = 1; j <= N; j++) {

                    blocked[i][j] = true;
                }
            }

            for (int i = 1; i <= N; i++) {
                uf.union(virtualtop, squre[1][i]);
//                uf.union(virtualbottom, squre[N][i]);
            }
        }catch (Exception e) {
            System.out.println("java.lang.IllegalArgumentException");

        }
    }

   /* public void block() {

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {

                blocked[i][j] = true;
            }

        }
    }*/

    public boolean isOpen(int i, int j) {

//        System.out.println(blocked[i][j]);
        try {
            if (blocked[i][j]) {
                return false;
            }

        }catch (Exception e){
            System.out.println("java.lang.IndexOutOfBoundsException");

        }
        return true;
    }

    public void open(int i, int j) {

        try {
            if (!isOpen(i, j))
                blocked[i][j] = false;

            if (j - 1 >= 1 && isOpen(i, j - 1)) {
                uf.union(squre[i][j], squre[i][j - 1]);
//            System.out.println("left");
            }

            if (j + 1 <= n && isOpen(i, j + 1)) {
                uf.union(squre[i][j], squre[i][j + 1]);
//            System.out.println("right");
            }

            if (i - 1 >= 1 && isOpen(i - 1, j)) {
                uf.union(squre[i][j], squre[i - 1][j]);
//            System.out.println("up");
            }

            if (i + 1 <= n && isOpen(i + 1, j)) {
                uf.union(squre[i][j], squre[i + 1][j]);
//            System.out.println("down");
            }

            if(i==n && isFull(i,j))
                uf.union(virtualbottom, squre[i][j]);

        }catch (Exception e){
            System.out.println("java.lang.IndexOutOfBoundsException");

        }

    }

    public boolean isFull(int i, int j) {

//        System.out.println(squre[i][j]);
            try {

                if (isOpen(i, j) && uf.connected(squre[i][j], virtualtop))
               /* if(percolates())
                    while()
        && squre[i][j]!= uf.parent[virtualbottom]*/
                    return true;


            }catch (Exception e){
                System.out.println("java.lang.IndexOutOfBoundsException");

            }
                return false;


    }


    public boolean percolates() {

        if (n==1) {
            if (isOpen(1, 1))
                return true;
        }

        else if (uf.connected(virtualtop, virtualbottom))
            return true;

        return false;
    }
}