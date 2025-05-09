import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {

    private IService remoteService;
    private final Scanner inputScanner = new Scanner(System.in);

    public Client() {
        try {
            // Get reference to local RMI registry on port 8000
            Registry registry = LocateRegistry.getRegistry("localhost", 8000);
            // Look up remote service registered as "RemoteService"
            Object obj = registry.lookup("RemoteService");

            // Verify if returned object is of type IService
            if (obj instanceof IService) {
                remoteService = (IService) obj;
            } else {
                throw new RemoteException("Service type error");
            }
        } catch (RemoteException | NotBoundException e) {
            System.err.println("Error connecting to server: " + e.getMessage());
            System.exit(1);
        }
    }

    public void exec() {
        while (true) {
            System.out.println("\n=== Menu ===");
            System.out.println("1. Invert word");
            System.out.println("2. Perform calculation");
            System.out.println("3. Get server information");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int option = inputScanner.nextInt();
            inputScanner.nextLine(); // Consume newline

            try {
                switch (option) {
                    case 1:
                        System.out.print("Enter a word: ");
                        String word = inputScanner.nextLine();
                        String result = remoteService.inverterWord(word);
                        System.out.println("Result: " + result);
                        break;

                    case 2:
                        System.out.print("Enter first number: ");
                        double num1 = inputScanner.nextDouble();
                        System.out.print("Enter operator (+, -, *, /): ");
                        char operator = inputScanner.next().charAt(0);
                        System.out.print("Enter second number: ");
                        double num2 = inputScanner.nextDouble();

                        double calculationResult = remoteService.calculate(num1, operator, num2);
                        System.out.println("Result: " + calculationResult);
                        break;

                    case 3:
                        String serverInfo = remoteService.getServerInfo();
                        System.out.println(serverInfo);
                        break;

                    case 4:
                        System.out.println("Closing...");
                        return;

                    default:
                        System.out.println("Invalid option!");
                }
            } catch (RemoteException e) {
                System.err.println("Error during execution: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new Client().exec();
    }
}