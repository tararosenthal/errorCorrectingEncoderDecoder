package correcter.model;

import java.io.*;
/*
 Class to store, read, and write received data.
 */
public class ReceivedData {
    private byte[] bytes;

    private ReceivedData() {
    }
    /*
     * Static factory method to get instance of received data utilizing new bytes in which to introduce errors.
     * Will automatically write received data to associated file.
     * @param bytes         data in which to introduce error
     */
    public static ReceivedData getInstance(byte[] bytes) {
        if (bytes == null) {
            return ReceivedData.getInstance();
        } else {
            FileHandling fileHandling = new FileHandling();
            EncodeDecode encodeDecode = new EncodeDecode();
            ReceivedData receivedData = new ReceivedData();
            receivedData.bytes = encodeDecode.simulateError(bytes);
            try (OutputStream outputStream = new FileOutputStream(fileHandling.getReceivedFile())) {
                outputStream.write(receivedData.bytes);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            return receivedData;
        }
    }
    /*
     * Static factory method to get instance of received data with bytes that have already had errors introduced and written to file.
     * If bytes have not been written to the associated file, object will contain empty data.
     */
    public static ReceivedData getInstance() {
        FileHandling fileHandling = new FileHandling();
        ReceivedData receivedData = new ReceivedData();
        try (InputStream inputStream = new FileInputStream(fileHandling.getReceivedFile())) {
            receivedData.bytes = inputStream.readAllBytes();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return receivedData;
    }

    public byte[] getBytes() {
        return bytes;
    }
}
