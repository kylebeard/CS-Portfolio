import java.awt.Component;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

public class EnumCellEditor<E extends Enum<E>> extends JComboBox<E> implements TableCellEditor {

    private List<CellEditorListener> listeners = new ArrayList<>();

    public EnumCellEditor(E[] items) {
        super(items);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int col) {
        setForeground(table.getForeground());
        setBackground(table.getBackground());
        setFont(table.getFont());
        setSelectedItem(value);

        return this;
    }

    @Override
    public void addCellEditorListener(CellEditorListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeCellEditorListener(CellEditorListener listener) {
        listeners.remove(listener);
    }
    
    @Override
    public void cancelCellEditing() {
        ChangeEvent e = new ChangeEvent(this);
        for (CellEditorListener listener : listeners)
            listener.editingCanceled(e);
    }

    @Override
    public boolean stopCellEditing() {
        ChangeEvent e = new ChangeEvent(this);
        List<CellEditorListener> tempListeners = new ArrayList<>(listeners);
        for (CellEditorListener listener : tempListeners)
            listener.editingStopped(e);
        return true;
    }

    @Override
    public boolean shouldSelectCell(EventObject eo) {
        return false;
    }

    @Override
    public boolean isCellEditable(EventObject eo) {
        return true;
    }

    @Override
    public Object getCellEditorValue() {
        return getSelectedItem();
    }
}
