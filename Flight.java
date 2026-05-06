import java.util.Map;
public class Flight {
  private int flightNum;
  private String airline;
  private String origin;
  private String destination;
  private double departureTime;
  private double arrivalTime;
   private Map<String, Integer> availableseats;
  protected  Map<String , Double> prices;

public Flight(int flightNum, String airline, String origin, String destination, double departureTime,
        double arrivalTime, Map <String , Integer> availableseats, Map<String , Double> prices) {
    this.flightNum = flightNum;
    this.airline = airline;
    this.origin = origin;
    this.destination = destination;
    this.departureTime = departureTime;
    this.arrivalTime = arrivalTime;
    this.availableseats = availableseats;
    this.prices = prices;
}

public int getFlightNum() {
    return flightNum;
}
 public void setFlightNum(int flightNum) {
    this.flightNum = flightNum;
 }

 public String getAirline() {
    return airline;
}
 public void setAirline(String airline) {
    this.airline = airline;
 }
 public String getOrigin() {
    return origin;
}
 public void setOrigin(String origin) {
    this.origin = origin;
 }
 public String getDestination() {
    return destination;
}
 public void setDestination(String destination) {
    this.destination = destination;
 }

 public double getDepartureTime() {
    return departureTime;
}
 public void setDepartureTime(double departureTime) {
    this.departureTime = departureTime;
 }
 public double getArrivalTime() {
    return arrivalTime;
}
 public void setArrivalTime(double arrivalTime) {
    this.arrivalTime = arrivalTime;
 }
 public Map <String , Integer> getAvailableseats() {
    return availableseats;
}
public void setAvailableseats(Map<String , Integer> availableseats) {
    this.availableseats = availableseats;
}
public Map<String , Double> getPrices() {
    return prices;
}
public void setPrices(Map< String , Double >prices) {
    this.prices = prices;
}

public boolean checkAvailability(String seatClass) {
    return availableseats.containsKey(seatClass) && 
           availableseats.get(seatClass) > 0;
}

public void updateSchedule(double departureTime, double arrivalTime, String destination){
   this.departureTime = departureTime;
    this.arrivalTime = arrivalTime;
    this.destination = destination;
    System.out.println("Schedule updated! \nDeparture time: " + departureTime
    + "\nArrival time: " + arrivalTime + "\nDestination: " + destination);
}

public double  calculatePrice(String seatClass){
   return 0;
}

public boolean reserveSeat(String seatClass) {
    if (checkAvailability(seatClass)) {
        availableseats.put(seatClass, availableseats.get(seatClass) - 1);
        return true;
    }
    return false;
}
  
}

