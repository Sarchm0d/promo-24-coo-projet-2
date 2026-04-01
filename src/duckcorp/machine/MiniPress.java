package duckcorp.machine;

import duckcorp.duck.Duck;
import duckcorp.duck.DuckType;
import duckcorp.duck.MiniDuck;

/**
 * Presse produisant des Mini Canards.
 *
 * TODO (Ex2) :
 *   - Faites hériter cette classe de Machine
 *   - Implémentez le constructeur sans paramètre avec un appel à super
 *   - Implémentez produceDuck(), getPurchaseCost(), getName()
 * @author Roussille Philippe <roussille@3il.fr>
 */
public class MiniPress extends Machine {

    public static final int PURCHASE_COST    = 300;
    public static final int CAPACITY         = 8;
    public static final int MAINTENANCE_COST = 30;

    public MiniPress() {
        super(DuckType.MINI, CAPACITY, MAINTENANCE_COST);
    }

    @Override
    public Duck produceDuck() {
        return new MiniDuck(computeQuality());
    }

    @Override
    public int getPurchaseCost() {
        return PURCHASE_COST;
    }

    @Override
    public String getName() {
        return "Mini-Presse";
    }
}
