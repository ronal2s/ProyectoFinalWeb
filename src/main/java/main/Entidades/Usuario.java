package main.Entidades;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Usuario {
    @Id
    private String usuario;
    @Column()
    private String nombre;
    @Column()
    private String clave;
    @OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL , orphanRemoval = true)
    private List<Formulario> formularios = new ArrayList<Formulario>();


    public Usuario(){ }
    
    public Usuario(String usuario, String nombre, String clave) {
        this.usuario = usuario;
        this.nombre = nombre;
        this.clave = clave;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return clave;
    }

    public void setPassword(String clave) {
        this.clave = clave;
    }
}
