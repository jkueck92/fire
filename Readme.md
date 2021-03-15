# fire / dme monitoring system

This system listens to an external serial device (the alerting receiver).
If an alert event is received different actions, like sending a telegram message or
call an api can be executed.

The system is designed to be run on a Raspberry Pi 3 and upwards in the user context of pi.

### Requirements (tested with)
- Raspberry Pi OS (Lite)
- OpenJDK 11

### configuration

In order to use the software you have to place a file called _application.properties_ in the /home/pi/fire/config/ directory.

Following properties are used:

- botToken= _TELEGRAM BOT TOKEN_
- chatIds= _TELEGRAM CHAT ID's_
- messageFormat=city,street
- diveraToken= _DIVERA API TOKEN_
- comPort=ttyUSB0 _USB SERIAL DEVICE (DME)_