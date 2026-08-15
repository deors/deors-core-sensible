package deors.core.sensible;

import java.awt.AWTException;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Robot;

public final class DesktopConditions {

    private DesktopConditions() {

        super();
    }

    public static boolean isAvailable() {

        return !GraphicsEnvironment.isHeadless() && robotCanBeCreated();
    }

    private static boolean robotCanBeCreated() {

        try {
            new Robot();
            return true;
        } catch (AWTException | HeadlessException | SecurityException _) {
            return false;
        }
    }
}
