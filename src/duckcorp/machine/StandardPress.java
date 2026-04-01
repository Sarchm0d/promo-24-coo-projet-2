package duckcorp.machine;

import duckcorp.duck.Duck;

/**
 * Presse produisant des canards Standard.
 *
 * TODO (Ex2) :
 *   - Faites hériter cette classe de Machine
 *   - Implémentez le constructeur sans paramètre avec un appel à super
 *   - Implémentez produceDuck(), getPurchaseCost(), getName()
 * @author Roussille Philippe <roussille@3il.fr>
 */
public class StandardPress {

    public static final int PURCHASE_COST    = 500;
    public static final int CAPACITY         = 5;
    public static final int MAINTENANCE_COST = 50;
}
