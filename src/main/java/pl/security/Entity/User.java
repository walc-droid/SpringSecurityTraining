//package pl.security.Entity;
//
//
//import javax.persistence.*;
//
//@Entity
//@Table(name = "User")
//public class User {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private int id;
//    private String userName;
//    private String password;
//    private boolean active;
//    private String roles;
//
//
//    public int getId() {
//        return id;
//    }
//
//    public User setId(int id) {
//        this.id = id;
//        return this;
//    }
//
//    public String getUserName() {
//        return userName;
//    }
//
//    public User setUserName(String userName) {
//        this.userName = userName;
//        return this;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public User setPassword(String password) {
//        this.password = password;
//        return this;
//    }
//
//    public boolean isActive() {
//        return active;
//    }
//
//    public User setActive(boolean active) {
//        this.active = active;
//        return this;
//    }
//
//    public String getRoles() {
//        return roles;
//    }
//
//    public User setRoles(String roles) {
//        this.roles = roles;
//        return this;
//    }
//}
