package fr.univamu.iut.restaurant.restaurant_api_plats;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
@ApplicationScoped
public class PlatsApplication extends Application {


    /**
     * Méthode appelée par l'API CDI pour injecter la connection à la base de données au moment de la création
     * de la ressource
     *
     * @return un objet implémentant l'interface PlatsRepositoryInterface utilisée
     * pour accéder aux données des plats, voire les modifier
     */
    @Produces
    private PlatsRepositoryInterface openDbConnection() {
        PlatsRepositoryMariadb db = null;

        try {
            db = new PlatsRepositoryMariadb("jdbc:mariadb://mysql-henricy.alwaysdata.net/henricy_restaurant_plats", "henricy", "#Catcher1902");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return db;
    }

    /**
     * Méthode permettant de fermer la connexion à la base de données lorsque l'application est arrêtée
     *
     * @param platsRepo la connexion à la base de données instanciée dans la méthode @openDbConnection
     */
    private void closeDbConnection(@Disposes PlatsRepositoryInterface platsRepo) {
        platsRepo.close();
    }
}