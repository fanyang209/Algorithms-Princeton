import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.Stack;

/**
 * Created by richard on 11/2/2015.
 */
public class Solver {

    private MinPQ<Node> btree;
    private MinPQ<Node> btreeTwin;
    private Node solution;

    private static class Node implements Comparable<Node>{

        private Board board;
        private Node pre;
        private int moves;

        Node(Board board,int moves,Node pre){
            this.board = board;
            this.moves = moves;
            this.pre = pre;
        }

        @Override
        public int compareTo(Node o) {
            int priority1 = this.board.manhattan()+this.moves;
            int priority2 = o.board.manhattan()+o.moves;

            if (priority1==priority2)
                return o.moves - this.moves;
            else
            return priority1 - priority2;
        }

        public boolean equals(Object obj) {
            if (obj == this)
                return true;

            if (obj== null)
                return false;

            if (obj.getClass()!= this.getClass())
                return false;

            Node n = (Node) obj;
            return  this.compareTo(n)==0;
        }
    }



    public Solver(Board initial){

        if (initial == null)
            throw new NullPointerException();

        Node n = new Node(initial,0,null);
        Node nt = new Node(initial.twin(),0,null);

        btree = new MinPQ<Solver.Node>();
        btreeTwin = new MinPQ<Solver.Node>();

//        insert initial node into the PQ
        btree.insert(n);
        btreeTwin.insert(nt);

        while (true){
//            delete the node with minimum priority
            Node min = btree.delMin();
            Node minTwin = btreeTwin.delMin();

            solution = min;

            if (min.board.isGoal())
                break;
            else if (minTwin.board.isGoal()){
                solution = null;
                break;
            }else {
//                System.out.println("asasa");
                int moves = min.moves;
                moves++;
// insert all neighbors into btree
                for (Board nb : min.board.neighbors()){
                    if (min.pre == null || !nb.equals(min.pre.board))
                        btree.insert(new Node(nb,moves,min));
                }

                for(Board nb: minTwin.board.neighbors()){
                    if (minTwin.pre == null || !nb.equals(minTwin.pre.board))
                        btreeTwin.insert(new Node(nb,moves,minTwin));
                }
            }
        }

    }

    public boolean isSolvable(){
       return solution != null;
    }

    public int moves(){
        if (solution == null)
            return -1;
        else
            return solution.moves;
    }

    public Iterable<Board> solution(){

        Stack<Board> ret = new Stack<Board>();
        if (solution == null)
            return null;

        ret.push(solution.board);
        Node pre = solution.pre;
        if (pre !=null){
            ret.push(pre.board);
            while ((pre = pre.pre) != null)
                ret.push(pre.board);
        }
        return ret;
    }

    public static void main(String[] args){

        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);

        }
    }
}