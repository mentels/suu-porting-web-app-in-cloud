Portowanie aplikacji webowej w chmurze
============================

GigaSpaces XAP
--------------

[Charakterystyka XAP](#charakterystyka-xap)

[Wrażenia z QuickStart](#quickstart)

[Deploy aplikacji webowej](#deploy-aplikacji-webowej)


### Charakterystyka XAP ###

* In-memory computing software platform
* XAP pozwala na uruchomienie aplikacji tak żeby dzlałała na jednej
platformie wraz ze wszystkimi potrzebnymi zależnościami
* Szybki dostęp do danych, które są trzymane bezpośrednio w pamięci
* Zapewnia wysoką dostępność poprzez automatyczny backup danych
* Może skalować się automatycznie lub na żądanie
* W ramach kontera XAP zarządzamy jednostkami, które nazywają się
*processing units*. Taka jednostka sams w sobie zawierają juz wszystko
co jest potrzebne do działania. Może ona się automatycznie dostosowywać
do aktualnego obciążenia.

### QuickStart ###

Na tym etapie udało się skonfigurować środowisko developerskie, postawić
GigaSpaces XAP na lokalnym komputerze oraz podłączyć się do instancji.
Następnie udało się przetestować funkjonalność persystencji przez
składowanie prosych obiektów POJO.

#### Zalety ####

* Strona pobierania prowadzi bezpośrednio do tutoriala
* Można wybrać 5min guide lub full tutorial
* Jest opis konfiguracji środowiska

#### Wady ####

* Brakuje opisu konfiguracji środowiska w 5min StartGuide. Trzeba sobie
to potem znaleźć
* Brak informacji o tym, że trzeba licencję wrzucić
* Był problem z ustawienie CP do 5min StartGuide.

### Deploy aplikacji webowej ###

Operate [tutorialu](http://docs.gigaspaces.com/xap97/step-1---deploying-your-web-application-to-the-gigaspaces-environment.html#DeployDirections)

1. Odpalamy GS agent’a, który startuje nam GSM (Gigaspaces Service
Manger) i 2x GSC (Gigaspace Service Container)
1. Deploy’u można dokonać na trzy sposoby: (Docs/deploying)
1. UI - mamy do tego dostępny wizzard
  1. Skrypt dostarczony przez gigaspaces
  1. Przez skrypt, który dostarczamy z aplikacją - w pliku build.xml
  po prostu dowołujemy się do odpowiedniego API dostarczonego przez XAP’a
1. Defaultowo GigaSpaces używa Jetty Web Container - taki kontener
jest startowany w momencie deploy’u aplikacji i jest on przypisywany
do GSC
1. Ustawienia propertisów applikacji można też na 3 sposoby j/w (port,
dostępna ilość wątków można ustawić w pliku: META-INF/spring/pu.properties)
1. Ustalanie SLA dla aplikacji
  1. Zapewnienie, że aplikacja działa poprawnie (liczba instancji,
  czy osobna JVM, czy osobna maszyna; jeżeli pada jakaś VMka z naszą
  instancją to zostanie ona automatycznie przeniesiona na nową)
  1. automatyczna skalowalność (ustawiamy progi dla np. wykorzystania CPU,
  ilość requestów na sekundę itp - skaluje się automatycznie w górę
  lub w dół)
  1. Ustawienia SLA dokonujemy w pliku META-INF/spring/sla.xml
1. Kiedy mamy dwie instancje aplikacji to działają one na różnych
portach. Żeby uruchomić load balancing trzeba postawić na froncie np.
Apache HTTP Sever. Gigaspacs ma natomiast agent’a, który monitoruje
ilość instancji naszej aplikacji i odpowienio uaktualnia konfig
Apache’a i prosi o reload jeśli jest potrzeba.






