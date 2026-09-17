package modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Nodo del árbol general (n-ario) de decisiones de una publicación.
 *
 * Un nodo es de uno de dos tipos, según si tiene hijos o no:
 *  - Nodo de pregunta: guarda el texto que se le muestra al jugador
 *    (por ejemplo "¿Qué quieres hacer con esta publicación?") y una
 *    lista de hijos, uno por cada opción disponible.
 *  - Nodo de acción (hoja): no tiene hijos y en su lugar guarda el
 *    efecto (Impacto) que esa decisión final produce sobre la ciudad,
 *    tanto si la publicación era verdadera como si era falsa.
 *
 * Cada nodo (salvo la raíz) guarda además la "etiquetaRama": el texto
 * de la opción que el jugador eligió en el nodo padre para llegar hasta
 * aquí (p. ej. "Compartir", "Verificar", "Sí", "No", "Ignorar").
 */
public class Nodo {

    private String pregunta;
    private String etiquetaRama;
    private List<Nodo> hijos;

    private Impacto impactoSiVerdadera;
    private Impacto impactoSiFalsa;

    public Nodo(String pregunta) {
        this.pregunta = pregunta;
        this.hijos = new ArrayList<>();
    }

    public void agregarHijo(Nodo hijo, String etiquetaRama) {
        hijo.etiquetaRama = etiquetaRama;
        this.hijos.add(hijo);
    }

    public boolean esHoja() {
        return hijos.isEmpty();
    }

    public Impacto obtenerImpacto(boolean publicacionVerdadera) {
        return publicacionVerdadera ? impactoSiVerdadera : impactoSiFalsa;
    }

    public String getPregunta() {
        return pregunta;
    }

    public String getEtiquetaRama() {
        return etiquetaRama;
    }

    public List<Nodo> getHijos() {
        return hijos;
    }

    public Impacto getImpactoSiVerdadera() {
        return impactoSiVerdadera;
    }

    public void setImpactoSiVerdadera(Impacto impactoSiVerdadera) {
        this.impactoSiVerdadera = impactoSiVerdadera;
    }

    public Impacto getImpactoSiFalsa() {
        return impactoSiFalsa;
    }

    public void setImpactoSiFalsa(Impacto impactoSiFalsa) {
        this.impactoSiFalsa = impactoSiFalsa;
    }
}
