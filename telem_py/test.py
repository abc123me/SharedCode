#!/usr/bin/python3

from server import *
from time import sleep
from sys import argv

class DooDad(TelemetrySource, TelemetryServerListener):
	def __init__(self):
		self._str = "Hello world!"
	def getTelemetryKeys(self, from_client=None):
		return ['str', 'test1', 'test 1']
	def tryGetTelemetryValue(self, key, from_client=None):
		if key == 'str': return self._str
		elif key == 'test1': return "Test"
		elif key == 'test 1': return "Test123"
		else: return None
	def trySetTelemetryValue(self, key, val, from_client=None):
		if key == 'str': self._str = val
		elif key == 'test 1': return "Unsettable!"
		else: return "Unknown key!"
		return None
	def client_connect(self, client):
		print("Client connected from %s" % str(client.address))
	def client_disconnect(self, client):
		print("Client disconnected from %s" % str(client.address))
	def server_started(self, serv, sock):
		print("Server started on %s!" % str(sock.getsockname()))
	def server_stopped(self):
		print("Server stopped!")

src = DooDad()
serv = TelemetryServer(src, listener=src)
port_num = 1234
if len(argv) > 1:
    port_num = int(argv[1])
serv.start_server(port_num)

print("Server running, please hit Ctrl+C to stop!")
while True:
	try:
		sleep(0.1)
	except KeyboardInterrupt:
		break
print("Stopping server!")
serv.stop_server()
print("Bye!")
