## Reactive Dashboard API
Dieses Repository beinhaltet das beispielhafte WebAPI-Projekt für meinen gemeinsamen [Jaxenter.de Artikel 11/2017](https://jaxenter.de/) mit [Manfred Steyer](https://www.softwarearchitekt.at/) und zeigt, wie mittels [Spring 5](https://docs.spring.io/spring-framework/docs/5.0.0.RELEASE/spring-framework-reference/web-reactive.html) bzw. [Spring Boot 2.0](https://docs.spring.io/spring-boot/docs/2.0.0.M5/reference/htmlsingle/) ein simples reaktives Backend implementiert werden kann, welches ereignisbasierte Daten mittels SSE an eine Browser-Anwendung sendet, um ein Dashboard in Echtzeit zu aktualisieren.

#### Teil 1
Der Einfachheit halber wird im 1. Teil dieses Artikels auf eine vollständige Persistenzschicht verzichtet. Stattdessen werden die benötigten Entitäten lediglich mit Maps im Hauptspeicher verwaltet. Weiters beschränkt sich die erste Version des WebAPI auf einen traditionellen *@RestController*, der mit gewöhnlichen Annotationen versehen ist. Die vollständige Implementierung steht unter dem *git tag* **article-part-1** bereit. 

#### Teil 2
Im zweiten Teil der Artikelserie wird die Implementierung um eine reaktive Persistenzschicht erweitert. Diese verwendet [Spring Data MongoDB](https://docs.spring.io/spring-data/mongodb/docs/2.0.0.RELEASE/reference/html/) und erlaubt auf Basis von [MongoDB](https://www.mongodb.com/) sowie dem reaktiven Datenbanktreiber einen nicht-blockierenden sowie asynchronen Datenbankzugriff. Ebenso wird in Teil zwei der *@RestController* durch geeignete Router- sowie Handler-Funktionen ersetzt, welche sich mittels DSL und funktionalen Sprachkonstrukten beschreiben lassen. Die vollständige Implementierung steht unter dem *git tag* **article-part-2** bereit. 

#### Angular SPA als Client
Eine dazugehörige Client Implementierung, welche dieses WebAPI konsumiert, findet sich in Form einer Angular SPA in folgendem [GitHub Repository](https://github.com/manfredsteyer/angular-reactive-sample).

#### Cloud VM für Demozwecke
Zum Experimentieren steht eine in Microsoft Azure gehostete Version der Dashboard API bereit, welche testweise statt einer lokalen Instanz verwendet werden kann. Diese VM läuft von 01.11.-31.12.2017. Die Dashboard API *BASE URL* lautet http://hpgrahsl.northeurope.cloudapp.azure.com:8080/dashboard/api/
