package main.Services;

import main.Entidades.Formulario;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class FormularioServices extends GestionDB<Formulario> {
    private static FormularioServices instance;

    private FormularioServices() {
        super(Formulario.class);
    }

    public static FormularioServices getInstance(){
        if(instance==null){
            instance = new FormularioServices();
        }
        return instance;
    }

    public List<Formulario> findByNombre(String nombre) {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT f FROM Formulario f where f.nombre = :nombre");
        query.setParameter("nombre", nombre);
        List<Formulario> lista = query.getResultList();
        return lista;
    }
}