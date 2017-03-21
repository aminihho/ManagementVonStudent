package Model.Sonstiges;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Kourda and Annelie on 13.06.16.
 */
public class Functions {

    public Functions(){}
    /***
     * Diese Function konveriert ein ArrayList von Arraylist in array von array von String.
     * @parm1: ist ein ArrayList
     * RückgabeWert: ist ein Array von String.
     */

    public String[][] arrayListTo2DArrayVonString(ArrayList<ArrayList<String>> listarray){

        int zeillenAnzahl= listarray.size(); int i=0; int j=0;


        String[][] array=new String[zeillenAnzahl][100];

        for(i=0;i<zeillenAnzahl;i++){

            int spaltenAnzahl=listarray.get(i).size();

            for(j=0;j<spaltenAnzahl;j++){

                if(listarray.get(i).get(j) != null)
                    array[i][j]=listarray.get(i).get(j).toString();

                else
                    array[i][j] = "";


            }
        }

        return array;
    }

    /***
     * Diese Function konveriert ein Arraylist in array von String.
     * @parm1: ist ein ArrayList
     * RückgabeWert: ist ein Array von String.
     */

    public String[] arrayListTo1DString(ArrayList<String> listarray){

        int zeillenAnzahl= listarray.size(); int i=0;
        String[] array=new String[zeillenAnzahl];

        for(i=0;i<zeillenAnzahl;i++){

            if( listarray.get(i) != null){
                array[i] = listarray.get(i).toString();
            }

            else{
                array[i] = "";
            }
        }

        return array;
    }

    public String[] arrayListTo1DStringFuerCB(ArrayList<String> listarray){

        int zeillenAnzahl= listarray.size(); int i=0;
        String[] array=new String[zeillenAnzahl];

        array[0] = "-";
        for(i = 1;i < zeillenAnzahl;i++) {
            array[i]=listarray.get(i).toString();
        }

        return array;
    }

    public String[] arrayListTo1DStringFuerCBohneBS(ArrayList<String> listarray){

        int zeillenAnzahl= listarray.size(); int i=0;
        String[] array=new String[zeillenAnzahl];

        for(i = 0;i < zeillenAnzahl;i++) {
            array[i]=listarray.get(i).toString();
        }

        return array;
    }





    /**
     * diese Funkton konvertiert 2-D Object Liste in 1-D Object Liste.
     * @param liste2d  array in 2 D
     * @return
     */
    public ArrayList<String> arrayListe2DTo1DArrayListe(ArrayList<ArrayList<String>> liste2d){
        ArrayList<String>liste=new ArrayList<String>();

        for(int j=0; j<liste2d.size();j++)
        {
            for(int i=0;i<liste2d.get(j).size();i++)
            {
                liste.add(liste2d.get(j).get(i));
            }
        }
        return liste;
    }

    /**
     *    liste : 1234568
     *    resutl  1-2,3-4,5-6,7-8
     *
     */
    public ArrayList<String> ConcateSpalten (int spaltenAnzahl, ArrayList<String> liste, char character ) {

        ArrayList<String> resultList = new ArrayList<String>();
        int size = liste.size();

        for(int i=0; i < size; i+=2){
            String temp = liste.get(i) + " " + character + " " + liste.get(i+1);
            resultList.add(temp);
        }

        return resultList;
    }

    public boolean exists(String value, ArrayList<String> liste){

        String stripedValue = value.replaceAll("\\s","");
        stripedValue = stripedValue.toLowerCase();
        int size = liste.size();

        for (int i = 0; i< size; i++){
            String status = liste.get(i);
            status = status.replaceAll("\\s","");
            status = status.toLowerCase();

         

            if(status.equals(stripedValue)){
                return true;
            }

        }

        return  false;

    }

    public boolean existsOnce(String value1, String value2, ArrayList<String> liste){

        String stripedValue1 = value1.replaceAll("\\s","");
        stripedValue1 = stripedValue1.toLowerCase();

        String stripedValue2 = value2.replaceAll("\\s","");
        stripedValue2 = stripedValue2.toLowerCase();


        int size = liste.size();

        for (int i = 0; i< size; i++){
            String valueList = liste.get(i);
            valueList = valueList.replaceAll("\\s","");
            valueList = valueList.toLowerCase();


            if(valueList.equals(value1) && (!value1.equals(value2))){
        
                return true;
            }

        }

        return  false;

    }

    public boolean mobExists(String[][] listeAktivitaeten){

        int zeilen = listeAktivitaeten.length;

        for (int i = 0; i < zeilen; i++){
            String nameAkt = listeAktivitaeten[i][1];

            //System.out.println(nameAkt.substring(0, 2));

            if (nameAkt.substring(0, 2).equals("M_")){
                return true;
            }
        }

        return false;
    }


public  JSONArray speicherenArray1DimInJsonObject (String[] arrayWert, String indexname) throws JSONException{
    
    if(arrayWert.length <=0 ){
        return null; 
    }
    
   
    JSONArray jsonArray = new JSONArray(); 
    JSONObject obj = new JSONObject();
        for(int i=0; i< arrayWert.length; i++){
        
                String index = indexname+Integer.toString(i) ; 
                obj.put(index, arrayWert[i]);
      
        }
        jsonArray.put(obj); 
    return jsonArray ; 
}



    public JSONArray speicherenArray2DimInJsonObject(ArrayList<ArrayList<String>> list_aktivitaten, String indexname) throws JSONException {
        JSONArray jsonArray = new JSONArray();

        if (list_aktivitaten.size() <= 0) {
            return null;
        }

        for (int i = 0; i < list_aktivitaten.size(); i++) {
            //  System.out.println(Integer.toString(i)+Integer.toString(j)+list_aktivitaten.get(i).get(j));
            JSONObject jsonObject = new JSONObject();

            if (list_aktivitaten.get(i).size() == 3) {
                jsonObject.put("aktivitaet_name", list_aktivitaten.get(i).get(0));
                jsonObject.put("aktivitaet_id", list_aktivitaten.get(i).get(1));
                jsonObject.put("aktivitaet_zeitraum", list_aktivitaten.get(i).get(2));

            } else {
                jsonObject.put("aktivitaet_name", list_aktivitaten.get(i).get(0));
                jsonObject.put("aktivitaet_id", list_aktivitaten.get(i).get(1));
                jsonObject.put("aktivitaet_zeitraum", list_aktivitaten.get(i).get(2));
                jsonObject.put("aktivitaet_durchfuerung", list_aktivitaten.get(i).get(3));
                jsonObject.put("aktivitaet_art", list_aktivitaten.get(i).get(4));
            }
            jsonArray.put(jsonObject);
        }

        return jsonArray;
    }


}
