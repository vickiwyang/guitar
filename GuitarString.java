/*******************************************************************************
 *
 *  This is a template file for GuitarString.java. It lists the constructors
 *  and methods you need, along with descriptions of what they're supposed
 *  to do.
 *
 *  Note: it won't compile until you fill in the constructors and methods
 *        (or at least commment out the ones whose return type is non-void).
 *
 ******************************************************************************/

public class GuitarString {

    private final RingBuffer rb; // ring buffer encapsulated in GuitarString

    // creates a guitar string of the specified frequency,
    // using sampling rate of 44,100
    public GuitarString(double frequency) {
        int SAMPLING_RATE = 44100;
        int n = (int) Math.ceil(SAMPLING_RATE / frequency);
        rb = new RingBuffer(n);
        // Enqueue n zeros to represent guitar string at rest
        for (int i = 0; i < n; i++) {
            rb.enqueue(0);
        }
    }

    // creates a guitar string whose size and initial values are given by
    // the specified array
    public GuitarString(double[] init) {
        int n = init.length;
        rb = new RingBuffer(n);
        // Enqueue input array values
        for (int i = 0; i < n; i++) {
            rb.enqueue(init[i]);
        }

    }

    // returns the number of samples in the ring buffer
    public int length() {
        return rb.size();
    }

    // plucks the guitar string (by replacing the buffer with white noise)
    public void pluck() {
        for (int i = 0; i < rb.capacity(); i++) {
            rb.dequeue();
            rb.enqueue(StdRandom.uniform(-0.5, 0.5));
        }
    }

    // advances the Karplus-Strong simulation one time step
    public void tic() {
        double ENERGY_DECAY = 0.996;
        double first = rb.dequeue(); // save deleted sample at front of buffer
        double next = rb.peek(); // saves the sample that is now at front
        rb.enqueue(ENERGY_DECAY * (0.5 * (first + next)));
    }

    // returns the current sample
    public double sample() {
        return rb.peek();
    }


    // tests and calls every constructor and instance method in this class
    public static void main(String[] args) {
        // Test GuitarString(double frequency) constructor
        GuitarString a = new GuitarString(440.0); // freq of concert A
        GuitarString c = new GuitarString(523.25); // freq of C above concert A
        // n should be 101 for a and 85 for c
        StdOut.println(a.length() + " " + c.length());
        // samples should be initialized to 0.0
        StdOut.println(a.sample() + " " + c.sample());

        // Test GuitarString(double[]) constructor
        double[] test1 = { 1.0, 2.0, 3.0, 4.0 };
        double[] test2 = new double[100];
        for (int i = 0; i < test2.length; i++) {
            test2[i] = i;
        }
        GuitarString testString1 = new GuitarString(test1);
        GuitarString testString2 = new GuitarString(test2);
        // n should be 4 for testString 1 and 100 for testString2
        StdOut.println(testString1.length() + " " + testString2.length());
        // sample should be 1.0 for testString 1 and 0.0 for testString2
        StdOut.println(testString1.sample() + " " + testString2.sample());

        // Test pluck() on a and c
        a.pluck();
        c.pluck();
        // lengths should be unchanged
        StdOut.println(a.length() + " " + c.length());
        // sampled values shouldbe between -0.5 and 0.5
        StdOut.println(a.sample() + " " + c.sample());

        // Test client with a GuitarString with sample values, tic'd 25 times
        double[] samples = {
                0.2, 0.4, 0.5, 0.3, -0.2,
                0.4, 0.3, 0.0, -0.1, -0.3
        };
        GuitarString testString = new GuitarString(samples);
        int m = 25; // 25 tics
        for (int i = 0; i < m; i++) {
            double sample = testString.sample();
            StdOut.printf("%6d %8.4f\n", i, sample);
            testString.tic();
        }
    }

}
