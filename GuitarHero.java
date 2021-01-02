public class GuitarHero {

    public static void main(String[] args) {
        // String representation of the 37 notes on the keyboard
        String keyboardString = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
        // Create and initialize array of 37 GuitarString objects
        GuitarString[] strings = new GuitarString[keyboardString.length()];
        for (int i = 0; i < keyboardString.length(); i++) {
            strings[i] = new GuitarString(440 * Math.pow(2, (i - 24.0) / 12.0));
        }

        // the main input loop
        Keyboard keyboard = new Keyboard();
        while (true) {

            // check if the user has played a key; if so, process it
            if (keyboard.hasNextKeyPlayed()) {
                // the key the user played
                char key = keyboard.nextKeyPlayed();

                // pluck the corresponding string if the key played is valid
                if (keyboardString.indexOf(key) != -1) {
                    strings[keyboardString.indexOf(key)].pluck();
                }
            }

            // compute the superposition of the samples
            double sample = 0;
            for (int i = 0; i < keyboardString.length(); i++) {
                sample += strings[i].sample();
            }

            // play the sample on standard audio
            StdAudio.play(sample);

            // advance the simulation of each guitar string by one step
            for (int i = 0; i < keyboardString.length(); i++) {
                strings[i].tic();
            }

        }
    }

}

