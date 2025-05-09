import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IService extends Remote {
    String inverterWord(String word) throws RemoteException;
    double calculate(double num1, char operation, double num2) throws RemoteException;
    String getServerInfo() throws RemoteException;
}