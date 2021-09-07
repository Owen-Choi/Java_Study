package Small_Projects;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//  this code is written automatically by GUI.form
//  https://www.youtube.com/watch?v=-SmNpKskfJc
//  https://docs.oracle.com/javase/tutorial/uiswing/learn/index.html
public class CelsiusConverterGUI extends JFrame{
    private JPanel mainPanel;
    private JTextField celsisusTextField;
    private JLabel celsiusLabel;
    private JButton convertButton;
    private JLabel fahrenheitLabel;

    public CelsiusConverterGUI(String title) {
        super(title);       //JFrame

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);     //Guess this method is for Frame. that's why its parameter is JPanel
        this.pack();                        //Guess this method is for fitting
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Grab to the text from the celsiusTextField
                //Conver to a double
                //Update the fahrenheitLabel
                int tempFahr = (int)((Double.parseDouble(celsisusTextField.getText())) * 1.8 + 32);
                fahrenheitLabel.setText(tempFahr + "Fahrenheit");
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new CelsiusConverterGUI("My First Celsius Converter");
        frame.setVisible(true);             //We can hide window through this method
    }
}
