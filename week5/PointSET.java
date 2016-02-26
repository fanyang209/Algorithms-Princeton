import edu.princeton.cs.algs4.*;

import java.util.TreeSet;

/**
 * Created by richard on 11/10/2015.
 */
public class PointSET {

    private TreeSet<Point2D> pointSET;

    public PointSET(){
        this.pointSET = new TreeSet<Point2D>();
    }

    public boolean isEmpty(){
        return this.pointSET.size() == 0;
    }

    public int size(){

        return  this.pointSET.size();
    }

    public void insert(Point2D p){
        pointSET.add(p);
    }

    public boolean contains(Point2D p){
        return pointSET.contains(p);
    }

    public  void draw(){
        for (Point2D p : pointSET){
            StdDraw.point(p.x(),p.y());
        }
    }

    public Iterable<Point2D> range(RectHV rect){
        TreeSet<Point2D> rangeSet = new TreeSet<Point2D>();
        for (Point2D p :pointSET){
            if (rect.contains(p)){
                rangeSet.add(p);
            }
        }
        return rangeSet;
    }


    public  Point2D nearest(Point2D p){

        Point2D nearestPoint2D = null;
        double distance = Double.MAX_VALUE;

        if (this.pointSET.isEmpty()){
            return nearestPoint2D;
        }

        for (Point2D pointIter : pointSET){
            if (pointIter.distanceTo(p)<distance){
                nearestPoint2D = pointIter;
                distance = pointIter.distanceTo(p);
            }
        }
        return nearestPoint2D;
    }

    public static void main(String args[]) {
        PointSET pointSet = new PointSET();
        String fileName = args[0];
        In in = new In(fileName);
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D point = new Point2D(x,y);
            pointSet.insert(point);
            StdOut.println("point:" + point);
            StdOut.println("nearest: " + pointSet.nearest(point));
        }
        pointSet.draw();
    }
}
