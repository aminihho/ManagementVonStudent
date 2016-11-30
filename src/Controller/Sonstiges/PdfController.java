package Controller.Sonstiges;

import Model.Sonstiges.PdfModel;

import View.Sonstiges.PdfView;


import java.io.File;
import java.io.IOException;

import java.awt.*;
import java.awt.event.*;


/**
 * Created by annelie on 08.11.16.
 */
public class PdfController implements ActionListener, KeyListener, WindowListener {
    private PdfModel model;
    private PdfView view;

    public PdfController(PdfModel model, PdfView view){
        this.model = model;
        this.view = view;

    }

    public void init() throws IOException {


    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    @Override
    public void windowOpened(WindowEvent windowEvent) {

    }

    @Override
    public void windowClosing(WindowEvent windowEvent) {

    }

    @Override
    public void windowClosed(WindowEvent windowEvent) {

    }

    @Override
    public void windowIconified(WindowEvent windowEvent) {

    }

    @Override
    public void windowDeiconified(WindowEvent windowEvent) {

    }

    @Override
    public void windowActivated(WindowEvent windowEvent) {

    }

    @Override
    public void windowDeactivated(WindowEvent windowEvent) {

    }
}
