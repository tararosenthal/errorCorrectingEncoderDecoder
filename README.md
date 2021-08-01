# errorCorrectingEncoderDecoder
## Using bitwise operations and Hamming code, encodes and decodes data, correcting any errors.

### Program operates in three modes specified by user input:
- Encode: reads send file, encodes data using a shortened Hamming code, and saves to encoded file
- Send: reads encoded file, introduces error in one bit per byte, and saves to received file
- Decode: reads received file, decodes data while correcting all errors, and saves to decoded file

### Shortened Hamming code is formated as follows: P1 P2 D P4 D D D 0
- Each value corresponds to a bit, ignoring spaces.
- P1 is parity for every other value.
- P2 is parity for every other set of two values.
- P4 is parity for every other set of 4 values.
- D corresponds to bit from original data
- 0 is always empty due to shortened format

