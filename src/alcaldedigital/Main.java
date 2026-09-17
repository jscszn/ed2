package alcaldedigital;

import javax.swing.SwingUtilities;
import vista.VentanaPrincipal;

/**
 * Punto de entrada de Alcalde Digital.
 *
 * Universidad del Norte — Estructura de Datos II — Laboratorio.
 * Equipo: Jose Castro, Daniela Idarraga, Mariana Gutierrez, Santiago Gómez.
 */
public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaPrincipal().setVisible(true));
    }
}
