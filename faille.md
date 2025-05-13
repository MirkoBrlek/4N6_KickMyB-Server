| Étape # | Description |
|--------:|-------------|
| 1 | Se connecter via un appel `POST /api/id/signup`. |
| 2 | Puisque la méthode `GET /api/detail/{id}` n'implémente aucun contrôle d'accès, n'importe quel utilisateur peut accéder aux détails de la tâche de quelqu’un d’autre. |
| 3 | On peut ensuite créer une tâche par `POST /api/add`, puis récupérer toutes nos tâches avec `GET /api/home`. Le dernier ID affiché sera celui de la tâche nouvellement créée, puisque les IDs sont incrémentés de 1 à chaque création. |
| 4 | Il suffit ensuite de faire des appels `GET /api/detail/{id}`, en remplaçant `{id}` par les valeurs de 1 jusqu’à l’ID obtenu à l’étape 3 (aucune tâche n’aura un ID plus grand), jusqu’à trouver celle dont le titre est **"Tâche à modifier pour le poste 24"**. |
| 5 | Finalement, on appelle `GET /api/progress/{id}/{valeur}` en remplaçant `{id}` par l’ID trouvé à l’étape 4, et `{valeur}` (de 0 à 100) par le pourcentage de progression souhaité. Cela fonctionne car cette route n'est pas protégée par un contrôle d'accès. |
