package correcter.model;

import java.io.*;
/*
 * Class to store, read, and write decoded data.
 */
public class DecodedData {
    private byte[] bytes;

    private DecodedData() {
    }
    /*
     * Static factory method to get instance of decoded data utilizing new encoded bytes.
     * Will automatically write decoded data to associated file.
     * @param bytes         encoded data to be decoded
     */
    public static DecodedData getInstance(byte[] bytes) {
        if (bytes == null) {
            return DecodedData.getInstance();
        } else {
            FileHandling fileHandling = new FileHandling();
            DecodedData decodedData = new DecodedData();
            decodedData.bytes = EncodeDecode.decodeData(bytes);
                try (OutputStream outputStream = new FileOutputStream(fileHandling.getDecodedFile())) {
                    outputStream.write(decodedData.bytes);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                return decodedData;
        }
    }
    /*
     * Static factory method to get instance of decoded data with bytes that have already been decoded and written to file.
     * If bytes have not been written to the associated file, object will contain empty data.
     */
    public static DecodedData getInstance() {
        FileHandling fileHandling = new FileHandling();
        DecodedData decodedData = new DecodedData();
        try (InputStream inputStream = new FileInputStream(fileHandling.getDecodedFile())) {
            decodedData.bytes = inputStream.readAllBytes();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return decodedData;
    }

    public byte[] getBytes() {
        return bytes;
    }
}
