import socket
import sys
import time
from thread import *
import car

def stop():
    print "stop"
    car.stop()

def forward(on):
    print "forward: %s" % on
    if on:
        car.forward()
    else:
        car.stop()

def back(on):
    print "back: %s" % on
    if on:
        car.back()
    else:
        car.stop()

def left(on):
    print "left: %s" % on
    if on:
        car.left()
    else:
        car.straight()

def right(on):
    print "right: %s" % on
    if on:
        car.right()
    else:
        car.straight()

# Set mapping between socket command name and function name
armoptions = {'FF' : forward,
              'BB' : back,
              'LL' : left,
              'RR' : right}


HOST = '' # Symbolic name meaning all available interfaces
PORT = 5000 # Arbitrary non-privileged port

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

# Allow socket reuse
s.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)

try:
    s.bind((HOST,PORT))
except socket.error , msg:
    print 'Bind failed. Error Code : ' + str(msg[0]) + ' Message ' + msg[1]
    sys.exit()

s.listen(10)

def clientthread(conn):
    # Keep running until quit command executed
    while 1:
        # Get the data
        data = conn.recv(1024)
	print data
        if not data:
	    print data
            print "Socket closed"
            break

        # Strip off any whitespace if present
        data = ''.join(data.split())

        # Loop through the recieved buffer a character at a time building up commands
        i = 0;
        cmd = ""
        while i < len(data):
            # Look for the special case of a stop
            if data[i] == '.':
                stop()
                i += 1
                continue

            # Build up command
            cmd += data[i]

            # If enough characters for a command has been found then interpret and call function
            if len(cmd) == 2:
                param = True
                if len(data) > i+1:
                    # on/off
                    if data[i+1] == '+':
                        param = True
                        i += 1
                    elif data[i+1] == '-':
                        param = False
                        i += 1
                    elif data[i+1].isdigit():
                        param = data[i+1]
                        i += 1

                # Get option and call function
                try:
                    armoptions[cmd](param)
                except KeyError:
                    print "Bad command: " + cmd

                # Clear command once issued
                cmd = ""

            # Move onto next character
            i += 1


##
## Main starting piont of server
## 
running = True
try:
    while running:
        print "Waiting ..."
        # Wait to accept a connection from client control app
        conn, addr = s.accept()
        print 'Connected with ' + addr[0] + ':' + str(addr[1])

        # Start listening to new socket on seperate thread
        start_new_thread(clientthread ,(conn,))
  
finally:
    print "Exiting"
    try:
        car.stop()  # Stop the car if we get here in error
    except:
        pass
    s.close()
    time.sleep(0.1) # wait a little for threads to finish
    car.cleanup()

