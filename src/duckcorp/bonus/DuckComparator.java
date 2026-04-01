package duckcorp.bonus;

import duckcorp.duck.Duck;

import java.util.Comparator;

/**
 * Comparateur de canards par score de qualité croissant.
 * Utilisé par Factory.fulfillOrder() pour expédier les canards
 * les moins bons en premier et conserver les meilleurs en stock.
 *
 * TODO (Bonus 2) :
 *   - Implémentez compare(Duck a, Duck b)
 *
 * Attention : la soustraction directe des scores peut produire un dépassement
 * entier dans des cas limites. Préférez une comparaison sûre.
 * @author Roussille Philippe <roussille@3il.fr>
 */
public class DuckComparator implements Comparator<Duck> {

    /**
     * Compare deux canards par qualité croissante.
     * Retourne un entier négatif si a est de moins bonne qualité que b,
     * zéro s'ils ont la même qualité, positif sinon.
     */
    @Override
    public int compare(Duck a, Duck b) {
        // TODO
        throw new UnsupportedOperationException("TODO : DuckComparator.compare()");
    }
}
