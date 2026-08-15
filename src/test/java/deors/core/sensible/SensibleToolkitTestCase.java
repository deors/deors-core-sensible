package deors.core.sensible;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JWindow;

import org.junit.jupiter.api.Test;

public class SensibleToolkitTestCase {

    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;

    public SensibleToolkitTestCase() {

        super();
    }

    @Test
    public void testSetContentSizeFrame() {

        JFrame frame = new JFrame();
        try {
            SensibleToolkit.setContentSize(frame, WIDTH, HEIGHT);

            assertEquals(WIDTH, frame.getContentPane().getWidth());
            assertEquals(HEIGHT, frame.getContentPane().getHeight());
            assertTrue(frame.getWidth() >= WIDTH);
            assertTrue(frame.getHeight() >= HEIGHT);
        } finally {
            frame.dispose();
        }
    }

    @Test
    public void testSetContentSizeDialog() {

        JDialog dialog = new JDialog();
        try {
            SensibleToolkit.setContentSize(dialog, WIDTH, HEIGHT);

            assertEquals(WIDTH, dialog.getContentPane().getWidth());
            assertEquals(HEIGHT, dialog.getContentPane().getHeight());
            assertTrue(dialog.getWidth() >= WIDTH);
            assertTrue(dialog.getHeight() >= HEIGHT);
        } finally {
            dialog.dispose();
        }
    }

    @Test
    public void testSetContentSizeFrameWithMenuBar() {

        JFrame frame = new JFrame();
        try {
            frame.setResizable(false);

            JMenuBar menuBar = new JMenuBar();
            menuBar.add(new JMenu("File"));
            frame.setJMenuBar(menuBar);

            SensibleToolkit.setContentSize(frame, WIDTH, HEIGHT);

            assertEquals(WIDTH, frame.getContentPane().getWidth());
            assertEquals(HEIGHT, frame.getContentPane().getHeight());
        } finally {
            frame.dispose();
        }
    }

    @Test
    public void testSetContentSizeWindowWithoutContentPane() {

        JWindow window = new JWindow();
        try {
            SensibleToolkit.setContentSize(window, WIDTH, HEIGHT);

            assertEquals(WIDTH, window.getWidth());
            assertEquals(HEIGHT, window.getHeight());
        } finally {
            window.dispose();
        }
    }

    @Test
    public void testSetContentSizeSecondCallReplacesPreviousSize() {

        JFrame frame = new JFrame();
        try {
            SensibleToolkit.setContentSize(frame, 800, 230);
            SensibleToolkit.setContentSize(frame, WIDTH, HEIGHT);

            assertEquals(WIDTH, frame.getContentPane().getWidth());
            assertEquals(HEIGHT, frame.getContentPane().getHeight());
        } finally {
            frame.dispose();
        }
    }

    @Test
    public void testSetContentSizeCorrectsClientAreaAfterPack() {

        UndersizedAfterPackFrame frame = new UndersizedAfterPackFrame();
        try {
            SensibleToolkit.setContentSize(frame, WIDTH, HEIGHT);
            frame.validate();

            assertEquals(WIDTH, frame.getContentPane().getWidth());
            assertEquals(HEIGHT, frame.getContentPane().getHeight());
        } finally {
            frame.dispose();
        }
    }

    /**
     * Frame whose {@code pack()} leaves the client area short of the
     * preferred size, so {@code setContentSize} must apply the inset
     * correction.
     */
    private static final class UndersizedAfterPackFrame extends JFrame {

        private static final long serialVersionUID = 1L;

        private static final int SHRINK_W = 20;
        private static final int SHRINK_H = 10;

        @Override
        public void pack() {

            super.pack();
            setSize(getWidth() - SHRINK_W, getHeight() - SHRINK_H);
            validate();
        }
    }
}
