package Chackaton.com.Organization;

import Chackaton.com.Users.Users;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "organization_memberships")
public class OrganizationMembership {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @ElementCollection(targetClass = OrganizationRole.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "membership_roles", joinColumns = @JoinColumn(name = "membership_id"))
    @Enumerated(EnumType.STRING)
    private Set<OrganizationRole> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Set<OrganizationRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<OrganizationRole> roles) {
        this.roles = roles;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
