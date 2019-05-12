import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main
{
    
   static Connection conn=null;
   private static ArrayList<Ptiseis> list= new ArrayList<>();
       
   
    public static void main(String[] args)
    {
        Main m= new Main();//antikeimeno tis klasis main gia na mporesw na xrisimopoiisw tis methodoys ths
        m.connection2DB();//syndesh kai dimiourgia pinakwn sthn vasi dedomenwn
        //kapoies eggrafes sthn vasi dedomenwn
        m.insertPtiseis("28-03-2019", "13:30", "Samos","Athens",  50);
        m.insertPtiseis("28-08-2019", "06:23", "Athens","Samos",  50);
        m.insertPtiseis("03-02-2019", "09:30", "Thessaloniki","Athens", 50);
        m.insertPtiseis("28-03-2020", "13:30", "Athens","Thessaloniki",  50);

      
       ServerSocket serverSocket;//dimmiourgia server socket gia na epikoinwnei me ton client
       Socket clientSocket;//dimiourgia socket gia na dexetai minimata apo ton client
       ObjectOutputStream oos;//dimiourgia antikeimenou object dioti antallasoyn antikeimena gia na prgmatopoiithei i epikoinwnia tous, to ObjectOutput to xrisimopoioyme gia na steiloyme ston pelati
       ObjectInputStream in;//gia na paroume antikeimena apo ton client
      
      
        
        //ksekinaei o server kai perimenei gia syndesi
         try {
             serverSocket= new ServerSocket(5555);//dimiourgia toy antikeimenou tou serversocket pou orisame pio panw kai toy vazoume port 5555
             clientSocket = serverSocket.accept();//dexomaste se epikoinwnia ton client
             oos = new ObjectOutputStream(clientSocket.getOutputStream());//orizoume sto object pws tha stelnei ston client antikeimena 
             in= new ObjectInputStream(clientSocket.getInputStream()); //orizoyme sto object pws tha lambanei apo ton client antikeimena
             
     
             Object line=null;
             
          
                while(true)
                {
                    line=in.readObject();//diabazoume to antikeimeno p esteile o pelatis
                    
                    if(line.equals("End"))//an exei steilei End tote i syndesi termatizei 
                    {
                        System.out.println("Closing connection....."); 
 
                        clientSocket.close();//kleinooyme yhn syndesi
                        in.close();//kleinoume tin roh pou anoiksame gia na ypodexomaste ta antikeimena
                        oos.close();//kleinoume tin roh pou anoiksame gia na stelnoyme ta antikeimena                        
                        m.CloseConnection();//kleinoume thn syndesh me thn basi dedomenwn
                        System.gc();//kaloume ton garbage collector gia na adeiasoume tin mnimi apo ta skoupidia
                        System.exit(0);//termatismos programatos
                        break;
                    }
                    else if(line.equals("show itinerary"))//an steilei to minima stin synthiki tote tou apantame me ola ta dromologia pou yparxoyn sthn vasi me basi thn imerominia kai ton proorismo
                    {
                        oos.writeObject("ok");//apantame ok ston client
                        oos.flush();
                        String from=(String)in.readObject();//diabazoume to antikeimeno p esteile o pelatis
                        String to=(String)in.readObject();//diabazoume to antikeimeno p esteile o pelatis
                        String date=(String)in.readObject();                        //diabazoume to antikeimeno p esteile o pelatis
                        oos.writeObject(m.SelectItinerary(from,to,date));//tou stelnoyme to apotelesma ths synartisis
                        oos.flush();//katharizoume ton buffer
                        
                    }
                    else if(line.equals("kratisi"))
                    {
                        oos.writeObject("ok kratisi");
                        oos.flush();
                        String name= (String)in.readObject();
                        String surname= (String)in.readObject();
                        int id= (int)in.readObject();
                        int seats=(int)in.readObject();                      
                        oos.writeObject(m.insertReservation(name,surname,id,seats));
                        oos.flush();
                         
                         
                    }
                    else if(line.equals("Flights"))
                    {
                        oos.writeObject(m.Display());
                        oos.flush();
                    }
                }
             
           
             
             
             
             
             
         } catch (IOException ex) {
            ex.printStackTrace();
         }
         catch (ClassNotFoundException ex) {
                ex.printStackTrace();
             }
        
            
        
        
        
    } 
   //h synartisi epistrefei mia Arraylist me ta taksidia pou epelekse o client
    public  ArrayList<Ptiseis> SelectItinerary(String From,String To,String Date)
    {
        
        ArrayList<Ptiseis> itinerary= new ArrayList<>();//dimiourgia arraylist
     //dimiourgia query gia thn vasi dedomenwn wste na paroume to apotelesma pou zitise o client
        String select= "SELECT Flight_id, departure, destination, DATE, hour, arithmos_thesewn FROM ptiseis"
                +" WHERE( departure=? AND destination=? AND DATE=?);";
        PreparedStatement stmnt=null;//orismos antikeimenou to opoio to xrisimopoioume gia na etoimasoume to query gia na ektelstei
        ResultSet rs=null;//antikeimeno gia na paroume to apotelesma tou query
        try
        {
            stmnt= conn.prepareStatement(select);//syndeoume to query me thn basi dedomenwn
            //vazoume tis metablites pou leipoyn sta erwtimatika tis opoies pirame apo ton xristi
            stmnt.setString(1, From);
            stmnt.setString(2, To);
            stmnt.setString(3, Date);
            
            rs= stmnt.executeQuery();//ekteloume to query kai pairnoume to apotelesma
            while(rs.next())//kanoume prospelasi sta apotelesmata kai ta bazoume se mia arraylist thn opoia epistrefoume ston xristi
            {
              
                Ptiseis ptisi= new Ptiseis(rs.getString("DATE"),rs.getString("hour"),rs.getString("departure"),rs.getString("destination"),rs.getInt("arithmos_thesewn"),rs.getInt("Flight_id"));
                itinerary.add(ptisi);
                
            }
            
            
            
        }catch(SQLException e)
        {
            e.printStackTrace();
        }
        return itinerary;
    }
//i synartisi Display ektypwnei oles tis ptiseis pou exei h vasi dedomenwn
    public ArrayList <Ptiseis> Display()
    {
        ArrayList <Ptiseis> list2= new ArrayList<>();
        String select="SELECT Flight_id,DATE,arithmos_thesewn,destination,departure,hour FROM ptiseis ";
        Statement stmt;
        ResultSet rs;
        try
        {
            stmt=conn.createStatement();
            rs=stmt.executeQuery(select);
            while (rs.next()) {
                Ptiseis ptisi= new Ptiseis(rs.getString("DATE"),rs.getString("hour"),rs.getString("departure"),rs.getString("destination"),rs.getInt("arithmos_thesewn"),rs.getInt("Flight_id"));
                list2.add(ptisi);
            }
        } catch (SQLException ex) {
           ex.printStackTrace();
       }
        return list2;
    }
    //termatismos tis syndesis me thn vasi dedomenwn
    public void CloseConnection()
    {
          
            try
            {
                if(conn !=null)
                {
                    conn.close();
                }
            }
            catch(SQLException ex)
            {
                ex.printStackTrace();
                
            }
        
    }
    public void connection2DB()
    {
        String url = "jdbc:sqlite:sqlite/Server.db";//orizoyme thn vasi 
        //dimiourgoume tous pinakes pou xreiazomaste
        String table= "CREATE TABLE IF NOT EXISTS ptiseis ( Flight_id integer PRIMARY KEY,  DATE date,hour time(7) , departure VARCHAR(100),destination VARCHAR(100) , arithmos_thesewn integer);";
        String tableSeat= "CREATE TABLE IF NOT EXISTS reservation (id integer PRIMARY KEY, seats integer ,name VARCHAR(50), surname VARCHAR(50), id_flight VARCHAR(100) REFERENCES ptiseis(Flight_id));";
        Statement st=null;
        
        try
        {
            conn=DriverManager.getConnection(url);//syndeomaste me thn vasi kai an den yparxei dimiourgeitai
           //ektelesi twn query gia thn dimiourgia pinakwn
            st= conn.createStatement();
            st.execute(table);
            st.execute(tableSeat);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        
    }
  //i synartisisi ayth bazei dedomena ston pinaka reservation o opoios krataei oles tis kratiseis pou exoun oloklirwthei
    //epistrefei ena boolean pou eksartatai apo tis theseis pou exei epileksei o client, se periptwsi pou den yparxoyn diathesimes epistrefei false
    public boolean insertReservation(String name,String surname,int id,int seats)
    {
        String sql= "INSERT INTO reservation (name, surname,seats) VALUES(?,?,?)";
        String query="Select arithmos_thesewn,ptiseis.Flight_id FROM ptiseis WHERE arithmos_thesewn>? and Flight_id=? ";
        PreparedStatement  pstn,pstn1;
        ResultSet rs;
        int theseis;
        int fid;
        boolean done=false;
        
       
        try
        {
            pstn1= conn.prepareStatement(query);           
            pstn1.setInt(1, seats);
            pstn1.setInt(2, id);
            rs=pstn1.executeQuery();
            theseis=rs.getInt("arithmos_thesewn");
            fid=rs.getInt("Flight_id");
            if(theseis>=seats && fid==id)
            {
                done=true;
                pstn= conn.prepareStatement(sql);
                pstn.setString(1, name);
                pstn.setString(2, surname);
                pstn.setInt(3, seats);
                pstn.executeUpdate();
                updateFlights(id,seats);
            }
       
            
             
             
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return done;
    }
    //h synartisi ayth enhmerwnei ton pinaka ptiseis(o opoios krataei oles tis ptiseis) meta apo mia kratisi afairwntas tis theseis pou ekane kratisi o client
    public void updateFlights(int id_flight, int seat)
    {
        
        String select= "SELECT arithmos_thesewn FROM ptiseis WHERE Flight_id=?;";
        String sql="UPDATE ptiseis SET arithmos_thesewn=? WHERE Flight_id=?;";
        ResultSet rs;
        PreparedStatement pstmt,pstmt2;
        int seats;
        try
        {
            pstmt2=conn.prepareStatement(select);
            pstmt2.setInt(1, id_flight);
            rs=pstmt2.executeQuery();
            seats=rs.getInt("arithmos_thesewn");
            pstmt=conn.prepareStatement(sql);
            System.out.println("seats ="+seats);
            seats=seats-seat;
            pstmt.setInt(1, seats);
            pstmt.setInt(2, id_flight);
             pstmt.executeUpdate();
            
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
              
        }
        
    }
    //isagwgi ptisewn sthn vasi dedomenwn
    public void insertPtiseis(String date,String time, String from,String To,int thesis)
    {
        String sql= "INSERT INTO ptiseis (DATE,hour,departure,destination,arithmos_thesewn) VALUES(?,?,?,?,?)";
        PreparedStatement  pstn;
        try
        {
             pstn= conn.prepareStatement(sql);
             pstn.setString(1, date);
             pstn.setString(2, time);
             pstn.setString(3, from);
             pstn.setString(4, To);
             pstn.setString(5, String.valueOf(thesis));
             pstn.executeUpdate();
             
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
   
    
}
