# Sapioza

## Objectifs du système :

> Ébauche des objectifs du système, à affiner

*Nous nous proposons de réaliser une application ayant pour objectif la centralisation des articles scientifiques, tous domaines confondus.* 

Cette application permet de poster des articles, de les soumettre pour relecture, de les consulter etc. 
L'application a pour ambition de ressembler à un réseau social de partage de connaissances scientifiques; des systèmes d'abonnement (à un chercheur, à une revue...), de notification, de notes et commentaires des lecteurs et de pages d'articles populaires notamment sont mis en place dans ce but.

L'application dispose d'un outil de recherche solide proposant plusieurs filtres de recherche (années de publication, titre, abstract, auteur, domaine...).
Il est possible de configurer des notifications en cas de nouvelle parution dans un domaine précis (e.g Nouvel article en informatique incluant le mot clé "blind signature").

Les chercheurs peuvent choisir individuellement de placer ou non leurs articles derrière un paywall.

Au dépôt d'un article, celui-ci est analysé pour s'assurer du sérieux de la publication (dans quelle revue a-t-il été publié...).
Nous disposons également de notre propre certification : si l'article est soumis pour relecture, celui-ci est envoyé à nos chercheurs partenaires du domaine, qui après étude valident ou non l'article (avec potentiellement des allers-retours entre le chercheur et les relecteurs).

### Acteurs et communication :

La liste des acteurs est : 
- Utilisateur (Chercheur ou Lecteur)
- Chercheurs partenaires
- Paper Service (CRUD Auteur & *U* Lecteur) => BDD = Meta-données (L'API est ici)
- Storage Service => BDD = PDFs
- Payment Service
- Notification Service

### Use Cases :

#### Utilisateur :

- Poster un article
- Soumettre un article pour relecture
- Consulter un article
- Rechercher un article
- S'abonner à un chercheur
- S'abonner à une revue
- Noter un article
- Commenter un article
- Consulter les articles populaires
- Consulter les articles de ses abonnements
- Consulter les articles de ses notifications

#### Chercheurs partenaires :

- Valider un article
- Refuser un article
- Consulter les articles en attente de validation


## Diagrammes de Séquence :

> À réaliser après confirmation des objectifs  

## Exigences et Remarques : 

> See SOLR & CAMEL:PDF

