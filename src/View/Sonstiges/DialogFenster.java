package View.Sonstiges;

import javax.swing.*;
import java.awt.*;

/**
 * Created by annelie on 02.06.16.
 */
public class DialogFenster extends JFrame{
    public DialogFenster(){}

    /**
     *  Yes no Option Panel
     *
     *  Output: Ja = 0, Nein = 1
     */
    public int dialogFensterFragen ( JFrame view, String message,String title ){

        JLabel frageLbl = new JLabel("<html>" + message + "</html>");
        frageLbl.setFont(new Font("Sans Serif", Font.PLAIN, 15));

        int n = JOptionPane.showConfirmDialog(
                view,
                frageLbl,
                title,
                JOptionPane.YES_NO_OPTION);
        return n;
    }

    public String dialogFensterInput(JFrame view, Object[] possibilities, String defaultoption ,String message,String title)
    {
        JLabel frageLbl = new JLabel("<html>" + message + "</html>");
        frageLbl.setFont(new Font("Sans Serif", Font.PLAIN, 15));

        String s = (String)JOptionPane.showInputDialog(
                view,
                frageLbl,
                title,
                JOptionPane.PLAIN_MESSAGE,
                null,
                possibilities,
                defaultoption);

        //If a string was returned, say so.
        if ((s != null) && (s.length() > 0)) {
            return s;
        }

        return "";
    }


    public String dialogFensterInputText(JFrame view ,String message,String title)
    {

        String s = (String)JOptionPane.showInputDialog(
                view,message,
                title,
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                null);

        //If a string was returned, say so.
        if ((s != null) && (s.length() > 0)) {
            return s;
        }
        return "";
    }


    public void erfolgDialog(JFrame view, String message, String titel){

        JLabel erfolgEingabeLbl = new JLabel("<html>" + message + "</html>");
        erfolgEingabeLbl.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        Icon erfolgIcon = new ImageIcon(getClass().getResource("/res/yes.png"));


        //custom title, custom icon
        JOptionPane.showMessageDialog(view,
                erfolgEingabeLbl,
                titel,
                JOptionPane.PLAIN_MESSAGE,
                erfolgIcon);


    }

    public void errorDialog(JFrame view, String message){

        JLabel fehlerEingabeLbl = new JLabel("<html>" + message +" </html>");
        fehlerEingabeLbl.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        Icon fehlerIcon = new ImageIcon(getClass().getResource("/res/nope.png")); //

        //custom title, custom icon
        JOptionPane.showMessageDialog(this,
                fehlerEingabeLbl,
                "Fehler",
                JOptionPane.PLAIN_MESSAGE,
                fehlerIcon);
    }

    public void infoDialog(JFrame view, String message){

        JLabel fehlerEingabeLbl = new JLabel("<html>" + message +" </html>");
        fehlerEingabeLbl.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        Icon fehlerIcon = new ImageIcon(getClass().getResource("/res/warning.png")); //warning.png

        //custom title, custom icon
        JOptionPane.showMessageDialog(this,
                fehlerEingabeLbl,
                "Information",
                JOptionPane.PLAIN_MESSAGE,
                fehlerIcon);
    }

    public void informationDialog(JFrame view, String message, String titel){

        JLabel fehlerEingabeLbl = new JLabel("<html>" + message +" </html>");
        fehlerEingabeLbl.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        Icon infoIcon = new ImageIcon(getClass().getResource("/res/info.png"));

        //custom title, custom icon
        JOptionPane.showMessageDialog(this,
                fehlerEingabeLbl,
                titel,
                JOptionPane.PLAIN_MESSAGE,
                infoIcon);
    }
}
