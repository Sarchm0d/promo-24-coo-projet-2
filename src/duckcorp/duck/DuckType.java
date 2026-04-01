package duckcorp.duck;

/**
 * Énumération des types de canards produits par DuckCorp™.
 *
 * @author Roussille Philippe <roussille@3il.fr>
 */
public enum DuckType {
    STANDARD("Standard"),
    MINI("Mini"),
    LUXURY("Luxe");

    private final String label;

    DuckType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
