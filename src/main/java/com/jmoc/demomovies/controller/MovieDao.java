package com.jmoc.demomovies.controller;

import com.jmoc.demomovies.model.Movie;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author josemanuel
 */
@Stateless
public class MovieDao {
    @PersistenceContext(unitName = "movies_PU")
	private EntityManager em;
    
    public void create(Movie entity){
        em.persist(entity); 
    }
    
    public void deleteById(Long id) {
		Movie entity = em.find(Movie.class, id);
		if (entity != null) {
			em.remove(entity);
		}
    }

    public Movie findById(Long id) {
            return em.find(Movie.class, id);
    }

    public Movie update(Movie entity) {
            return em.merge(entity);
    }

    public List<Movie> listAll(Integer startPosition, Integer maxResult) {
            TypedQuery<Movie> findAllQuery = em.createQuery(
                            "SELECT DISTINCT m FROM Movie m ORDER BY m.id", Movie.class);
            if (startPosition != null) {
                    findAllQuery.setFirstResult(startPosition);
            }
            if (maxResult != null) {
                    findAllQuery.setMaxResults(maxResult);
            }
            return findAllQuery.getResultList();
    }
}
