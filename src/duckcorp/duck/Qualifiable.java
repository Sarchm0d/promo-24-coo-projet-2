package duckcorp.duck;

/**
 * Interface représentant un objet dont on peut évaluer la qualité.
 *
 * TODO (Ex1) :
 *   - Implémentez isDefective() et getQualityLabel() (méthodes default)
 * @author Roussille Philippe <roussille@3il.fr>
 */
public interface Qualifiable {

    /** Retourne le score de qualité (entre 0 et 100). */
    int getQualityScore();

    /**
     * Retourne true si le score de qualité est strictement inférieur à 20.
     * TODO : implémentez cette méthode default en vous appuyant sur getQualityScore().
     */
    default boolean isDefective() {
        throw new UnsupportedOperationException("TODO : Qualifiable.isDefective()");
    }

    /**
     * Retourne un libellé décrivant la qualité :
     *   score >= 80 -> "Excellent" / >= 50 -> "Bon" / >= 20 -> "Médiocre" / < 20 -> "Défectueux"
     * TODO : implémentez cette méthode default.
     */
    default String getQualityLabel() {
        throw new UnsupportedOperationException("TODO : Qualifiable.getQualityLabel()");
    }
}
