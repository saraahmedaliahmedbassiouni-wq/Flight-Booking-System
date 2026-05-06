
public abstract class user {
     private   String username ;
      private  String password;
      private int id;
      private String name;
      private String email;
      private String contactInfo;
      private String role;
      private boolean loggedIn;
     public user (String username , String password, int id,String name,String email,String contactInfo)
      {
        this.username = username;
        this.password = password;
        this.id=id;
        this.name=name;
        this.email=email;
        this.contactInfo=contactInfo;    
      }
      
      public String getUsername() {
        return username;
     }
    
     public String getPassword() {
        return password;
     }
    
      public void setUsername(String username) {
        this.username = username;
     }
      
     
      public void setPassword(String password) {
    if (password.length() >= 6 && password.matches("[a-zA-Z0-9]+")) {
        this.password = password;
    } else {
        throw new IllegalArgumentException("Password must be at least 6 characters and contain only letters and digits.");
    }
}
        
      public long getId() {
        return id;
      }
    
      public void setId(int id) {
        this.id = id;
      }
    
      public String getName() {
        return name;
      }
    
      public void setName(String name) {
        this.name = name;
      }
    
      public String getEmail() {
        return email;
      }
    
      public void setEmail(String email) {
        this.email = email;
      }
      public String getContactInfo() {
        return contactInfo;
      }
    
      public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
      }

      public static boolean validatePassword(String password) {
        return password != null && password.length() >= 6 &&
               password.matches(".*[a-zA-Z].*") && 
               password.matches(".*[0-9].*");
    }

    public boolean login(String inputUsername, String inputPassword) {
        if (inputUsername.equals(username) && inputPassword.equals(password)) {
            loggedIn = true;
            return true;
        }
        return false;
    }

    public void logout() { loggedIn = false; }
    
    public void updateProfile(String name, String email, String contactInfo){
        setName(name);
        setEmail(email);
        setContactInfo(contactInfo);
        System.out.println("Profile updated successfully.");
    }

    protected  String getRole() {
      return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}

