package gift.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User extends BaseEntity{
    @Column(nullable = false, unique = true)
    private String userId;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private Boolean admin;

    protected User(){
        super();
    }

    public User(String userId, String email, String password) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.admin = false;
    }
    public Long getId() {
        return super.getId();
    }
    public String getUserId(){
        return userId;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public void updateEntity(String email, String password){
        this.email = email;
        this.password = password;
    }
}
