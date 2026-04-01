package duckcorp;

import duckcorp.duck.Duck;
import duckcorp.factory.Factory;
import duckcorp.machine.Machine;
import duckcorp.order.Order;
import duckcorp.order.OrderStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Boucle de jeu principale — 8 tours.
 * Fichier fourni — ne pas modifier.
 * @author Roussille Philippe <roussille@3il.fr>
 */
public class Game {

    private static final int    MAX_TURNS      = 8;
    private static final double INITIAL_BUDGET = 5_000.0;

    private final Factory  factory;
    private final Market   market;
    private final Console  console;
    private final List<Order> activeOrders = new ArrayList<>();

    public Game() {
        this.factory = new Factory(INITIAL_BUDGET);
        this.market  = new Market();
        this.console = new Console();
    }

    public void run() {
        console.printWelcome();
        for (int turn = 1; turn <= MAX_TURNS; turn++) {
            playTurn(turn);
        }
        console.printGameOver(factory);
    }

    // -------------------------------------------------------------------------

    private void playTurn(int turn) {
        console.printTurnHeader(turn, MAX_TURNS, factory);

        // 1. Expirations
        tickOrders();

        // 2. Nouvelles commandes (réputation-dépendantes)
        List<Order> newOrders = market.generateOrders(turn, (int) factory.getReputation());
        activeOrders.addAll(newOrders);
        console.printNewOrders(newOrders);

        // 3. Achat de machine
        console.printMachineShop(factory.getBudget());
        int shopChoice = console.askMachineShopChoice();
        if (shopChoice != 0) {
            Machine bought = createMachine(shopChoice);
            boolean ok = factory.buyMachine(bought);
            console.printMachinePurchaseResult(ok, bought);
        }

        // 4. Maintenance
        if (!factory.getMachines().isEmpty()) {
            console.printMachines(factory.getMachines());
            int mChoice = console.askMaintenanceChoice(factory.getMachines().size());
            if (mChoice != 0) {
                Machine m = factory.getMachines().get(mChoice - 1);
                boolean ok = factory.maintainMachine(m);
                console.printMaintenanceResult(ok, m);
            }
        }

        // 5. Production
        List<Duck> produced = factory.runProduction();
        console.printProduction(produced);

        // 6. Expédition
        List<Order> pending = getPending();
        if (!pending.isEmpty()) {
            console.printActiveOrders(pending, factory.getStock());
            List<Integer> choices = console.askOrderChoices(pending.size());
            for (int idx : choices) {
                Order order = pending.get(idx);
                boolean ok = factory.fulfillOrder(order);
                console.printFulfillmentResult(ok, order);
            }
        }

        // 7. Fin de tour
        factory.endTurn();
        console.printTurnSummary(factory, turn);
    }

    // -------------------------------------------------------------------------

    private void tickOrders() {
        for (Order o : activeOrders) {
            o.tick();
        }
        List<Order> toRemove = new ArrayList<>();
        for (Order o : activeOrders) {
            if (o.getStatus() == OrderStatus.EXPIRED) {
                toRemove.add(o);
                factory.notifyExpiredOrder();
            }
        }
        activeOrders.removeAll(toRemove);
    }

    private List<Order> getPending() {
        List<Order> result = new ArrayList<>();
        for (Order o : activeOrders) {
            if (o.getStatus() == OrderStatus.PENDING) result.add(o);
        }
        return result;
    }

    /**
     * TODO (Ex2) : instanciez et retournez la bonne sous-classe de Machine selon le choix.
     *   1 -> StandardPress  /  2 -> MiniPress  /  3 -> LuxuryMold
     */
    private Machine createMachine(int choice) {
        throw new UnsupportedOperationException("TODO : Game.createMachine()");
    }
}
