import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;
import java.util.Stack;

/**
 * Created by richard on 10/28/2015.
 */
public class Board {

//    private int[][] tiles ;
    private int[] blocks;
    private int n;
    private int hamming = -1;

    public Board(int[][] tiles)  {

//    this.tiles = tiles;
        n = tiles.length;
        this.blocks = new int[n*n];


        for (int i=0;i<n;i++)
            for (int j=0;j<n;j++){
                this.blocks[i*n+j] = tiles[i][j];
            }
    }

    public int dimension() {
        return this.n;
    }


//    public int tileAt(int i, int j){
//        if (tiles==null){
//            return 0;
//        }else
//        return tiles[i][j];
//    }


//    public int size(){
//
//        return tiles[0].length *tiles[0].length ;
//    }


    public int hamming(){

        int order = 1;
        int count = 0;


            for (int j=0;j<n*n;j++){
                if (blocks[j]!= 0 && blocks[j]!= order){
//                    System.out.println(order);
                    count++;
                }
                    order++;
            }

        return  count;




    }


    public int manhattan(){

    int i_manhattan =0;


            for (int j=0;j<n*n;j++){
                if (blocks[j]!=0 && blocks[j]!=j+1){
                    i_manhattan += Math.abs((blocks[j]-1)%n -j%n) +
                            Math.abs(Math.floor((blocks[j]-1)/n) -Math.floor(j/n));
                }
            }
        return i_manhattan;
    }



    public boolean isGoal(){
        return hamming() == 0;
    }


//    public boolean isSolvable(){
//
//        int inversions =0;
//        int blank_row = 0;
//
//        if (n%2==1){
//            for (int i=0;i<n;i++)
//                for (int j=i;j<n;j++){
//                    if (tiles[0][0]>tiles[i][j])
//                }
//        }
//
//    }

    public Board twin(){
        while (true){
            int random1 = StdRandom.uniform(n*n);
            int random2 = StdRandom.uniform(n*n);

            if (blocks[random1]!=0 && blocks[random2]!=0  && random1!=random2)
            return getSwappedBoard(random1,random2);
        }

    }

    private Board getSwappedBoard(int o, int n) {
        if (o<0 || n<0 || o>= this.n*this.n || n>= this.n*this.n)
            throw  new IllegalArgumentException();

        int[] bl = Arrays.copyOf(blocks,blocks.length);
        int temp = bl[o];
        bl[o] = bl[n];
        bl[n] = temp;

        int[][] SwappedBoard = new int[ this.n][ this.n];
        for (int i=0;i< this.n;i++)
            for (int j=0;j< this.n;j++){
                SwappedBoard[i][j]= bl[i* this.n+j];
            }

        return new Board(SwappedBoard);
    }


    public boolean equals(Object y) {
        if (y==this)
            return true;
        if (y==null)
            return false;
        if (y.getClass() != this.getClass())
            return false;

        Board that = (Board) y;
        if (this.n != that.n)
            return  false;

        for (int i=0;i<blocks.length;i++)
            if (blocks[i]!= that.blocks[i])
                return false;


        return true;
    }


    public Iterable<Board> neighbors(){

        Stack<Board> neighbor = new Stack<Board>();
        for (int i=0;i<n*n;i++ )
            if (blocks[i]==0){
                if (i%n-1>=0)
                    neighbor.push(getSwappedBoard(i,i-1));
                if (i%n+1<=n-1)
                    neighbor.push(getSwappedBoard(i,i+1));
                if (i-n>=0)
                    neighbor.push(getSwappedBoard(i,i-n));
                if (i+n<=n*n-1)
                    neighbor.push(getSwappedBoard(i,i+n));
                break;
            }
            return neighbor;

    }


    public String toString(){
        StringBuilder s =new StringBuilder();
        s.append(n+"\n");
        for (int i=0;i<n;i++){
            for (int j=0;j<n;j++)
                s.append(String.format("%2d ", blocks[i*n+j]));
        s.append("\n");
        }
        return s.toString();
    }


    public static void main(String[] args){

        In in = new In(args[0]);
        int N = in.readInt();
        int[][] tiles = new int[N][N];
        for (int i=0;i<N;i++)
            for (int j=0;j<N;j++){
                tiles[i][j] = in.readInt();
            }

        Board initial = new Board(tiles);
        System.out.println(initial);

//        System.out.println("Neighbors:  ");
//        for (Board n: initial.neighbors())
//            System.out.println(n);

        System.out.println("twin:  ");
        System.out.println(initial.twin());

        System.out.println("isgoal:  ");
        System.out.println(initial.isGoal());
    }

}