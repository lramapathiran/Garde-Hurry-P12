# Garde Hurry
Platforme d'entraide dans la garde d'enfants dans une communauté de parents d'une même ville.

## Pré-requis

1- Installer le kit de développement Java (JDK 8 minimum).

2- Installer Maven 4

3- Installer mySQL 8

## Contenu du repository

1- Dossier ScriptSQL avec un fichier Data_And_Structure.sql qui permet la création de la base de données et insertion de données:
  - Un Utilisateur avec le rôle d'Administrateur: Identifiant = l.fernand@gmail.com/Mot de passe=l.fernand@gmail.com
  - Le reste qui sont de simples utilisateurs, ex : Identifiant = s.Monthy@gmail.com/Mot de passe=s.Monthy@gmail.com

Pour plus de facilité, l'identifiant qui correspond à l'adresse email de l'utilisateur aura le même mot de passe.

2- Dossier api : contient le projet Maven/Spring Boot qui gère les appels api REST et toute la partie business.

3- Dossier emailBatch : contient le projet Maven/Spring Boot qui gère toute la partie Batch avec les envois de notifications.

4- Dossier webApplication : contient le projet Maven/Spring Boot qui gère toute la partie front de l'application.

5- Dossier Documentations qui contient :  
  - note_d_attention.pdf : une note d'attention expliquant le but et la raison de ce projet.
  - bilan.pdf : le bilan qui décrit et évalue rétrospectivement mon expérience.
  - Modele_Physique_De_Donnees.pdf : le modèle physique de données pour représenter les entités et leurs relations dans l'application.

## Utilisation de Spring Boot

1- Configuration dans le fichier application.properties 

	1a-Dans api/src/main/resources : personnaliser le port, le username et le mot de passe mySQL.	
	1b-Dans emailBatch/src/main/resources : personnaliser le port(doit être différent de celui utilisé en 1a), le cron.expression, spring.mail.username/password selon son adresse Gmail(de même que le fichier: com.lavanya.emailBatch.configuration.EmailConfiguration).
	1c-Dans webapplication/src/main/resources : personnaliser le port(doit être différent de celui utilisé en 1b, ici nous avons utilisé le port 8080)


2- Démarrer l'application avec ``mvn spring-boot:run``.

3- Ouvrir l'application sur localhost:8080 et se connecter avec les utilisateurs mentionnés ci-dessus.

## Déploiement

1- Packager l'application avec ``mvn package`` pour chaque projet de l'application (api, webapplication, emailBatch).

2- Exécuter les applications:

	2a- api avec ``java -jar -Dserver.port=9090 api-0.0.1-SNAPSHOT.jar``.
	2b- webapplication avec ``java -jar -Dserver.port=8080 webapplication-0.0.1-SNAPSHOT.jar``.
	2c- emailBatch avec ``java -jar -Dserver.port=8080 emailBatch-0.0.1-SNAPSHOT.jar``.

## Tests d'intégration

Des tests d'intégration sur la partie business de l'application avec springboot test ont permis une couverture de tests de 70%.
