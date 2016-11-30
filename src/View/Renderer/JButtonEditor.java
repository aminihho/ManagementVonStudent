package View.Renderer;

import Controller.Update.UpdatePersonController;
import Model.Update.UpdatePersonModel;

import javax.swing.table.TableCellEditor;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.AbstractCellEditor;
import javax.swing.JTable;

/**
 * Created by annelie on 02.06.16.
 */
public class JButtonEditor extends AbstractCellEditor implements TableCellEditor{

    String text;
    public JButton button_table ;

    // View und Controller
    public UpdatePersonModel _indexModel;
    private UpdatePersonController _indexControler;

    // das ist mien Konstruktor und hier wird alle Button
    public JButtonEditor (){

        button_table=new JButton();

        button_table.setOpaque(true);
        button_table.addActionListener(_indexControler);

    }

    public JButtonEditor(UpdatePersonModel model, UpdatePersonController controler)
    {
        super();
        _indexModel=model;

        _indexControler=controler;

        button_table=new JButton();

        button_table.setOpaque(true);
        button_table.addActionListener(_indexControler);

    }

    @Override
    public Component getTableCellEditorComponent(JTable jTable, Object o, boolean b, int i, int i1) {
        button_table.setText("x");
        return button_table;
    }

    @Override
    public Object getCellEditorValue() {
        return null;
    }

    public boolean stopCellEditing(){
        return true;
    }
}
