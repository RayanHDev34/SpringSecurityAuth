# 🏡 ChâTop – Spring Boot Backend

## 📖 Contexte

Ce projet fait partie du développement du portail **ChâTop**, une application permettant aux **locataires** de contacter les **propriétaires** pour des **locations saisonnières**.

Le front-end (en **Angular**) utilisait initialement des données simulées avec **Mockoon**.  
Ce back-end en **Spring Boot** remplace ces données mockées et gère la logique réelle :  
authentification, gestion des annonces, messagerie interne et stockage d’images via **Cloudinary**.

---

## ⚙️ Outils et technologies utilisées

- **Java 17**
- **Spring Boot 3.5.6**
    - Spring Web
    - Spring Security (JWT)
    - Spring Data JPA
- **MySQL** – Base de données
- **Cloudinary** – Stockage des images
- **Swagger / OpenAPI** – Documentation de l’API
- **IntelliJ IDEA** – Environnement de développement
- **Postman** – Tests des endpoints

---

## 🚀 Installation et exécution (avec IntelliJ IDEA)

### 1️⃣ Cloner le projet

```bash
git clone https://github.com/RayanHDev34/SpringSecurityAuth.git
cd SpringSecurityAuth
```

### 2️⃣ Ouvrir dans IntelliJ

- Ouvre le projet dans **IntelliJ IDEA**
- Laisse **Maven** télécharger les dépendances automatiquement

### 3️⃣ Configurer la base de données

Crée une base **MySQL** :

```sql
CREATE DATABASE chatop_db;
```

Puis configure le fichier `src/main/resources/application.properties` :

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/chatop_db
spring.datasource.username=root
spring.datasource.password=ton_mot_de_passe
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```

### 4️⃣ Ajouter la configuration Cloudinary

Toujours dans `application.properties` :

```properties
cloudinary.cloud-name=dkqmlwvq4
cloudinary.api-key=583286228578224
cloudinary.api-secret=18ydod7BRMtU_1u-Gi-rwXWqy9I
```

### 5️⃣ Lancer le projet

Dans **IntelliJ** :

1. Ouvre `SpringSecurityAuthApplication.java`
2. Clique sur **Run ▶️**

Le serveur démarre sur :  
👉 [http://localhost:8080](http://localhost:8080)

---

## 🌐 Documentation Swagger

Une fois le projet lancé, ouvre :  
👉 [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

Cette interface te permet de **tester** et **documenter** toutes les routes de l’API.

---

## ✨ Fonctionnalités principales

### 🔐 Authentification JWT
- Inscription et connexion sécurisées
- Génération et validation de token JWT

| Méthode | Endpoint | Description |
|----------|-----------|-------------|
| `POST` | `/api/auth/register` | Inscription d’un nouvel utilisateur |
| `POST` | `/api/auth/login` | Connexion et génération du token JWT |
| `GET` | `/api/auth/me` | Récupération des informations de l’utilisateur connecté |

---

### 👤 Gestion des utilisateurs
- Récupération du profil de l’utilisateur authentifié via le token JWT

| Méthode | Endpoint | Description |
|----------|-----------|-------------|
| `GET` | `/api/auth/me` | Retourne les informations de l’utilisateur connecté |

---

### 🏠 Gestion des locations
- CRUD complet : création, lecture, mise à jour et suppression
- Upload et remplacement d’images via **Cloudinary**

| Méthode | Endpoint | Description |
|----------|-----------|-------------|
| `GET` | `/api/rentals` | Liste toutes les locations |
| `GET` | `/api/rentals/{id}` | Récupère une location spécifique |
| `POST` | `/api/rentals` | Crée une nouvelle location (avec image) |
| `PUT` | `/api/rentals/{id}` | Met à jour une location existante |
| `DELETE` | `/api/rentals/{id}` | Supprime une location |

---

### 💬 Messagerie interne
- Envoi de messages entre locataires et propriétaires

| Méthode | Endpoint | Description |
|----------|-----------|-------------|
| `POST` | `/api/messages` | Envoie un message (avec `message`, `user_id`, `rental_id`) |

---
