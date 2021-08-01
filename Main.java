package correcter;

import correcter.controller.SetMode;
import correcter.model.ModeProcess;
/*
 * Sets mode with user input and runs specified process for program.
 */
public class Main {
    public static void main(String[] args) {
        SetMode.setMode();
        ModeProcess.modeProcess();
    }
}
