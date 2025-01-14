package fr.pantheonsorbonne.model;
import fr.pantheonsorbonne.enums.Roles;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Entity
@Table(name = "User", indexes = @Index(name = "index_email", columnList = "email"))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String firstName;
    private String email;
    private String password;
    private Date creationDate = new Date();
    private Date deletionDate;

    @Enumerated(EnumType.STRING)
    private Roles role = Roles.USER;

    @ManyToMany(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinTable(name = "User_User",
            joinColumns = @JoinColumn(name = "User_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "Follower_id", referencedColumnName = "id"))
    private List<User> Users;

    public User() {
    }
    public User(Long id, String name, String firstName, String email, String password) {
        this.id = id;
        this.name = name;
        this.firstName = firstName;
        this.email = email;
        this.password = password;
    }

    public User(String name, String firstName, String email, String password) {
        this.name = name;
        this.firstName = firstName;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", firstName='" + firstName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", creationDate=" + creationDate +
                ", deletionDate=" + deletionDate +
                //", Users=" + this.Users. +
                '}';
    }

    public List<User> getUsers() {
        return Users;
    }

    public void setUsers(List<User> Users) {
        this.Users = Users;
    }

    public void addFollower(User user) {
        this.Users.add(user);
    }

    public void removeFollower(User user) {
        this.Users.remove(user);
    }

    public boolean isFollowing(User user) {
        return this.Users.contains(user);
    }

    public void subscribeTo(User user) {
        this.addFollower(user);
    }

    public void setDeletionDate() {
        this.deletionDate = new Date();
    }

    public List<Long> getUsersIds() {
        return this.Users.stream().map(User::getId).collect(Collectors.toList());
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }
}
