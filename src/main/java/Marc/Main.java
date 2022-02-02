package Marc;

import Marc.Controller.Controller;
import Marc.View.Window;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        try {
            new Window(new Controller(7070)).open();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

}
