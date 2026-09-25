package alcaldedigital;

import javax.swing.SwingUtilities;
import vista.VentanaPrincipal;

// entrega 1 - árboles
// Jose Castro, Daniela Idarraga, Mariana Gutierrez, Santiago Gomez
public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaPrincipal().setVisible(true));
    }
}
