#!/usr/bin/python

import socket
import time

UPDATES_PER_SECOND = 42

print "Starting device status feed"

def readLines():
	lineArray = []

	for x in range(0, UPDATES_PER_SECOND):
		line = phonestatusfile.readline()

		if not line: break

		lineArray.append(line)

	return lineArray

phonestatusfile = open("/home/training/training_materials/bigdata/data/devicestatus.txt", "r")

updateSocket = socket.socket()
host = socket.gethostname()
port = 44444

updateSocket.connect((host, port))

while 1:
	startTime = time.clock()

	lines = readLines()

	if len(lines) != UPDATES_PER_SECOND:
		print "Input file exhausted. Exiting."
		break

	for line in lines:
		updateSocket.send(line)

	# Sleep until its time to send the next batch
	time.sleep(1 - (time.clock() - startTime))

updateSocket.close()
phonestatusfile.close()
