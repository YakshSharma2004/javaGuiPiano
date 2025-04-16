package mainPackage;

public class User {
    private int userId;
    private String username;
    private String passwordHash;
    private String role;

    public User(int userId, String username, String passwordHash, String role) {
        this.userId = userId;
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    // getters only; we never expose the raw hash via setters
    public int getUserId()        { return userId;     }
    public String getUsername()   { return username;   }
    public String getPasswordHash(){ return passwordHash; }
    public String getRole()       { return role;       }
}
