package Vista ;
import java.io.*;
import java.util.*;

import javax.swing.JTextArea;
public class Planificador {
    private List<Proceso> todosLosProcesos = new ArrayList<>();
    private Queue<Proceso> colaTiempoReal = new LinkedList<>();
    private Queue<Proceso> colaUsuario1 = new LinkedList<>();
    private Queue<Proceso> colaUsuario2 = new LinkedList<>();
    private Queue<Proceso> colaUsuario3 = new LinkedList<>();

    public void cargarProcesosDesdeArchivo(String rutaArchivo) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(rutaArchivo));
        String linea;
        int id = 1;

        while ((linea = br.readLine()) != null) {
            String[] datos = linea.split(",");
            int tiempoLlegada = Integer.parseInt(datos[0]);
            int prioridad = Integer.parseInt(datos[1]);
            int tiempoProcesador = Integer.parseInt(datos[2]);
            int memoriaRequerida = Integer.parseInt(datos[3]);
            int[] recursos = {
                    Integer.parseInt(datos[4]), // Impresoras
                    Integer.parseInt(datos[5]), // Escáneres
                    Integer.parseInt(datos[6]), // Cámaras
                    Integer.parseInt(datos[7])  // Parlantes
            };

            Proceso proceso = new Proceso(id++, prioridad, tiempoLlegada, tiempoProcesador, memoriaRequerida, recursos);
            todosLosProcesos.add(proceso);
            agregarProceso(proceso);
        }
        br.close();
    }

    public List<Proceso> getTodosLosProcesos() {
        return todosLosProcesos;
    }

    private void agregarProceso(Proceso proceso) {
        switch (proceso.getPrioridad()) {
            case 0 -> colaTiempoReal.add(proceso);
            case 1 -> colaUsuario1.add(proceso);
            case 2 -> colaUsuario2.add(proceso);
            case 3 -> colaUsuario3.add(proceso);
        }
    }

    public void ejecutarPlanificador(JTextArea areaEjecucion) {
        while (!colaTiempoReal.isEmpty() || !colaUsuario1.isEmpty() || !colaUsuario2.isEmpty() || !colaUsuario3.isEmpty()) {
            Proceso proceso = null;

            if (!colaTiempoReal.isEmpty()) proceso = colaTiempoReal.poll();
            else if (!colaUsuario1.isEmpty()) proceso = colaUsuario1.poll();
            else if (!colaUsuario2.isEmpty()) proceso = colaUsuario2.poll();
            else if (!colaUsuario3.isEmpty()) proceso = colaUsuario3.poll();

            if (proceso != null) {
                areaEjecucion.append("Ejecutando Proceso ID: " + proceso.getId() + "\n");
                proceso.decrementarTiempo();

                if (proceso.getTiempoRestante() > 0) {
                    agregarProceso(proceso);
                } else {
                    areaEjecucion.append("Proceso ID: " + proceso.getId() + " finalizado.\n");
                }
            }
        }
    }
}
