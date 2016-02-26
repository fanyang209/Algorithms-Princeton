/**
 * Created by richard on 11/11/2015.
 */
import edu.princeton.cs.algs4.*;


import java.util.TreeSet;

public class KdTree {

    private static class KdNode{

        private KdNode leftNode;
        private KdNode rightNode;
        private final boolean isVertical;
        private final double x;
        private final double y;

        public KdNode(final double x,final double y, final KdNode leftNode,
                      final KdNode rightNode,final boolean isVertical){
            this.x = x;
            this.y = y;
            this.leftNode = leftNode;
            this.rightNode = rightNode;
            this.isVertical = isVertical;
        }

    }

    private static final RectHV container = new RectHV(0,0,1,1);
    private KdNode rootNode;
    private int size;

    public KdTree(){
        this.size = 0;
        this.rootNode = null;
    }

    public boolean isEmpty(){
        return this.size == 0;
    }

    public int size(){
        return this.size;
    }

    public void insert(final Point2D p){
        this.rootNode = insert(this.rootNode,p,true);
    }

    private KdNode insert(final KdNode node, final Point2D p,
                          final boolean isVertical){

        if (node == null){
            size++;
            return new KdNode(p.x(),p.y(),null,null,isVertical);
        }

        if (node.x == p.x() && node.y == p.y() ){
            return node;
        }

        if (node.isVertical && p.x()<node.x || !node.isVertical && p.y()<node.y ){

            node.leftNode = insert(node.leftNode,p,!node.isVertical);
        }else {
            node.rightNode = insert(node.rightNode,p,!node.isVertical);
        }

        return node;
    }

    public boolean contains(Point2D p){
        return contains(rootNode,p.x(),p.y());
    }

    private boolean contains(KdNode node, double x, double y){

        if (node == null){
            return false;
        }

        if (node.x == x && node.y == y){
            return true;
        }

        if (node.isVertical && x<node.x || !node.isVertical && y< node.y){
            return contains(node.leftNode,x,y);
        }else {
            return contains(node.rightNode,x,y);
        }
    }

    public void draw(){
        StdDraw.setScale(0,1);

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius();
        container.draw();

        draw(rootNode,container);
    }

    private void draw(final KdNode node, final RectHV rect){
        if (node == null){
            return;
        }

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        new Point2D(node.x, node.y).draw();

        Point2D min,max;
        if (node.isVertical){
            StdDraw.setPenColor(StdDraw.RED);
            min = new Point2D(node.x, rect.ymin());
            max = new Point2D(node.x ,rect.ymax());
        }else {
            StdDraw.setPenColor(StdDraw.BLUE);
            min = new Point2D(rect.xmin(), node.y);
            max = new Point2D(rect.xmax(), node.y);
        }

        StdDraw.setPenRadius();
        min.drawTo(max);

        draw(node.leftNode, leftRect(rect, node));
        draw(node.rightNode, rightRect(rect, node));
    }

    private RectHV leftRect(final RectHV rect , final KdNode node){
        if (node.isVertical){
            return new RectHV(rect.xmin(), rect.ymin(),node.x,rect.ymax());
        }else {
            return new RectHV(rect.xmin(),rect.ymin(),rect.xmax(),node.y);
        }
    }


    private RectHV rightRect(final RectHV rect , final KdNode node){
        if (node.isVertical){
            return new RectHV(node.x, rect.ymin(),rect.xmax(),rect.ymax());
        }else {
            return new RectHV(rect.xmin(),node.y,rect.xmax(),rect.ymax());
        }
    }

    public Iterable<Point2D> range(final RectHV rect){
        final TreeSet<Point2D> rangeSet = new TreeSet<Point2D>();
        range(rootNode , container, rect,rangeSet);

        return rangeSet;
    }

    private void range(final KdNode node, final RectHV nrect,
                       final RectHV rect, final TreeSet<Point2D> rangeSet){
        if (node == null)
            return;

        if (rect.intersects(nrect)){
            final Point2D p = new Point2D(node.x, node.y);
            if (rect.contains(p))
                rangeSet.add(p);
            range(node.leftNode,leftRect(nrect,node),rect,rangeSet);
           range(node.rightNode,rightRect(nrect, node),rect,rangeSet);
        }
    }

    public Point2D nearest(final Point2D p){
        return nearest(rootNode,container,p.x(),p.y(),null);
    }

    private Point2D nearest(final KdNode node, final RectHV rect,
                            final double x, final double y, final Point2D candidate){
        if (node == null){
            return candidate;
        }

        double dqn = 0.0;
        double drq = 0.0;
        RectHV left = null;
        RectHV right = null;
        final Point2D query = new Point2D(x,y);
        Point2D nearest = candidate;

        if (nearest != null){
            dqn = query.distanceSquaredTo(nearest);
            drq = rect.distanceSquaredTo(query);
        }

        if (nearest == null || dqn > drq){
            final Point2D point = new Point2D(node.x,node.y);
            if (nearest == null || dqn > query.distanceSquaredTo(point))
                nearest = point;

            if (node.isVertical){
                left = new RectHV(rect.xmin(), rect.ymin(),node.x,rect.ymax());
                right = new RectHV(node.x, rect.ymin(), rect.xmax(), rect.ymax());

                if (x < node.x) {
                    nearest = nearest(node.leftNode, left, x, y, nearest);
                    nearest = nearest(node.rightNode, right, x, y, nearest);
                } else {
                    nearest = nearest(node.rightNode, right, x, y, nearest);
                    nearest = nearest(node.leftNode, left, x, y, nearest);
                }
            } else {
                left = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.y);
                right = new RectHV(rect.xmin(), node.y, rect.xmax(), rect.ymax());

                if (y < node.y) {
                    nearest = nearest(node.leftNode, left, x, y, nearest);
                    nearest = nearest(node.rightNode, right, x, y, nearest);
                } else {
                    nearest = nearest(node.rightNode, right, x, y, nearest);
                    nearest = nearest(node.leftNode, left, x, y, nearest);
                }
            }
        }
        return nearest;
    }

    public static void main(String[] args) {
        KdTree kdTree = new KdTree();
        String fileName = args[0];
        In in = new In(fileName);
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D point = new Point2D(x,y);
            StdOut.println("contains before: " + kdTree.contains(point));
            kdTree.insert(point);
            StdOut.println("point:" + point);
            StdOut.println("contains after: " + kdTree.contains(point));
            StdOut.println("size: " + kdTree.size());
        }
        kdTree.draw();
    }
}