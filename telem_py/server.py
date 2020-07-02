from socket import *
from telem_net import *
from telem import TelemetrySource
from threading import Thread
from abc import ABC, abstractmethod
from time import sleep

class TelemetryServerListener(ABC):
	@abstractmethod
	def client_connect(self, client):
		pass
	@abstractmethod
	def client_disconnect(self, client):
		pass
	@abstractmethod
	def server_started(self, serv, sock):
		pass
	@abstractmethod
	def server_stopped(self):
		pass
    
class Client:
	def __init__(self, ctple):
		self.sock = ctple[0]
		self.address = ctple[1]
		self.id = None

class TelemetryServer:
	def __init__(self, src, listener=None, encoding='utf-8'):
		if not isinstance(src, TelemetrySource):
			raise "__init__ requires a TelemetrySource"
		if listener != None and (not isinstance(listener, TelemetryServerListener)):
			raise "listener must be a TelemetryServerListener"
		self._sthr_interrupt = False
		self._src = src
		self._listener = listener
		self._running = False
		self._encoding = encoding
	def start_server(self, port, addr='0.0.0.0'):
		if self._running:
			raise "Server already running!"
		self.sock = socket(AF_INET, SOCK_STREAM)
		self.sock.bind((addr, port))
		self._clients = []
		self._sthr_interrupt = False
		self._sthr = Thread(name="Telemetry server thread", target=self._server_thread, args=())
		self._sthr.start()
	def stop_server(self):
		self._sthr_interrupt = True
		self._sthr.join()
		self.sock.close()
		self.sock = None
	def _server_thread(self):
		listener = self._listener
		if listener != None:
			listener.server_started(self, self.sock)
		self.sock.listen(3)
		self.sock.setblocking(False)
		self._running = True
		while not self._sthr_interrupt:
			try:
				client = Client(self.sock.accept())
				client.sock.setblocking(0)
				self._clients.append(client)
				if listener != None:
					listener.client_connect(client)
			except BlockingIOError: pass
			to_remove = []
			for client in self._clients:
				dat = None
				try: dat = client.sock.recv(1024)
				except BlockingIOError: continue
				except OSError: dat = []
				if len(dat) > 0:
					try:
						self._handle_client_msg(client, dat)
					except ValueError as e:
						client.sock.send(nak(e.args[0]))
						client.sock.close()
				else: to_remove.append(client)
			for client in to_remove:
				if listener != None:
					listener.client_disconnect(client)
				client.sock.close()
				self._clients.remove(client)
			sleep(0.01 if len(self._clients) > 0 else 0.1)
		self.sock.close()
		self._running = False
		if listener != None:
			listener.server_stopped()
	def _handle_client_msg(self, client, dat):
		dat = unframe(dat)
		hdr = dat[0]
		if len(dat) <= 0: return
		csock = client.sock
		if hdr == KEY_REQUEST:
			keys = self._src.getTelemetryKeys(from_client=client)
			csock.send(ack(frame(keys)))
		elif hdr == GET_REQUEST:
			if len(dat) < 2:
				csock.send(nak(FAIL_NO_KEY_SPEC_RESP))
				return
			val = self._src.tryGetTelemetryValue(dat[1].decode('utf-8'), from_client=client)
			if val != None: csock.send(ack(encode_str(str(val)), nl=True))
			else: csock.send(nak(FAIL_NO_KEY_SPECD_RESP))
		elif hdr == SET_REQUEST:
			if len(dat) < 2:
				csock.send(nak(FAIL_NO_KEY_SPECD_RESP))
				return
			if len(dat) < 3:
				dat.append(None)
			key = dat[1].decode('utf-8')
			val = None if dat[2] == None else dat[2].decode('utf-8')
			res = self._src.trySetTelemetryValue(key, val, from_client=client)
			if res != None: csock.send(nak(encode_str(str(res))))
			else: csock.send(ack())
		elif hdr == EXIT_REQUEST:
			csock.send(ack())
			csock.close()
			return
		else: csock.send(nak(FAIL_UNK_CMD_RESP))
	def print_clients(self):
		print("Clients: ")
		for c in self._clients:
			print(c[1])


