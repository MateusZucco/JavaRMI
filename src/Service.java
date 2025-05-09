import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Service implements IService {

    public static void main(String[] args) {
        try {
            // Set security policy for RMI operations
            System.setProperty("java.security.policy", "policy.txt");

            // Create service instance and export it for remote access
            Service service = new Service();
            IService stub = (IService) UnicastRemoteObject.exportObject(service, 0);

            // Create and register RMI registry on port 8000
            Registry registry = LocateRegistry.createRegistry(8000);
            registry.bind("RemoteService", stub);

            System.out.println("Service running on port 3000");
        } catch (RemoteException | AlreadyBoundException e) {
            System.err.println("Error to start service: " + e.getMessage());
        }
    }

    @Override
    public String inverterWord(String word) throws RemoteException {
        StringBuilder sb = new StringBuilder(word);
        return sb.reverse().toString();
    }

    @Override
    public double calculate(double num1, char operation, double num2) throws RemoteException {
        return switch (operation) {
            case '+' -> num1 + num2;
            case '-' -> num1 - num2;
            case '*' -> num1 * num2;
            case '/' -> {
                if (num2 == 0) throw new ArithmeticException("Div zero error!");
                yield num1 / num2;
            }
            default -> throw new IllegalArgumentException("Invalid operation!");
        };
    }

    @Override
    public String getServerInfo() throws RemoteException {
        return "Date: " + new java.util.Date() + "\n" +
                "OS: " + System.getProperty("os.name") + "\n" +
                "Java Version: " + System.getProperty("java.version");
    }
}