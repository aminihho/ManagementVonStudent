/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Sonstiges;
import java.util.Observable;
import java.util.Observer;
import Model.Sonstiges.ExcelModel;
import Controller.Sonstiges.ExcelController;
import org.json.JSONException;
/**
 *
 * @author kourda
 */
public class ExcelView implements Observer {

        // Model.
	private ExcelModel model;
        private ExcelController controller;
        
    public ExcelView(ExcelModel _model, String name  ) throws JSONException{
        
        this.model = _model; 
        this.model.addObserver(this);
        this.controller = new ExcelController ( this.model, this);
        this.controller.createExcelDatei();
       
    }
    
    @Override
    public void update(Observable o, Object arg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
