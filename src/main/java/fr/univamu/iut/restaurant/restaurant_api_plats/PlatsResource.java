package fr.univamu.iut.restaurant.restaurant_api_plats;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/plats")
@ApplicationScoped
public class PlatsResource {

    /**
     * Service utilisé pour accéder aux données des plats et récupérer/modifier leurs informations
     */
    private PlatsService service;

    /**
     * Constructeur par défaut
     */
    public PlatsResource() {
    }

    /**
     * Constructeur permettant d'initialiser le service avec une interface d'accès aux données
     *
     * @param platsRepo objet implémentant l'interface d'accès aux données
     */
    public PlatsResource(PlatsRepositoryInterface platsRepo) {
        this.service = new PlatsService(platsRepo);
    }

    /**
     * Endpoint permettant de récupérer tous les plats enregistrés
     *
     * @return la liste des plats (avec leurs informations) au format JSON
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllPlats() {
        return service.getAllPlatsJSON();
    }

    /**
     * Endpoint permettant de récupérer les informations d'un plat dont l'identifiant est passé en paramètre dans le chemin
     *
     * @param id identifiant du plat recherché
     * @return les informations du plat recherché au format JSON
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getPlat(@PathParam("id") int id) {

        String result = service.getPlatJSON(id);

        // si le plat n'a pas été trouvé
        if (result == null)
            throw new NotFoundException();

        return result;
    }

    /**
     * Endpoint permettant de mettre à jour un plat
     *
     * @param id       identifiant du plat à mettre à jour
     * @param plat     le plat transmis en HTTP au format JSON et convertit en objet APIPlats
     * @return une réponse "updated" si la mise à jour a été effectuée, une erreur NotFound sinon
     */
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePlat(@PathParam("id") int id, APIPlats plat) {

        // si le plat n'a pas été trouvé
        if (!service.updatePlat(id, plat))
            throw new NotFoundException();
        else
            return Response.ok("updated").build();
    }

    /**
     * Endpoint permettant d'ajouter un nouveau plat
     *
     * @param plat le plat transmis en HTTP au format JSON et convertit en objet APIPlats
     * @return la réponse HTTP contenant l'identifiant du plat ajouté
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPlat(APIPlats plat) {
        int id = service.addPlat(plat);
        return Response.status(Response.Status.CREATED).entity(id).build();
    }

    /**
     * Endpoint permettant de supprimer un plat
     *
     * @param id identifiant du plat à supprimer
     * @return une réponse "deleted" si la suppression a été effectuée, une erreur NotFound sinon
     */
    @DELETE
    @Path("{id}")
    public Response deletePlat(@PathParam("id") int id) {

        // si le plat n'a pas été trouvé
        if (!service.deletePlat(id))
            throw new NotFoundException();
        else
            return Response.ok("deleted").build();
    }
}
