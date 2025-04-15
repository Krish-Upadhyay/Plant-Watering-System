import org.firmata4j.IODevice;
import org.firmata4j.ssd1306.SSD1306;

import java.io.IOException;

/**
 * The {@code OLED} class is responsible for managing the SSD1306 OLED display.
 * It shows real-time soil moisture readings and the pump state (ON/OFF).
 */
public class OLED {

    private SSD1306 theOledObject;
    private MoistureSensor moistureSensor;
    private Pump pump;

    /**
     * Constructs an {@code OLED} object and initializes the SSD1306 OLED display.
     *
     * @param board the {@link IODevice} representing the connected Grove board
     * @param pump  the {@link Pump} instance to retrieve the current pump state
     * @throws IOException if initialization of the OLED display or moisture sensor fails
     */
    public OLED(IODevice board, Pump pump) throws IOException {
        // Initialize the OLED
        this.theOledObject = new SSD1306(board.getI2CDevice((byte) 0x3C), SSD1306.Size.SSD1306_128_64);
        this.theOledObject.init();
        // Moisture sensor connected to A0 (pin 14)
        this.moistureSensor = new MoistureSensor(board, 14);
        this.pump = pump;
    }

    /**
     * Displays the current moisture level and pump state on the OLED screen.
     * The display shows:
     * <ul>
     *     <li>The moisture value in analog (0–1023)</li>
     *     <li>The current pump state (ON or OFF)</li>
     * </ul>
     *
     * @throws IOException if reading from the sensor or updating the display fails
     */
    public void displayMoistureLevel() throws IOException {

        // Obtain the current moisture value and the state of the pump and store it to appropriate variables
        long moistureValue = moistureSensor.getMoistureLevel();
        boolean pumpState = pump.pumpState;

        // clear OLED before displaying
        theOledObject.getCanvas().clear();

        // Display moisture readings and pump state on the OLED display
        String moistureDisplay = "Moisture: " + moistureValue;
        String pumpStateDisplay = "Pump: " + (pumpState ? "ON" : "OFF");
        theOledObject.getCanvas().drawString(0, 0, moistureDisplay);
        theOledObject.getCanvas().drawString(0, 10, pumpStateDisplay);
        theOledObject.display();
    }
}
