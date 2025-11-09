package Chackaton.com.Organization;

import Chackaton.com.Users.Users;
import jakarta.persistence.*;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "organization")
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;

    @OneToOne(cascade = CascadeType.REFRESH , fetch = FetchType.EAGER)
    private Users owner;

    private LocalDateTime createdAt;

    public Organization(String address,
                        LocalDateTime createdAt,
                        Long id,
                        String name,
                        Users owner) {
        this.address = address;
        this.createdAt = createdAt;
        this.id = id;
        this.name = name;
        this.owner = owner;
    }

    public Organization() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
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

    public Users getOwner() {
        return owner;
    }

    public void setOwner(Users owner) {
        this.owner = owner;
    }
}
