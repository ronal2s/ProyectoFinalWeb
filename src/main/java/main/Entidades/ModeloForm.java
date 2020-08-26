package main.Entidades;

public class ModeloForm {
    private int id;
    private String nombre;
    private String sector;
    private String nivelEscolar;
    private double latitud;
    private double longitud;
    private String mimeType;
    private String foto;

    public ModeloForm() { }

    public ModeloForm(int id, String nombre, String sector, String nivelEscolar, double latitud, double longitud, String mimeType, String foto) {
        this.id = id;
        this.nombre = nombre;
        this.sector = sector;
        this.nivelEscolar = nivelEscolar;
        this.latitud = latitud;
        this.longitud = longitud;
        this.mimeType = mimeType;
        this.foto = foto;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getSector() { return sector; }

    public void setSector(String sector) { this.sector = sector; }

    public String getNivelEscolar() { return nivelEscolar; }

    public void setNivelEscolar(String nivelEscolar) { this.nivelEscolar = nivelEscolar; }

    public double getLatitud() { return latitud; }

    public void setLatitud(double latitud) { this.latitud = latitud; }

    public double getLongitud() { return longitud; }

    public void setLongitud(double longitud) { this.longitud = longitud; }

    public String getMimeType() { return mimeType; }

    public void setMimeType(String mimeType) { this.mimeType = mimeType; }

    public String getFoto() { return foto; }

    public void setFoto(String foto) { this.foto = foto; }
}
