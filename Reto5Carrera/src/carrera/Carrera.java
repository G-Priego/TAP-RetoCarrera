package carrera;

import javax.swing.SwingUtilities;

public class Carrera {

    public static void main(String[] args) {        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AthleticRaceInterface().setVisible(true);                
            }
        });
    }
    
}
