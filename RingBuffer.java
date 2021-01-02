public class RingBuffer {

    private double[] rb;          // items in the buffer
    private int first;            // index for the next dequeue or peek
    private int last;             // index for the next enqueue
    private int size;             // number of items in the buffer

    // creates an empty ring buffer with the specified capacity
    public RingBuffer(int capacity) {
        rb = new double[capacity];

        // Initialize instance variables
        first = 0;
        last = 0;
        size = 0;
    }

    // return the capacity of this ring buffer
    public int capacity() {
        return rb.length;
    }

    // return number of items currently in this ring buffer
    public int size() {
        return size;
    }

    // is this ring buffer empty (size equals zero)?
    public boolean isEmpty() {
        if (size == 0) return true;
        return false;
    }

    // is this ring buffer full (size equals capacity)?
    public boolean isFull() {
        if (size == capacity()) return true;
        return false;
    }

    // adds item x to the end of this ring buffer
    public void enqueue(double x) {
        if (isFull())
            throw new RuntimeException("Ring buffer is full.");
        else {
            rb[last] = x;
            if (last == capacity() - 1) last = 0;
            else last++;
            size++;
        }
    }

    // deletes and returns the item at the front of this ring buffer
    public double dequeue() {
        if (isEmpty())
            throw new RuntimeException("Ring buffer is empty.");
        else {
            double value = rb[first];
            if (first == capacity() - 1) first = 0;
            else first++;
            size--;
            return value;
        }
    }

    // returns the item at the front of this ring buffer
    public double peek() {
        if (isEmpty())
            throw new RuntimeException("Ring buffer is empty.");
        else
            return rb[first];
    }


    // tests and calls every instance method in this class
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        RingBuffer buffer = new RingBuffer(n);

        // Check buffer capacity and whether empty after object creation
        StdOut.println("capacity: " + buffer.capacity());
        StdOut.println("empty buffer: " + buffer.isEmpty());

        // Enqueue numbers 1 through n
        for (int i = 1; i <= n; i++) {
            buffer.enqueue(i);
        }
        double t = buffer.dequeue();
        buffer.enqueue(t);
        StdOut.println("Size after wrap-around is " + buffer.size());
        // Confirm that the buffer is full
        StdOut.println("full buffer: " + buffer.isFull());

        // Repeatedly dequeues first two numbers and enqueues their sum
        while (buffer.size() >= 2) {
            double x = buffer.dequeue();
            double y = buffer.dequeue();
            buffer.enqueue(x + y);
        }
        StdOut.println(buffer.peek());
    }

}
