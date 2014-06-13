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

> Przyjmijmy, że GS_HOME to katalog domowy, w którym będzie zainstalowany
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
Jeśli wszystko przebiegło poprawianie, interfejs webowy powinien być
dostępny pod dwoma adresami, gdyż moduł *feeder* jest skonfigurowany
do pracy w dwóch instancjach:  
 * 127.0.0.1:8080/my-app-feeder/  
 * 127.0.0.1:8081/my-app-feeder/  
6. W celu upewnienia się, że wszystko działa poprawnie należy wpisać
dowolne dane w polach *Name* i *Value*, a następnie odświeżyć stronę
i sprawdzić czy pojawiły się pod *Space Contents*. Możemy także sprawdzić
logi **GSM**, które powinny być podobne do poniższego:  
`2014-06-13 14:02:45,334 my-app-processor [1]
INFO [com.mycompany.app.processor.Processor] -  ------
PROCESSED : id[A0^1402660278329^35] type[0]
rawData[Name: goValue vergo] data[PROCESSED : Name: goValue vergo]
processed[true]`

## Kluczowe elementy rozwiązania XAP ##

1. **XAP** realizuje "chmurę" w sposobie składowania danych. W podstawowej
konfiguracji są one trzymane w pamięci operacyjnej w celu maksymalnej
wydajności. Można oczywiście skonfigurować backend, który będzie persystował
dane. Można to robić w sposób synchroniczny oraz asynchroniczny. Do
"chmury danych" można się podłączyć zdalnie, może ona być rozpięta na wielu
maszynach (tzw. *partitioning*). Daje to dużą elastyczność i pozwala
na składowanie w pamięci operacyjnej większej ilości danych niż jej rozmiar.
2. **XAP** posiada także mechanizmy automatycznego skalowania, które reagują
na różnego rodzaju metryki. Progi, powyżej/poniżej których dany
mechanizm zadziała można określać w trakcie deploy'u aplikacji lub na etapie
tworzenia (pliki *sla.xml*).
3. **XAP** pozwala także na równoważenie obciążenie przez uruchomienie
wielu instancji danej aplikacji, nawet na różnych maszynach. Korzystają
one wtedy z tej samej "chmury danych". W ten sposób możemy dynamicznie
skalować aplikacje.






