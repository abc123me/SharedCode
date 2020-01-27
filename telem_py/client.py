from socket import *
from telem_net import *
from telem import TelemetrySource
from threading import Thread
from abc import ABC, abstractmethod
from time import sleep

class TelemetryClient(TelemetrySource):
	def __init__(self, buflen=1024):
		self._sock = None
		self._buflen = buflen
	def __len__(self):
		return len(self.getTelemetryKeys())
	def __getitem__(self, key):
		return self.tryGetTelemetryValue(key)
	def __setitem__(self, key, val):
		return self.trySetTelemetryValue(key, val)
		
	def connect(self, addr, fam=AF_INET):
		if self._sock == None:
			self._sock = socket(family=fam)
			self._sock.connect(addr)
		else:
			self.disconnect()
			self.connect(addr, fam)
	def disconnect(self):
		if self._sock != None:
			self._sock.send(TelemetryNet.EXIT_REQUEST)
			self._sock.close()
		self._sock = None
	def _recv(self):
		buf = self._sock.recv(self._buflen)
		
		return _bytes_to_str(buf)
	def getTelemetryKeys(self):
		self._sock.send(TelemetryNet.KEY_REQUEST)
		key_str = self._recv(self._buflen)
		keys = key_str.lstrip(b'[').rstrip(b']').split(b',')
		for i in range(0, len(keys)):
			keys[i] = keys[i].decode('utf-8')
		return keys
	def tryGetTelemetryValue(self, key):
		pass
	def trySetTelemetryValue(self, key, val):
		pass
	def keys(self):
		return self.getTelemetryKeys();
