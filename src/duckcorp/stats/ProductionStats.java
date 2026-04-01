package duckcorp.stats;

import duckcorp.duck.Duck;
import duckcorp.duck.DuckType;
import duckcorp.order.Order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Suivi des statistiques de production et de vente de l'usine.
 *
 * TODO (Bonus 1) :
 *   - Implémentez recordProduction(), recordSale(),
 *     getTotalProduced(), getMostProduced()
 *
 * Les getters et le constructeur sont fournis.
 * La Map produced est protected pour permettre la sous-classe ConcurrentProductionStats.
 * @author Roussille Philippe <roussille@3il.fr>
 */
public class ProductionStats {

    protected Map<DuckType, Integer> produced;
    protected Map<DuckType, Integer> sold;
    private double totalRevenue;
    private int totalOrders;
    private int ordersExpired;

    public ProductionStats() {
        produced = new HashMap<>();
        sold     = new HashMap<>();
        for (DuckType t : DuckType.values()) {
            produced.put(t, 0);
            sold.put(t, 0);
        }
    }

    // --- Getters fournis ---

    public double getTotalRevenue()          { return totalRevenue; }
    public int    getTotalOrders()           { return totalOrders; }
    public int    getOrdersExpired()         { return ordersExpired; }
    public Map<DuckType, Integer> getProduced() { return produced; }
    public Map<DuckType, Integer> getSold()     { return sold; }

    // --- Méthode fournie ---

    /** Incrémente le compteur de commandes expirées. Appelée par Factory. */
    public void recordExpiredOrder() {
        ordersExpired++;
    }

    // --- TODO ---

    /**
     * Enregistre la production d'une liste de canards.
     * Pour chaque canard, incrémente son compteur dans produced.
     *
     * Utilisez getOrDefault() ou merge() plutôt qu'un null-check manuel.
     * Réfléchissez à la signature du paramètre : doit-elle accepter
     * uniquement une List<Duck>, ou quelque chose de plus général ?
     */
    public void recordProduction(List<Duck> ducks) {
        // TODO
        throw new UnsupportedOperationException("TODO : ProductionStats.recordProduction()");
    }

    /**
     * Enregistre la vente d'une commande honorée.
     * Met à jour sold, totalRevenue et totalOrders.
     */
    public void recordSale(Order order) {
        // TODO
        throw new UnsupportedOperationException("TODO : ProductionStats.recordSale()");
    }

    /**
     * Retourne le nombre total de canards produits toutes catégories confondues.
     * Parcourez produced.values() avec une boucle.
     */
    public int getTotalProduced() {
        // TODO
        throw new UnsupportedOperationException("TODO : ProductionStats.getTotalProduced()");
    }

    /**
     * Retourne le DuckType le plus produit.
     * Parcourez produced en une seule passe en conservant le maximum courant.
     * Retourne null si rien n'a encore été produit.
     */
    public DuckType getMostProduced() {
        // TODO
        throw new UnsupportedOperationException("TODO : ProductionStats.getMostProduced()");
    }
}
