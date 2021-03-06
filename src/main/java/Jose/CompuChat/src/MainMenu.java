package Jose.CompuChat.src;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class MainMenu {
    private Scanner reader;
    private P2PTask mainTask;


    public MainMenu(P2PTask mainTask) {
        this.mainTask = mainTask;
        this.reader = new Scanner(System.in);
        showMainMenu();
    }

    private String getLocalIP() {
        String localIp = "~can't be identified~";
        try {
            localIp = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return localIp;
    }

    private void showMainMenu() {
        String op;
        String ip = getLocalIP();
        do {
            System.out.println("      ~P2P CONNECTION SERVICE ~       ");
            System.out.println("Your IP is "+ip);
            System.out.println();
            System.out.println(" ______________________________________");
            System.out.println("|                                      |");
            System.out.println("| 1. Create server                     |");
            System.out.println("|                                      |");
            System.out.println("| 2. Connect as client                 |");
            System.out.println("|                                      |");
            System.out.println("| 3. Exit                              |");
            System.out.println("|______________________________________|");
            op = reader.nextLine();
            switch (op) {
                case "1" -> mainTask.createServerListener();
                case "2" -> showClientMenu();
                case "3" -> System.exit(0);
                default -> op = "0";
            }
        } while (op.equals("0"));
    }

    private void showClientMenu() {

        System.out.println("      ~P2P CONNECTION SERVICE ~       ");
        System.out.println(" ______________________________________");
        System.out.println("|                                      |");
        System.out.println("| 1. Enter IP address                  |");
        String ip = reader.nextLine();
        mainTask.createClientConnection(ip);
    }

}
