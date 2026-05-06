import java.util.Map;

public class Domestic extends Flight {
    
    public Domestic(int flightNum, String airline, String origin, String destination, double departureTime,
            double arrivalTime, Map<String , Integer> availableseats, Map<String, Double> prices) {
        super(flightNum, airline, origin, destination, departureTime, arrivalTime, availableseats, prices);
            }
    @Override
    public double calculatePrice(String seatClass) {
        Double price = prices.get(seatClass);
        if (price == null) {
            throw new IllegalArgumentException("Invalid seat class: " + seatClass);
        }
        return (price * (1-0.1)) ;
    }
    
}
