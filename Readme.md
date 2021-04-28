# Fire / dme monitoring system

### Einstellungen
* alertMessageDateTimeFormat
* telegramBotId
* isTelegramEnabled
* isDatabaseEnabled
* isDiveraEnabled
* diveraApiUrl
* diveraAccessKey
* comPort

### Komponenten
Die folgenden Komponenten sind aktuell verfügbar.

* Datenbank
* Telegram
* Divera
* Serial

#### Datenbank
Die Datenbankkomponente speichert alle eingehenden Alarmierungen automatisch in der Datenbank. Die Alarmierung wird als 
kompletter Text übernommen. zusätzlich wird noch die Uhrzeit und das Datum der Alarmierung erfasst. Die Komponente
kann über die Einstellung *isDatabaseEnabled* aktiviert oder dektiviert werden.

#### Telegram
Die Telegramkomponente kann über einen definierten Bot Nachrichten an verschiedene Chats verschicken. Über die
Einstellung *telegramBotId* muss die von Telegram zur Verfügung gestellte id des Bots hinterlegt werden. In der 
Datenbank werden Chats gespeichert die bei einer Alarmierung ausgelesen werden. Für die Chats muss eine *ChatId*,
eine *Message* und ob der Chat aktiviert oder deaktiviert ist hinterlegt werden. Die Message kann verschiedene 
Platzhalter enthalten die bei der Versendung dann mit den Daten der Alarmierung gefüllt werden. An deaktivierte 
Chats werden keine Nachrichten weitergeleitet. Die Komponente an sich kann über die Einstellung *isTelegramEnabled*
aktiviert oder deaktiviert werden.

Folgende Platzhalter stehen zur Verfügung:

* {city}
* {street}
* {object}
* {category}
* {keyword}
* {keywordText}
* {remark1}
* {remark2}
* {timestamp}

#### Divera
Über die Diverakomponente können Nachrichten an Divera geschickt werden. Dazu muss die Einstellung *diveraApiUrl* mit
der auf die von Divera zur Verfügung gestellten Api zeigen. Die Einstellung *diveraAccessKey* muss den von Divera
zur Verfügung gestellten Accesskey enthalten über den die Api angesprochen werden kann. Die gesamte Komponente kann
über die Einstellung *isDiveraEnabled* aktivert oder deaktiviert werden.

Die Felder die der Api übergeben werden sind zur zeit noch statisch. Im Feld *title* wird der die Kategorie und 
das Stichwort übergeben. Als Beispiel: H021. Das Feld *priority* wird immer als true übergeben. Das Feld *address* 
wird mit der Straße, falls vorhanden dem Objekt und danach dem Ort übergeben. Beispiel: Mustermannstraße 1,
Supermarkt, Superstadt. Das Feld *text* wird mit den zur Verfügung stehenden Informationen aus der Alarmierung 
gefüllt. Konkret wird hier dar Stichworttext, das Remark1 und Remark2 gestzt. Beispiel: Ölspur - bedeckt die Straße -
großflächig im Kreuzungsbereich. Falls kein Stichworttext vorhanden ist wird dafür der Platzhalter: Keine 
Stichwortbeschreibung vorhanden ausgegeben. Falls die Remarks nicht gefüllt sind werden diese einfach weggelassen.

#### Serial
Die Serialkomponente hört auf der SerialPort des Systems. Falls dort eine Alarmierung eingeht wird ein Event getriggert
das die restlichen Komponenten benachrichtigt. Für die Komponente muss die Einstellung *comPort* gesetzt werden.
Hier muss der SerialPort eingetragen werden über den der Melder angeschlossen ist.

### Web-Schnittstelle
Das System stellt eine Rest-Api zur Verfügung über die es möglich ist bestimmte Daten aus dem System zu ändern und 
auszulesen. 

#### Alarmierungen
Über die Alarmierungs-Api ist es möglich die gespeicherten Alarmierungen auszulesen. Dafür werden zwei Schnittstellen
bereitgestellt.

* GET /api/alerts
* GET /api/alerts/{id}

#### Chats
Die Chat-Api stellt Crud-Operationen zur Verfügung.

* GET /api/chats
* GET /api/chats/{id}
* PUT /api/chats - Muss ein Json-Body übergeben bekommen mit den Feldern *id*, *chatId*, *isEnabled*, *message*
* POST /api/chats - Muss ein Json-Body übergeben bekommen mit den Feldern *chatId*, *isEnabled*, *message*

#### Einstellungen
Die Einstellungs-Api stellt lese und update Funktionen bereit.

* GET /api/settings
* GET /api/settings/{id}
* PUT /api/settings/{id} - Hier muss als URL-Parameter der neue Wert übergeben werden
