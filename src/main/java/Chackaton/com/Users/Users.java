package Chackaton.com.Users;

import jakarta.persistence.*;

import javax.management.relation.Role;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String confirmationCode;
    private boolean enabled = false;

    private String pendingEmail;
    private String emailChangeCode;

    private LocalDateTime registrationDate;

    private String passwordResetCode;
    private LocalDateTime passwordResetCodeExpiryDate;

    @ElementCollection(targetClass = Role.class , fetch = FetchType.LAZY)
    @CollectionTable(name = "users_role" , joinColumns = @JoinColumn("users_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    public Users(Long id,
                 String email,
                 String password){
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public Users(){}

    public String getConfirmationCode() {
        return confirmationCode;
    }

    public void setConfirmationCode(String confirmationCode) {
        this.confirmationCode = confirmationCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailChangeCode() {
        return emailChangeCode;
    }

    public void setEmailChangeCode(String emailChangeCode) {
        this.emailChangeCode = emailChangeCode;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordResetCode() {
        return passwordResetCode;
    }

    public void setPasswordResetCode(String passwordResetCode) {
        this.passwordResetCode = passwordResetCode;
    }

    public LocalDateTime getPasswordResetCodeExpiryDate() {
        return passwordResetCodeExpiryDate;
    }

    public void setPasswordResetCodeExpiryDate(LocalDateTime passwordResetCodeExpiryDate) {
        this.passwordResetCodeExpiryDate = passwordResetCodeExpiryDate;
    }

    public String getPendingEmail() {
        return pendingEmail;
    }

    public void setPendingEmail(String pendingEmail) {
        this.pendingEmail = pendingEmail;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
