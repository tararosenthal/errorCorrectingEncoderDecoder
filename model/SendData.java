package correcter.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
/*
 Class to store and read send data.
 */
public class SendData {
    private byte[] bytes;

    private SendData() {
    }
    /*
     * Static factory method to get instance of send data with bytes that have already been written to file.
     * If bytes have not been written to the associated file, object will contain empty data.
     */
    public static SendData getInstance() {
        SendData sendData = new SendData();
        FileHandling fileHandling = new FileHandling();
        try (InputStream inputStream = new FileInputStream(fileHandling.getSendFile())) {
            sendData.bytes = inputStream.readAllBytes();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return sendData;
    }

    public byte[] getBytes() {
        return bytes;
    }
}
