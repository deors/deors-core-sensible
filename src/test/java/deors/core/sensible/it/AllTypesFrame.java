package deors.core.sensible.it;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import deors.core.sensible.SensibleCheckBox;
import deors.core.sensible.SensibleTextField;

public class AllTypesFrame
    extends JFrame
    implements ActionListener, PropertyChangeListener {

    private static final long serialVersionUID = 3211782048847695813L;

    private AllTypesObject allTypesObject1;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JCheckBox checkBox1;
    private JCheckBox checkBox2;
    private JCheckBox checkBox3;
    private JCheckBox checkBox4;
    private JCheckBox checkBox5;
    private JCheckBox checkBox6;
    private JCheckBox checkBox7;
    private JCheckBox checkBox8;
    private JCheckBox checkBox9;
    private JCheckBox checkBox10;
    private JCheckBox checkBox11;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JLabel label5;
    private JLabel label6;
    private JLabel label7;
    private JLabel label8;
    private JLabel label9;
    private JLabel label10;
    private JLabel label11;
    private JPanel mainContentPane;
    private SensibleCheckBox sensibleCheckBox1;
    private SensibleTextField sensibleTextField1;
    private SensibleTextField sensibleTextField2;
    private SensibleTextField sensibleTextField3;
    private SensibleTextField sensibleTextField5;
    private SensibleTextField sensibleTextField6;
    private SensibleTextField sensibleTextField7;
    private SensibleTextField sensibleTextField8;
    private SensibleTextField sensibleTextField9;
    private SensibleTextField sensibleTextField10;
    private SensibleTextField sensibleTextField11;

    public AllTypesFrame() {
        super();
        initialize();
    }

    public AllTypesFrame(String title) {
        super(title);
        initialize();
    }

    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == getButton1()) {
            getAllTypesObject1().getSlong().setRequired(true);
            getAllTypesObject1().getSboolean().setRequired(true);
            getAllTypesObject1().getSbigdecimal().setRequired(true);
            getAllTypesObject1().getSdate().setRequired(true);
            getAllTypesObject1().getSdatetime1().setRequired(true);
            getAllTypesObject1().getSdatetime2().setRequired(true);
            getAllTypesObject1().getStime1().setRequired(true);
            getAllTypesObject1().getStime2().setRequired(true);
            getAllTypesObject1().getSpassword().setRequired(true);
        } else if (event.getSource() == getButton2()) {
            getAllTypesObject1().getSlong().setRequired(false);
            getAllTypesObject1().getSboolean().setRequired(false);
            getAllTypesObject1().getSbigdecimal().setRequired(false);
            getAllTypesObject1().getSdate().setRequired(false);
            getAllTypesObject1().getSdatetime1().setRequired(false);
            getAllTypesObject1().getSdatetime2().setRequired(false);
            getAllTypesObject1().getStime1().setRequired(false);
            getAllTypesObject1().getStime2().setRequired(false);
            getAllTypesObject1().getSpassword().setRequired(false);
        } else if (event.getSource() == getButton3()) {
            JOptionPane.showMessageDialog(
                AllTypesFrame.this,
                getAllTypesObject1().isDataComplete() ? "the object is complete"
                    : "the object is not complete (the button should not be enabled)",
                "Information", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void propertyChange(PropertyChangeEvent event) {
        if (event.getSource() == getAllTypesObject1()) {
            if ((event.getPropertyName().equals("dataComplete"))) {
                getButton3().setEnabled(allTypesObject1.isDataComplete());
            }
        } else if (event.getSource() == getAllTypesObject1().getSstring()) {
            if ((event.getPropertyName().equals("valid"))) {
                getCheckBox1().setSelected(allTypesObject1.getSstring().isValid());
            }
        } else if (event.getSource() == getAllTypesObject1().getSinteger()) {
            if ((event.getPropertyName().equals("valid"))) {
                getCheckBox2().setSelected(allTypesObject1.getSinteger().isValid());
            }
        } else if (event.getSource() == getAllTypesObject1().getSlong()) {
            if ((event.getPropertyName().equals("valid"))) {
                getCheckBox3().setSelected(allTypesObject1.getSlong().isValid());
            }
        } else if (event.getSource() == getAllTypesObject1().getSboolean()) {
            // SensibleCheckBox are always valid
            // so we need to trap the value change event
            // and check whether the binded boolean is valid
            if ((event.getPropertyName().equals("value"))) {
                getCheckBox4().setSelected(allTypesObject1.getSboolean().isValid());
            }
        } else if (event.getSource() == getAllTypesObject1().getSbigdecimal()) {
            if ((event.getPropertyName().equals("valid"))) {
                getCheckBox5().setSelected(allTypesObject1.getSbigdecimal().isValid());
            }
        } else if (event.getSource() == getAllTypesObject1().getSdate()) {
            if ((event.getPropertyName().equals("valid"))) {
                getCheckBox6().setSelected(allTypesObject1.getSdate().isValid());
            }
        } else if (event.getSource() == getAllTypesObject1().getStime1()) {
            if ((event.getPropertyName().equals("valid"))) {
                getCheckBox7().setSelected(allTypesObject1.getStime1().isValid());
            }
        } else if (event.getSource() == getAllTypesObject1().getStime2()) {
            if ((event.getPropertyName().equals("valid"))) {
                getCheckBox8().setSelected(allTypesObject1.getStime2().isValid());
            }
        } else if (event.getSource() == getAllTypesObject1().getSdatetime1()) {
            if ((event.getPropertyName().equals("valid"))) {
                getCheckBox9().setSelected(allTypesObject1.getSdatetime1().isValid());
            }
        } else if (event.getSource() == getAllTypesObject1().getSdatetime2()) {
            if ((event.getPropertyName().equals("valid"))) {
                getCheckBox10().setSelected(allTypesObject1.getSdatetime2().isValid());
            }
        } else if (event.getSource() == getAllTypesObject1().getSpassword()) {
            if ((event.getPropertyName().equals("valid"))) {
                getCheckBox11().setSelected(allTypesObject1.getSpassword().isValid());
            }
        }
    }

    public AllTypesObject getAllTypesObject1() {
        if (allTypesObject1 == null) {
            allTypesObject1 = new AllTypesObject();
            allTypesObject1.addPropertyChangeListener(this);
            allTypesObject1.getSstring().addPropertyChangeListener(this);
            allTypesObject1.getSinteger().addPropertyChangeListener(this);
            allTypesObject1.getSlong().addPropertyChangeListener(this);
            allTypesObject1.getSboolean().addPropertyChangeListener(this);
            allTypesObject1.getSbigdecimal().addPropertyChangeListener(this);
            allTypesObject1.getSdate().addPropertyChangeListener(this);
            allTypesObject1.getStime1().addPropertyChangeListener(this);
            allTypesObject1.getStime2().addPropertyChangeListener(this);
            allTypesObject1.getSdatetime1().addPropertyChangeListener(this);
            allTypesObject1.getSdatetime2().addPropertyChangeListener(this);
            allTypesObject1.getSpassword().addPropertyChangeListener(this);
        }
        return allTypesObject1;
    }

    private JButton getButton1() {
        if (button1 == null) {
            button1 = new JButton();
            button1.setName("JButton1");
            button1.setText("make fields required");
            button1.setBounds(15, 350, 200, 25);
            button1.addActionListener(this);
        }
        return button1;
    }

    private JButton getButton2() {
        if (button2 == null) {
            button2 = new JButton();
            button2.setName("JButton2");
            button2.setText("make fields not required");
            button2.setBounds(15, 385, 200, 25);
            button2.addActionListener(this);
        }
        return button2;
    }

    private JButton getButton3() {
        if (button3 == null) {
            button3 = new JButton();
            button3.setName("JButton3");
            button3.setText("object complete");
            button3.setBounds(300, 385, 130, 25);
            button3.addActionListener(this);
        }
        return button3;
    }

    private JCheckBox getCheckBox1() {
        if (checkBox1 == null) {
            checkBox1 = new JCheckBox();
            checkBox1.setName("JCheckBox1");
            checkBox1.setText("valid value");
            checkBox1.setBounds(312, 14, 127, 22);
            checkBox1.setEnabled(false);
        }
        return checkBox1;
    }

    private JCheckBox getCheckBox2() {
        if (checkBox2 == null) {
            checkBox2 = new JCheckBox();
            checkBox2.setName("JCheckBox2");
            checkBox2.setText("valid value");
            checkBox2.setBounds(312, 44, 127, 22);
            checkBox2.setEnabled(false);
        }
        return checkBox2;
    }

    private JCheckBox getCheckBox3() {
        if (checkBox3 == null) {
            checkBox3 = new JCheckBox();
            checkBox3.setName("JCheckBox3");
            checkBox3.setText("valid value");
            checkBox3.setBounds(312, 74, 127, 22);
            checkBox3.setEnabled(false);
        }
        return checkBox3;
    }

    private JCheckBox getCheckBox4() {
        if (checkBox4 == null) {
            checkBox4 = new JCheckBox();
            checkBox4.setName("JCheckBox4");
            checkBox4.setText("valid value");
            checkBox4.setBounds(312, 104, 127, 22);
            checkBox4.setEnabled(false);
        }
        return checkBox4;
    }

    private JCheckBox getCheckBox5() {
        if (checkBox5 == null) {
            checkBox5 = new JCheckBox();
            checkBox5.setName("JCheckBox5");
            checkBox5.setText("valid value");
            checkBox5.setBounds(312, 134, 127, 22);
            checkBox5.setEnabled(false);
        }
        return checkBox5;
    }

    private JCheckBox getCheckBox6() {
        if (checkBox6 == null) {
            checkBox6 = new JCheckBox();
            checkBox6.setName("JCheckBox6");
            checkBox6.setText("valid value");
            checkBox6.setBounds(312, 164, 127, 22);
            checkBox6.setEnabled(false);
        }
        return checkBox6;
    }

    private JCheckBox getCheckBox7() {
        if (checkBox7 == null) {
            checkBox7 = new JCheckBox();
            checkBox7.setName("JCheckBox7");
            checkBox7.setText("valid value");
            checkBox7.setBounds(312, 194, 127, 22);
            checkBox7.setEnabled(false);
        }
        return checkBox7;
    }

    private JCheckBox getCheckBox8() {
        if (checkBox8 == null) {
            checkBox8 = new JCheckBox();
            checkBox8.setName("JCheckBox8");
            checkBox8.setText("valid value");
            checkBox8.setBounds(312, 224, 127, 22);
            checkBox8.setEnabled(false);
        }
        return checkBox8;
    }

    private JCheckBox getCheckBox9() {
        if (checkBox9 == null) {
            checkBox9 = new JCheckBox();
            checkBox9.setName("JCheckBox9");
            checkBox9.setText("valid value");
            checkBox9.setBounds(312, 254, 127, 22);
            checkBox9.setEnabled(false);
        }
        return checkBox9;
    }

    private JCheckBox getCheckBox10() {
        if (checkBox10 == null) {
            checkBox10 = new JCheckBox();
            checkBox10.setName("JCheckBox10");
            checkBox10.setText("valid value");
            checkBox10.setBounds(312, 284, 127, 22);
            checkBox10.setEnabled(false);
        }
        return checkBox10;
    }

    private JCheckBox getCheckBox11() {
        if (checkBox11 == null) {
            checkBox11 = new JCheckBox();
            checkBox11.setName("JCheckBox11");
            checkBox11.setText("valid value");
            checkBox11.setBounds(312, 314, 127, 22);
            checkBox11.setEnabled(false);
        }
        return checkBox11;
    }

    private JLabel getLabel1() {
        if (label1 == null) {
            label1 = new JLabel();
            label1.setName("JLabel1");
            label1.setText("string");
            label1.setBounds(15, 18, 130, 16);
        }
        return label1;
    }

    private JLabel getLabel2() {
        if (label2 == null) {
            label2 = new JLabel();
            label2.setName("JLabel2");
            label2.setText("int");
            label2.setBounds(15, 48, 130, 16);
        }
        return label2;
    }

    private JLabel getLabel3() {
        if (label3 == null) {
            label3 = new JLabel();
            label3.setName("JLabel3");
            label3.setText("long");
            label3.setBounds(15, 78, 130, 16);
        }
        return label3;
    }

    private JLabel getLabel4() {
        if (label4 == null) {
            label4 = new JLabel();
            label4.setName("JLabel4");
            label4.setText("boolean");
            label4.setBounds(15, 108, 130, 16);
        }
        return label4;
    }

    private JLabel getLabel5() {
        if (label5 == null) {
            label5 = new JLabel();
            label5.setName("JLabel5");
            label5.setText("bigdecimal");
            label5.setBounds(15, 138, 130, 16);
        }
        return label5;
    }

    private JLabel getLabel6() {
        if (label6 == null) {
            label6 = new JLabel();
            label6.setName("JLabel6");
            label6.setText("date");
            label6.setBounds(15, 168, 130, 16);
        }
        return label6;
    }

    private JLabel getLabel7() {
        if (label7 == null) {
            label7 = new JLabel();
            label7.setName("JLabel7");
            label7.setText("time (HMS)");
            label7.setBounds(15, 198, 130, 16);
        }
        return label7;
    }

    private JLabel getLabel8() {
        if (label8 == null) {
            label8 = new JLabel();
            label8.setName("JLabel8");
            label8.setText("time (HM)");
            label8.setBounds(15, 228, 130, 16);
        }
        return label8;
    }

    private JLabel getLabel9() {
        if (label9 == null) {
            label9 = new JLabel();
            label9.setName("JLabel9");
            label9.setText("date/time (HMS)");
            label9.setBounds(15, 258, 130, 16);
        }
        return label9;
    }

    private JLabel getLabel10() {
        if (label10 == null) {
            label10 = new JLabel();
            label10.setName("JLabel10");
            label10.setText("date/time (HM)");
            label10.setBounds(15, 288, 130, 16);
        }
        return label10;
    }

    private JLabel getLabel11() {
        if (label11 == null) {
            label11 = new JLabel();
            label11.setName("JLabel11");
            label11.setText("password");
            label11.setBounds(15, 318, 130, 16);
        }
        return label11;
    }

    private JPanel getMainContentPane() {
        if (mainContentPane == null) {
            mainContentPane = new JPanel();
            mainContentPane.setName("MainContentPane");
            mainContentPane.setLayout(null);
            mainContentPane.add(getLabel1(), getLabel1().getName());
            mainContentPane.add(getSensibleTextField1(), getSensibleTextField1().getName());
            mainContentPane.add(getCheckBox1(), getCheckBox1().getName());
            mainContentPane.add(getLabel2(), getLabel2().getName());
            mainContentPane.add(getSensibleTextField2(), getSensibleTextField2().getName());
            mainContentPane.add(getCheckBox2(), getCheckBox2().getName());
            mainContentPane.add(getLabel3(), getLabel3().getName());
            mainContentPane.add(getSensibleTextField3(), getSensibleTextField3().getName());
            mainContentPane.add(getCheckBox3(), getCheckBox3().getName());
            mainContentPane.add(getLabel4(), getLabel4().getName());
            mainContentPane.add(getSensibleCheckBox1(), getSensibleCheckBox1().getName());
            mainContentPane.add(getCheckBox4(), getCheckBox4().getName());
            mainContentPane.add(getLabel5(), getLabel5().getName());
            mainContentPane.add(getSensibleTextField5(), getSensibleTextField5().getName());
            mainContentPane.add(getCheckBox5(), getCheckBox5().getName());
            mainContentPane.add(getLabel6(), getLabel6().getName());
            mainContentPane.add(getSensibleTextField6(), getSensibleTextField6().getName());
            mainContentPane.add(getCheckBox6(), getCheckBox6().getName());
            mainContentPane.add(getLabel7(), getLabel7().getName());
            mainContentPane.add(getSensibleTextField7(), getSensibleTextField7().getName());
            mainContentPane.add(getCheckBox7(), getCheckBox7().getName());
            mainContentPane.add(getLabel8(), getLabel8().getName());
            mainContentPane.add(getSensibleTextField8(), getSensibleTextField8().getName());
            mainContentPane.add(getCheckBox8(), getCheckBox8().getName());
            mainContentPane.add(getLabel9(), getLabel9().getName());
            mainContentPane.add(getSensibleTextField9(), getSensibleTextField9().getName());
            mainContentPane.add(getCheckBox9(), getCheckBox9().getName());
            mainContentPane.add(getLabel10(), getLabel10().getName());
            mainContentPane.add(getSensibleTextField10(), getSensibleTextField10().getName());
            mainContentPane.add(getCheckBox10(), getCheckBox10().getName());
            mainContentPane.add(getLabel11(), getLabel11().getName());
            mainContentPane.add(getSensibleTextField11(), getSensibleTextField11().getName());
            mainContentPane.add(getCheckBox11(), getCheckBox11().getName());
            mainContentPane.add(getButton1(), getButton1().getName());
            mainContentPane.add(getButton2(), getButton2().getName());
            mainContentPane.add(getButton3(), getButton3().getName());
        }
        return mainContentPane;
    }

    private SensibleCheckBox getSensibleCheckBox1() {
        if (sensibleCheckBox1 == null) {
            sensibleCheckBox1 = new SensibleCheckBox();
            sensibleCheckBox1.setName("SensibleCheckBox1");
            sensibleCheckBox1.setText("");
            sensibleCheckBox1.setBounds(145, 104, 28, 22);
            sensibleCheckBox1.setData(getAllTypesObject1().getSboolean());
        }
        return sensibleCheckBox1;
    }

    private SensibleTextField getSensibleTextField1() {
        if (sensibleTextField1 == null) {
            sensibleTextField1 = new SensibleTextField();
            sensibleTextField1.setName("SensibleTextField1");
            sensibleTextField1.setBounds(145, 15, 150, 22);
            sensibleTextField1.setData(getAllTypesObject1().getSstring());
        }
        return sensibleTextField1;
    }

    private SensibleTextField getSensibleTextField2() {
        if (sensibleTextField2 == null) {
            sensibleTextField2 = new SensibleTextField();
            sensibleTextField2.setName("SensibleTextField2");
            sensibleTextField2.setBounds(145, 45, 150, 22);
            sensibleTextField2.setData(getAllTypesObject1().getSinteger());
        }
        return sensibleTextField2;
    }

    private SensibleTextField getSensibleTextField3() {
        if (sensibleTextField3 == null) {
            sensibleTextField3 = new SensibleTextField();
            sensibleTextField3.setName("SensibleTextField3");
            sensibleTextField3.setBounds(145, 75, 150, 22);
            sensibleTextField3.setData(getAllTypesObject1().getSlong());
        }
        return sensibleTextField3;
    }

    private SensibleTextField getSensibleTextField5() {
        if (sensibleTextField5 == null) {
            sensibleTextField5 = new SensibleTextField();
            sensibleTextField5.setName("SensibleTextField5");
            sensibleTextField5.setBounds(145, 135, 150, 22);
            sensibleTextField5.setData(getAllTypesObject1().getSbigdecimal());
        }
        return sensibleTextField5;
    }

    private SensibleTextField getSensibleTextField6() {
        if (sensibleTextField6 == null) {
            sensibleTextField6 = new SensibleTextField();
            sensibleTextField6.setName("SensibleTextField6");
            sensibleTextField6.setBounds(145, 165, 150, 22);
            sensibleTextField6.setData(getAllTypesObject1().getSdate());
        }
        return sensibleTextField6;
    }

    private SensibleTextField getSensibleTextField7() {
        if (sensibleTextField7 == null) {
            sensibleTextField7 = new SensibleTextField();
            sensibleTextField7.setName("SensibleTextField7");
            sensibleTextField7.setBounds(145, 195, 150, 22);
            sensibleTextField7.setData(getAllTypesObject1().getStime1());
        }
        return sensibleTextField7;
    }

    private SensibleTextField getSensibleTextField8() {
        if (sensibleTextField8 == null) {
            sensibleTextField8 = new SensibleTextField();
            sensibleTextField8.setName("SensibleTextField8");
            sensibleTextField8.setBounds(145, 225, 150, 22);
            sensibleTextField8.setData(getAllTypesObject1().getStime2());
        }
        return sensibleTextField8;
    }

    private SensibleTextField getSensibleTextField9() {
        if (sensibleTextField9 == null) {
            sensibleTextField9 = new SensibleTextField();
            sensibleTextField9.setBounds(145, 255, 150, 22);
            sensibleTextField9.setName("SensibleTextField9");
            sensibleTextField9.setData(getAllTypesObject1().getSdatetime1());
        }
        return sensibleTextField9;
    }

    private SensibleTextField getSensibleTextField10() {
        if (sensibleTextField10 == null) {
            sensibleTextField10 = new SensibleTextField();
            sensibleTextField10.setName("SensibleTextField10");
            sensibleTextField10.setBounds(145, 285, 150, 22);
            sensibleTextField10.setData(getAllTypesObject1().getSdatetime2());
        }
        return sensibleTextField10;
    }

    private SensibleTextField getSensibleTextField11() {
        if (sensibleTextField11 == null) {
            sensibleTextField11 = new SensibleTextField();
            sensibleTextField11.setName("SensibleTextField11");
            sensibleTextField11.setBounds(145, 315, 150, 22);
            sensibleTextField11.setPasswordField(true);
            sensibleTextField11.setData(getAllTypesObject1().getSpassword());
        }
        return sensibleTextField11;
    }

    private void initialize() {
        setName("SensibleDataType & SensibleObject Test");
        setTitle("SensibleDataType & SensibleObject Test");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(450, 450));
        setContentPane(getMainContentPane());
    }
}
