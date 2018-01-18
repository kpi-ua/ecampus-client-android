package ua.kpi.ecampus.helper;


public class Sleep {

    private static final int SLEEP_TIME = 5000;

    public static void sleepThread() {
        try {
            Thread.sleep(SLEEP_TIME);
        } catch (Exception e) {
        }
    }
}
