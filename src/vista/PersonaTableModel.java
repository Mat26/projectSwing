package vista;

import Exception.DAOException;
import Modelo.Persona;
import dao.PersonaDAO;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class PersonaTableModel extends AbstractTableModel {

    private PersonaDAO personas;
    private List<Persona> datos = new ArrayList<>();

    public PersonaTableModel(PersonaDAO personas) {
        this.personas = personas;
    }

    public void updateModel() throws DAOException {
        datos = personas.obtenerPersonas();
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "ID";
            case 1:
                return "Nombre";
            case 2:
                return "Apellido";
            case 3:
                return "Edad";
            default:
                return "[no]";
        }
    }

    @Override
    public int getRowCount() {
        return datos.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Persona preguntado = datos.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return preguntado.getId();
            case 1:
                return preguntado.getName();
            case 2:
                return preguntado.getApellido();
            case 3:
                return preguntado.getEdad();
            default:
                return "";
        }
    }

}
