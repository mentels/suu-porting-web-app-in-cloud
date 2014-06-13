# Gigaspaces XAP #

## Opis wytworzonej aplikacji ##

W wyniku prac projektowych z produktem **Gigaspaces XAP** została wytworzona
przykładowa [aplikacja](/sample-xap-webapp). Korzysta ona z rozwiązania
składowania danych w chmurze oferowanego przez testowany produkt. W jej
skład wchodzą trzy moduły zgodnie z maven'em:
* *feeder* - moduł oferujący interfejs webowy, przez który można wprowadzać
dane do systemu
* *processor* - moduł symulujący przetwarzanie danych
* *common* - modułu wykorzystywany przez pozostałe dwa.

*feeder* oraz *processor* to tzw. **Processing Units**. Współpracują
one z tzw. **Space'em**, w którym składowane są dane.

Poniższy schemat przedstawia architekturę zaimplementowanego przykładu:

![alt text](/xap_sample.png)

## Depoly wytworzonej aplikacji ##

> Przyjmijmy, żę GS_HOME to katalog domowy, w którym będzie zainstalowany
**XAP**.

1. W pierwszym kroku musimy zainstalować
**[XAP LITE EDITION](http://www.gigaspaces.com/xap-download)** oraz
pobrać licencję wg instrukcji na stronie.
2. Następnie musimy uruchomić **Grid Service Manager**, który jest
głównym kontenerem do uruchamiania aplikacji:  
`GS_HOME/bin/gs-agent.sh`.
3. W kolejnym kroku należy zbudować przykładową aplikację. Należy zacząć
od sklonowania tego repozytorium:  
`git clone https://github.com/mentels/suu-porting-web-app-in-cloud.git`  
Następnie przechodzimy do katalogu `sample-xap-webapp` i wydajemy
polecenie:  
`mvn package -DskipTests`
Powyższa komenda zbuduje nam 2 artefakty:  
 * `sample-xap-webapp/processor/target/my-app-processor.jar`
 * `sample-xap-webapp/feeder/target/my-app-feeder.war`
4. Kiedy mamy już wszystko zbudowane można przejść do deploy. Zaczynamy
od uruchomienia aplikacji *processor*, która jednocześnie tworzy
*space'a* i czeka na dane do przetworzenia:  
`GS_HOME/bin/gs.sh deploy sample-xap-webapp/processor/target/my-app-processor.jar`
5. Żeby mieć w pełni funkcjonalny system, uruchamiamy teraz aplikację
webową:  
`GS_HOME/bin/gs.sh deploy sample-xap-webapp/feeder/target/my-app-feeder.war`
Jeśli wszystko przebiegło poprawanie, interfejs webowy powninien być
dostępny pod dwoma adresami, gdyż moduł *feeder* jest skonfiurowany
do pracy w dwóch instancjach:  
 * 127.0.0.1:8080/my-app-feeder/  
 * 127.0.0.1:8081/my-app-feeder/  
6. W celu upewnienia się, że wszystko działa poprawnie należy wpisać
dowolne dane w polach *Name* i *Value* a następnie odświeżyć stronę
i sprawdzić czy pojawiły się pod *Space Contents*. Możemy także sprawdzić
logi **GSM**, które powinny być podobne do poniższego:
`2014-06-13 14:02:45,334 my-app-processor [1]
INFO [com.mycompany.app.processor.Processor] -  ------
PROCESSED : id[A0^1402660278329^35] type[0]
rawData[Name: goValue vergo] data[PROCESSED : Name: goValue vergo]
processed[true]`






