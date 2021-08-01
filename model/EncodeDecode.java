package correcter.model;

import java.util.Random;

public class EncodeDecode {
    private final Random RANDOM = new Random(); // only used for simulating error, create a new object to get a new seed
    private static final int[] P1_ARRAY = new int[] {5, 3, 1}; //bit locations p1 parity compares in encoded byte
    private static final int[] P2_ARRAY = new int[] {5, 2, 1}; //bit locations p2 parity compares in encoded byte
    private static final int[] P4_ARRAY = new int[] {3, 2, 1}; //bit locations p4 parity compares in encoded byte
    private static final int[] DATA_POSITIONS = new int[] {5, 3, 2, 1}; //bit locations for storing data in encoded byte
    private static final int[] PARITY_POSITIONS = new int[] {7, 6, 4}; // bit locations for storing parity in encoded byte
    private static final int[] CALCULATED_PARITY_ARRAY = new int[3]; //calculated values of p1, p2, and p4 in that order
    /*
     * Flips exactly one random bit per byte from 0 to 1 or 1 to 0.
     * @param bytes         bytes to have bits flipped
     * @return bytes        bytes after bits have been flipped
     */
    public byte[] simulateError(byte[] bytes) {
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) flipBit(bytes[i], RANDOM.nextInt(8));
        }
        return bytes;
    }
    /*
     * Transforms byte data into an encoded format that allows for error correction.
     * 8 bits of data from original byte will be split in half into two encoded bytes with following 8 bit format:
     * PPDPDDD0 where P is a parity value for error correction, D is original data, and 0 is an empty value
     * Parity values keep track of even vs odd number of set bits (value of 1) in the data
     * P1 looks at every other value, P2 looks at sets of two, P4 looks at sets of four (see P array above for bit locations)
     * Leftmost P value in array is P1, followed by P2, then P4
     * @param bytes                 bytes to be encoded
     * @return expandedBytes        encoded bytes
     */
    public static byte[] encodeData(byte[] bytes) {
        byte[] encodedBytes = new byte[bytes.length * 2];
        int arrayCount = 0;
        for (byte b : bytes) {
            int toBeEncoded = b & 0xff; //masks value
            int toBeEncodedBitCount = 7;
            for (int i = 0; i < 2; i++) {
                int encodedByte = 0;
                for (int dataPosition : DATA_POSITIONS) { //sets data bits
                    encodedByte = setBit(encodedByte, dataPosition, getBit(toBeEncoded, toBeEncodedBitCount--));
                }
                setCalculatedParityArray(encodedByte); //calculates parity values
                encodedByte = setParityBits(encodedByte); //sets parity bits
                encodedBytes[arrayCount++] = (byte) encodedByte;
            }
        }
        return encodedBytes;
    }
    /*
     * Decodes encoded data back into original format while correcting errors introduced at bit level.
     * @param bytes                 encoded data to be decoded
     * @return decodedBytes         decoded data
     */
    public static byte[] decodeData(byte[] bytes) {
        byte[] decodedBytes = new byte[bytes.length / 2];
        int encodedArrayCount = 0;
        for (int i = 0; i < decodedBytes.length; i++) {
            int decodedByte = 0;
            int decodedBitCount = 7;
            for (int j = 0; j < 2; j++) {
                int encodedByte = bytes[encodedArrayCount++] & 0xff; //masks value
                int errorBit = parityErrorLocation(encodedByte, findParity(encodedByte)); //finds bit with error
                encodedByte = flipBit(encodedByte, errorBit); //corrects error
                for (int position : DATA_POSITIONS) {
                    decodedByte = setBit(decodedByte, decodedBitCount--, getBit(encodedByte, position)); //fills decoded byte with correct bits
                }
            }
            decodedBytes[i] = (byte) decodedByte;
        }
        return decodedBytes;
    }
    /*
     * Flips bit (from 0 to 1 or 1 to 0) at designated position in an int for error introduction or error correcting.
     * @param b             int in which to flip bit
     * @param position      location of bit to flip (rightmost bit has location of 0)
     * @return int          int with specified bit flipped
     */
    private static int flipBit(int b, int position) {
        return b ^ (1 << position);
    }
    /*
     * Returns value of bit at specified location in an int
     * @param b         int in which to get bit
     * @param position  location of bit to get value of (rightmost bit has location of 0)
     * @return int      value of bit (either 0 or 1)
     */
    private static int getBit(int b, int position) {
        return b >> position & 1;
    }
    /*
     * Returns an int with a bit set at a specified position. Cannot reset a bit (i.e. turn a 1 into a 0)
     * @param b         int value in which to set bit
     * @param position  location to set bit
     * @param bit       value to set bit to (should be either 0 or 1)
     * @return int      int value with bit set at specified location if bit is 1, else returns original int value
     */
    private static int setBit(int b, int position, int bit) {
        if (bit == 1) {
            int compare = 1 << position;
            return b | compare;
        } else {
            return b;
        }
    }
    /*
     * Sets calculated parity values for a specific encoded byte (cast to int) to an array.
     * See encodeData method comments for more details on parity values.
     * @param b         encoded byte from which to calculate parity values
     */
    private static void setCalculatedParityArray(int b) {
        CALCULATED_PARITY_ARRAY[0] = calculateParity(b, P1_ARRAY[0], P1_ARRAY[1], P1_ARRAY[2]);
        CALCULATED_PARITY_ARRAY[1] = calculateParity(b, P2_ARRAY[0], P2_ARRAY[1], P2_ARRAY[2]);
        CALCULATED_PARITY_ARRAY[2] = calculateParity(b, P4_ARRAY[0], P4_ARRAY[1], P4_ARRAY[2]);
    }
    /*
     * Sets parity values as bits in an encoded byte (cast to int).
     * @param b         encoded byte to set parity bits
     * @return b        encoded byte with parity bits set
     */
    private static int setParityBits(int b) {
        b = setBit(b, PARITY_POSITIONS[0], CALCULATED_PARITY_ARRAY[0]);
        b = setBit(b, PARITY_POSITIONS[1], CALCULATED_PARITY_ARRAY[1]);
        b = setBit(b, PARITY_POSITIONS[2], CALCULATED_PARITY_ARRAY[2]);
        return b;
    }
    /*
     * Calculates a single parity value in a given int based on three bits with specified locations.
     * @param b                     int in which to calculate parity
     * @param pos1, po2, pos3       position of bits for calculating parity (order does not matter)
     * @return int                  1 if bits contain 3 or 1 ones, 0 otherwise
     */
    private static int calculateParity(int b, int pos1, int pos2, int pos3) {
        return (getBit(b, pos1) ^ getBit(b, pos2) ^ getBit(b, pos3));
    }
    /*
     * Finds location of error in an encoded byte (cast to int) by comparing calculated with encoded parity values
     * @param b         int in which to find error
     * @param parity    encoded parity values from b
     * @return int      bit location of error
     */
    private static int parityErrorLocation(int b, int[] parity) {
        setCalculatedParityArray(b);
        int sum = 0;

        if (parity[0] != CALCULATED_PARITY_ARRAY[0]) {
            sum += 1;
        }
        if (parity[1] != CALCULATED_PARITY_ARRAY[1]) {
            sum += 2;
        }
        if (parity[2] != CALCULATED_PARITY_ARRAY[2]) {
            sum += 4;
        }

        if (sum == 0) { //no error. will flip first bit, which is ignored
            return 0;
        } else { //calculated error locations will correspond to 1 - 7 from left to right
            return 8 - sum; //subtract from 8 to find bit location from 1 - 7 right to left
        }
    }
    /*
     * Finds encoded parity values in an encoded byte (cast as int) based on known locations.
     * @param b         encoded byte (cast as int)
     * @return          encoded parity values as int array (each value will be 0 or 1)
     */
    private static int[] findParity(int b) {
        int[] parity = new int[3];
        parity[0] = getBit(b, PARITY_POSITIONS[0]);
        parity[1] = getBit(b, PARITY_POSITIONS[1]);
        parity[2] = getBit(b, PARITY_POSITIONS[2]);

        return parity;
    }
}
