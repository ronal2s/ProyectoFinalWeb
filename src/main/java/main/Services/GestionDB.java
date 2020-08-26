package main.Services;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaQuery;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class GestionDB<T> {
    private static EntityManagerFactory emf;
    private Class<T> claseEntidad;


    public GestionDB(Class<T> claseEntidad) {
        if(emf == null) {

                emf = Persistence.createEntityManagerFactory("Parcial2Web");

        }
        this.claseEntidad = claseEntidad;

    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }


    private Object getValorCampo(T entidad) {
        if (entidad == null) {
            return null;
        }

        for (Field f : entidad.getClass().getDeclaredFields()) {
            if (f.isAnnotationPresent(Id.class)) {
                try {
                    f.setAccessible(true);
                    Object valorCampo = f.get(entidad);

                    System.out.println("Nombre del campo: " + f.getName());
                    System.out.println("Tipo del campo: " + f.getType().getName());
                    System.out.println("Valor del campo: " + valorCampo);

                    return valorCampo;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }


    public boolean crear(T entidad) throws IllegalArgumentException, EntityExistsException, PersistenceException {
        EntityManager em = getEntityManager();

        try {

            em.getTransaction().begin();
            em.persist(entidad);
            em.getTransaction().commit();
            return true;
        } finally {
            em.close();
        }
    }

    public boolean editar(T entidad) throws PersistenceException {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        boolean dd = false;
        try {
            em.merge(entidad);
            em.getTransaction().commit();
            dd = true;
        } finally {
            em.close();
        }
        return dd;
    }

    public boolean eliminar(Object entidadId) throws PersistenceException {
        boolean ok = false;
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            T entidad = em.find(claseEntidad, entidadId);
            em.remove(entidad);
            em.getTransaction().commit();
            ok = true;
        } finally {
            em.close();
        }
        return ok;
    }

    public T find(Object id) throws PersistenceException {
        EntityManager em = getEntityManager();
        try {
            return em.find(claseEntidad, id);
        } finally {
            em.close();
        }
    }

    public List<T> findall() throws PersistenceException {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<T> criteriaQuery = em.getCriteriaBuilder().createQuery(claseEntidad);
            criteriaQuery.select(criteriaQuery.from(claseEntidad));
            return em.createQuery(criteriaQuery).getResultList();
        } finally {
            em.close();
        }
    }
}
