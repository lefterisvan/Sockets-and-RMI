
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;


public class GUI  implements ActionListener 
{
    
    private JFrame frame1;//dimiourgia frame wste na dimiourgithei to parathuro gia ta grafika
    private final JCheckBox elegxos=new JCheckBox("elegxos");//kouti epilogis
    private final JCheckBox kratisi=new JCheckBox("Kratisi");
    private JButton ok= new JButton ("OK");//koumpi
    private RMInterface rm;//antikeimeno tou interface pou dimiourgisame gia na mporesei na pragmatopoiithei i syndesi twn RMI

    public GUI() {
        try {
            rm=(RMInterface)Naming.lookup("//localhost/AirTickets");//orizome pou tha koitaei to antikeimeno tou interface wste n parei tis synartiseis pou exoume diathesei apo ton server1
            System.out.println("connection done");
        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void select()
    {
        frame1= new JFrame("Make a Choice");
        JLabel label=new JLabel("Make a Choice");//dimiourgia etiketas sto parathyro twn grafikwn
        JPanel panel = new JPanel();//dimiourgia panel gia na to baloume sto frme
        BufferedImage mypic;//eisagwgi eikonas
        JLabel pic = null;
        WindowListener listener = new WindowAdapter() //dinoume energeia sto kleisimo tou parathyrou
        {//molis kleisei to parathyro tha ginoun ta parakatw
            @Override
            public void windowClosing(WindowEvent w) 
            {
                
                try {
                    rm.Finish();//kalei apo ton server1 thn synartisi gia na termatisoun oi sindeseis
                    System.gc();//katharizoume tin mnimi apo ta skoupidia 
                    System.exit(0);//termatismos programmatos
                } catch (RemoteException ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
            } 
        };
        
        frame1.addWindowListener(listener);//prosthiki tou windowslistener sto frame
        try
        {
            mypic = ImageIO.read(new File("airplane-flight.jpg"));//anoigma eikonas
            pic= new JLabel (new ImageIcon(mypic));//prostiki eikonas sthn etiketa
        }
        catch (IOException ex) 
        {
            ex.printStackTrace();
        }
        
        frame1.setSize(400,400);//dinoume megethos sto frame
        frame1.setVisible(true);//to kanoume orato ston xristi
        frame1.setLocationRelativeTo(null);//to orizoume na emfanizetai sto kentro tis othonis
        frame1.setResizable(true);//epitrepoume ston xristi na allaksei megethos sto frame

        panel.setLayout(new GridLayout(7,3));//bazoume layout sto panel g
        panel.add(label);//prosthetoume to label sto panel
        panel.add(elegxos);//prosthetoume sto panel to checkbox elegxos
        panel.add(kratisi);//prosthetoume sto panel to checkbox elegxos
        panel.add(ok);//prosthetoume sto panel to checkbox elegxos
        elegxos.addActionListener(this);//energopoisi tou checkbox meta to clikarisma
        kratisi.addActionListener(this);
        ok.addActionListener(this);
        //toy thetw ena gridlayout wste na toy dwsw thn morfi p thelw egw
        
        frame1.add(panel,BorderLayout.LINE_START);//bazoume to panel sthn aristeri meria toy frame
        frame1.add(pic,BorderLayout.LINE_END);//bazoume thn eikona sthn deksia meria toy frame
        frame1.pack();//epitrepoume sto frame na prosarmozetai sto dedomena twn antikeimenwn pou filoksenei
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
    }    

    private JFrame search = new JFrame();
    private JTextField name= new JTextField(null,15);//pedio eggrafis keimenou apo ton xristi
    private JTextField surname= new JTextField(null,15);
    private JButton ok_search= new JButton("Done");//koumpi
    //string pinakes
    private String [] places={"Thessaloniki","Athens","Samos","Rhodes","Kriti","Chios","Paros"};
    private String [] num_theseis={"01","02","03","04","05","06","07","08","09","10","11","12"};
    private String [] days={"01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
    private String [] months={"01","02","03","04","05","06","07","08","09","10","11","12"};
    private String [] years={"2019","2020","2021"};
    //box me listes pou orisame emeis wste na einai pio eukoli i xrisi toy programmatos
    private JComboBox placesBox=new JComboBox(places);
    private JComboBox placesBox2=new JComboBox(places);
    private JComboBox Num_seats_Box=new JComboBox(num_theseis);
    private JComboBox Days=new JComboBox(days);
    private JComboBox Months=new JComboBox(months);
    private JComboBox Years=new JComboBox(years);
    private JComboBox Days1=new JComboBox(days);
    private JComboBox Months1=new JComboBox(months);
    private JComboBox Years1=new JComboBox(years);
    private JLabel Name= new JLabel("Name");
    private JLabel Surname= new JLabel("SurName");
    private JButton ok_person= new JButton("Next");
    private JFrame personFrame= new JFrame("Identification");
    public void person()
    {
        Container vo= personFrame.getContentPane();//kaluteri taksinomisi tou frame
        GridLayout gl= new GridLayout(5,1);//layout pou xwrizei to layout se grammes kai stiles
        vo.setLayout(gl);//prosthetoyme to layout ston container o opoiod exei prostethei sto frame

        WindowListener listener = new WindowAdapter() 
        {
            @Override
            public void windowClosing(WindowEvent w) 
            {
                System.gc();
                select();
                
            } 
        };
 
        personFrame.addWindowListener(listener);
        vo.add(Name);
        vo.add(name);        
        vo.add(Surname);
        vo.add(surname);
        ok_person.addActionListener(this);
        vo.add(ok_person);
        personFrame.setVisible(true);
      
        personFrame.setLocationRelativeTo(null);
        personFrame.setResizable(true);
        personFrame.setSize(400,300);
        personFrame.pack();
        
    }
    public void search_for_flight()
    {
        
        JPanel p1= new JPanel();
        JPanel p2= new JPanel();
        Container vo= search.getContentPane();
        GridLayout gl= new GridLayout(12,2);
        GridLayout g2= new GridLayout(1,3);
        WindowListener listener = new WindowAdapter() 
        {
            @Override
            public void windowClosing(WindowEvent w) 
            {
                System.gc();
                select();
                
            } 
        };
 
        search.addWindowListener(listener);
        p1.setLayout(g2);
        p2.setLayout(g2);
        vo.setLayout(gl);
        p1.add(Days);
        p1.add(Months);
        p1.add(Years);
        p2.add(Days1);
        p2.add(Months1);
        p2.add(Years1);
        
        
        JLabel departure = new JLabel("From");
        JLabel destination = new JLabel("To");
        JLabel destinationDate = new JLabel("Date TO Back");
        JLabel departureDate = new JLabel("Date To Go");
        JLabel seats = new JLabel("Number Of Seats"); 
        
        vo.add(departure);
        vo.add(placesBox);
        vo.add(departureDate);
        vo.add(p2);
        vo.add(destination);
        vo.add(placesBox2);
        vo.add(destinationDate);
        vo.add(p1);
        vo.add(seats);
        vo.add(Num_seats_Box); 
        ok_search.addActionListener(this);
        vo.add(ok_search);
        search.setVisible(true);
      
        search.setLocationRelativeTo(null);
        search.setResizable(true);
        search.setSize(500,300);  
        search.pack();
         
    }
    private JButton done_Display= new JButton("Done");
    JFrame frame_display= new JFrame("Display All Flights");
    JScrollPane jsp;
    public void Display_Flights(ArrayList<Ptiseis> list)
    {
        Container vo= frame_display.getContentPane();
        vo.setLayout(new GridLayout(2,1));
        JPanel p1= new JPanel();
        JPanel p2= new JPanel();
        p2.setLayout(new FlowLayout());
        p1.setSize(300,250);
        p1.setLayout(new GridLayout(list.size(),1));
         WindowListener listener = new WindowAdapter() 
        {
            @Override
            public void windowClosing(WindowEvent w) 
            {
                System.gc();
                select();
                
            } 
        };
 
        frame_display.addWindowListener(listener);
        
        
        for(int i=0; i<list.size(); i++)
        {
            JLabel flight = new JLabel(list.get(i).getFlight_id()+" From: "+list.get(i).getFrom()+" To: "+list.get(i).getTo()+" Date "+list.get(i).getDate());           
            
            p1.add(flight);
           
            
        }
        jsp=new JScrollPane(p1);
        
        vo.add(jsp);
        done_Display.setBounds(50, 50, 50, 50);
        done_Display.addActionListener(this);
        p2.add(done_Display);
        vo.add(p2);
        frame_display.setVisible(true);
      frame_display.pack();
        frame_display.setLocationRelativeTo(null);
        frame_display.setResizable(true);
        frame_display.setSize(300,300); 
        
        
    }
    
    private JCheckBox Box[],Box2[];
    private JButton ok_availability= new JButton("Next");
    JFrame frame= new JFrame("Availability");
    public void show_available(ArrayList<Ptiseis> list,String Dest_date,ArrayList<Ptiseis> list2,String Depa_Date)
    {
        JScrollPane jsp,jsp2;
        Container pane= frame.getContentPane();
        
        GridLayout gl= new GridLayout(5,1);
        pane.setLayout(gl);
        JPanel p1= new JPanel();
        p1.setLayout(new FlowLayout());
        JPanel p11= new JPanel();
        p11.setLayout(new GridLayout(list.size()+1,2));
        JPanel p2=new JPanel();
        p2.setLayout(new FlowLayout());
        JPanel p22=new JPanel();
        p22.setLayout(new GridLayout(list2.size()+1,2));
        JPanel p3= new JPanel();
        p3.setLayout(new FlowLayout());
        p22.setSize(150,250);
        p11.setSize(150,250);
        
 
         WindowListener listener = new WindowAdapter() 
        {
            @Override
            public void windowClosing(WindowEvent w) 
            {
                System.gc();
                select();
                
            } 
        };
 
        frame.addWindowListener(listener);
        Box = new JCheckBox[list.size()];
        Box2 = new JCheckBox[list2.size()];
        
        SimpleDateFormat sd=new SimpleDateFormat("dd-MM-yyyy");//i apaitoumeni morfi pou prepei na exei h hmerominia wste na einai swsti gia na thn dextei h vsi dedomenwn
        JLabel from= new JLabel("Destination");
         p1.add(from);
        for(int i=0; i<list.size(); i++)
        {
            try 
            {
                //metatropi twn string sthn apaitoumenh morfh gia thn sygkrisi me ta apotelesmata stin vasi dedomenwn
                if(sd.parse(Dest_date).equals(sd.parse(list.get(i).getDate())) && list.get(i).getSeatNum()!=0)
                {
                    JLabel flight = new JLabel("From :"+list.get(i).getFrom()+" To: "+list.get(i).getTo()+" Date: "+list.get(i).getDate()+" Seats: "+list.get(i).getSeatNum());
                    JCheckBox check= new JCheckBox(""+list.get(i).getFlight_id());                
                    check.addActionListener(this);
                    Box[i]=check;
                     p11.add(check);
                     p11.add(flight);
                }
                    
                
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            
        }
        JLabel to= new JLabel("Departure");
         p2.add(to);
        for(int i=0; i<list2.size(); i++)
        { 
            try 
            {
                if(sd.parse(Depa_Date).equals(sd.parse(list2.get(i).getDate())) && list2.get(i).getSeatNum()!=0)
                {
                    JLabel flight = new JLabel("From :"+list2.get(i).getFrom()+" To: "+list2.get(i).getTo()+" Date: "+list2.get(i).getDate() +" Seats: "+list2.get(i).getSeatNum());
                    JCheckBox check= new JCheckBox(""+list2.get(i).getFlight_id());                
                    check.addActionListener(this);
                    Box2[i]=check;
                    p22.add(check);
                    p22.add(flight);
                }
                    
                
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            
        }
        ok_availability.addActionListener(this);
        p3.add(ok_availability);
        jsp=new JScrollPane(p11);
        jsp2=new JScrollPane(p22);
        pane.add(p1);
        pane.add(jsp);
        pane.add(p2);
        pane.add(jsp2);
        pane.add(p3);
        frame.setVisible(true);
        //frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setSize(1000,300);
      
    }
    static int id1=-1,id2=-1;
    static int seats=-1;
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        
        
        String onoma = null,epitheto = null;
        
        
        try
        {
            
            
            if(elegxos.isSelected())//an to checkbox ayto einai epilegmeno
            {                    
                kratisi.setSelected(false);//apenergopoioume to checkbox
            }
            else if(kratisi.isSelected())
            {                   
                elegxos.setSelected(false);                    
            }                
            if(e.getSource()==ok)//an o xristis patisei ayto to koumpi tote ginontai oi parakatw energeies
            {
                if(elegxos.isSelected())
                {
                    Display_Flights(rm.Flights_display());//kaloume apo ton server1 na mas steilei oles tis ptiseis gia na tis ektipwsoume ston xristi
                }
                else if(kratisi.isSelected())
                {
                    search_for_flight();
                }
                frame1.dispose();//kleinoume to parathyro pou den xreiazetai
            }
            else if(e.getSource()==done_Display)
            {
                select();
                frame_display.dispose();
            }
            else if(e.getSource()==ok_search)
            {
                //pairnoume ta antikeimena pou pliktrologise i epelekse o xristis ta metatrepoume se string kai ta stelnoume ston server1
                String depa=(String)placesBox.getSelectedItem();
                String dest=(String)placesBox2.getSelectedItem();
                String day=(String)Days.getSelectedItem();
                String month=(String)Months.getSelectedItem();
                String year =(String)Years.getSelectedItem();
                String date=day+"-"+month+"-"+year;
                String day1=(String)Days1.getSelectedItem();
                String month1=(String)Months1.getSelectedItem();
                String year1 =(String)Years1.getSelectedItem();
                String date1=day1+"-"+month1+"-"+year1;
                seats=Integer.parseInt((String)Num_seats_Box.getSelectedItem());
                ArrayList <Ptiseis> list1= new ArrayList<>(rm.display(depa,dest , date1));
                ArrayList <Ptiseis> list= new ArrayList<>(rm.display(dest, depa, date));
                if(list1.isEmpty() && list.isEmpty())//an einai adeia oi arraylists tote den yparxoyn ptiseis me ta dedomena poy evale o xristis
                {
                    //joptionpane wste na enimerwnei ton xristi oti den yparxoyn ptiseis
                    JOptionPane.showOptionDialog(new JFrame(),"There are no available flights for this destination or date" , "Confirmation Message", JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE, null, null, null);
                }
                else show_available(list1,date1,list,date);//emfanizoume tis ptiseis
                for(int i=0;i<Box.length;i++)
                {
                    if(Box[i].isSelected())//an epileksei ena check apo tis ptiseis
                    {
                    
                        for(int j=0; j<i;j++)
                        {
                            Box[j].setSelected(false);//den mporei na epileksei alles ptiseis
                        }
                        for(int j=i+1; j<Box.length;j++)
                        {
                            Box[j].setSelected(false);
                        }               
                    }
                }
                for(int i=0;i<Box2.length;i++)
                {
                    if(Box2[i].isSelected())
                    {
                        for(int j=0; j<i;j++)
                        {
                            Box2[j].setSelected(false);
                        }
                        for(int j=i+1; j<Box2.length;j++)
                        {
                            Box2[j].setSelected(false);
                        }               
                    }
                }
                search.dispose();
                
            }
            else if(e.getSource()==ok_availability)
            {
                for(int i=0;i<Box.length;i++)
                {
                    if(Box[i].isSelected())
                    id1=Integer.parseInt(Box[i].getText());
                }
                for(int i=0;i<Box2.length;i++)
                {
                    if(Box2[i].isSelected())
                    id2=Integer.parseInt(Box2[i].getText());
                }
                person();
                frame.dispose();
                
               
            }
            else if(e.getSource()==ok_person)
            {
                onoma=name.getText();
                epitheto=surname.getText();
                boolean done_go=rm.kratisi(onoma,epitheto , id1, seats);
                boolean done_back=rm.kratisi(onoma,epitheto , id2, seats);
                
                if(done_go==true && done_back==true)
                {
                    int result =JOptionPane.showOptionDialog(new JFrame(), onoma+" "+epitheto+" "+"your reservation has been confirmed" , "Confirmation Message", JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE, null, null, null);
                    if(result==JOptionPane.OK_OPTION)
                    {
                        System.exit(0);
                    }
                    else
                    {
                        select();
                    }
                }
            }
        }
        catch(Exception  ex)
        {
            ex.printStackTrace();
        }
      
    }

}
