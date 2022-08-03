package vista;

import Exception.DAOException;
import Modelo.Persona;
import dao.DAOManager;
import dao.mysql.MySQLDaoManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class VentanaPersonas extends javax.swing.JFrame {

    private DAOManager manager;
    private PersonaTableModel model;

    public VentanaPersonas(DAOManager manager) throws DAOException {
        setTitle("Mintic: Registro Personas");
        //initComponents();
        this.manager = manager;
        this.model = new PersonaTableModel(manager.getPersonaDAO());
        this.model.updateModel();
        this.tabla.setModel(model);
        this.tabla.getSelectionModel().addListSelectionListener(e -> validarSeleccionado());
    }

    private void validarSeleccionado() {
        boolean seleccionValida = tabla.getSelectedRow() != -1;
        editar.setEnabled(seleccionValida);
        borrar.setEnabled(seleccionValida);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Toolbar = new javax.swing.JToolBar();
        nuevo = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        editar = new javax.swing.JButton();
        borrar = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        guardar = new javax.swing.JButton();
        cancelar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        detalle = new vista.DPersonaVentana();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Toolbar.setRollover(true);

        nuevo.setText("Nuevo");
        nuevo.setFocusable(false);
        nuevo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        nuevo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        nuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nuevoActionPerformed(evt);
            }
        });
        Toolbar.add(nuevo);
        Toolbar.add(jSeparator1);

        editar.setText("Editar");
        editar.setEnabled(false);
        editar.setFocusable(false);
        editar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        editar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        editar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editarActionPerformed(evt);
            }
        });
        Toolbar.add(editar);

        borrar.setText("Borrar");
        borrar.setEnabled(false);
        borrar.setFocusable(false);
        borrar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        borrar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        borrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                borrarActionPerformed(evt);
            }
        });
        Toolbar.add(borrar);
        Toolbar.add(jSeparator2);

        guardar.setText("Guardar");
        guardar.setEnabled(false);
        guardar.setFocusable(false);
        guardar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        guardar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardarActionPerformed(evt);
            }
        });
        Toolbar.add(guardar);

        cancelar.setText("Cancelar");
        cancelar.setEnabled(false);
        cancelar.setFocusable(false);
        cancelar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cancelar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelarActionPerformed(evt);
            }
        });
        Toolbar.add(cancelar);

        getContentPane().add(Toolbar, java.awt.BorderLayout.PAGE_START);

        jPanel1.setLayout(new java.awt.BorderLayout());

        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tabla);

        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);
        jPanel1.add(detalle, java.awt.BorderLayout.LINE_END);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private Persona getPersonaSeleccionado() throws DAOException {
        Long id = (Long) tabla.getValueAt(tabla.getSelectedRow(), 0);
        return manager.getPersonaDAO().obtenerPorId(id);
    }

    private void editarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editarActionPerformed
        try {
            Persona persona = getPersonaSeleccionado();
            detalle.setPersona(persona);
            detalle.setEditable(true);
            detalle.cargarDatos();
            guardar.setEnabled(true);
            cancelar.setEnabled(true);
        } catch (DAOException ex) {
            System.out.println("Error al cargar los datos del detalle.");
        }
    }//GEN-LAST:event_editarActionPerformed

    private void cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelarActionPerformed
        detalle.setPersona(null);
        detalle.setEditable(false);
        detalle.cargarDatos();
        tabla.clearSelection();
        guardar.setEnabled(false);
        cancelar.setEnabled(false);
    }//GEN-LAST:event_cancelarActionPerformed

    private void nuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevoActionPerformed
        detalle.setPersona(null);
        detalle.cargarDatos();
        detalle.setEditable(true);
        guardar.setEnabled(true);
        cancelar.setEnabled(true);
    }//GEN-LAST:event_nuevoActionPerformed

    private void guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarActionPerformed
        try {
            detalle.guardarDatos();
            Persona persona = detalle.getPersona();
            if (persona.getId() == null) {
                manager.getPersonaDAO().insertar(persona);
            } else {
                manager.getPersonaDAO().modificar(persona);
            }
            detalle.setPersona(null);
            detalle.setEditable(false);
            detalle.cargarDatos();
            tabla.clearSelection();
            guardar.setEnabled(false);
            cancelar.setEnabled(false);
            
            model.updateModel();
            model.fireTableDataChanged();
        } catch (DAOException ex) {
            System.out.println("Error al insertar o modificar en la base de datos.");
        }
    }//GEN-LAST:event_guardarActionPerformed

    private void borrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_borrarActionPerformed
        if(JOptionPane.showConfirmDialog(rootPane, "¿Seguro que quieres borrar esta persona?",
                "Borrar Persona",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
            try{
            Persona persona = getPersonaSeleccionado();
            manager.getPersonaDAO().eliminar(persona);
            model.updateModel();
            model.fireTableDataChanged();
            }catch(DAOException ex){
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }//GEN-LAST:event_borrarActionPerformed

    public static void main(String args[]) throws SQLException {
        DAOManager manager = new MySQLDaoManager("localhost:3306", "ejemploProjectSwing", "root", "password");
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new VentanaPersonas(manager).setVisible(true);
            } catch (DAOException e) {
                System.out.println("Errorrrrrrrrrrrrrrr");
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToolBar Toolbar;
    private javax.swing.JButton borrar;
    private javax.swing.JButton cancelar;
    private vista.DPersonaVentana detalle;
    private javax.swing.JButton editar;
    private javax.swing.JButton guardar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JButton nuevo;
    private javax.swing.JTable tabla;
    // End of variables declaration//GEN-END:variables
}
