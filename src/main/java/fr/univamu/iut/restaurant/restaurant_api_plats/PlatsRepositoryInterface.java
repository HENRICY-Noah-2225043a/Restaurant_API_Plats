package fr.univamu.iut.restaurant.restaurant_api_plats;

import java.util.ArrayList;

/**
 * Interface d'accès aux données des plats
 */
public interface PlatsRepositoryInterface {

    /**
     * Méthode fermant le dépôt où sont stockées les informations sur les plats
     */
    public void close();

    /**
     * Méthode retournant le plat dont l'identifiant est passé en paramètre
     * @param id identifiant du plat recherché
     * @return un objet APIPlats représentant le plat recherché
     */
    public APIPlats getPlat(int id);

    /**
     * Méthode retournant la liste des plats
     * @return une liste d'objets APIPlats
     */
    public ArrayList<APIPlats> getAllPlats();

    /**
     * Méthode permettant de mettre à jour un plat enregistré
     * @param id identifiant du plat à mettre à jour
     * @param nom nouveau nom du plat
     * @param description nouvelle description du plat
     * @param prix nouveau prix du plat
     * @param categorie nouvelle catégorie du plat
     * @param disponible nouveau statut du plat
     * @return true si le plat existe et la mise à jour a été faite, false sinon
     */
    public boolean updatePlat(int id, String nom, String description, double prix, String categorie, boolean disponible);

    /**
     * Méthode permettant d'ajouter un nouveau plat
     * @param nom nom du plat
     * @param description description du plat
     * @param prix prix du plat
     * @param categorie catégorie du plat
     * @return l'identifiant du plat ajouté
     */
    public int addPlat(String nom, String description, double prix, String categorie);

    /**
     * Méthode permettant de supprimer un plat
     * @param id identifiant du plat à supprimer
     * @return true si le plat a été supprimé, false sinon
     */
    public boolean deletePlat(int id);
}
