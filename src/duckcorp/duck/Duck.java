package duckcorp.duck;

/**
 * Classe abstraite représentant un canard en plastique.
 *
 * TODO (Ex1) :
 *   - Faites implémenter l'interface Qualifiable à cette classe
 *   - Implémentez equals() et hashCode() basés uniquement sur l'id
 *   - Implémentez les méthodes abstraites dans les sous-classes
 * @author Roussille Philippe <roussille@3il.fr>
 */
public abstract class Duck {

    private static int counter = 0;

    private final String   id;
    private final DuckType type;
    private final int      qualityScore;

    /** Constructeur fourni. Génère automatiquement un identifiant unique. */
    protected Duck(DuckType type, int qualityScore) {
        this.id           = type.name().charAt(0) + String.format("%04d", ++counter);
        this.type         = type;
        this.qualityScore = Math.max(0, Math.min(100, qualityScore));
    }

    // --- Getters fournis ---

    public String   getId()          { return id; }
    public DuckType getType()        { return type; }
    public int      getQualityScore(){ return qualityScore; }   // satisfera Qualifiable

    // --- Méthodes abstraites à implémenter dans les sous-classes ---

    public abstract double getBasePrice();
    public abstract String describe();

    // --- TODO : equals et hashCode ---

    /**
     * Deux canards sont égaux si et seulement si ils ont le même identifiant.
     * TODO : implémentez equals() en vous basant uniquement sur le champ id.
     */
    @Override
    public boolean equals(Object o) {
        throw new UnsupportedOperationException("TODO : Duck.equals()");
    }

    /** TODO : implémentez hashCode() de façon cohérente avec equals(). */
    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("TODO : Duck.hashCode()");
    }

    // --- toString fourni ---

    @Override
    public String toString() {
        return String.format("[%s] %s — qualité : %d/100", id, describe(), qualityScore);
    }
}
