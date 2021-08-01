package correcter.model;

public class ModeProcess {
    /*
     * Algorithms for program actions based on mode set by user.
     */
    public static void modeProcess() {
        switch (Mode.getMode()) {
            case ENCODE: //encodes data from send file and saves to encoded file
                EncodedData.getInstance(SendData.getInstance().getBytes());
                break;
            case SEND: //introduces errors into data from encoded file and saves to received file
                ReceivedData.getInstance(EncodedData.getInstance().getBytes());
                break;
            case DECODE: //decodes data from received file and saves to decoded file
                DecodedData.getInstance(ReceivedData.getInstance().getBytes());
                break;
        }
    }
}
