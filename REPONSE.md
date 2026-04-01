## Question Ex1
**Non.** Méthode default n'accède pas aux champs privés. `isDefective()` appelle `getQualityScore()` publique de Duck (expose qualityScore privé). `this.qualityScore` compilera pas.


