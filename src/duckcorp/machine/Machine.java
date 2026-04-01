package duckcorp.machine;

import duckcorp.duck.Duck;
import duckcorp.duck.DuckType;

import java.util.Random;

/**
 * Classe abstraite représentant une machine de production.
 *
 * TODO (Ex2) :
 *   - Faites implémenter l'interface Maintainable à cette classe
 *   - Implémentez maintain()
 *   - Implémentez produceDuck(), getPurchaseCost(), getName() dans les sous-classes
 * @author Roussille Philippe <roussille@3il.fr>
 */
public abstract class Machine implements Maintainable {

    private static final Random RANDOM = new Random();

    private final DuckType producedType;
    private final int      capacity;
    private int            condition;
    private final int      maintenanceCost;

    /** Constructeur fourni. */
    protected Machine(DuckType producedType, int capacity, int maintenanceCost) {
        this.producedType    = producedType;
        this.capacity        = capacity;
        this.condition       = 100;
        this.maintenanceCost = maintenanceCost;
    }

    // --- Getters fournis ---

    public DuckType getProducedType()    { return producedType; }
    public int      getCapacity()        { return capacity; }
    public int      getMaintenanceCost() { return maintenanceCost; }

    /**
     * Retourne l'état courant de la machine (entre 0 et 100).
     * Fourni — satisfera automatiquement Maintainable.getCondition() quand vous
     * ajouterez implements Maintainable.
     */
    public int getCondition() { return condition; }

    // --- TODO : maintain() ---

    /**
     * Effectue une maintenance : augmente condition de 40 points, plafonnée à 100.
     * TODO (Ex2) : implémentez cette méthode.
     */
    public void maintain() {
        condition = Math.min(100, condition + 40);
    }

    // --- Méthodes fournies ---

    /**
     * Dégrade la machine de 10 points (minimum 0).
     * Appelée par Factory.endTurn(). Ne pas modifier.
     */
    public void degrade() {
        condition = Math.max(0, condition - 10);
    }

    /**
     * Calcule la qualité d'un canard produit selon l'état de la machine.
     * À appeler dans produceDuck(). Ne pas modifier.
     */
    protected final int computeQuality() {
        int base      = (int) (condition * 0.7);
        int variation = RANDOM.nextInt(31);
        return Math.min(100, base + variation);
    }

    // --- Méthodes abstraites à implémenter dans les sous-classes ---

    public abstract Duck   produceDuck();
    public abstract int    getPurchaseCost();
    public abstract String getName();

    // --- toString fourni ---

    @Override
    public String toString() {
        return String.format("%s [capacité : %d/tour, état : %d%%]",
                getName(), capacity, condition);
    }
}
