package modelo;

// efecto sobre los indicadores de la ciudad. cada hoja del árbol tiene 2:
// uno si la publicación era verdadera y otro si era falsa
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
