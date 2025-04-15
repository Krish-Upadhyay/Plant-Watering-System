import org.firmata4j.IODevice;
import org.firmata4j.Pin;

import java.io.IOException;

/**
 * Class to initialize pin for moisture sensor and obtain the value of the sensor
 */
public class MoistureSensor {
    // Pin where the moisture sensor is connected
    private Pin moisturePin;

    /**
     * Constructs a MoistureSensor object.
     * @param board The Firmata device controlling the sensor.
     * @param pinNumber The pin number connected to the moisture sensor.
     * @throws IOException if communication with the board fails during pin setup
     */
    public MoistureSensor(IODevice board, int pinNumber) throws IOException {
        // Initialize the pin
        moisturePin = board.getPin(pinNumber);
        // Set the pin mode to analog input
        moisturePin.setMode(Pin.Mode.ANALOG);
    }

    /**
     * Reads the moisture value from the sensor.
     *
     * @return The moisture value
     */
    public long getMoistureLevel() {
        // Get the analog value from the moisture sensor
        return moisturePin.getValue();
    }
}
