KEY_REQUEST =	b'keys'
GET_REQUEST =	b'get'
SET_REQUEST =	b'set'
EXIT_REQUEST =	b'exit'
NEWLINE =		b'\n'
FAIL_RESP =		b'NAK' + NEWLINE
OK_RESP =		b'ACK' + NEWLINE
SEPERATOR = 	b' '
ESCAPE =		b'\\'
NEWLINE_ESC =	b'n'
SEPERATOR_ESC = b's'
FAIL_UNK_CMD_RESP		= b'Unknown command!' + NEWLINE
FAIL_NO_KEY_EXIST_RESP  = b'Key does not exist!' + NEWLINE
FAIL_NO_KEY_SPECD_RESP  = b'Key was not specified!' + NEWLINE
FAIL_NO_VAL_RESP		= b'No value for key!' + NEWLINE
FAIL_INVALID_ESCAPE_SEQ = b'Invalid escape sequence!' + NEWLINE
FAIL_UNSETTABLE         = "Key is unsettable"

def encode_str(s):
	if type(s) == type('str'):
		s = s.encode('utf-8')
	out = b''
	for c in s:
		c = bytes([c])
		if c == NEWLINE:
			out += ESCAPE + NEWLINE_ESC
		elif c == SEPERATOR:
			out += ESCAPE + SEPERATOR_ESC
		elif c == ESCAPE:
			out += ESCAPE + ESCAPE
		else:
			out += c
	return out
def decode_str(s):
	if type(s) == type('str'):
		s = s.encode('utf-8')
	out = b''
	skip = False
	for i in range(0, len(s)):
		if skip:
			skip = False
			continue
		c = bytes([s[i]])
		if c == ESCAPE:
			n = bytes([s[i + 1]]) if i < len(s) - 1 else None
			if n == NEWLINE_ESC:
				out += NEWLINE
			elif n == SEPERATOR_ESC:
				out += SEPERATOR
			elif n == ESCAPE:
				out += ESCAPE
			else:
				raise ValueError(FAIL_INVALID_ESCAPE_SEQ)
			skip = True
		else:
			out += c
	return out
def frame(parts):
	out = b''
	for part in parts:
		out += encode_str(part) + SEPERATOR
	return out[0:-len(SEPERATOR)] + NEWLINE
def unframe(raw):
	raw = raw.strip()
	dat = raw.split(SEPERATOR)
	for i in range(0, len(dat)):
		dat[i] = decode_str(dat[i])
	return dat
def ack(dat=None, nl=False):
	return OK_RESP + (b'' if dat == None else dat) + (NEWLINE if nl else b'')
def nak(rsn=None):
	return FAIL_RESP + (b'' if rsn == None else rsn)
