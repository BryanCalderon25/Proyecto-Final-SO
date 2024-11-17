package Modelo ;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Vista.Planificador;
import Vista.Proceso;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class VentanaPlanificador extends JFrame {
    private JTable tablaProcesos;
    private DefaultTableModel modeloTabla;
    private JTextArea areaEjecucion;
    private Planificador planificador;

    public VentanaPlanificador() {
        setTitle("Planificador Multinivel - Simulador");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null); // Sin layouts, ubicamos componentes manualmente

        planificador = new Planificador();

        // Etiqueta para cargar procesos
        JLabel lblCargar = new JLabel("Cargar Procesos:");
        lblCargar.setBounds(20, 20, 150, 20);
        add(lblCargar);

        // Botón para cargar procesos
        JButton btnCargar = new JButton("Seleccionar Archivo");
        btnCargar.setBounds(150, 20, 200, 30);
        add(btnCargar);

        // Tabla para mostrar los procesos cargados
        String[] columnas = {"Tiempo de Llegada", "Prioridad", "Tiempo de Procesador", "Memoria Requerida", "Impresoras", "Cámaras", "Parlantes"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaProcesos = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tablaProcesos);
        scrollTabla.setBounds(20, 70, 850, 200);
        add(scrollTabla);

        // Botón para ejecutar planificador
        JButton btnEjecutar = new JButton("Ejecutar Planificador");
        btnEjecutar.setBounds(150, 290, 200, 30);
        add(btnEjecutar);

        // Área para mostrar la ejecución en tiempo real
        JLabel lblEjecucion = new JLabel("Ejecución de Procesos:");
        lblEjecucion.setBounds(20, 340, 200, 20);
        add(lblEjecucion);

        areaEjecucion = new JTextArea();
        areaEjecucion.setEditable(false);
        JScrollPane scrollEjecucion = new JScrollPane(areaEjecucion);
        scrollEjecucion.setBounds(20, 370, 850, 150);
        add(scrollEjecucion);

        // Acción para cargar el archivo
        btnCargar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int opcion = fileChooser.showOpenDialog(VentanaPlanificador.this);

                if (opcion == JFileChooser.APPROVE_OPTION) {
                    File archivo = fileChooser.getSelectedFile();
                    try {
                        planificador.cargarProcesosDesdeArchivo(archivo.getAbsolutePath());
                        actualizarTabla();
                        JOptionPane.showMessageDialog(VentanaPlanificador.this, "Procesos cargados correctamente.");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(VentanaPlanificador.this, "Error al cargar el archivo: " + ex.getMessage());
                    }
                }
            }
        });

        // Acción para ejecutar el planificador
        btnEjecutar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                areaEjecucion.setText(""); // Limpiar área de ejecución
                planificador.ejecutarPlanificador(areaEjecucion);
            }
        });
    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0); // Limpiar tabla
        for (Proceso proceso : planificador.getTodosLosProcesos()) {
            modeloTabla.addRow(new Object[]{
                    proceso.getTiempoLlegada(),
                    proceso.getPrioridad(),
                    proceso.getTiempoProcesador(),
                    proceso.getMemoriaRequerida(),
                    proceso.getRecursos()[0], // Impresoras
                    proceso.getRecursos()[2], // Cámaras
                    proceso.getRecursos()[3]  // Parlantes
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaPlanificador ventana = new VentanaPlanificador();
            ventana.setVisible(true);
        });
    }
}
