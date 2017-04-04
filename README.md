# DemoWebApp  ![Travis CI build status](https://travis-ci.org/phackt/webapp.svg?branch=master "Travis CI build status")  [![Coverage Status](https://coveralls.io/repos/github/phackt/DemoWebApp/badge.svg?branch=master)](https://coveralls.io/github/phackt/DemoWebApp?branch=master) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/0805b1bf41734f8c8cb7a3d739b0a429)](https://www.codacy.com/app/gabriel-compan/DemoWebApp?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=phackt/DemoWebApp&amp;utm_campaign=Badge_Grade)

Détails sur le blog: [https://phackt.com/xss-cors-csrf-partie-1-xss](https://phackt.com/xss-cors-csrf-partie-1-xss)  
  
Une appli cas d'école limitant les vulnérabilités Web:
  - XSS
  - CSRF
  - CORS
  - HTTP plain text

Les fonctionnalités:
  - Importer et sauvegarder des fichiers
  - Scanner des sites web autorisant les requêtes CORS (à venir)

Les technologies:
  - Java 8 (testé sous Tomcat 8.0.35, Servlet 3.1)
  - Spring 4 (MVC, Security)
  - Hibernate 4
  - Tiles
  - And much more...

Pour se loguer: user/user

*A venir: développement d'un scanner de sites ayant une configuration CORS permissive.*

Quelques préconisations identifiées pour sécuriser votre web app:

> Il convient de faire le lien entre différentes vulnérabilités comme
> les injections XSS, les requêtes Cross-Site et savoir ce qu'est
> le Cross-Origin Ressource Sharing (souvent nécessaire sur des 
> services distribués).

## XSS
Comment s'en prémunir;
- **defaultHtmlEscape** étant à True par défaut, préférez les Spring tags pour vos outputs (ex: ```<spring:message code="home.label.uploadFiles.springForm.small_func_description" />```)  
- Si JSTL, les caractères sont automatiquement échappés. EL et scriptlet sont cependant vulnérables aux XSS.<br />Autre exemple si PHP, utilisez htmlentities (avec l'option ENT_QUOTES pour échapper le '), ou htmlspecialchars.
- Vous pouvez créer un Filter qui en entrée assainira vos données avec une API comme [JSoup](https://jsoup.org/cookbook/cleaning-html/whitelist-sanitizer) ou [OWASP Html Sanitizer](https://github.com/OWASP/java-html-sanitizer) et qui en sortie échappera explicitement vos données avec [HtmlUtils.htmlEscape](http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/util/HtmlUtils.html#htmlEscape-java.lang.String-) ou [StringEscapeUtils.escapeHtml4](https://commons.apache.org/proper/commons-lang/javadocs/api-release/).
- Vous pouvez également protéger votre application derrière un Web Application Firewall (ex: ModSecurity - http://www.modsecurity.org/)
- N'hésitez pas à éprouver votre application avec un outil tel que [Xenotix](https://www.owasp.org/index.php/OWASP_Xenotix_XSS_Exploit_Framework "https://www.owasp.org/index.php/OWASP_Xenotix_XSS_Exploit_Framework") ou le support [OWASP XSS Filter Evasion Cheat Sheet](https://www.owasp.org/index.php/XSS_Filter_Evasion_Cheat_Sheet "https://www.owasp.org/index.php/XSS_Filter_Evasion_Cheat_Sheet")
- Spring Security inclut par défaut de nombreux headers dans la réponse HTTP;
    - **X-Content-Type-Options**: Stipule de ne pas déviner le MIME-Type si mal renseigné
    - **X-XSS-Protection**: Stipule d'activer le filtre XSS du navigateur
 - Configurer les directives [Content-Security-Policy](https://content-security-policy.com/)  

## Cross Site Request Forgery
Comment s'en prémunir;   
- Evitez de rester enregistré, pensez à vous délogguer
- Utilisez un nonce (unique token) qui n'est pas prédictible par l'assaillant
- Utilisez les headers de sécurité;
    - **Cache-Control**, **Expires**: Spécifie que la ressource ne doit pas être mise en cache
    - **X-Frame-Options**: Empêche la page d'être incluse dans une frame
    - **Content-Security-Policy**: Stipule plusieurs politiques de sécurité sur les requêtes Cross-Origin des éléments HTML (empêche également l'éxécution de scripts inline (XSS))

## Cross Origin Resource Sharing

Les requêtes CORS interviennent lorsque l'hôte cible est différent de l'origine de la ressource initiant la requête. <br />
Ceci ne concernant pas les élements HTML classiques tels que les balises IMG, SCRIPT, STYLE, etc, mais principalement les XMLHttpRequest. Il existe deux types de requêtes CORS, les simples (idempotentes -- GET, POST), et les préflight (PUT, DELETE, ou requêtes GET/POST avec header spécifique (ex Authorization)).

Les requêtes CORS peuvent être autorisées (ou bloquées au niveau du serveur) -- CorsFilter sous Tomcat (> 7).<br />Si notre application WEB ne doit pas partager de ressources à des sites tiers, il convient de les bloquer explicitement (erreur 403 retournée) au lieu de déléguer la sécurité au navigateur.<br />Le navigateur bloquera l'accès à la réponse en l'absence d'un header Access-Control-Allow-Origin, cependant le serveur aura déjà interprété cette requête comme une requête classique et donc valide (code 200 retourné).<br /> *Attention une requête simple CORS POST withCredentials envoie votre cookie dans la requête sur le site tiers*.

## HTTPS
Spring security intégre également par défaut la politique **HTTP Strict Transport Security (header Strict-Transport-Security)**, qui spécifie au navigateur d'interpréter toutes les requêtes over SSL/TLS (redirection interne 307).<br />Il est primordial de chiffrer les échanges avec un certificat signé par une CA (Cache HSTS sous chrome chrome://net-internals/#hsts).

Il est également important de connaitre si les parties tierces utilisées (framework Java, JS, ...) sont exemptes de vulnérabilités.

***Cette application utilise HSTS, vous devez avoir configuré votre serveur pour autoriser les connexion SSL.<br />Un certificat self-signed est présent dans crt/demowebapp.crt. Ceci ne sert que pour une utilisation en local, sinon il convient de créer un certificat signé par une CA.***
  
  
## Réferences
http://docs.spring.io/spring-security/site/docs/current/reference/html/headers.html<br />
http://www.veracode.com/directory/owasp-top-10<br />
https://www.owasp.org/index.php/XSS_(Cross_Site_Scripting)_Prevention_Cheat_Sheet<br />
https://tomcat.apache.org/tomcat-8.0-doc/config/filter.html#CORS_Filter<br />
https://www.w3.org/TR/cors/<br />
http://news.netcraft.com/archives/2016/03/17/95-of-https-servers-vulnerable-to-trivial-mitm-attacks.html<br />
https://geekflare.com/secure-cookie-flag-in-tomcat/  
https://www.owasp.org/index.php/Category:OWASP_Application_Security_Verification_Standard_Project  
