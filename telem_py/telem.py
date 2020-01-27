from abc import ABC, abstractmethod

class TelemetrySource(ABC):
	@abstractmethod # Returns a list of all available telemery keys as strings, or tuple (key, can_set, can_get, )
	def getTelemetryKeys(self, from_client=None):
		pass
	@abstractmethod # Returns the value for the telemrty key, otherwise returns None
	def tryGetTelemetryValue(self, key, from_client=None):
		pass
	@abstractmethod # Returns None on success, otherwise returns an error code
	def trySetTelemetryValue(self, key, val, from_client=None):
		pass
