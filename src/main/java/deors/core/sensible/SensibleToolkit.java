package deors.core.sensible;

import java.awt.Insets;
import java.awt.Window;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Toolkit methods for managing Swing and Sensible components.<br>
 *
 * @author deors
 * @version 1.0
 */
public final class SensibleToolkit {

    /**
     * Default constructor. This class is a toolkit and therefore it cannot be instantiated.
     */
    private SensibleToolkit() {
        super();
    }

    /**
     * Centers a window in the screen.
     *
     * @param window the Window that will be centered
     */
    public static void centerWindow(Window window) {

        Insets insets = window.getInsets();
        window.setLocation(
            ((int) window.getToolkit().getScreenSize().getWidth()
                - window.getWidth()) / 2,
            ((int) window.getToolkit().getScreenSize().getHeight()
                - window.getHeight()
                - insets.top
                - insets.bottom) / 2);
    }

    /**
     * Creates an image icon from the given resource path. If the resource could
     * not be found, the method return <code>null</code>.
     *
     * @param path the path to the image file
     *
     * @return the image icon object
     */
    public static ImageIcon createImageIcon(String path) {

        URL url = SensibleContext.class.getResource(path);

        return url == null ? null : new ImageIcon(url);
    }

    /**
     * Sets the application look and feel. If the given look and feel could not be
     * instantiated, the method returns <code>false</code>.
     *
     * @param lookAndFeel the look and feel
     *
     * @return true if look and feel change was successful
     */
    public static boolean setLookAndFeel(String lookAndFeel) {

        try {
            UIManager.setLookAndFeel(lookAndFeel);
            return true;
        } catch (ClassNotFoundException cnfe) {
            return false;
        } catch (UnsupportedLookAndFeelException ulafe) {
            return false;
        } catch (InstantiationException ie) {
            return false;
        } catch (IllegalAccessException iae) {
            return false;
        }
    }
}
