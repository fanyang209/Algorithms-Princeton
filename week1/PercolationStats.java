import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

import java.util.Scanner;

/**
 * Created by richard on 9/13/2015.
 */
public class PercolationStats {
    private int n;
    private int t;



    private  double[] x;

    public PercolationStats(int N, int T) {
        this.n = N;
        this.t = T;
        int random_i;
        int random_j;
//        System.out.println(n);

        x = new double[T];
        double count;



        for (int i = 0; i < T; i++) {

             count = 0.0;
            Percolation p = new Percolation(N);

            while (true) {



                random_i = StdRandom.uniform(1, N+1);
                random_j = StdRandom.uniform(1, N+1);


//                System.out.println(random_i + ", " + random_j);
//                System.out.print(random_j);
//                System.out.println(p.blocked[0][0]);

                if (!p.isOpen(random_i, random_j)) {

                    p.open(random_i, random_j);

                    count++;


                    }


                if (p.percolates())
                    break;
            }

//            System.out.println(count+"adfd");
            x[i] = count / (N*N);

//            System.out.println(count / (N*N)+"fgh");
        }




    }


    public  double mean() {

   return  StdStats.mean(x);

        }

    public  double stddev() {

        return StdStats.stddev(x);
    }

    public  double confidenceLo() {

        return mean()-1.96*stddev()/Math.sqrt(t);
    }

    public  double confidenceHi() {

        return mean()+1.96*stddev()/Math.sqrt(t);
    }


    public static void main(String[] args) {

            System.out.println("please input the N and T");
             Scanner scan = new Scanner(System.in);
            int n = scan.nextInt();
            int time = scan.nextInt();

        PercolationStats p = new PercolationStats(n, time);

        System.out.println("mean                     = "+ p.mean());
        System.out.println("stddev                   = "+ p.stddev());
        System.out.println("95% confidence interval  = "+ p.confidenceLo()+","+p.confidenceHi());
    }
}

