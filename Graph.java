import edu.princeton.cs.introcs.StdDraw;
import java.util.ArrayList;

/**
 * Uses StdDraw to visually display real-time moisture levels
 * over time
 */
public class Graph {

    /**
     * Constructor for Graph object that sets window size (canvas), and raane for x and y axis
     */
    public Graph() {
        // Window size upon run
        StdDraw.setCanvasSize(1500, 800);
        // X-axis range (time)
        StdDraw.setXscale(-5, 105);
        // Y-axis range (moisture)
        StdDraw.setYscale(480, 760);
    }

    /**
     * Plots the time and moisture level data points.
     * Draws both axes, labels, and a line connecting each point to the next .
     *
     * @param times a list of time values in seconds
     * @param moistureLevels a list of corresponding moisture readings
     */
    public void plot(ArrayList<Integer> times, ArrayList<Long> moistureLevels) {
        StdDraw.clear();

        // Draw axes using black coloured pen
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.line(0, 500, 100, 500); // X-axis
        StdDraw.line(0, 500, 0, 650);   // Y-axis

        // Draw tick labels for axes
        for (int i = 0; i <= 100; i += 10)
            StdDraw.text(i, 485, String.valueOf(i));
        for (int i = 500; i <= 750; i += 25)
            StdDraw.text(-3, i, String.valueOf(i));

        // Axis labels
        StdDraw.text(50, 475, "Time (seconds)");
        StdDraw.text(-6, 625, "Moisture", 90);  // Rotated vertically

        // Plot data points and connect them with lines
        for (int i = 0; i < times.size(); i++) {
            int time = times.get(i);
            long moisture = moistureLevels.get(i);
            StdDraw.point(time, moisture);

            // start drawing lines from the first to the second and so on (not from before 0 because there is no time value)
            if (i > 0) {
                int prevTime = times.get(i - 1);
                long prevMoisture = moistureLevels.get(i - 1);
                StdDraw.line(prevTime, prevMoisture, time, moisture);
            }
        }

        StdDraw.show();
    }
}
