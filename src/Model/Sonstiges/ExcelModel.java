/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Sonstiges;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;
import org.json.JSONObject;

/**
 *
 * @author kourda
 */
public class ExcelModel extends Observable {

    JSONObject studentsInfo = null;
    private ArrayList<String> querysInfo = null; 
    
   public ExcelModel(JSONObject students_inf){
       
       this.studentsInfo = students_inf; 
   }
   
   
   public JSONObject getStudentsInfo(){
       
       return this.studentsInfo; 
   }
   
   
    public void setQueryInfo(ArrayList<String> _newListe ){
        
        this.querysInfo = _newListe; 
    }
    
    public ArrayList<String> getQueryInfo(){
        
        return this.querysInfo; 
    }
 

}
