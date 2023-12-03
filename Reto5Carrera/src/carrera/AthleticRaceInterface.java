package carrera;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class AthleticRaceInterface extends JFrame{
    private ArrayList<Runner> listaRunner;
    private ArrayList<String> listaNombres;
    private JTextField campoNombre;
    private JButton botonRegistrar;
    private JTextArea areaNombres;
    private JTextArea areaResultados;
    private static JButton botonIniciar;
    private JButton botonReiniciar;
    private JButton botonTerminar;
    private static int corredoresTerminados;
    private static boolean carreraEnCurso;
    private int contador;
    
    public AthleticRaceInterface(){
        super("Carrera atlética");
        this.setLayout(new BorderLayout());
        
        // Inicializar arraylists
        listaRunner = new ArrayList();
        listaNombres = new ArrayList();
        
        corredoresTerminados = 0;
        carreraEnCurso = false;
        contador = 0;
        addComponentes();
        addEventos();
        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void addComponentes(){
        // Panel superior
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel registrar = new JLabel("Registrar corredor");
        registrar.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        panelSuperior.add(registrar, BorderLayout.NORTH);
        campoNombre = new JTextField(20);
        panelSuperior.add(campoNombre, BorderLayout.CENTER);
        botonRegistrar = new JButton("Registrar");
        panelSuperior.add(botonRegistrar, BorderLayout.EAST);

        // Panel central
        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        areaNombres = new JTextArea(10, 30);
        areaNombres.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaNombres);
        panelCentral.add(scroll, BorderLayout.CENTER);
        JLabel etiqueta = new JLabel("Corredores registrados");
        etiqueta.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        panelCentral.add(etiqueta, BorderLayout.NORTH);

        // Panel inferior
        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel labelResultados = new JLabel("Resultados");
        labelResultados.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        panelInferior.add(labelResultados, BorderLayout.NORTH);
        JPanel panelBotones = new JPanel();
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));
        panelBotones.setLayout(new GridLayout(3, 1, 0, 20));
        botonIniciar = new JButton("Iniciar");
        panelBotones.add(botonIniciar);
        botonReiniciar = new JButton("Reiniciar");
        panelBotones.add(botonReiniciar);
        botonTerminar = new JButton("Terminar");
        panelBotones.add(botonTerminar);
        areaResultados = new JTextArea(10, 30);
        areaResultados.setEditable(false);
        JScrollPane scroll2 = new JScrollPane(areaResultados);
        panelInferior.add(scroll2, BorderLayout.CENTER);
        panelInferior.add(panelBotones, BorderLayout.EAST);
        
        // Añadir paneles a la ventana
        this.add(panelSuperior, BorderLayout.NORTH);
        this.add(panelCentral, BorderLayout.CENTER);
        this.add(panelInferior, BorderLayout.SOUTH);        
    }
    
    public void addEventos(){
        botonRegistrar.addActionListener(new BotonRegistrarListener());
        botonIniciar.addActionListener(new BotonIniciarListener());
        botonReiniciar.addActionListener(new BotonReiniciarListener());
        botonTerminar.addActionListener(new BotonTerminarListener());
    }
    
    private class BotonRegistrarListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent ae) {        
            // Definir un patrón de nombre valido Ejemplo: Nombre Apellido
            Pattern patronNombre = Pattern.compile("[A-Z][a-z]+\\s[A-Z][a-z]+");
            
            if (patronNombre.matcher(campoNombre.getText()).matches()) { //Validación de patrón establecido
                if(listaNombres.contains(campoNombre.getText())){
                    JOptionPane.showMessageDialog(null, "El participante ingresado ya existe", "Alerta", JOptionPane.WARNING_MESSAGE);
                }else{
                    if(listaNombres.isEmpty() || listaNombres.size()<5){ // Validación de tamaño máximo de 5
                        listaNombres.add(campoNombre.getText());
                        campoNombre.setText("");
                        if (listaNombres.size()==5){
                            setArregloRunner();
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "No se puede registrar más de 5 participantes ", "Alerta", JOptionPane.WARNING_MESSAGE);
                        setArregloRunner();
                    }
                }
            } else {
                // Si no cumple con el formato de Nombre Apellido
                JOptionPane.showMessageDialog(null, "Ingrese un nombre válido. Ejemplo: Greta Priego", "Error", JOptionPane.WARNING_MESSAGE);
            }
            mostrarNombres();            
        }        
    }
    
    private class BotonIniciarListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent ae) {
            if(listaNombres.isEmpty()){
                JOptionPane.showMessageDialog(null, "No se puede iniciar la carrera sin participantes", "Alerta", JOptionPane.WARNING_MESSAGE);
            }else if(listaNombres.size()<5){
                JOptionPane.showMessageDialog(null, "Se requiere de 5 participantes ", "Alerta", JOptionPane.WARNING_MESSAGE);
            }
            if (contador!=0){
                areaResultados.setText("");
            }
            if (!carreraEnCurso) {
                contador++;
                // Deshabilitar el botón para evitar múltiples clics
                botonIniciar.setEnabled(false);
                iniciarCarrera();
            }
        }        
    }
    
    private void iniciarCarrera() { 
        carreraEnCurso = true;
        for (int i = 0; i < listaRunner.size(); i++) {
            ThreadRunner thRunner = new ThreadRunner(listaRunner.get(i), areaResultados);
            Thread hilo = new Thread(thRunner);
            hilo.start();
        }
    }
    
    public static void corredorTerminado(){
        corredoresTerminados++;
        if (corredoresTerminados == 5) {
            carreraEnCurso = false;
            corredoresTerminados = 0;
            botonIniciar.setEnabled(true);
        }    
    }
    
    private class BotonReiniciarListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent ae) {            
            listaNombres.clear();
            listaRunner.clear();
            mostrarNombres();
            areaResultados.setText("");
        }        
    }
    
    private class BotonTerminarListener implements ActionListener {   
        @Override
        public void actionPerformed(ActionEvent ae) {
            System.exit(0);         
        }        
    }
    
    public void mostrarNombres(){
        StringBuilder sb = new StringBuilder();
        for (String nombre : listaNombres) {
            sb.append(nombre).append("\n");
        }
        areaNombres.setText(sb.toString());
    }
    
    public void setArregloRunner(){        
        for (int i = 0; i < 5; i++) {
            listaRunner.add(new Runner(listaNombres.get(i), Runner.numeroRandom()));
        }
    }
}
