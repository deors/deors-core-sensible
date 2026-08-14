package deors.core.sensible;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Window;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
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
     * Sizes a window so that its content pane client area is exactly
     * the given width and height. Window decorations (title bar, borders)
     * and the menu bar are added on top of this size.
     *
     * <p>Must be called after the content pane and menu bar have been
     * installed, and after {@code setResizable} has been set to its
     * final value, so that insets match the decorations that will
     * actually be shown. On modern Windows, {@code JFrame.setSize(w, h)}
     * includes those decorations and shrinks the client area, which
     * clips absolute layouts on the right and bottom.</p>
     *
     * @param window the window to size
     * @param width the desired content pane width
     * @param height the desired content pane height
     */
    public static void setContentSize(Window window, int width, int height) {

        Container content = contentPaneOf(window);
        Dimension requested = new Dimension(width, height);

        if (content != null) {
            content.setPreferredSize(requested);
        } else {
            window.setPreferredSize(requested);
        }

        window.pack();

        // Windows 10/11 + DWM can report insets that do not match the
        // real client area. Correct any leftover difference.
        Dimension actual = content != null ? content.getSize() : window.getSize();
        int deltaW = width - actual.width;
        int deltaH = height - actual.height;
        if (deltaW != 0 || deltaH != 0) {
            window.setSize(window.getWidth() + deltaW, window.getHeight() + deltaH);
        }
    }

    /**
     * Returns the Swing content pane for frames and dialogs.
     *
     * @param window the window
     *
     * @return the content pane, or {@code null} if the window has none
     */
    private static Container contentPaneOf(Window window) {

        if (window instanceof JFrame) {
            return ((JFrame) window).getContentPane();
        }
        if (window instanceof JDialog) {
            return ((JDialog) window).getContentPane();
        }
        return null;
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
