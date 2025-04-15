import org.firmata4j.IODevice;
import org.firmata4j.Pin;

import java.io.IOException;

public class Pump {
    // Pin where the pump is connected
    private Pin pumpPin;
    /** Boolean attribute to indicate the state of the pump being on or off */
    public boolean pumpState;

    /**
     * Constructs a Pump object.
     * @param board The Firmata device controlling the pump.
     * @param pinNumber The pin number connected to the pump.
     * @throws IOException if setting the pin mode fails
     */
    public Pump(IODevice board, int pinNumber) throws IOException {

        // intialize the pump
        pumpPin = board.getPin(pinNumber);
        pumpPin.setMode(Pin.Mode.OUTPUT);
    }

    /**
     * Turns the pump ON.
     */
    public void turnOn() {
        try {
            // Set pump  to on by setting pin value to 1
            pumpPin.setValue(1);
            System.out.println("The Pump is on!");
            pumpState = true;
        } catch (IOException e) {
            System.out.println("Pump did not turn on :( ");
        }
    }

    /**
     * Turns the pump OFF.
     */
    public void turnOff() {
        try {
            // Turn pump off by setting pin value to 0
            pumpPin.setValue(0);
            System.out.println("The Pump is off!");
            pumpState = false;
        } catch (IOException e) {
            System.out.println("Failed to turn off the pump.");
        }
    }
}
