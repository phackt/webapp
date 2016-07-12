# OneWebApp

Une appli cas d'école limitant les vulnérabilités Web:
  - XSS
  - CSRF
  - CORS
  - HTTP plain text

Les fonctionnalités:
  - Importer et sauvegarder des fichiers
  - Scanner des sites web autorisant les requêtes CORS

Les technologies:
  - Java 8 (testé sous Tomcat 8.0.35, Servlet 3.1)
  - Spring 4 (MVC, Security)
  - Hibernate 4
  - Tiles
  - And much more...

Quelques préconisations identifiées pour sécuriser votre web app:

> Il convient de faire le lien entre différentes vulnérabilités comme
> les injections XSS, les requêtes Cross-Site et savoir ce qu'est
> le Cross-Origin Ressource Sharing (souvent nécessaire sur des 
> services distribués).

### XSS
Comment s'en prémunir;
- Double backquote sur les valeurs HTML (class='${className}' est vulnérable, même si les html special caractères sont échappés)
- Echapper donc coté serveur les caractères HTML (tester les fonctions de fraweworks de rendu que vous utilisez). <br />Si JSTL, les caractères sont automatiquement échappés. EL (scriptlet) est cependant vulnérable aux XSS.<br />Autre exemple si PHP, utilisez htmlentities (avec l'option ENT_QUOTES), ou htmlspecialchars.
- Vous pouvez également protéger votre application derrière un Web Application Firewall (ex: ModSecurity)
- Spring Security inclut par défaut de nombreux headers dans la réponse HTTP;
    - **X-Content-Type-Options**: Stipule de ne pas déviner le MIME-Type si mal renseigné
    - **X-XSS-Protection**: Stipule d'activer le filtre XSS du navigateur

### Cross Site Request Forgery
Comment s'en prémunir;   
- Evitez de rester enregistré, pensez à vous délogguer
- Utilisez un nonce (unique token) qui n'est pas prédictible par l'assaillant
- Utilisez les headers de sécurité;
    - **Cache-Control**, **Expires**: Spécifie que la ressource ne doit pas être mise en cache
    - **X-Frame-Options**: Empêche la page d'être incluse dans une frame
    - **Content-Security-Policy**: Stipule plusieurs politiques de sécurité sur les requêtes Cross-Origin des éléments HTML (empêche également l'éxécution de scripts inline (XSS))

### Cross Origin Resource Sharing

Les requêtes CORS interviennent lorsque l'hôte cible est différent de l'origine de la ressource initiant la requête. <br />
Ceci ne concernant pas les élements HTML classiques tels que les balises IMG, SCRIPT, STYLE, etc, mais principalement les XMLHttpRequest.Il existe deux types de requêtes CORS, les simples (idempotentes -- GET, POST), et les préflight (PUT, DELETE, ou requêtes GET/POST avec header spécifique (ex Authorization)).

Les requêtes CORS peuvent être autorisées (ou bloquées au niveau du serveur) -- CorsFilter sous Tomcat (> 7).<br />Si notre application WEB ne doit pas partager de ressources à des sites tiers, il convient de les bloquer explicitement (erreur 403 retournée) au lieu de déléguer la sécurité au navigateur.<br />Le navigateur bloquera l'accès à la réponse en l'absence d'un header Access-Control-Allow-Origin, cependant le serveur aura interprété la requête comme une requête classique (code 200 retourné).<br /> *Attention une requête simple CORS POST withCredentials envoie votre cookie dans la requête sur le site tiers*.

### HTTPS
Spring security intégre également par défaut la politique HTTP Strict Transport Security (header Strict-Transport-Security), qui spécifie au navigateur d'interpréter toutes les requêtes over SSL.<br />Il est primordial de crypter les échanges avec un certificat signé par une CA.

Il est également important de connaitre si les parties tiers utilisées (framework Java, JS, ...) sont exemptes de vulnérabilités.

***Cette application utilise HSTS, vous devez avoir configuré votre serveur pour autoriser les connexion SSL.<br />Un certificat self-signed est présent dans crt/onewebapp.crt. Ceci ne sert que pour une utilisation en local, sinon il convient de créer un certificat signé par une CA***

### Réferences
http://docs.spring.io/spring-security/site/docs/current/reference/html/headers.html<br />
http://www.veracode.com/directory/owasp-top-10<br />
https://www.owasp.org/index.php/Main_Page<br />
https://tomcat.apache.org/tomcat-8.0-doc/config/filter.html#CORS_Filter<br />
https://www.w3.org/TR/cors/<br />