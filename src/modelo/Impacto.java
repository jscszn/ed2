package modelo;

/**
 * Efecto que una acción final del árbol de decisiones produce sobre los
 * indicadores de Ciudad Nova (ver Ciudad.java). Es un objeto inmutable:
 * cada hoja del árbol guarda dos Impacto (uno para cuando la publicación
 * resulta verdadera y otro para cuando resulta falsa).
 */
public class Impacto {

    private String mensaje;
    private int informacionVerificada;
    private int confianza;
    private int convivencia;
    private int bienestarDigital;
    private int desinformacion;
    private int conflictos;

    public Impacto(String mensaje, int informacionVerificada, int confianza, int convivencia,
            int bienestarDigital, int desinformacion, int conflictos) {
        this.mensaje = mensaje;
        this.informacionVerificada = informacionVerificada;
        this.confianza = confianza;
        this.convivencia = convivencia;
        this.bienestarDigital = bienestarDigital;
        this.desinformacion = desinformacion;
        this.conflictos = conflictos;
    }

    public String getMensaje() {
        return mensaje;
    }

    public int getInformacionVerificada() {
        return informacionVerificada;
    }

    public int getConfianza() {
        return confianza;
    }

    public int getConvivencia() {
        return convivencia;
    }

    public int getBienestarDigital() {
        return bienestarDigital;
    }

    public int getDesinformacion() {
        return desinformacion;
    }

    public int getConflictos() {
        return conflictos;
    }
}
