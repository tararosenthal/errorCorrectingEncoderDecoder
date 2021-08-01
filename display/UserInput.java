package correcter.display;

public class UserInput {
    /*
     * String output for mode input request.
     */
    public static void requestMode() {
        System.out.print("Write a mode: ");
    }
    /*
     * String output for error message after invalid mode is input.
     */
    public static void error() {
        System.out.print("Error, please enter one of these three options: encode, decode, or send");
    }
}
