# DuckCorp™ : Projet Java

**Durée : 3h**
**Travail individuel · Internet autorisé**
**Deadline : 1er avril 2026 à 12h00 (heure de Rodez), le dernier commit avant 12h00 sera pris en compte.**
**Les bonus peuvent être réalisés chez vous après la séance.**

---

## Modalités

- **Solo.** Le projet est individuel.
- **Internet autorisé.** Toutes les ressources en ligne sont accessibles.
- **Git obligatoire.** Forkez le dépôt du projet, mettez-le en **privé**, puis travaillez dessus.
- **Invitez le bot de correction** à votre dépôt GitHub : ajoutez **`SuperCanardRoussille`** comme collaborateur.
  Assurez-vous que le fichier `AUTHORS` est présent et correctement rempli **avant** d'envoyer l'invitation.

### Entraide sur les bonus

Vous pouvez vous entraider entre étudiants.
Si quelqu'un vous a aidé, créez un fichier **`HELPERS`** à la racine de votre dépôt
et listez-y leurs noms (et les parties concernées) cela leur vaudra des points bonus.

### Bonnes pratiques attendues

- **Committez souvent** : un commit par fonctionnalité ou par méthode implémentée, pas un seul commit à la fin.
- **Pushez régulièrement** : un dépôt local non poussé n'est pas un rendu.
- **Messages de commit clairs** : décrivez ce que vous avez fait, pas comment (`"Implémente Stock.remove()"`, pas `"fix"`).
- **Commentez votre code** : Javadoc sur les méthodes que vous écrivez, commentaires sur les choix non évidents.
- **Testez** : un `main` de test, des appels manuels, ou tout ce que vous jugez utile pour vérifier votre implémentation.

### Fichier AUTHORS obligatoire

Votre dépôt doit contenir un fichier **`AUTHORS`** à la racine, structuré exactement ainsi :

```
projet2promo24c01n2
<votre prénom> <votre NOM> <votre email@3il.fr>
```

---

## Présentation

Vous êtes directeur général de **DuckCorp™**, une usine de canards en plastique
dont la réputation est à construire. Votre mission : piloter l'usine pendant **8 tours**
pour terminer avec le meilleur score possible.

Ce projet est un jeu de gestion en console. Une grande partie du code est fournie :
le moteur de jeu (`Game.java`), l'interface console (`Console.java`) et le marché
(`Market.java`). Votre rôle est d'implémenter les classes métier qui manquent,
sans lesquelles le jeu ne peut pas tourner.

---

## Le jeu

### Objectif

Maximiser votre score final, calculé ainsi :

```
score = budget + réputation × 80 + commandes_honorées × 200 − commandes_expirées × 100
```

Chaque euro restant compte, mais honorer des commandes et maintenir une bonne
réputation est bien plus rentable que d'accumuler du cash.

### Les canards

DuckCorp™ produit trois types de canards :

| Type     | Prix de base | Profil                                                   |
|----------|-------------|----------------------------------------------------------|
| Standard | 25 €        | Volume, commandes fréquentes, marges modestes            |
| Mini     | 12 €        | Très demandé, produit en grande quantité                 |
| Luxe     | 80 €        | Marges élevées, peu demandé, disponible seulement si réputation ≥ 80 |

Chaque canard a un **score de qualité** (0–100) qui dépend de l'état de la machine
qui l'a produit. Un canard défectueux (score < 20) pénalise la réputation lors de l'expédition.

### Les machines

Les machines sont le cœur de la production. Elles tournent automatiquement à chaque tour
et produisent un nombre fixe de canards selon leur capacité.

| Machine         | Coût d'achat | Capacité       | Maintenance |
|-----------------|-------------|----------------|-------------|
| Presse Standard | 500 €       | 5 canards/tour | 50 €        |
| Mini-Presse     | 300 €       | 8 canards/tour | 30 €        |
| Moule de Luxe   | 800 €       | 2 canards/tour | 80 €        |

**Dégradation :** à chaque fin de tour, chaque machine perd 10 points de condition.
En dessous de 30 de condition, la qualité des canards produits chute fortement
et la réputation est pénalisée de 5 points par machine en état critique.

**Maintenance :** payer la maintenance restaure 40 points de condition (plafonnée à 100).
Une machine bien entretenue produit des canards de bien meilleure qualité.

### La réputation

La réputation est un indicateur central entre 0 et 100. Elle évolue à chaque tour :

- **+3** par commande honorée avec une qualité moyenne ≥ 70
- **+1** par commande honorée avec une qualité moyenne ≥ 50
- **−5** par commande expirée (délai dépassé sans expédition)
- **−5** par machine en état critique en fin de tour

La réputation détermine ce que le marché vous propose :

| Réputation | Niveau              | Effet sur le marché                     |
|-----------|---------------------|-----------------------------------------|
| ≥ 80      | Premium             | Toutes catégories, prix pleins          |
| 50 – 79   | Correct             | Standard + Mini uniquement, prix pleins |
| 20 – 49   | Dégradé             | Standard + Mini, prix réduits (×0,80)   |
| < 20      | Mauvaise réputation | Mini uniquement, prix réduits (×0,60)   |

### Le marché

À chaque tour, le marché génère 2 à 4 commandes aléatoires. Chaque commande précise :
- le **type** de canards demandés
- la **quantité**
- le **prix unitaire** proposé
- le **délai** (en tours) avant expiration

Si vous ne livrez pas une commande dans le délai imparti, elle expire et votre réputation
en pâtit. Vous n'êtes pas obligé d'accepter toutes les commandes.

> **Note générale :** 6 questions ouvertes sont posées tout au long du sujet.
> Elles **ne sont pas optionnelles** : répondez-y dans un fichier `REPONSES.md` à la racine de votre dépôt (à créer).
> Chaque réponse vaut 1 à 2 pts, intégrés dans le barème de l'exercice correspondant.

---

## Déroulement d'un tour

Chaque tour se déroule en 7 étapes dans cet ordre :

```
1. Expiration des commandes trop vieilles
2. Arrivée de nouvelles commandes du marché
3. Achat optionnel d'une machine
4. Maintenance optionnelle d'une machine
5. Production automatique (toutes les machines tournent)
6. Expédition des commandes choisies
7. Fin de tour : dégradation des machines
```

### Exemple de tour complet

```
════════════════════════════════════════════
  Tour 3 / 8
  Budget : 3 840 €   Réputation : [Correct] 72
  Score estimé : 12 520 pts
════════════════════════════════════════════
Stock : 8x Standard, 0x Mini, 0x Luxe  (total : 8)

--- Nouvelles commandes ---
  [CMD-007] 10x Standard → 28€/u (280€) — 3 tour(s)
  [CMD-008]  2x Luxe     → 72€/u (144€) — 2 tour(s)

--- Boutique de machines ---
  Budget disponible : 3 840 €
  [1] Presse Standard   500 €  — 5 canards/tour   maintenance : 50 €
  [2] Mini-Presse       300 €  — 8 canards/tour   maintenance : 30 €
  [3] Moule de Luxe     800 €  — 2 canards/tour   maintenance : 80 €
  [0] Passer
Votre choix : 3
  → Moule de Luxe acheté ! −800 €

--- Vos machines ---
  [1] Presse Standard [capacité : 5/tour, état : 45%]
  [2] Moule de Luxe   [capacité : 2/tour, état : 100%]
  [0] Passer
Entretenir la machine n° (0 pour passer) : 0

--- Production de ce tour ---
  +5 STANDARD (qualité moy. : 63/100)
  +2 LUXE     (qualité moy. : 95/100)
  Total : 7 canard(s) fabriqué(s)

--- Commandes en attente ---
Stock : 13x Standard, 0x Mini, 2x Luxe  (total : 15)
  [1] [CMD-001]  8x Standard → 25€/u (200€) — 1 tour(s)  [✓ RÉALISABLE]
  [2] [CMD-007] 10x Standard → 28€/u (280€) — 3 tour(s)  [✗ stock insuffisant]
  [3] [CMD-008]  2x Luxe     → 72€/u (144€) — 2 tour(s)  [✓ RÉALISABLE]
  [0] Passer
Expédier (ex: 1,2 — 0 pour passer) : 1,3
  → CMD-001 expédiée ! +200 €
  → CMD-008 expédiée ! +144 €

--- Fin du tour 3 ---
  Budget : 3 184 €   Réputation : [Correct] 74
```

---

## Architecture fournie

```
src/duckcorp/
├── Main.java          ← point d'entrée, fourni
├── Game.java          ← boucle de jeu (8 tours), fourni
├── Console.java       ← affichage et saisies, fourni
├── Market.java        ← génération des commandes, fourni
├── duck/
│   ├── DuckType.java           ← enum (STANDARD, MINI, LUXURY), fourni
│   ├── Qualifiable.java        ← TODO Ex1
│   ├── Duck.java               ← TODO Ex1
│   ├── StandardDuck.java       ← TODO Ex1
│   ├── MiniDuck.java           ← TODO Ex1
│   └── LuxuryDuck.java         ← TODO Ex1
├── machine/
│   ├── Maintainable.java       ← TODO Ex2
│   ├── Machine.java            ← TODO Ex2
│   ├── StandardPress.java      ← TODO Ex2
│   ├── MiniPress.java          ← TODO Ex2
│   └── LuxuryMold.java         ← TODO Ex2
├── stock/
│   └── Stock.java              ← TODO Ex3
├── order/
│   ├── OrderStatus.java        ← enum (PENDING, FULFILLED, EXPIRED), fourni
│   └── Order.java              ← TODO Ex4
├── factory/
│   └── Factory.java            ← TODO Ex5
├── stats/
│   ├── ProductionStats.java    ← TODO Bonus 1
│   └── ConcurrentProductionStats.java ← TODO Bonus 3
└── bonus/
    ├── DuckComparator.java     ← TODO Bonus 2
    ├── OrderBook.java          ← TODO Bonus 2
    └── ProductionQueue.java    ← TODO Bonus 3
```

> **Note :** le projet ne compile pas dans l'état initial, c'est normal.
> Il compilera progressivement au fur et à mesure que vous implémentez les exercices.
> À partir de l'Ex2 entièrement complété, vous pouvez lancer le jeu.

---

## Partie obligatoire

---

### Exercice 1 : Hiérarchie des canards (20 pts)

L'objectif est de modéliser les trois types de canards produits par DuckCorp™.
Tous partagent une structure commune (identifiant unique, type, qualité) définie
dans la classe abstraite `Duck`, et implémentent un contrat de qualité via l'interface `Qualifiable`.

#### `Qualifiable` (interface)

Cette interface définit ce que signifie "avoir une qualité" pour un objet.
Elle sera implémentée par `Duck` et permettra au code de l'usine de vérifier
si un canard est défectueux sans connaître son type exact.

- Déclarez la méthode abstraite `int getQualityScore()` : retourne le score de qualité (0–100).
- Ajoutez une méthode `default boolean isDefective()` : retourne `true` si le score est < 20.
  Un canard défectueux dans une expédition nuit à la réputation.
- Ajoutez une méthode `default String getQualityLabel()` qui traduit le score en libellé :
  - score ≥ 80 → `"Excellent"` / ≥ 50 → `"Bon"` / ≥ 20 → `"Médiocre"` / < 20 → `"Défectueux"`

#### `Duck` (classe abstraite)

`Duck` représente tout canard DuckCorp™, quel que soit son type.
Chaque canard reçoit à sa création un identifiant unique auto-généré (ex. `S0042`),
un type (`DuckType`) et un score de qualité.

- Faites implémenter `Qualifiable` à `Duck`.
- Implémentez `equals(Object)` et `hashCode()` basés **uniquement sur l'id** :
  deux canards sont identiques si et seulement si ils ont le même identifiant,
  peu importe leur qualité ou leur type.
- Les méthodes abstraites `getBasePrice()` et `describe()` sont à implémenter
  dans les sous-classes (chaque type a son prix et sa description).

#### `StandardDuck`, `MiniDuck`, `LuxuryDuck`

Chacune représente un type de canard concret produit par une machine spécifique.

Pour chacune :
- Faites hériter de `Duck`.
- Implémentez le constructeur `(int qualityScore)` avec un appel à `super`
  en passant le bon `DuckType`.
- Implémentez `getBasePrice()` : retournez la constante `BASE_PRICE` définie dans la classe.
- Implémentez `describe()` : retournez le nom du canard en français
  (`"Canard Standard"`, `"Mini Canard"`, `"Canard de Luxe"`).

Prix de base : Standard = 25 €, Mini = 12 €, Luxe = 80 €.

> **Question (Ex1) :** Une méthode `default` dans une interface peut-elle accéder aux champs
> privés de la classe qui l'implémente ? Justifiez, en vous appuyant sur `isDefective()`
> dans `Qualifiable` et `getQualityScore()` dans `Duck`.

---

### Exercice 2 : Hiérarchie des machines (20 pts)

Les machines sont les outils de production de l'usine. Elles partagent une logique
commune (capacité, condition, dégradation) définie dans `Machine`, et exposent
un contrat d'entretien via l'interface `Maintainable`.

La qualité des canards produits dépend directement de l'état de la machine :
une machine en bon état produit des canards de qualité élevée, une machine dégradée
produit des rebuts.

#### `Maintainable` (interface)

Cette interface définit ce que signifie "pouvoir être entretenu".
Elle sera implémentée par `Machine` et permettra à la `Factory` de vérifier
l'état d'une machine sans connaître son type.

- Déclarez les méthodes abstraites `int getCondition()` et `void maintain()`.
- Ajoutez une méthode `default boolean needsMaintenance()` : retourne `true` si condition < 30.
  C'est le seuil en dessous duquel la machine devient critique.
- Ajoutez une méthode `default String getConditionLabel()` qui traduit la condition en libellé :
  - ≥ 80 → `"Parfait"` / ≥ 50 → `"Correct"` / ≥ 30 → `"Usé"` / < 30 → `"Critique"`

#### `Machine` (classe abstraite)

`Machine` représente toute machine de production, quel que soit son type.
Elle contient la logique commune : gestion de la condition, calcul de la qualité produite.

- Faites implémenter `Maintainable` à `Machine`.
- Implémentez `getCondition()` : retournez le champ `condition`.
- Implémentez `maintain()` : augmentez `condition` de 40 points, plafonnée à 100.
  Payer la maintenance restaure la machine sans la remettre à neuf d'un coup.
- Les méthodes `degrade()` (appelée automatiquement en fin de tour) et `computeQuality()`
  (calcule la qualité d'un canard selon l'état de la machine) sont fournies, ne les modifiez pas.

#### `StandardPress`, `MiniPress`, `LuxuryMold`

Chacune est une machine concrète qui produit un type spécifique de canard.

Pour chacune :
- Faites hériter de `Machine`.
- Implémentez le constructeur sans paramètre avec un appel à `super`
  en passant `DuckType`, capacité et coût de maintenance appropriés.
- Implémentez `produceDuck()` : créez et retournez le canard correspondant
  en utilisant `computeQuality()` hérité pour déterminer sa qualité.
  Ne recalculez pas la qualité vous-même.
- Implémentez `getPurchaseCost()` et `getName()`.

#### `Game.createMachine(int choice)` (dans `Game.java`)

Une méthode `createMachine()` est déclarée dans `Game.java` avec un TODO.
C'est le seul endroit du fichier fourni que vous devez modifier.
Complétez-la : retournez l'instance de la bonne sous-classe de `Machine` selon le choix
(1 → `StandardPress`, 2 → `MiniPress`, 3 → `LuxuryMold`).

> **Question (Ex2) :** Dans ce projet, `Maintainable` est une interface et `Machine` est une
> classe abstraite. Quelle règle Java vous aurait empêché de faire l'inverse
> (rendre `Maintainable` abstraite et `Machine` une interface) ? Plus généralement,
> quand choisit-on une interface plutôt qu'une classe abstraite ?

---

### Exercice 3 : Stock générique (15 pts)

Le stock est l'entrepôt de l'usine. Il contient tous les canards produits
et pas encore expédiés. Il est générique (`Stock<T extends Duck>`) pour
permettre de stocker tout type de canard tout en conservant le typage statique.

Les méthodes `add()`, `getAll()` et `total()` sont fournies.
Vous implémentez les opérations métier :

- **`List<T> remove(DuckType type, int count)`** : retire exactement `count` canards
  du type demandé du stock et les retourne. Cette méthode est appelée par `Factory`
  lors d'une expédition. Si le stock ne contient pas assez de canards du bon type,
  levez une `IllegalStateException` avec un message explicite.
  La signature de retour doit conserver le type générique `T`, pas `Duck`.
  _Conseil : une seule passe suffit._

- **`int count(DuckType type)`** : retourne le nombre de canards d'un type donné dans le stock.
  Utilisé par `Order.canBeFulfilled()` pour savoir si une commande est réalisable.

- **`int countDefective()`** : retourne le nombre de canards défectueux dans le stock.
  _Conseil : appelez `isDefective()`, ne comparez pas le score manuellement._

- **`Map<DuckType, Integer> countByType()`** : retourne une map associant chaque type
  au nombre de canards correspondants en stock. Tous les types doivent apparaître dans
  la map (y compris avec 0 si le type est absent).
  _Conseil : une seule passe sur la liste, pas trois appels à `count()`._

---

### Exercice 4 : Commandes (10 pts)

Une commande représente une demande d'un client : il veut N canards d'un type donné,
à un prix fixé, avant une certaine échéance. Si la commande n'est pas expédiée à temps,
elle expire et la réputation en pâtit.

Les getters, le constructeur et la méthode `tick()` (qui décrémente le délai
à chaque tour et passe la commande à `EXPIRED` si le délai atteint 0) sont fournis.

- **`double getTotalValue()`** : retourne la valeur totale de la commande
  (`quantity × pricePerUnit`). Utilisée pour créditer le budget lors d'une expédition.

- **`boolean canBeFulfilled(... stock)`** : retourne `true`
  si le stock contient au moins `quantity` canards du bon type.
  Réfléchissez à la signature du paramètre : doit-elle accepter uniquement un `Stock<Duck>`,
  ou tout `Stock` dont le type générique étend `Duck` ?

- **`void fulfill()`** : passe le statut de la commande à `FULFILLED`.
  Appelée par `Factory` après avoir retiré les canards du stock.

- **`equals(Object)` et `hashCode()`** : deux commandes sont identiques si et seulement
  si elles ont le même id. Une même commande ne doit pas apparaître deux fois dans
  une structure telle qu'un `Set` ou une `Map`.

> **Question (Ex4) :** Expliquez pourquoi `canBeFulfilled(Stock<Duck> stock)` serait une
> signature plus restrictive que `canBeFulfilled(Stock<? extends Duck> stock)`.
> Donnez un exemple de code Java qui compilerait avec la seconde mais pas avec la première.

---

### Exercice 5 : Usine (15 pts)

La `Factory` est le cœur du jeu : elle orchestre machines, stock, budget et réputation.
C'est elle que `Game.java` appelle à chaque étape d'un tour.

Le constructeur, les getters, `notifyExpiredOrder()` (pénalité réputation + stats
lors d'une expiration) et `computeScore()` (calcul du score final) sont fournis.

- **`boolean buyMachine(Machine machine)`** : tente d'acheter une machine.
  Vérifie que le budget est suffisant, déduit le coût d'achat et ajoute la machine
  à la liste. Retourne `false` si le budget est insuffisant (aucun achat).

- **`boolean maintainMachine(Machine machine)`** : tente d'effectuer la maintenance
  d'une machine existante. Vérifie le budget, déduit le coût et délègue à `machine.maintain()`.

- **`List<Duck> runProduction()`** : déclenche la production de toutes les machines
  pour ce tour. Chaque machine produit autant de canards que sa capacité (via `produceDuck()`),
  qui sont ajoutés au stock. Retourne la liste complète des canards produits ce tour.
  _Conseil : déléguez à `machine.produceDuck()` (un `instanceof` ici serait une faute de conception)._

- **`boolean fulfillOrder(Order order)`** : tente d'honorer une commande.
  Si `canBeFulfilled()` est vrai : retire les canards du stock, crédite le budget,
  met à jour la réputation selon la qualité moyenne des canards expédiés
  (+3 si moy ≥ 70, +1 si moy ≥ 50), marque la commande honorée, met à jour les stats.
  _Conseil : utilisez `DuckComparator` (Bonus 2) pour expédier les moins bons en premier
  et conserver les meilleurs en stock._

**Question ouverte (dans `REPONSES.md`) :** `Factory` expose `getMachines()` qui retourne
une `List<Machine>` non modifiable (via `Collections.unmodifiableList()`).
Pourquoi ce choix ? Que se passerait-il si on retournait la liste interne directement ?
Peut-on quand même modifier les machines elles-mêmes (via leurs méthodes) depuis l'extérieur ?

---

## Bonus

---

### Bonus 1 : Statistiques de production (15 pts)

Les statistiques permettent d'afficher un bilan en fin de partie et d'alimenter
le score final. `ProductionStats` utilise des `Map<DuckType, Integer>` pour suivre
la production et les ventes par type de canard.

#### `ProductionStats`

- **`void recordProduction(... ducks)`** : pour chaque canard de la liste,
  incrémente son compteur dans la map `produced`.
  Utilisez `getOrDefault()` ou `merge()` plutôt qu'un null-check manuel.
  Réfléchissez à la signature : doit-elle accepter uniquement une `List<Duck>`,
  ou quelque chose de plus général ?

- **`void recordSale(Order order)`** : enregistre une vente : incrémente `sold`
  pour le bon type, ajoute la valeur de la commande à `totalRevenue`,
  incrémente `totalOrders`.

- **`int getTotalProduced()`** : retourne la somme de tous les canards produits,
  toutes catégories confondues. Parcourez `produced.values()`.

- **`DuckType getMostProduced()`** : retourne le type de canard le plus produit.
  Parcourez la map en une seule passe en conservant le maximum courant.
  Retournez `null` si rien n'a encore été produit.

**Question ouverte (dans `REPONSES.md`) :** réécrivez `getMostProduced()` en une seule
expression utilisant l'API Stream :
`produced.entrySet().stream()`, `max()`, `Comparator.comparingInt()`, `Optional`.
Comparez avec votre implémentation impérative : laquelle est plus lisible ? plus efficace ?

#### `Factory.endTurn()` (5 pts)

Cette méthode est appelée automatiquement par `Game` en fin de tour.
Elle doit : dégrader toutes les machines (via `degrade()`) et, pour chaque machine
dont la condition passe en dessous de 30 après dégradation, pénaliser la réputation
de 5 points.

---

### Bonus 2 : Tri et carnet de commandes (8 pts)

#### `DuckComparator`

Un `Comparator<Duck>` qui trie les canards par score de qualité **croissant** :
le premier canard d'une liste triée sera le moins bon.
Utilisé par `Factory.fulfillOrder()` pour expédier les canards les moins bons en premier
et préserver les meilleurs pour les commandes futures.

Implémentez `compare(Duck a, Duck b)`.
Utilisez `Integer.compare()` : la soustraction directe des scores peut produire
un dépassement entier dans des cas limites.

#### `OrderBook`

Un carnet de commandes qui maintient automatiquement les commandes triées
par urgence croissante : la commande qui expire le plus tôt apparaît en tête.
Cela permet au joueur de voir immédiatement quelle commande doit être prioritaire.

- **`addOrder(Order)`** : ajoute la commande dans la liste et la retrie immédiatement.
  En cas d'égalité de délai, triez par id pour garantir un ordre stable et reproductible.
  Utilisez `Collections.sort()` avec un `Comparator` chaîné.

- **`getPendingOrders()`** : retourne uniquement les commandes au statut `PENDING`
  dans l'ordre de tri courant, sans modifier la liste interne.

- **`getMostUrgent()`** : retourne la première commande `PENDING` (la plus urgente),
  ou `null` si aucune commande n'est en attente.

---

### Bonus 3 : Concurrence (12 pts)

#### `ConcurrentProductionStats`

Dans un scénario où plusieurs machines tournent en parallèle (threads),
les accès concurrents à la map `produced` peuvent corrompre les données.
`ConcurrentProductionStats` résout ce problème en utilisant `ConcurrentHashMap`.

- Implémentez le constructeur : remplacez les champs `produced` et `sold`
  hérités (qui sont `protected` dans `ProductionStats`) par des `ConcurrentHashMap`,
  puis initialisez chaque entrée à 0 pour tous les `DuckType`.
- Surchargez `recordProduction()` pour incrémenter les compteurs de façon atomique,
  sans risque de race condition.

#### `ProductionQueue`

Une file d'attente bornée de canards entre les machines (productrices) et le stock
(consommateur). Elle s'appuie sur `ArrayBlockingQueue` qui est thread-safe par conception.

Dans ce projet le jeu est mono-thread : utilisez les méthodes **non-bloquantes**
`offer()` (ajout) et `poll()` (retrait).

- **`boolean enqueue(Duck duck)`** : tente d'ajouter un canard. Retourne `false` si plein.
- **`Duck dequeue()`** : retire et retourne le premier canard. Retourne `null` si vide.
- **`List<Duck> drainToStock(Stock<Duck> stock)`** : transfère tous les canards disponibles
  dans la file vers le stock en une seule opération via `queue.drainTo()`.
  Retourne la liste des canards transférés.

**Question ouverte (dans `REPONSES.md`) :** dans un contexte multi-thread réel
où plusieurs machines tournent en parallèle, quelles méthodes faudrait-il utiliser
à la place de `offer()` et `poll()`, et pourquoi ?

