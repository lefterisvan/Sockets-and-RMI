
import java.io.Serializable;



public class Ptiseis implements Serializable{
    
    private String date;
    private String time,from,to;
    private int seatNum,flight_id;

    public Ptiseis() {
    }

    public Ptiseis(String date, String time, String from, String to, int seatNum, int flight_id) {
        this.date = date;
        this.time = time;
        this.from = from;
        this.to = to;
        this.seatNum = seatNum;
        this.flight_id = flight_id;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public int getSeatNum() {
        return seatNum;
    }

   

    public int getFlight_id() {
        return flight_id;
    }

    @Override
    public String toString() {
        return "Ptiseis{" + "date=" + date + ", time=" + time + ", from=" + from + ", to=" + to + ", seatNum=" + seatNum + ", flight_id=" + flight_id + '}';
    }

    
    
}
