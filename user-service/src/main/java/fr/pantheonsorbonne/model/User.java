package fr.pantheonsorbonne.model;
import fr.pantheonsorbonne.enums.Roles;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String firstName;
    private String email;
    private String password;
    private LocalDate creationDate = LocalDate.now();
    private LocalDate deletionDate = null;
    @Enumerated(EnumType.STRING)
    private Roles role = Roles.USER;
    @UuidGenerator
    private String uuid;

    @ManyToMany(targetEntity = User.class)
    @JoinTable(name = "User_User",
            joinColumns = @JoinColumn(name = "User_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "Follower_id", referencedColumnName = "id"))
    private List<User> followers;

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
        return followers;
    }

    public void setUsers(List<User> Users) {
        this.followers = Users;
    }

    public void addFollower(User user) {
        this.followers.add(user);
    }

    public void removeFollower(User user) {
        this.followers.remove(user);
    }

    public boolean isFollowing(User user) {
        return this.followers.contains(user);
    }

    public void subscribeTo(User user) {
        this.addFollower(user);
    }

    public void setDeletionDate() {
        this.deletionDate = LocalDate.now();
    }

    public void setDeletionDate(LocalDate deletionDate) {
        this.deletionDate = deletionDate;
    }

    public LocalDate getDeletionDate() {
        return deletionDate;
    }

    public List<Long> getUsersIds() {
        return this.followers.stream().map(User::getId).collect(Collectors.toList());
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
