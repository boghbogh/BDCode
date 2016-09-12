#!/usr/bin/python

import socket
import time
import random

print "Starting RNG feed"

updateSocket = socket.socket()
host = socket.gethostname()
port = 44444

word_file = "/usr/share/dict/words"
words = open(word_file).read().splitlines()

updateSocket.connect((host, port))

while 1:
	startTime = time.clock()

	updateSocket.send(random.choice(words) + " " + str(random.randrange(100000)) + "\n")

	# Sleep until its time to send the next batch
	time.sleep(0.5)

updateSocket.close()