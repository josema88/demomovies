/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jmoc.demomovies.rest;

import com.jmoc.demomovies.controller.MovieDao;
import com.jmoc.demomovies.model.Movie;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

/**
 *
 * @author josemanuel
 */
@RequestScoped
@Path("/movies")
@Produces("application/json")
@Consumes("application/json")
public class MovieEndpoint{
    
    @EJB
    MovieDao movieDao;
    
    @POST
	public Response create(Movie movie) {
		movieDao.create(movie); 
		return Response
				.created(UriBuilder.fromResource(MovieEndpoint.class).path(String.valueOf(movie.getId())).build())
				.build();
	}
    
    @GET
    @Path("/{id:[0-9][0-9]*}")
    public Response findById(@PathParam("id") final Long id) {
            Movie movie = movieDao.findById(id);
            if (movie == null) {
                    return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.ok(movie).build();
    }

    @GET
    public List<Movie> listAll(@QueryParam("start") final Integer startPosition,
                    @QueryParam("max") final Integer maxResult) {
            final List<Movie> movies = movieDao.listAll(startPosition, maxResult);
            return movies;
    }

    @PUT
    @Path("/{id:[0-9][0-9]*}")
    public Response update(@PathParam("id") Long id, Movie movie) {
            movie = movieDao.update(movie);
            return Response.ok(movie).build();
    }

    @DELETE
    @Path("/{id:[0-9][0-9]*}")
    public Response deleteById(@PathParam("id") final Long id) {
            movieDao.deleteById(id);
            return Response.noContent().build();
    }
}
