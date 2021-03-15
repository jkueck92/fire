# fire/dme monitoring system as systemd service

Place the fire.service file at /etc/systemd/system/ and make sure java is installed or symlinked at /usr/bin/java.

The fire application has to be installed in /home/pi/fire/ otherwise change the working directory in the service file.