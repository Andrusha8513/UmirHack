package Chackaton.com.Users;


import Chackaton.com.Organization.OrganizationMembership;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "users")
public class Users implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String lastName;
    private String surName;

    private String email;
    private String password;
    private String confirmationCode;
    private boolean enabled  = false;
    private String pendingEmail;
    private String emailChangeCode;


    private LocalDateTime registrationDate;

    private String passwordResetCode;
    private LocalDateTime passwordResetCodeExpiryDate;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrganizationMembership> memberships = new HashSet<>();

    @ElementCollection(targetClass = Role.class , fetch = FetchType.LAZY)
    @CollectionTable(name = "users_role" , joinColumns = @JoinColumn(name = "users_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }


    public Users(Long id,
                 String email,
                 String password){
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public Users(){
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if(lastName.length() > 20){
            throw new IllegalArgumentException("Фамилия не может быть такой длинной ");
        }
        this.lastName = lastName;
    }

    public Set<OrganizationMembership> getMemberships() {
        return memberships;
    }

    public void setMemberships(Set<OrganizationMembership> memberships) {
        this.memberships = memberships;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name.length() > 20){
            throw new IllegalArgumentException("Имя не может таким длинным");
        }
        this.name = name;
    }

    public String getSurname() {
        return surName;
    }

    public void setSurname(String surname) {
        if(surname.length() > 20){
            throw  new IllegalArgumentException("Отчество не может быть таким длинным");
        }
        this.surName = surname;
    }

    public void setEmail(String phone){
        this.email = phone;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getEmail(){
        return email;}

    public String getPassword(){
        return password;
    }

    public Long getId(){
        return id;
    }

    public String getConfirmationCode() {
        return confirmationCode;
    }

    public void setConfirmationCode(String confirmationCode) {
        this.confirmationCode = confirmationCode;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setRegistrationDate(LocalDateTime registrationDate){
        this.registrationDate = registrationDate;
    }
    public LocalDateTime getRegistrationDate(){
        return registrationDate;
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
    public void setEmailChangeCode(String emailChangeCode){
        this.emailChangeCode = emailChangeCode;
    }

    public String getEmailChangeCode(){
        return emailChangeCode;
    }

    public String getPendingEmail() {
        return pendingEmail;
    }

    public void setPendingEmail(String pendingEmail) {
        this.pendingEmail = pendingEmail;
    }

    @PrePersist
    public void prePersist(){
        this.registrationDate = LocalDateTime.now();
    }
}