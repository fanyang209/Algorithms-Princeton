import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Created by richard on 10/12/2015.
 */
public class FastCollinearPoints {

    public static void main(String[] args) {

        // read the N points from a file
        In in = new In(args[0]);
        int N = in.readInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.show(0);
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
    }

    private LinkedList<LineSegment> line  = new LinkedList<LineSegment>();

    public FastCollinearPoints(Point[] points) {

        for (int a=0;a<points.length;a++)
            for (int b=a+1;b<points.length;b++){
                if (points[a] == points[b]){
                    throw new IllegalArgumentException("repeated point");
                }
            }

       /* Point[][] line = new Point[points.length][points.length];
        Point temp;

        for (int i = 0; i < points.length; i++) {
            temp = points[i];
            points[i] = points[0];
            points[0] = temp;

            for (int j = 0; j < points.length; j++) {

                line[i][j] = points[j];
            }
            Arrays.sort(line[i] ,temp.slopeOrder());
        }*/


        int N = points.length;


        for (int i=0; i<N;i++){

//    extract origin from points array
            Point origin = points[i];
            Point[]  otherpoint = new Point[N-1];
            int count = 0;


            for (int j=0;j<N;j++){
                if (j<i)  otherpoint[j] = points[j];
                if (j>i)  otherpoint[j-1] =points[j];
            }

//            Sort the otherpoint according to the slopes they makes with origin
            Arrays.sort(otherpoint,origin.slopeOrder());

//            Check if any 3 (or more) adjacent points in the sorted order have equal slopes with respect to origin
            for (int j=0;j< otherpoint.length-1;j++) {
                if (origin.slopeTo(otherpoint[j]) == origin.slopeTo(otherpoint[j + 1])) {
                    count++;
                    continue;
                }else {

                    if (count >= 3) {
                        Point[] order = new Point[count + 1];
                        for (int z = 1; z < count + 1; z++) {
                            order[0] = origin;
                            order[z] = otherpoint[j - count];
                        }


//                    Arrays.sort(otherpoint, j - count, j, otherpoint[j - count].compareTo(otherpoint[j]));
                        for (int a = 0; a < order.length - 1; a++)
                            for (int b = a + 1; b < order.length; b++) {
                                if (order[a].compareTo(order[b]) >= 0) {
                                    Point temp = order[a];
                                    order[a] = order[b];
                                    order[b] = temp;

                                }
                            }

                        for (int k = 0; k < order.length; k++) {
                            if (k == order.length)
                                StdOut.print(order[k]);
                            else
                                StdOut.print(order[k] + " -> ");
                        }


                        line.add(new LineSegment(order[0], order[order.length - 1]));
                    }
                }
                        }
              count = 0;
                }

            }







    public  int numberOfSegments()  {

        return segments().length;
    }


    public LineSegment[] segments(){

        LineSegment[] lineSegment = new  LineSegment[line.size()];
        for (int i =0;i<line.size();i++){

            lineSegment[i] =  line.get(i);
        }

        return   lineSegment;
    }
}