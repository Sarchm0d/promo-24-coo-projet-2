package duckcorp.bonus;

import duckcorp.duck.Duck;
import duckcorp.stock.Stock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * File d'attente de production bornée.
 * Les machines déposent des canards (producteurs) ;
 * le stock les consomme (consommateur).
 *
 * TODO (Bonus 3) :
 *   - Implémentez enqueue(), dequeue(), drainToStock()
 *
 * Dans ce projet, le jeu est mono-thread : utilisez offer() et poll()
 * (non-bloquants) plutôt que put() et take().
 *
 * Question ouverte : dans un contexte multi-thread réel (plusieurs machines
 * tournant en parallèle), quelles méthodes faudrait-il utiliser à la place,
 * et pourquoi ?
 * @author Roussille Philippe <roussille@3il.fr>
 */
public class ProductionQueue {

    private final ArrayBlockingQueue<Duck> queue;

    /**
     * Crée une file bornée à {@code capacity} canards.
     */
    public ProductionQueue(int capacity) {
        this.queue = new ArrayBlockingQueue<>(capacity);
    }

    /**
     * Tente d'ajouter un canard à la file.
     * Utilise offer() (non-bloquant).
     *
     * @return true si le canard a été ajouté, false si la file est pleine
     */
    public boolean enqueue(Duck duck) {
        // TODO
        throw new UnsupportedOperationException("TODO : ProductionQueue.enqueue()");
    }

    /**
     * Retire et retourne le premier canard de la file.
     * Utilise poll() (non-bloquant).
     *
     * @return le canard retiré, ou null si la file est vide
     */
    public Duck dequeue() {
        // TODO
        throw new UnsupportedOperationException("TODO : ProductionQueue.dequeue()");
    }

    /**
     * Transfère tous les canards disponibles dans la file vers le stock.
     * Retourne la liste des canards transférés.
     *
     * Conseil : utilisez queue.drainTo(list) puis ajoutez chaque canard au stock.
     */
    public List<Duck> drainToStock(Stock<Duck> stock) {
        // TODO
        throw new UnsupportedOperationException("TODO : ProductionQueue.drainToStock()");
    }

    /** Retourne le nombre de canards actuellement dans la file (fourni). */
    public int size() {
        return queue.size();
    }
}
