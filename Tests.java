import org.firmata4j.IODevice;
import org.firmata4j.firmata.FirmataDevice;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class PlantWateringTest {

    //TEST EACH CLASS INDIVIDUALLY
    @Test
    void testPumpOff() throws Exception {
        PlantWatering plantWatering = new PlantWatering();
        int testMoistureLevel = 400;
        plantWatering.checkMoistureValue(testMoistureLevel);
        assertFalse(plantWatering.pump.pumpState, "Pump should be OFF when moisture is below or at the wet state.");
    }

    @Test
    void testPumpOn() throws Exception {
        PlantWatering plantWatering = new PlantWatering();
        int testMoistureLevel = 560;
        plantWatering.checkMoistureValue(testMoistureLevel);
        assertTrue(plantWatering.pump.pumpState, "Pump should be ON when moisture above the wet state.");
    }
    @Test
    void testGetMoistureLevelWorks() throws Exception {
        IODevice board = new FirmataDevice("COM3");
        board.start();
        board.ensureInitializationIsDone();
        MoistureSensor sensor = new MoistureSensor(board, 14);
        long value = sensor.getMoistureLevel();
        System.out.println("Moisture: " + value);
        assertTrue(value >= 0 && value <= 1023, "Moisture value should be between 0 and 1023.");
        board.stop();
    }



}

