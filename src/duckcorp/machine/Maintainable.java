package duckcorp.machine;

/**
 * Interface représentant une machine pouvant être entretenue.
 *
 * TODO (Ex2) :
 *   - Implémentez needsMaintenance() et getConditionLabel() (méthodes default)
 * @author Roussille Philippe <roussille@3il.fr>
 */
public interface Maintainable {

    /** Retourne l'état de la machine (entre 0 et 100). */
    int getCondition();

    /** Effectue une opération de maintenance sur la machine. */
    void maintain();

    /**
     * Retourne true si la machine nécessite une maintenance (condition < 30).
     * TODO : implémentez cette méthode default.
     */
    default boolean needsMaintenance() {
        throw new UnsupportedOperationException("TODO : Maintainable.needsMaintenance()");
    }

    /**
     * Retourne un libellé décrivant l'état de la machine :
     *   condition >= 80 -> "Parfait" / >= 50 -> "Correct" / >= 30 -> "Usé" / < 30 -> "Critique"
     * TODO : implémentez cette méthode default.
     */
    default String getConditionLabel() {
        throw new UnsupportedOperationException("TODO : Maintainable.getConditionLabel()");
    }
}
