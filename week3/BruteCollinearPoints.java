import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by richard on 10/11/2015.
 */
public class BruteCollinearPoints {

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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
    }

    private final Point[] points;
    private ArrayList<Point[]> p = new ArrayList<Point[]>();

    public BruteCollinearPoints(Point[] points){

        for (int a=0;a<points.length;a++)
            for (int b=a+1;b<points.length;b++){
                if (points[a] == points[b]){
                    throw new IllegalArgumentException("repeated point");
                }
            }

        this.points = points;

        if (points == null)
            throw new NullPointerException("constructor is null");



        for (int i=0; i<points.length;i++)
            for (int j =i+1;j<points.length ;j++)
                for (int z =j+1; z<points.length;z++)
                    for (int k =z+1;k<points.length;k++){

//                        p.add(new int[]{points[i].slopeOrder().compare(points[j],points[z]), points[i].slopeOrder().compare(points[j], points[k]),points[i].slopeOrder().compare(points[z], points[k])});
                          p.add(new Point[]{points[i],points[j],points[z],points[k]});
                        if (points[i]==null || points[j]==null || points[k]==null || points[z]==null)
                            throw new NullPointerException("point is null");

                    }
    }

    public  int numberOfSegments()  {

        return segments().length;
    }


    public LineSegment[] segments(){

        Point temp;

        LinkedList<LineSegment> line = new LinkedList<LineSegment>();

     for (int i=0;i<p.size();i++) {
         if (p.get(i)[0].slopeTo(p.get(i)[1])== p.get(i)[0].slopeTo(p.get(i)[2])   && p.get(i)[0].slopeTo(p.get(i)[1])== p.get(i)[0].slopeTo(p.get(i)[3])) {

            for (int j=0;j<p.get(0).length-1;j++)
                for (int k=j+1;k<p.get(0).length;k++){
                    if (p.get(i)[j].compareTo(p.get(i)[k])>0){
                       temp =  p.get(i)[j];
                        p.get(i)[j] = p.get(i)[k];
                        p.get(i)[k] = temp;
                    }
                }
             line.add(new LineSegment( p.get(i)[0], p.get(i)[3]));
         }



     }
        LineSegment[] lineSegment = new  LineSegment[line.size()];
        for (int i =0;i<line.size();i++){

            lineSegment[i] =  line.get(i);
        }

      return   lineSegment;
    }
}
