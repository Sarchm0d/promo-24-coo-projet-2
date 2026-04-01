package duckcorp.machine;

import duckcorp.duck.Duck;
import duckcorp.duck.DuckType;
import duckcorp.duck.StandardDuck;

/**
 * Presse produisant des canards Standard.
 *
 * TODO (Ex2) :
 *   - Faites hériter cette classe de Machine
 *   - Implémentez le constructeur sans paramètre avec un appel à super
 *   - Implémentez produceDuck(), getPurchaseCost(), getName()
 * @author Roussille Philippe <roussille@3il.fr>
 */
public class StandardPress extends Machine {

    public static final int PURCHASE_COST    = 500;
    public static final int CAPACITY         = 5;
    public static final int MAINTENANCE_COST = 50;

    public StandardPress() {
        super(DuckType.STANDARD, CAPACITY, MAINTENANCE_COST);
    }

    @Override
    public Duck produceDuck() {
        return new StandardDuck(computeQuality());
    }

    @Override
    public int getPurchaseCost() {
        return PURCHASE_COST;
    }

    @Override
    public String getName() {
        return "Presse Standard";
    }
}
