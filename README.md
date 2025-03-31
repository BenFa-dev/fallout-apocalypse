# Apocalypse : The Fall

Projet de développement pour explorer les dernières fonctionnalités d'Angular 19, à travers un jeu post-apocalyptique
inspiré de Fallout.

Pour le côté backend, on est sur du Spring Boot 3.

## 🛠 Stack Technique

### Frontend ([Détails](frontend/README.md))

- **Angular 19**
- **NgRx 19** : Gestion d'état
- **Phaser 3** : Moteur de jeu 2D

### Backend ([Détails](backend/README.md))

- **Spring Boot 3**
- **JPA + Hibernate**
- **PostgreSQL**
- **Kafka** : Event streaming
- **Spring AI** : IA pour génération de contenu

### Authentification

- **Keycloak** : Authentification/Autorisation

## 🎮 État Actuel

Techniques

- ✅ Landing page avec auth Keycloak
- ✅ Connexion / déconnexion
- ✅ Intégration Phaser
- ✅ Kafka + Websocket

Fonctionnalités

- ✅ Carte du monde
- ✅ Création de personnage (basique)
- ✅ Déplacement du joueur
    - ✅ Brouillard de guerre

Plus de détails dans les README spécifiques :

- [Frontend](frontend/README.md)
- [Backend](backend/README.md)

## 📄 Licence

Ce projet est sous licence MIT. 