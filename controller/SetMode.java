package correcter.controller;

import correcter.display.UserInput;
import correcter.model.Mode;

import java.util.Scanner;

public class SetMode {
    /*
     * Requests user to input mode. Then sets that mode as program-wide status.
     * If invalid mode is input, outputs error message and ends program.
     */
    public static void setMode() {
        UserInput.requestMode();
        Scanner scanner = new Scanner(System.in);

        switch (scanner.nextLine().toLowerCase().trim()) {
            case "encode":
                Mode.setMode(Mode.ENCODE);
                break;
            case "send":
                Mode.setMode(Mode.SEND);
                break;
            case "decode":
                Mode.setMode(Mode.DECODE);
                break;
            default:
                UserInput.error();
                System.exit(0);
                break;
        }
    }
}
