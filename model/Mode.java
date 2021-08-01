package correcter.model;
/*
 * Program wide status for determining needed actions.
 */
public enum Mode {
    ENCODE,
    SEND,
    DECODE;

    private static Mode mode;

    public static Mode getMode() {
        return mode;
    }

    public static void setMode(Mode mode) {
        Mode.mode = mode;
    }
}
