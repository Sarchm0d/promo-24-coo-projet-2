package duckcorp;

import duckcorp.duck.DuckType;
import duckcorp.order.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Génère les commandes disponibles à chaque tour.
 * Les commandes proposées dépendent de la réputation de l'usine.
 * Fichier fourni — ne pas modifier.
 * @author Roussille Philippe <roussille@3il.fr>
 */
public class Market {

    private static final Random RANDOM = new Random();

    /**
     * Génère entre 2 et 4 commandes pour le tour courant.
     * La réputation influe sur les types disponibles et les prix :
     *
     *   réputation >= 80 : toutes catégories, prix pleins         [Premium]
     *   réputation >= 50 : Standard + Mini uniquement, prix pleins [Correct]
     *   réputation >= 20 : Standard + Mini, prix réduits (×0.80)  [Dégradé]
     *   réputation <  20 : Mini uniquement, prix réduits (×0.60)  [Mauvaise réputation]
     *
     * @param turn       numéro du tour courant (1–8)
     * @param reputation réputation actuelle de l'usine (0–100)
     */
    public List<Order> generateOrders(int turn, int reputation) {
        List<Order> orders = new ArrayList<>();
        int count = 2 + RANDOM.nextInt(3);

        List<DuckType> available = availableTypes(reputation);
        double priceMultiplier   = priceMultiplier(reputation);

        for (int i = 0; i < count; i++) {
            DuckType type       = available.get(RANDOM.nextInt(available.size()));
            int      quantity   = randomQuantity(type);
            double   price      = randomPrice(type) * priceMultiplier;
            int      deadline   = 1 + RANDOM.nextInt(4);
            orders.add(new Order(type, quantity, Math.round(price), deadline));
        }
        return orders;
    }

    private List<DuckType> availableTypes(int reputation) {
        List<DuckType> types = new ArrayList<>();
        if (reputation < 20) {
            types.add(DuckType.MINI);
        } else if (reputation < 50) {
            types.add(DuckType.STANDARD);
            types.add(DuckType.MINI);
        } else {
            types.add(DuckType.STANDARD);
            types.add(DuckType.MINI);
            if (reputation >= 80) types.add(DuckType.LUXURY);
        }
        return types;
    }

    private double priceMultiplier(int reputation) {
        if (reputation >= 50) return 1.0;
        if (reputation >= 20) return 0.80;
        return 0.60;
    }

    private int randomQuantity(DuckType type) {
        switch (type) {
            case STANDARD: return 5  + RANDOM.nextInt(11);
            case MINI:     return 10 + RANDOM.nextInt(21);
            case LUXURY:   return 1  + RANDOM.nextInt(5);
            default:       return 5;
        }
    }

    private double randomPrice(DuckType type) {
        switch (type) {
            case STANDARD: return 20 + RANDOM.nextInt(21);
            case MINI:     return 10 + RANDOM.nextInt(11);
            case LUXURY:   return 60 + RANDOM.nextInt(41);
            default:       return 15;
        }
    }
}
