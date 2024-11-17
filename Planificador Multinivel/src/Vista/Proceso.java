package Vista;

public class Proceso {
    private int id;
    private int prioridad;
    private int tiempoLlegada;
    public int tiempoProcesador;
    private int tiempoRestante;
    public int memoriaRequerida;
    private int[] recursos; // Recursos: [impresoras, escáneres, cámaras, parlantes]
    private String estado;

    public Proceso(int id, int prioridad, int tiempoLlegada, int tiempoProcesador, int memoriaRequerida, int[] recursos) {
        this.id = id;
        this.prioridad = prioridad;
        this.tiempoLlegada = tiempoLlegada;
        this.tiempoProcesador = tiempoProcesador;
        this.tiempoRestante = tiempoProcesador;
        this.memoriaRequerida = memoriaRequerida;
        this.recursos = recursos;
        this.estado = "listo"; // Estado inicial
    }

    // Métodos Getters y Setters
    public int getId() { return id; }
    public int getPrioridad() { return prioridad; }
    public int getTiempoProcesador() { return tiempoProcesador; }
    public int getMemoriaRequerida() { return memoriaRequerida; }

    public int getTiempoLlegada() { return tiempoLlegada; }
    public int getTiempoRestante() { return tiempoRestante; }
    public int[] getRecursos() { return recursos; }
    public String getEstado() { return estado; }

    public void decrementarTiempo() {
        if (tiempoRestante > 0) tiempoRestante--;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void degradarPrioridad() {
        if (prioridad < 3) prioridad++;
    }
}
