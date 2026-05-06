public class Passenger {
    private long passengerId;
    private String name;
    private String passportNumber;
    private String dateOfBirth;
    private String specialRequests;

     public Passenger(long passengerId, String name, String passportNumber, String dateOfBirth, String specialRequests) {
        this.passengerId = passengerId;
        this.name = name;
        this.passportNumber = passportNumber;
        this.dateOfBirth = dateOfBirth;
        this.specialRequests = specialRequests;
     }

    public long getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(long passengerId) {
        this.passengerId = passengerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getSpecialRequests() {
        return specialRequests;
    }

    public void setSpecialRequests(String specialRequests) {
        this.specialRequests = specialRequests;
    }

    public void updateInfo (long passengerId, String name, String passportNumber, String dateOfBirth, String specialRequests) {
      setPassengerId(passengerId);
      setName(name);
      setDateOfBirth(dateOfBirth);
      setPassportNumber(passportNumber);
      setSpecialRequests(specialRequests);
    }

    public String getPassengerDetails() {
        return "Name:" + name 
        + " ID: " + passengerId
        + " Passport number: " + passportNumber
        + " Date  of birth: " + dateOfBirth
        + " Special requests " + specialRequests;
    }
}