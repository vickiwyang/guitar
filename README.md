# guitar

Simulates the sound of a guitar being plucked using the [Karplus–Strong algorithm](https://en.wikipedia.org/wiki/Karplus%E2%80%93Strong_string_synthesis).

**RingBuffer.java** implements the ring buffer data type used in **GuitarString.java** to model the vibrations of a guitar string.

**GuitarHero.java** is an interactive `GuitarString` client that plays guitar notes and chords in real-time based on user input. It relies on a helper class `Keyboard.java` (not included in this repo) that provides a GUI to play notes using the computer keyboard.
