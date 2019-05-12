
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

//interface gia na pragmatopoiithei i syndesi tou client me ton server1 mesw RMI
public interface RMInterface extends Remote {
    //oi synartiseis pou xreiastima
    public ArrayList<Ptiseis> display(String From, String To,String Date) throws RemoteException;//epistrefei mia arraylist me tis ptiseis po exei epileksei o client
    public boolean kratisi(String name , String Surname, int Flight_id,int seats) throws RemoteException;//pragmatopoiei thn kratisi
    public ArrayList<Ptiseis>  Flights_display() throws RemoteException;//epistrefei mia arraylist me oles tis ptiseis p exei h vasi dedomenwn
    public void Finish() throws RemoteException;//termatizei tin syndesi
   
    
    
}
