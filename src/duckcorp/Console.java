package duckcorp;

import duckcorp.duck.Duck;
import duckcorp.duck.DuckType;
import duckcorp.factory.Factory;
import duckcorp.machine.Machine;
import duckcorp.machine.LuxuryMold;
import duckcorp.machine.MiniPress;
import duckcorp.machine.StandardPress;
import duckcorp.order.Order;
import duckcorp.order.OrderStatus;
import duckcorp.stock.Stock;

import java.util.*;

/**
 * Gestion de l'affichage console et des saisies utilisateur.
 * Fichier fourni — ne pas modifier.
 * @author Roussille Philippe <roussille@3il.fr>
 */
public class Console {

    private final Scanner scanner = new Scanner(System.in);

    // -------------------------------------------------------------------------
    // Affichage général
    // -------------------------------------------------------------------------

    public void printWelcome() {
        System.out.println();
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║         DuckCorp™  —  Simulation         ║");
        System.out.println("╚══════════════════════════════════════════╝");
        System.out.println("Gérez votre usine de canards en plastique pendant 8 tours.");
        System.out.println("Maximisez votre score : budget, réputation et commandes honorées.");
        System.out.println();
        prompt("Appuyez sur Entrée pour commencer...");
        scanner.nextLine();
    }

    public void printTurnHeader(int turn, int maxTurns, Factory factory) {
        System.out.println();
        System.out.println("════════════════════════════════════════════");
        System.out.printf("  Tour %d / %d%n", turn, maxTurns);
        System.out.printf("  Budget : %.0f €   Réputation : [%s] %.0f%n",
                factory.getBudget(),
                reputationLabel((int) factory.getReputation()),
                factory.getReputation());
        System.out.printf("  Score estimé : %d pts%n", factory.computeScore());
        System.out.println("════════════════════════════════════════════");
        printStockSummary(factory.getStock());
        System.out.println();
    }

    public void printTurnSummary(Factory factory, int turn) {
        System.out.println();
        System.out.printf("--- Fin du tour %d ---%n", turn);
        System.out.printf("  Budget : %.0f €   Réputation : [%s] %.0f%n",
                factory.getBudget(),
                reputationLabel((int) factory.getReputation()),
                factory.getReputation());
    }

    public void printGameOver(Factory factory) {
        System.out.println();
        System.out.println("════════════════════════════════════════════");
        System.out.println("  FIN DE PARTIE — 8 tours terminés");
        System.out.println("════════════════════════════════════════════");
        System.out.printf("  Budget restant      : %.0f €%n",      factory.getBudget());
        System.out.printf("  Réputation finale   : %.0f (%s)%n",   factory.getReputation(),
                reputationLabel((int) factory.getReputation()));
        System.out.printf("  Commandes honorées  : %d%n",          factory.getStats().getTotalOrders());
        System.out.printf("  Commandes expirées  : %d%n",          factory.getStats().getOrdersExpired());
        System.out.println();
        System.out.printf("  *** SCORE FINAL : %d points ***%n",   factory.computeScore());
        System.out.println("  (budget + réputation×80 + honorées×200 − expirées×100)");
        System.out.println("════════════════════════════════════════════");
    }

    // -------------------------------------------------------------------------
    // Commandes
    // -------------------------------------------------------------------------

    public void printNewOrders(List<Order> orders) {
        if (orders.isEmpty()) return;
        System.out.println("--- Nouvelles commandes ---");
        for (Order o : orders) {
            System.out.printf("  %s%n", o);
        }
        System.out.println();
    }

    public void printActiveOrders(List<Order> pending, Stock<Duck> stock) {
        if (pending.isEmpty()) {
            System.out.println("Aucune commande en attente.");
            return;
        }
        System.out.println("--- Commandes en attente ---");
        printStockSummary(stock);
        System.out.println();
        for (int i = 0; i < pending.size(); i++) {
            Order o = pending.get(i);
            String feasible = o.canBeFulfilled(stock) ? "✓ RÉALISABLE" : "✗ stock insuffisant";
            System.out.printf("  [%d] %s   [%s]%n", i + 1, o, feasible);
        }
        System.out.println("  [0] Passer");
    }

    public List<Integer> askOrderChoices(int maxIndex) {
        prompt("Expédier (ex: 1,2 — 0 pour passer) : ");
        String line = scanner.nextLine().trim();
        List<Integer> result = new ArrayList<>();
        if (line.equals("0") || line.isEmpty()) return result;
        for (String part : line.split(",")) {
            try {
                int idx = Integer.parseInt(part.trim()) - 1;
                if (idx >= 0 && idx < maxIndex) result.add(idx);
            } catch (NumberFormatException ignored) {}
        }
        return result;
    }

    public void printFulfillmentResult(boolean success, Order order) {
        if (success) {
            System.out.printf("  → %s expédiée ! +%.0f €%n", order.getId(), order.getTotalValue());
        } else {
            System.out.printf("  → %s : stock insuffisant.%n", order.getId());
        }
    }

    // -------------------------------------------------------------------------
    // Machines
    // -------------------------------------------------------------------------

    public void printMachineShop(double budget) {
        System.out.println("--- Boutique de machines ---");
        System.out.printf("  Budget disponible : %.0f €%n%n", budget);
        System.out.printf("  [1] Presse Standard   %d €  — %d canards/tour  maintenance : %d €%n",
                StandardPress.PURCHASE_COST, StandardPress.CAPACITY, StandardPress.MAINTENANCE_COST);
        System.out.printf("  [2] Mini-Presse       %d €  — %d canards/tour  maintenance : %d €%n",
                MiniPress.PURCHASE_COST, MiniPress.CAPACITY, MiniPress.MAINTENANCE_COST);
        System.out.printf("  [3] Moule de Luxe     %d €  — %d canards/tour  maintenance : %d €%n",
                LuxuryMold.PURCHASE_COST, LuxuryMold.CAPACITY, LuxuryMold.MAINTENANCE_COST);
        System.out.println("  [0] Passer");
    }

    public int askMachineShopChoice() {
        prompt("Votre choix : ");
        return readInt(0, 3);
    }

    public void printMachinePurchaseResult(boolean success, Machine machine) {
        if (success) {
            System.out.printf("  → %s achetée ! −%d €%n", machine.getName(), machine.getPurchaseCost());
        } else {
            System.out.printf("  → Budget insuffisant pour %s.%n", machine.getName());
        }
    }

    public void printMachines(List<Machine> machines) {
        System.out.println("--- Vos machines ---");
        for (int i = 0; i < machines.size(); i++) {
            Machine m = machines.get(i);
            String warn = m.getCondition() < 30 ? " ⚠ Maintenance recommandée !" : "";
            System.out.printf("  [%d] %s%s%n", i + 1, m, warn);
        }
        System.out.println("  [0] Passer");
    }

    public int askMaintenanceChoice(int machineCount) {
        prompt("Entretenir la machine n° (0 pour passer) : ");
        return readInt(0, machineCount);
    }

    public void printMaintenanceResult(boolean success, Machine machine) {
        if (success) {
            System.out.printf("  → %s entretenue ! −%d €%n", machine.getName(), machine.getMaintenanceCost());
        } else {
            System.out.printf("  → Budget insuffisant pour entretenir %s.%n", machine.getName());
        }
    }

    // -------------------------------------------------------------------------
    // Production
    // -------------------------------------------------------------------------

    public void printProduction(List<Duck> produced) {
        if (produced.isEmpty()) {
            System.out.println("Aucune production ce tour (pas de machines).");
            return;
        }
        System.out.println("--- Production de ce tour ---");
        Map<DuckType, List<Integer>> byType = new LinkedHashMap<>();
        for (DuckType t : DuckType.values()) byType.put(t, new ArrayList<>());
        for (Duck d : produced) byType.get(d.getType()).add(d.getQualityScore());

        for (Map.Entry<DuckType, List<Integer>> e : byType.entrySet()) {
            List<Integer> scores = e.getValue();
            if (scores.isEmpty()) continue;
            int avg = scores.stream().mapToInt(Integer::intValue).sum() / scores.size();
            String warn = avg < 50 ? " ⚠ Qualité médiocre" : "";
            System.out.printf("  +%d %s (qualité moy. : %d/100)%s%n",
                    scores.size(), e.getKey().getLabel(), avg, warn);
        }
        System.out.printf("  Total : %d canard(s) fabriqué(s)%n%n", produced.size());
    }

    // -------------------------------------------------------------------------
    // Utilitaires privés
    // -------------------------------------------------------------------------

    private void printStockSummary(Stock<Duck> stock) {
        Map<DuckType, Integer> counts = new LinkedHashMap<>();
        for (DuckType t : DuckType.values()) counts.put(t, 0);
        for (Duck d : stock.getAll()) counts.merge(d.getType(), 1, Integer::sum);

        StringBuilder sb = new StringBuilder("Stock : ");
        boolean first = true;
        for (Map.Entry<DuckType, Integer> e : counts.entrySet()) {
            if (!first) sb.append(", ");
            sb.append(e.getValue()).append("x ").append(e.getKey().getLabel());
            first = false;
        }
        sb.append("  (total : ").append(stock.total()).append(")");
        System.out.println(sb);
    }

    private String reputationLabel(int reputation) {
        if (reputation >= 80) return "Premium";
        if (reputation >= 50) return "Correct";
        if (reputation >= 20) return "Dégradé";
        return "Mauvaise réputation";
    }

    private int readInt(int min, int max) {
        while (true) {
            try {
                int v = Integer.parseInt(scanner.nextLine().trim());
                if (v >= min && v <= max) return v;
            } catch (NumberFormatException ignored) {}
            prompt("Entrée invalide. Réessayez (" + min + "–" + max + ") : ");
        }
    }

    private void prompt(String msg) {
        System.out.print(msg);
    }
}
