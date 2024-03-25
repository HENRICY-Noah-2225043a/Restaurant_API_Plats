package fr.univamu.iut.restaurant.restaurant_api_plats;

import java.io.Closeable;
import java.sql.*;
import java.util.ArrayList;

/**
 * Classe permettant d'accéder aux plats stockés dans une base de données Mariadb
 */
public class PlatsRepositoryMariadb implements PlatsRepositoryInterface, Closeable {

    /**
     * Accès à la base de données (session)
     */
    protected Connection dbConnection;

    /**
     * Constructeur de la classe
     *
     * @param infoConnection chaîne de caractères avec les informations de connexion
     *                      (p.ex. jdbc:mariadb://mysql-[compte].alwaysdata.net/[compte]_restaurant_db
     * @param user chaîne de caractères contenant l'identifiant de connexion à la base de données
     * @param pwd chaîne de caractères contenant le mot de passe à utiliser
     */
    public PlatsRepositoryMariadb(String infoConnection, String user, String pwd) throws SQLException, ClassNotFoundException {
        Class.forName("org.mariadb.jdbc.Driver");
        dbConnection = DriverManager.getConnection(infoConnection, user, pwd);
    }

    @Override
    public void close() {
        try {
            dbConnection.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public APIPlats getPlat(int id) {
        APIPlats selectedPlat = null;

        String query = "SELECT * FROM Plats WHERE id=?";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setInt(1, id);

            // exécution de la requête
            ResultSet result = ps.executeQuery();

            // récupération du premier (et seul) tuple résultat
            if (result.next()) {
                String nom = result.getString("nom");
                String description = result.getString("description");
                double prix = result.getDouble("prix");
                String categorie = result.getString("categorie");
                boolean disponible = result.getBoolean("disponible");

                selectedPlat = new APIPlats(id, nom, description, prix, categorie);
                selectedPlat.setDisponible(disponible);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return selectedPlat;
    }

    @Override
    public ArrayList<APIPlats> getAllPlats() {
        ArrayList<APIPlats> listPlats;

        String query = "SELECT * FROM Plats";

        // construction et exécution d'une requête préparée
        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            // exécution de la requête
            ResultSet result = ps.executeQuery();

            listPlats = new ArrayList<>();

            while (result.next()) {
                int id = result.getInt("id");
                String nom = result.getString("nom");
                String description = result.getString("description");
                double prix = result.getDouble("prix");
                String categorie = result.getString("categorie");
                boolean disponible = result.getBoolean("disponible");

                APIPlats currentPlat = new APIPlats(id, nom, description, prix, categorie);
                currentPlat.setDisponible(disponible);

                listPlats.add(currentPlat);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listPlats;
    }

    @Override
    public boolean updatePlat(int id, String nom, String description, double prix, String categorie, boolean disponible) {
        String query = "UPDATE Plats SET nom=?, description=?, prix=?, categorie=?, disponible=? WHERE id=?";
        int nbRowModified = 0;

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setString(1, nom);
            ps.setString(2, description);
            ps.setDouble(3, prix);
            ps.setString(4, categorie);
            ps.setBoolean(5, disponible);
            ps.setInt(6, id);
            // exécution de la requête
            nbRowModified = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return (nbRowModified != 0);
    }

    @Override
    public int addPlat(String nom, String description, double prix, String categorie) {
        String query = "INSERT INTO Plats (nom, description, prix, categorie, disponible) VALUES (?, ?, ?, ?, ?)";
        int generatedId = -1;

        try (PreparedStatement ps = dbConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, nom);
            ps.setString(2, description);
            ps.setDouble(3, prix);
            ps.setString(4, categorie);
            ps.setBoolean(5, true); // Plat disponible par défaut

            // exécution de la requête
            ps.executeUpdate();

            // Récupération de l'identifiant généré automatiquement par la base de données
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                generatedId = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return generatedId;
    }

    @Override
    public boolean deletePlat(int id) {
        String query = "DELETE FROM Plats WHERE id=?";
        int nbRowDeleted = 0;

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setInt(1, id);

            // exécution de la requête
            nbRowDeleted = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return (nbRowDeleted != 0);
    }
}


            
