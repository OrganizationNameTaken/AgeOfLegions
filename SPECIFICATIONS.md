# SPÉCIFICATIONS - PROJET THÉÂTRE D'INVASION

**Projet :** Simulation de gestion et de stratégie en Gaule Romaine

---

# I. SPÉCIFICATIONS FONCTIONNELLES (Règles du Jeu)

## 1. Vue d'ensemble
Le système simule la gestion, la vie quotidienne et les conflits en Gaule Romaine entre trois populations distinctes :
* **Les Gaulois :** Peuple résistant, incluant des civils et des druides.
* **Les Romains :** Envahisseurs organisés selon une hiérarchie militaire stricte.
* **Les Créatures Fantastiques :** Faune mythologique (ex: Lycanthropes).

## 2. Gestion des Personnages
Chaque entité simulée dispose d'un cycle de vie et de caractéristiques évolutives définies par la classe `Character`.

### 2.1 Attributs Vitaux
Chaque personnage possède :
* **État Civil :** Nom, Genre, Taille, Âge.
* **Métabolisme :** Santé, Faim, Force, Endurance.
* **Psychologie & Magie :** Belliquisme (agressivité) et Niveau de potion magique.
* **Cycle de vie :** Un personnage naît, vit, et meurt (méthode `die()`) si sa santé atteint zéro.

### 2.2 Rôles et Métiers
Les compétences sont définies par des rôles spécifiques implémentés via des interfaces :
* **Combattants :** Légionnaires, Généraux, Druides, Lycanthropes (capables d'attaquer et défendre).
* **Travailleurs :** Aubergistes, Forgerons, Marchands (produisent des services via `work()`).
* **Chefs :** Préfets, Généraux, Druides (dirigent et motivent).
* **Métamorphes :** Lycanthropes capables de se transformer (méthode `transform()`).

## 3. Règles de Survie et Nutrition
La gestion de l'alimentation est critique et soumise à des règles strictes.

### 3.1 Aliments
Les aliments (`Food`) possèdent un type (`FoodType`), une valeur nutritionnelle et une fraîcheur.
* *Exemples :* Sanglier (`WILD_BOAR`), Poisson (`PASSABLE_FISH`), Gui (`MISTLETOE`), etc.

### 3.2 La Règle de l'Indigestion (Mémoire de l'Estomac)
Pour éviter les abus, le système simule la digestion via un mécanisme de mémoire :
1.  **Mémoire :** Un personnage retient le dernier aliment consommé dans son attribut `stomach`.
2.  **Restriction :** Il est interdit de consommer deux fois de suite le même type d'aliment.
3.  **Conséquences :**
    * **Aliment Différent :** La Faim diminue et la Santé augmente.
    * **Même Aliment :** Le personnage subit une **indigestion** (dégâts à la santé) au lieu d'être nourri.

## 4. Système d'Alchimie (Magie)
La magie repose sur la consommation de potions fabriquées.

* **Production :** Seuls les **Druides** (rôle `PotionMaker`) peuvent préparer des potions ou des chaudrons.
* **Recettes :** Chaque potion requiert une liste précise d'ingrédients (ex: Gui + Huile de roche).
* **Effets :** L'effet (force, invulnérabilité) s'active uniquement quand un personnage utilise l'action `drinkPotion()`. L'intensité dépend de la dose ou du mode de consommation (fiole vs chaudron).

## 5. Système de Combat
Les conflits sont résolus mathématiquement sans équipement externe.

* **Résolution :** L'issue d'un combat (`fight`) est calculée selon la **Force**, l'**Endurance** et le **Niveau de potion** actuel des combattants.
* **Stratégie :** Les chefs peuvent donner des ordres, mais la puissance brute et magique reste déterminante.
* **Transformation :** Les Lycanthropes modifient leurs stats en changeant de forme.

## 6. Géographie et Contrôle
Le monde est divisé en zones distinctes gérées par le `InvasionTheater`.

* **Lieux Habités (`Settlement`) :** Villages Gaulois, Camps Romains, Villes.
* **Lieux Sauvages (`Wilderness`) :** Champs de bataille.
* **Capacité :** Chaque lieu a une superficie et une limite de population (vérifiée par `canContain`).
* **Chef de Clan (`ClanChief`) :** Supervise un lieu habité, ordonne la production de potions et les mouvements de troupes.

---

# II. SPÉCIFICATIONS TECHNIQUES (Architecture Logicielle)

**Architecture :** Orientée Objet, SOLID, Multithreadée.

## 1. Architecture Orientée Objet
Le projet implémente les concepts avancés demandés.

### 1.1 Classes Scellées (Sealed Classes)
Pour garantir la stabilité du modèle de données, la hiérarchie des personnages est verrouillée. La classe abstraite `Character` est déclarée `sealed` et n'autorise (`permits`) que les sous-classes `Gallic`, `Roman`, et `FantasticCreature`.

### 1.2 Interfaces et Polymorphisme
Les comportements sont découplés de la race via des interfaces :
* `Fighter` : Pour les entités combattantes.
* `Worker` : Pour les métiers civils.
* `PotionMaker` : Spécifique aux Druides.
* `Transformer` : Spécifique aux Lycanthropes.

## 2. Structures de Données

### 2.1 Collections
* `ArrayList` (de `Character`) : Utilisé dans `Location` pour gérer la population.
* `List` (de `Food`) : Gestion des stocks d'ingrédients et contenu des potions.

### 2.2 Gestion de l'Estomac (Attribut `stomach`)
Conformément au diagramme UML, la classe `Character` possède un attribut privé `stomach` de type `Food`. Cet objet sert de mémoire tampon pour comparer le type d'aliment courant avec le précédent lors de l'appel à la méthode `eat()`, implémentant ainsi la règle d'indigestion.

## 3. Algorithmique

### 3.1 Tri (Sorting)
Implémentation d'un algorithme de tri (utilisant par exemple `Comparator` ou `QuickSort`) pour ordonner les personnages d'un lieu (par **Force** décroissante ou **Santé** croissante) avant une action de groupe.

### 3.2 Validation de Recette
La classe `PotionRecipe` utilise un algorithme de validation qui parcourt la liste des ingrédients fournis via un **Itérateur** pour vérifier la correspondance avec la recette requise.

## 4. Concurrence (Multithreading)
L'application utilise des Threads pour la simulation temporelle :
* **Thread Principal :** Gère la boucle de jeu `InvasionTheater`.
* **Timer Thread :** Gère l'attribut `time`. À intervalles réguliers, il déclenche l'augmentation de la faim (`hunger`) et la péremption des aliments (`spoil()`).

## 5. Environnement et Qualité
* **Convention de Code :** nommage en anglais, Javadoc complète.
* **Gestion de Version :** git avec historique de commits conventionnels.
* **Tests Unitaires :** c'est des tests quoi...