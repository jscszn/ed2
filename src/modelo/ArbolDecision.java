package modelo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Árbol general (n-ario) de decisiones frente a una publicación de Civitas.
 *
 * ¿Qué problema resuelve?
 * Cada publicación que llega a un jugador dispara una secuencia de
 * decisiones jerárquicas y excluyentes: elegir "Verificar" abre una
 * subpregunta ("¿Es verdadera?") que no existe si el jugador elige
 * "Ignorar" o "Compartir" directamente. El árbol modela exactamente esa
 * ramificación y, además, es la estructura que la interfaz recorre para
 * saber qué botones mostrar en cada momento y qué consecuencia aplicar
 * a los indicadores de Ciudad Nova cuando el jugador llega a una hoja.
 *
 * ¿Por qué un árbol y no otra estructura?
 * Una lista o un arreglo no representan la relación padre-hijo entre una
 * pregunta y sus opciones, ni permiten que una rama tenga una profundidad
 * distinta a otra (p. ej. "Compartir" resuelve en un solo paso, mientras
 * que "Verificar" necesita un paso adicional). El árbol sí lo hace de
 * forma natural.
 *
 * ¿Qué variante se usa?
 * Un árbol general (n-ario), no binario: un nodo de pregunta puede tener
 * más de dos hijos (la raíz tiene tres: Compartir, Verificar, Ignorar).
 *
 * ¿Cómo se insertan y eliminan elementos?
 * insertarPregunta() e insertarAccion() agregan un nodo hijo (de pregunta
 * o de acción) a un nodo padre ya existente, etiquetando la rama con el
 * texto de la opción. eliminarRama() quita un hijo (y con él todo su
 * subárbol) de un nodo padre, identificándolo por su etiqueta.
 *
 * ¿Cómo se recorre?
 * preOrden() recorre el árbol en profundidad (raíz, luego cada hijo).
 * porNiveles() lo recorre en anchura con una cola, nivel por nivel.
 * avanzar() es el recorrido dirigido por el jugador: dado un nodo actual
 * y la opción elegida, devuelve el hijo correspondiente (o null si la
 * opción no existe en ese nodo).
 */
public class ArbolDecision {

    private Nodo raiz;

    public ArbolDecision() {
        this.raiz = new Nodo("¿Qué quieres hacer con esta publicación?");
        construirArbolPorDefecto(this.raiz);
    }

    public Nodo getRaiz() {
        return raiz;
    }

    // ---------- Inserción ----------

    public Nodo insertarPregunta(Nodo padre, String etiquetaRama, String pregunta) {
        Nodo nodo = new Nodo(pregunta);
        padre.agregarHijo(nodo, etiquetaRama);
        return nodo;
    }

    public Nodo insertarAccion(Nodo padre, String etiquetaRama, Impacto impactoSiVerdadera, Impacto impactoSiFalsa) {
        Nodo nodo = new Nodo(null);
        nodo.setImpactoSiVerdadera(impactoSiVerdadera);
        nodo.setImpactoSiFalsa(impactoSiFalsa);
        padre.agregarHijo(nodo, etiquetaRama);
        return nodo;
    }

    // ---------- Eliminación ----------

    public boolean eliminarRama(Nodo padre, String etiquetaRama) {
        if (padre == null || etiquetaRama == null) {
            return false;
        }
        return padre.getHijos().removeIf(hijo -> etiquetaRama.equalsIgnoreCase(hijo.getEtiquetaRama()));
    }

    // ---------- Recorridos ----------

    public List<Nodo> preOrden() {
        List<Nodo> resultado = new ArrayList<>();
        preOrden(raiz, resultado);
        return resultado;
    }

    private void preOrden(Nodo nodo, List<Nodo> resultado) {
        if (nodo == null) {
            return;
        }
        resultado.add(nodo);
        for (Nodo hijo : nodo.getHijos()) {
            preOrden(hijo, resultado);
        }
    }

    public List<Nodo> porNiveles() {
        List<Nodo> resultado = new ArrayList<>();
        Queue<Nodo> cola = new LinkedList<>();
        if (raiz != null) {
            cola.add(raiz);
        }
        while (!cola.isEmpty()) {
            Nodo actual = cola.poll();
            resultado.add(actual);
            cola.addAll(actual.getHijos());
        }
        return resultado;
    }

    public List<Nodo> obtenerHojas() {
        List<Nodo> hojas = new ArrayList<>();
        obtenerHojas(raiz, hojas);
        return hojas;
    }

    private void obtenerHojas(Nodo nodo, List<Nodo> hojas) {
        if (nodo == null) {
            return;
        }
        if (nodo.esHoja()) {
            hojas.add(nodo);
            return;
        }
        for (Nodo hijo : nodo.getHijos()) {
            obtenerHojas(hijo, hojas);
        }
    }

    public int altura(Nodo nodo) {
        if (nodo == null || nodo.esHoja()) {
            return 1;
        }
        int maximo = 0;
        for (Nodo hijo : nodo.getHijos()) {
            maximo = Math.max(maximo, altura(hijo));
        }
        return maximo + 1;
    }

    // ---------- Navegación dirigida por el jugador ----------

    public Nodo avanzar(Nodo actual, String etiquetaRama) {
        if (actual == null || etiquetaRama == null) {
            return null;
        }
        for (Nodo hijo : actual.getHijos()) {
            if (hijo.getEtiquetaRama().equalsIgnoreCase(etiquetaRama)) {
                return hijo;
            }
        }
        return null;
    }

    // ---------- Representación textual (para la interfaz preliminar) ----------

    public String imprimir() {
        StringBuilder sb = new StringBuilder();
        imprimir(raiz, 0, sb);
        return sb.toString();
    }

    private void imprimir(Nodo nodo, int nivel, StringBuilder sb) {
        if (nodo == null) {
            return;
        }
        sb.append("  ".repeat(nivel));
        if (nodo.getEtiquetaRama() != null) {
            sb.append("[").append(nodo.getEtiquetaRama()).append("] ");
        }
        sb.append(nodo.esHoja() ? "(acción final)" : nodo.getPregunta()).append("\n");
        for (Nodo hijo : nodo.getHijos()) {
            imprimir(hijo, nivel + 1, sb);
        }
    }

    // ---------- Árbol de ejemplo del laboratorio, con impactos ----------

    private void construirArbolPorDefecto(Nodo raiz) {
        // Compartir sin verificar: el jugador arriesga sin saber si es cierta.
        insertarAccion(raiz, "Compartir",
                new Impacto("La compartiste y era cierta: la comunidad se mantuvo informada.",
                        4, 3, 4, 1, 0, 0),
                new Impacto("La compartiste sin verificar y era falsa: ayudaste a propagar un rumor.",
                        -6, -8, -6, -2, 12, 6));

        // Verificar abre una subpregunta con dos ramas deterministas.
        Nodo verificar = insertarPregunta(raiz, "Verificar", "¿La publicación es verdadera?");
        insertarAccion(verificar, "Sí",
                new Impacto("Verificaste, era cierta, y la compartiste con confianza.",
                        10, 6, 5, 2, -2, -1),
                new Impacto("Verificaste, era cierta, y la compartiste con confianza.",
                        10, 6, 5, 2, -2, -1));
        insertarAccion(verificar, "No",
                new Impacto("Verificaste, era falsa, y la reportaste a tiempo.",
                        8, 4, 2, 1, -10, -3),
                new Impacto("Verificaste, era falsa, y la reportaste a tiempo.",
                        8, 4, 2, 1, -10, -3));

        // Ignorar: no arriesga, pero tampoco ayuda a frenar la desinformación.
        insertarAccion(raiz, "Ignorar",
                new Impacto("La ignoraste. La ciudad no se benefició, pero tampoco te arriesgaste.",
                        0, -1, -1, 1, 3, 0),
                new Impacto("La ignoraste. La ciudad no se benefició, pero tampoco te arriesgaste.",
                        0, -1, -1, 1, 3, 0));
    }
}
