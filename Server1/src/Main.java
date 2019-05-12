

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.UnknownHostException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;


//Main class kani extent to UnicastRemoteObject gia na mporesei na pragmatopoiithei h syndesi kai kanei implements to RMInterface to opoio dimiourgisame emeis gia na mporesei na doulepsei to RMI me tis synartiseis mas
public class Main  extends UnicastRemoteObject implements RMInterface
{
  
    static Socket socket = null; //dimiourgia socket gia na mporesei na syndethei me ton server2
    static ObjectInputStream  input = null; //antikeimeno gia na mporesei na lavei dedomena apo ton server2
    static ObjectOutputStream out = null;//antikeimeno gia na mporesei na steilei dedomena apo ton server2
   
    

    public Main() throws RemoteException {
       super();
    }
    
    
    public static void main(String[] args) 
    {
        Main m;
        try {
            m = new Main();
            m.connect2server();//syndesei me ton server 2
        } catch (RemoteException ex) {
           ex.printStackTrace();
        }
        
        
     
    }
    
    public void connect2server()
    {
//        
        try
        {
            socket= new Socket("127.0.0.1",5555);//syndesei me ton server2, vazoume thn IP tou server 2 kai thn Port pou akouei
            
            out=new ObjectOutputStream(socket.getOutputStream());//dimiourgia rohs gia apostolh dedomenwn ston server2
            input= new ObjectInputStream(socket.getInputStream());//dimiourgia rohs gia lipsi dedomenwn ston server2
          
        }
        catch(UnknownHostException u) 
        { 
            u.printStackTrace();
        } 
        catch(IOException i) 
        { 
            i.printStackTrace();
        } 
        
    
        try {
            LocateRegistry.createRegistry(1099);//gia na trexoume sto NETBEANS ta RMI
            Naming.rebind("//localhost/AirTickets", new Main());//diathetoume sthn efarmogi to interface pou dimioyrgisame wste na syndethei o client se ayto kai na kalesei tis synartiseis pou ulopoiountai parakatw
            
        } catch (RemoteException ex) {
            ex.printStackTrace();
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
                    
       
    }
    
    //i synartisi ayth afou kalestei apo ton client stelnei aitima mesw socket ston server2 wste na steilei tis ptiseis pou zitise o xristis me vasi ta dedomena pou zitise
    @Override
    public synchronized ArrayList<Ptiseis> display(String From,String To,String Date) throws RemoteException {
        ArrayList <Ptiseis> list= new ArrayList<>();
        try {
            
            out.writeObject("show itinerary");//apostoli antikeimenou ston server2
            out.flush();//katharismo buffer
            if(input.readObject().equals("ok"))//anagnwsi toy antikeimenou pou esteile o xristis
            {
                out.writeObject(From);
                out.flush();
                out.writeObject(To);
                out.flush();
                out.writeObject(Date);
                out.flush();
                list=(ArrayList<Ptiseis>)input.readObject();
            }
            
            
            
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
           ex.printStackTrace();
        }
       return list;
    }

   

   
//i synartisi ayth stelnei ston server2 ola ta dedomena pou evale o xristis gia na pragmatopoiithei i kratisi tou
    @Override
    public synchronized boolean kratisi(String name, String Surname,int id,int seats) throws RemoteException {
        boolean done=false;
        try {
            out.writeObject("kratisi");
            out.flush();
            if(input.readObject().equals("ok kratisi"))
            {
                out.writeObject(name);
                out.flush();
                out.writeObject(Surname);
                out.flush();
                out.writeObject(id);
                out.flush();
                out.writeObject(seats);
                out.flush();
                
                
                if(input.readObject().equals(true))
                {
                    done=true;
                }
                else done=false;
            }
            
            
        } catch (IOException ex) {
           ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return done;
        
    }

    //zitaei apo ton server2 na epistrepsei oles tis ptiseis pou einai grammenes stin vasi dedomenwn
    @Override
    public ArrayList<Ptiseis> Flights_display() throws RemoteException {
        ArrayList <Ptiseis> list= new ArrayList<>();
        try {
            
            out.writeObject("Flights");
            out.flush();
            list=(ArrayList <Ptiseis>)input.readObject();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return list;
    }
//termatizei thn syndesi
    @Override
    public void Finish() throws RemoteException {
        try {
            out.writeObject("End");
            out.flush();
            out.close();
            socket.close();
            System.gc();
            System.exit(0);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

   

   

   
    
  
    
}
