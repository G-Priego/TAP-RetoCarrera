package carrera;

import java.util.Random;

public class Runner {
    private String nombre;
    private int velocidad;

    public Runner(String nombre, int velocidad) {
        this.nombre = nombre;
        this.velocidad = velocidad;
    }
    
    public static int numeroRandom(){
        Random random = new Random();
        int num = random.nextInt(30)+1;
        return num;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }
    
    
}
