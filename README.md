Portowanie aplikacji webowej w chmurze
============================

GigaSpaces XAP
--------------

[Plan](#plan)

[Charakterystyka XAP](#charakterystyka-xap)

[Wrażenia z QuickStart](#quickstart)

[Deploy aplikacji webowej](#deploy-aplikacji-webowej)

[Deploy przykładowej aplikacji webowej](#deploy-przykladowej-aplikacji-webowe)

### Plan ###

* Konfiguracja space'a tak żeby korzystał z Hibernate'a
([tutorial](http://docs.gigaspaces.com/xap97/hibernate-space-persistency.html))
* Znalezienie przykładowej aplikacji webowej, którą odpalimy na XAP
i AWS.

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

Architektura XAP:

![alt text](http://d3a0pn6rx5g9yg.cloudfront.net/sites/default/files/xap_summer/big_big_image.png)

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

Operate na [tutorialu](http://docs.gigaspaces.com/xap97/step-1---deploying-your-web-application-to-the-gigaspaces-environment.html#DeployDirections)

1. Odpalamy GS agent’a, który startuje nam GSM (Gigaspaces Service
Manger) i 2x GSC (Gigaspace Service Container)
1. Deploy’u można dokonać na trzy sposoby:
  1. UI - mamy do tego dostępny wizzard
  1. Skrypt dostarczony przez gigaspaces - podajemy ścieżkę do
  aplikacji jako argument wywołania
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

### Deploy przykładowej aplikacji webowej ###

> Poniższa instrukcja została napisana pod systemy z rodziny Linux

> Dla potrzeb przykładu przyjmijmy że XAP_ROOT to katalog, w którym
mamy zainstalowaną chmurę XAP

1. Instalacja chmury XAP  
Pobieramy [XAP LITE EDITION](http://www.gigaspaces.com/xap-download).
Strona pozwala także wypełnić formularz, po którego wypełnieniu otrzymamy
licencję pocztą elektroniczną.

1. Konfiguracja przykładowej aplikacji  
Przykładowa aplikacja, którą będziemy chcieli uruchomić znajduje się
w lokalizacji: `$XAP_ROOT/examples/web/space-access`.
Załóżmy, że chcemy żeby uruchomiła się ona w dwóch
instancjach: po jednej na każdy kontener. W tym celu ustawiamy
odpowiednią linię w pliku
`space-access/WebContent/META-INF/spring/sla.xml`:  
 `<os-sla:sla number-of-instances="2" max-instances-per-vm="1"/>`
2. Budowanie przykładowej aplikacji webowej  
W głównym katalogu aplikacji wydajemy polecenie:  
`./build.sh dist`
Powyższa komenda zbuduje nam plik war, który będziemy następnie deploy'ować.
3. Uruchomienie GSM (Giga Space Manager)  
GSMktóry zarządza kontenerami aplikacyjnymi w ramach chmury.
Domyślnie tworzy on dwa takie kontenery (Gigaspace Service Container).
GSM uruchamiamy poleceniem:  
`$XAP_ROOT/bin/gs-agent.sh`
4. Uruchomienie space'a w ramach kontenera  
Nasza aplikacja potrzebuje space'a o nazwie `mySpace`, w którym
przechowuje dane.  W celu uruchomienia 1 space'a, który ma 1 backup
wydajemy polecenie:  
`$XAP_ROOT/bin/gs.sh deploy-space -cluster total_members=1,1 mySpace`
5. Uruchomienia przykładowej aplikacji  
Teraz robimy deploy naszej przykładowej aplikacji:  
`./bin/gs.sh deploy examples/web/space-access-raw/SpaceAccess.war`
W żargonie XAP każda instancja naszej aplikacji nazywa się
*Processing Unit*.
6. Sprawdzenie działania instancji aplikacji  
Domyślnie, pierwsza instancja aplikacji uruchamia się na porcie 8080,
natomiast kolejna instancja na kolejnym porcie: 8081. Możemy teraz
sprawdzić czy wszystko działa poprawnie odwiedzając URL'e:
 * http://127.0.0.1:8080/SpaceAccess/
 * http://127.0.0.1:8081/SpaceAccess/  
Dodając obiekt w ramach jednej instancji jest on też widoczny w drugiej.
7. Obserwacja statystyk aplikacji w interfejsie graficznym  
XAP dostarcza też interfejs graficzny, który pozwala podejrzeć
jakie aplikacje są uruchomione na platformie wraz z różnymi statystykami
wykorzystania zasobów. W celu uruchomienia GUI wydajemy polecenie:  
`$XAP_ROOT/gs-ui.sh`  
Istnieje także interfejs webowy. Uruchamia się go poleceniem:  
`$XAP_ROOT/gs-webui.sh`
8. Wyłączenie aplikacji  
Aby wyłączyć aplikację należy wydać polecenie:  
`./bin/gs.sh undeploy SpaceAcess`





