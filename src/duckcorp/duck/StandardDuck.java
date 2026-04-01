package duckcorp.duck;

 /**
  * Canard en plastique standard.
  *
  * TODO (Ex1) :
  *   - Faites hériter cette classe de Duck
  *   - Implémentez le constructeur StandardDuck(int qualityScore) avec un appel à super
  *   - Implémentez getBasePrice() et describe()
  * @author Roussille Philippe <roussille@3il.fr>
  */
public class StandardDuck extends Duck {

    public static final double BASE_PRICE = 25.0;

    public StandardDuck(int qualityScore) {
        super(DuckType.STANDARD, qualityScore);
    }

    @Override
    public double getBasePrice() {
        return BASE_PRICE;
    }

    @Override
    public String describe() {
        return "Canard standard en plastique";
    }
}

