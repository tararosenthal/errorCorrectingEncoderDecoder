package correcter.model;

import java.io.*;
/*
 Class to store, read, and write encoded data.
 */
public class EncodedData {
    private byte[] bytes;

    private EncodedData() {
    }
    /*
     * Static factory method to get instance of encoded data utilizing new bytes to be encoded.
     * Will automatically write encoded data to associated file.
     * @param bytes         data to be encoded
     */
    public static EncodedData getInstance(byte[] bytes) {
        if (bytes == null) {
            return EncodedData.getInstance();
        } else {
            FileHandling fileHandling = new FileHandling();
            EncodedData encodedData = new EncodedData();
            encodedData.bytes = EncodeDecode.encodeData(bytes);
            try (OutputStream outputStream = new FileOutputStream(fileHandling.getEncodedFile())) {
                outputStream.write(encodedData.bytes);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            return encodedData;
        }
    }
    /*
     * Static factory method to get instance of encoded data with bytes that have already been encoded and written to file.
     * If bytes have not been written to the associated file, object will contain empty data.
     */
    public static EncodedData getInstance() {
        FileHandling fileHandling = new FileHandling();
        EncodedData encodedData = new EncodedData();
        try (InputStream inputStream = new FileInputStream(fileHandling.getEncodedFile())) {
            encodedData.bytes = inputStream.readAllBytes();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return encodedData;
        }

        public byte[] getBytes () {
            return bytes;
        }
    }
