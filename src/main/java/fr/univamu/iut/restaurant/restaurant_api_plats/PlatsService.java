package fr.univamu.iut.restaurant.restaurant_api_plats;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import java.util.ArrayList;

public class PlatsService {

    protected PlatsRepositoryInterface platsRepo;

    public PlatsService(PlatsRepositoryInterface platsRepo) {
        this.platsRepo = platsRepo;
    }

    public String getAllPlatsJSON() {
        ArrayList<APIPlats> allPlats = platsRepo.getAllPlats();

        String result = null;
        try (Jsonb jsonb = JsonbBuilder.create()) {
            result = jsonb.toJson(allPlats);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return result;
    }

    public String getPlatJSON(int id) {
        String result = null;
        APIPlats plat = platsRepo.getPlat(id);

        if (plat != null) {
            try (Jsonb jsonb = JsonbBuilder.create()) {
                result = jsonb.toJson(plat);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }

        return result;
    }

    public boolean updatePlat(int id, APIPlats plat) {
        return platsRepo.updatePlat(id, plat.getNom(), plat.getDescription(), plat.getPrix(), plat.getCategorie(), plat.isDisponible());
    }

    public int addPlat(APIPlats plat) {
        return platsRepo.addPlat(plat.getNom(), plat.getDescription(), plat.getPrix(), plat.getCategorie());
    }

    public boolean deletePlat(int id) {
        return platsRepo.deletePlat(id);
    }
}
