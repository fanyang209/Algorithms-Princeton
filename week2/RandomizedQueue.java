import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by richard on 9/28/2015.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] q;            // queue elements
    private int N = 0;           // number of elements on queue
//    private int first = 0;       // index of first element of queue
//    private int last  = 0;
    private int random;

    public RandomizedQueue(){
        q = (Item[]) new Object[5];
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    private void resize(int max) {
        assert max >= N;
        Item[] temp = (Item[]) new Object[max];
        for (int i = 0; i < N; i++) {
            temp[i] = q[i];
        }
        q = temp;

    }


    public void enqueue(Item item){

        if(item == null) throw new NullPointerException("null item");

        if (N == q.length) resize(2*q.length);   // double size of array if necessary
        q[N] = item;                        // add item

        N++;
    }

    public Item dequeue(){

        if(isEmpty()) throw  new NoSuchElementException("Queue underflow");
        random = StdRandom.uniform(N);

        Item item = q[random];
        if(random!= N-1){
            q[random] = q[N-1];
        }

        q[N-1] = null;
        N--;

        if(N>0 && N == q.length/4)  resize(q.length/2);

        return item;
    }

    public Item sample(){

        if(isEmpty()) throw  new NoSuchElementException("Queue underflow");
         random = StdRandom.uniform(N);

        Item item = q[random];
        return item;
    }

    /*public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : q)
            s.append(item + " ");
        return s.toString();
    }*/

    @Override
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {

        private Item[] tempItem = (Item[]) new Object[q.length];
        private  int tempN = N;
        public boolean hasNext() {
            return tempN!=0;
        }



        public ArrayIterator(){
            for(int j=0;j<q.length;j++){
                tempItem[j] = q[j];
            }
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();

            int index = StdRandom.uniform(tempN);
            Item item = tempItem[index];
           if(index!= tempN-1){
               tempItem[index] = tempItem[tempN-1];
           }

            tempItem[tempN-1] = null;
            tempN--;
            return  item;
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<String> q = new  RandomizedQueue<String>();
//        StdOut.println("input the item");
        while (!StdIn.isEmpty()){

            String item = StdIn.readString();

            if (!item.equals("-")) q.enqueue(item);
            else if (!q.isEmpty()) StdOut.print(q.dequeue());


//            StdOut.print(q);

        }
        StdOut.println("(" + q.size() + " left on queue)");
    }
}
