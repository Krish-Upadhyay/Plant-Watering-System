import org.firmata4j.IODevice;
import org.firmata4j.Pin;
import org.firmata4j.firmata.FirmataDevice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Main class to control water pump and log moisture values.
 * Checks moisture value at every instance of time in order to control pump.
 */
public class PlantWatering extends TimerTask {

    // Creating and initializing attributes
    IODevice myGroveBoard;
    public Pump pump;
    public OLED oled;
    public MoistureSensor moisture;
    private Graph graph;
    private Pin buttonPin;
    /** Array list to store values of time for each given moisture value */
    public ArrayList<Integer> times = new ArrayList<>();
    /** Array list to store moisture values at a given time instance */
    public ArrayList<Long> moistureLevels = new ArrayList<>();
    /** Time instance that is set to 0 at the beggining of runtime and increments by one each second */
    public int time = 0;

    /** Moisture value at which plant is considered dry */
    public int dryState = 600;

    /** Moisture value at which soil is considered wet. */
    public int wetState = 560;

    /**
     * Constructs and initializes the PlantWatering system.
     * Connects to the Grove board on COM3, initializes the pump, OLED, moisture sensor, button, and graph.
     */
    public PlantWatering() {
        try {

            // initlaize grove board
            this.myGroveBoard = new FirmataDevice("COM3");
            this.myGroveBoard.start();
            this.myGroveBoard.ensureInitializationIsDone();
            System.out.println("Board started.");

            //initialize pins
            this.pump = new Pump(myGroveBoard, 7);
            this.oled = new OLED(myGroveBoard, pump);
            this.moisture = new MoistureSensor(myGroveBoard, 14);
            this.buttonPin = myGroveBoard.getPin(6);
            buttonPin.setMode(Pin.Mode.INPUT);
            this.graph = new Graph();


        } catch (Exception e) {
            System.out.println("Could not connect to board");
        }
    }

    /**
     * Executes the main logic every second:
     */
    @Override
    public void run() {
        try {

            // When button is pressed, override an exit and turn off the program
            if (buttonPin.getValue() == 1) {
                System.out.println("Button pressed! Stopping the program...");
                pump.turnOff();
                cancel();
            }

            // get the moisture value at an instance of time, append that value to the array list
            // then increment the time by 1
            long moistureValue = moisture.getMoistureLevel();
            times.add(time);
            moistureLevels.add(moistureValue);
            time++;

            // display moisure readings and pump state on OLED
            oled.displayMoistureLevel();

            // Call method to turn on or off the pump based on the value of the moisture sensor
            checkMoistureValue(moistureValue);

            // display moisture and time at a given interval to the console
            System.out.println("Time: " + time + " seconds | Moisture: " + moistureValue);

            //call the plot method in graph class and plot the time and moisture value
            graph.plot(times, moistureLevels);
        } catch (IOException e) {
            System.out.println("Something went wrong");
        }
    }

    /**
     * Checks the current moisture value and turns the pump on or off accordingly.
     *
     * @param moistureValue the current moisture level from the sensor
     */
    public void checkMoistureValue(double moistureValue) {

        // Check value of moisture, if its higher than the wet state, turn on, if its lower, then turn off
        if (moistureValue >= wetState) {
            pump.turnOn();
            System.out.println("The pump is ON!! Time for water!");
        } else {
            pump.turnOff();
            System.out.println("The pump turned OFF!! Looks like our plant is hydrated!");
        }
    }

    /**
     * Main method that starts the timer and runs the watering task every second.
     */
    public static void main(String[] args) {
        // Create a new timer object then override run every second
        Timer timer = new Timer();
        timer.schedule(new PlantWatering(), 0, 1000);
    }
}
