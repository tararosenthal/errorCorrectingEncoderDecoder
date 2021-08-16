package correcter.model;

import java.io.File;

public class FileHandling {
    /*
     * Hardcoding of file names. Can be changed later if need more flexibility.
     */
    private final File sendFile = new File("send.txt");
    private final File receivedFile = new File("received.txt");
    private final File encodedFile = new File("encoded.txt");
    private final File decodedFile = new File("decoded.txt");

    public File getSendFile() {
        return sendFile;
    }

    public File getReceivedFile() {
        return receivedFile;
    }

    public File getEncodedFile() {
        return encodedFile;
    }

    public File getDecodedFile() {
        return decodedFile;
    }
}
