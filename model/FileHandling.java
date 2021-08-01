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
    private File newFile; //allows creation of new file for data storage
    /*
     * Returns specific files based on string names. Returns a new file with given name if does correspond to set files.
     * @param string            name of file requested
     * @return File             file corresponding to requested
     */
    public File getFile(String string) {
        string = string.toLowerCase().trim();
        if ("send".equals(string)) {
            return sendFile;
        } else if ("received".equals(string)) {
            return receivedFile;
        } else if ("encoded".equals(string)) {
            return encodedFile;
        } else if ("decoded".equals(string)) {
            return decodedFile;
        } else {
            return newFile = new File(string + ".txt");
        }
    }

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
