package View.Renderer;

import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * Created by annelie on 13.06.16.
 */
public class JButtonRenderBemerkung implements TableCellRenderer{

    JButton button =new JButton ();

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        if(value==null)
        {

            button.setText("x");
            button.setToolTipText("Bemerkung Löschen");
        }
        else
        {
            button.setText("x");
        }
        return button;
    }
}
