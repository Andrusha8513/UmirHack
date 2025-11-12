package Chackaton.com.Organization;

import Chackaton.com.Users.Users;
import jakarta.persistence.*;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "organization")
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;

@OneToMany(mappedBy = "organization", cascade = CascadeType.ALL, orphanRemoval = true)
private Set<OrganizationMembership> members = new HashSet<>();
    private LocalDateTime createdAt;

    public Organization(String address,
                        LocalDateTime createdAt,
                        Long id,
                        String name) {
        this.address = address;
        this.createdAt = createdAt;
        this.id = id;
        this.name = name;
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




    public Set<OrganizationMembership> getMembers() {
        return members;
    }

    public void setMembers(Set<OrganizationMembership> members) {
        this.members = members;
    }

    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }
}
