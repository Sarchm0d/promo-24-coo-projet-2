## Question Ex1
**Non.** Méthode default n'accède pas aux champs privés. `isDefective()` appelle `getQualityScore()` publique de Duck (expose qualityScore privé). `this.qualityScore` compilera pas.


## Question Ex2
On ne peut pas inverser car en Java, une clase n'hérite que d'une classe. Si Machine était une interface, elle ne pourrait pas contenir le champ `condition` ni d'implémentation commune.
**Interface** = contrat de méthode. **Classe abstraite** = partager du code et champs.

## Question Ex4
`canBeFulfilled(Stock<? extends Duck>)` accepte tout stock de sous-type de Duck, donc plus flexible. Avec `Stock<Duck>`, on ne pourrait pas passer un `Stock<MiniDuck>`. Le second permet la covariance générique.
Exemple :
Stock<MiniDuck> miniStock = new Stock<>();
order.canBeFulfilled(miniStock); 


