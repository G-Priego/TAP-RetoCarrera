package carrera;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class ThreadRunner implements Runnable{
    private String nombre;
    private int velocidad;
    private JTextArea areaResultados;

    public ThreadRunner(Runner runner, JTextArea areaResultados){
        this.nombre = runner.getNombre();
        this.velocidad = runner.getVelocidad();
        this.areaResultados = areaResultados;
    }
    
    @Override
    public void run() {
        int tiempo = velocidad*1000;
        
        try {
            Thread.sleep(tiempo); // Método sleep
            actualizarTextArea("Corredor " + nombre + " - Tiempo: " + velocidad + " segundos");
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            AthleticRaceInterface.corredorTerminado();
        }
    }
    
    private void actualizarTextArea(final String mensaje) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                areaResultados.append(mensaje + "\n");
            }
        });
    }
}
