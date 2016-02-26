import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Created by richard on 9/25/2015.
 */
public class Deque<Item> implements Iterable<Item> {

    private int N;
    private Node<Item> first;
    private  Node<Item> last;


    public Deque(){
        first = null;
        last = null;
        N = 0;
    }

    private static class Node<Item> {
        Item item;
        Node<Item> next;
        Node<Item> pre;
    }

    public boolean isEmpty(){
        return N==0;
    }

    public int size(){
        return N;
    }


    public void addFirst(Item item){

        if(item == null) throw new NullPointerException("null item");
        Node<Item> oldfirst = first;

//        StdOut.println("oldfirst= "+oldfirst);

        first = new Node<Item>();
        first.item = item;
        first.pre = null;
//        StdOut.println("first= "+first);

        if(isEmpty()){
            last = first;
            first.next =null;
        }else {

            first.next = oldfirst;
            oldfirst.pre = first;

        }


        N++;
    }

    public void addLast(Item item){

        if(item == null) throw new NullPointerException("null item");

            Node<Item> oldlast = last;
//        StdOut.println("oldlast= "+oldlast);

        last = new Node<Item>();
        last.item = item;
        last.next = null;
//        StdOut.println("last= "+last);

        if(isEmpty()) {
            first = last;
            last.pre = null;
        }
//        else  if(oldfirst !=null) oldfirst = last;
        else    {
            last.pre = oldlast;
            oldlast.next = last;
        }
        N++;
    }

    public Item removeFirst(){

        if (isEmpty()) throw new NoSuchElementException("queue underflow");
        Item item = first.item;
        first = first.next;
        N--;
        if(isEmpty()){
            last = null;
            first = null;
        }else {
            first.pre = null;
        }

        return  item;

    }

    public Item removeLast(){

        if (isEmpty()) throw new NoSuchElementException("queue underflow");
        Item item = last.item;

//        StdOut.println("item= "+item);
        last = last.pre;
//        StdOut.println("last= "+last);
        N--;
        if (isEmpty()){
            last = null;
            first = null;
        }else{
            last.next =null;
        }
        return  item;
    }

  /*  public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this)
            s.append(item + " ");
        return s.toString();
    }
*/




    @Override
    public Iterator<Item> iterator() {
        return new ListIterator<Item>();
    }


    private class ListIterator<Item> implements Iterator<Item> {

        private Node<Item> current = (Node<Item>) first;


        @Override
        public boolean hasNext() {
            return current!= null;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }


    }

    public static void main(String[] args) {
        Deque<String> q = new  Deque<String>();
//        StdOut.println("input the item");
        while (!StdIn.isEmpty()){

            String item = StdIn.readString();

            StdOut.println("choose operation");
            String item1 = StdIn.readString();
            if(item1.equals("1"))  q.addFirst(item);

            if(item1.equals("2"))  q.addLast(item);

            if(item1.equals("3"))  q.removeFirst();

            if(item1.equals("4"))  q.removeLast();


            StdOut.print(q);
        }
        StdOut.println("(" + q.size() + " left on queue)");
    }
}
