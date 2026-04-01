## Question Ex1
**Non.** Méthode default n'accède pas aux champs privés. `isDefective()` appelle `getQualityScore()` publique de Duck (expose qualityScore privé). `this.qualityScore` compilera pas.

## Question Ex2
On ne peut pas inverser car en Java, une clase n'hérite que d'une classe. Si Machine était une interface, elle ne pourrait pas contenir le champ `condition` ni d'implémentation commune.
**Interface** = contrat de méthode. **Classe abstraite** = partager du code et champs.


