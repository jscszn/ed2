package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import modelo.ArbolDecision;
import modelo.BancoPublicaciones;
import modelo.Ciudad;
import modelo.Impacto;
import modelo.Nodo;
import modelo.Publicacion;

// ventana preliminar de la entrega 1, no la hice con el editor de NetBeans
// porque los botones cambian según el nodo del árbol y se generan solos
public class VentanaPrincipal extends javax.swing.JFrame {

    private final ArbolDecision arbolDecision;
    private final BancoPublicaciones bancoPublicaciones;
    private final Ciudad ciudad;

    private Nodo nodoActual;
    private Publicacion publicacionActual;

    private JLabel lblAutor;
    private JTextArea txtContenido;
    private JLabel lblPregunta;
    private JPanel panelOpciones;

    private JLabel lblInformacionVerificada;
    private JLabel lblConfianza;
    private JLabel lblConvivencia;
    private JLabel lblBienestar;
    private JLabel lblDesinformacion;
    private JLabel lblConflictos;

    public VentanaPrincipal() {
        this.arbolDecision = new ArbolDecision();
        this.bancoPublicaciones = new BancoPublicaciones();
        this.ciudad = new Ciudad();

        initComponents();
        nuevaPublicacion();
        actualizarIndicadores();
    }

    private void initComponents() {
        setTitle("Alcalde Digital - Ciudad Nova");
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(640, 560);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        add(construirPanelIndicadores(), BorderLayout.NORTH);
        add(construirPanelPublicacion(), BorderLayout.CENTER);
        add(construirPanelInferior(), BorderLayout.SOUTH);
    }

    private JPanel construirPanelIndicadores() {
        JPanel panel = new JPanel(new GridLayout(2, 3, 8, 4));
        panel.setBorder(BorderFactory.createTitledBorder("Estado de Ciudad Nova"));

        lblInformacionVerificada = new JLabel();
        lblConfianza = new JLabel();
        lblConvivencia = new JLabel();
        lblBienestar = new JLabel();
        lblDesinformacion = new JLabel();
        lblConflictos = new JLabel();

        panel.add(lblInformacionVerificada);
        panel.add(lblConfianza);
        panel.add(lblConvivencia);
        panel.add(lblBienestar);
        panel.add(lblDesinformacion);
        panel.add(lblConflictos);

        return panel;
    }

    private JPanel construirPanelPublicacion() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder("Publicación en Civitas"));

        lblAutor = new JLabel();
        lblAutor.setFont(lblAutor.getFont().deriveFont(Font.BOLD));

        txtContenido = new JTextArea(3, 30);
        txtContenido.setLineWrap(true);
        txtContenido.setWrapStyleWord(true);
        txtContenido.setEditable(false);
        txtContenido.setFocusable(false);
        txtContenido.setBackground(panel.getBackground());
        txtContenido.setFont(txtContenido.getFont().deriveFont(16f));

        lblPregunta = new JLabel();
        lblPregunta.setFont(lblPregunta.getFont().deriveFont(Font.BOLD, 15f));
        lblPregunta.setForeground(new Color(20, 60, 120));
        lblPregunta.setAlignmentX(JLabel.LEFT_ALIGNMENT);

        panelOpciones = new JPanel();
        panelOpciones.setLayout(new BoxLayout(panelOpciones, BoxLayout.Y_AXIS));

        panel.add(lblAutor);
        panel.add(Box.createVerticalStrut(6));
        panel.add(txtContenido);
        panel.add(Box.createVerticalStrut(12));
        panel.add(lblPregunta);
        panel.add(Box.createVerticalStrut(6));
        panel.add(panelOpciones);

        return panel;
    }

    private JPanel construirPanelInferior() {
        JPanel panel = new JPanel();

        JButton btnVerArbol = new JButton("Ver estructura del árbol");
        btnVerArbol.addActionListener(e -> mostrarEstructuraArbol());
        panel.add(btnVerArbol);

        return panel;
    }

    private void nuevaPublicacion() {
        publicacionActual = bancoPublicaciones.siguiente();
        nodoActual = arbolDecision.getRaiz();
        actualizarPublicacion();
    }

    private void actualizarPublicacion() {
        lblAutor.setText(publicacionActual.getAutor() + " publicó:");
        txtContenido.setText("\"" + publicacionActual.getContenido() + "\"");
        actualizarNodo();
    }

    private void actualizarNodo() {
        lblPregunta.setText(nodoActual.getPregunta());
        panelOpciones.removeAll();

        for (Nodo hijo : nodoActual.getHijos()) {
            JButton boton = new JButton(hijo.getEtiquetaRama());
            boton.setAlignmentX(JButton.LEFT_ALIGNMENT);
            boton.addActionListener(e -> elegirOpcion(hijo));
            panelOpciones.add(boton);
            panelOpciones.add(Box.createVerticalStrut(4));
        }

        panelOpciones.revalidate();
        panelOpciones.repaint();
    }

    private void elegirOpcion(Nodo siguienteNodo) {
        nodoActual = siguienteNodo;

        if (nodoActual.esHoja()) {
            Impacto impacto = nodoActual.obtenerImpacto(publicacionActual.isVerdadera());
            ciudad.aplicarImpacto(impacto);
            actualizarIndicadores();

            JOptionPane.showMessageDialog(this, impacto.getMensaje(),
                    "Consecuencia de tu decisión", JOptionPane.INFORMATION_MESSAGE);

            nuevaPublicacion();
            return;
        }

        actualizarNodo();
    }

    private void actualizarIndicadores() {
        lblInformacionVerificada.setText("Información verificada: " + ciudad.getInformacionVerificada());
        lblConfianza.setText("Confianza ciudadana: " + ciudad.getConfianzaCiudadana());
        lblConvivencia.setText("Convivencia: " + ciudad.getConvivencia());
        lblBienestar.setText("Bienestar digital: " + ciudad.getBienestarDigital());
        lblDesinformacion.setText("Desinformación: " + ciudad.getDesinformacion());
        lblConflictos.setText("Conflictos: " + ciudad.getConflictos());
    }

    private void mostrarEstructuraArbol() {
        String estructura = arbolDecision.imprimir();
        String resumen = String.format(
                "Altura del árbol: %d%nHojas (acciones finales): %d%nNodos totales (preorden): %d%n%n",
                arbolDecision.altura(arbolDecision.getRaiz()),
                arbolDecision.obtenerHojas().size(),
                arbolDecision.preOrden().size());

        JTextArea texto = new JTextArea(resumen + estructura, 18, 45);
        texto.setEditable(false);
        texto.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));

        JOptionPane.showMessageDialog(this, new javax.swing.JScrollPane(texto),
                "Estructura del árbol de decisiones", JOptionPane.PLAIN_MESSAGE);
    }
}
