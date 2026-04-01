package duckcorp.duck;

 /**
  * Canard en plastique de luxe.
  *
  * TODO (Ex1) :
  *   - Faites hériter cette classe de Duck
  *   - Implémentez le constructeur LuxuryDuck(int qualityScore) avec un appel à super
  *   - Implémentez getBasePrice() et describe()
  * @author Roussille Philippe <roussille@3il.fr>
  */
public class LuxuryDuck extends Duck {

    public static final double BASE_PRICE = 80.0;

    public LuxuryDuck(int qualityScore) {
        super(DuckType.LUXURY, qualityScore);
    }

    @Override
    public double getBasePrice() {
        return BASE_PRICE;
    }

    @Override
    public String describe() {
        return "Canard de luxe en plastique";
    }
}

