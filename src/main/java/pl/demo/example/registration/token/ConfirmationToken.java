package pl.demo.example.registration.token;

import pl.demo.example.appuser.AppUser;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ConfirmationToken {

    @SequenceGenerator(
            name = "confirmation_token_sequence",
            sequenceName = "confirmation_token_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "confirmation_token_sequence"

    )
    private Long id;
    @Column(nullable = false)
    private String token;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime expiresAt;

    private LocalDateTime confirmedAt;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "app_user_id"
    )
    private AppUser appUser;

    public ConfirmationToken() {
    }

    public ConfirmationToken(String token, LocalDateTime createdAt, LocalDateTime expiresAt,AppUser appUser) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.appUser = appUser;
    }

    public String getToken() {
        return token;
    }

    public ConfirmationToken setToken(String token) {
        this.token = token;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public ConfirmationToken setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public ConfirmationToken setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
        return this;
    }

    public LocalDateTime getConfirmedAt() {
        return confirmedAt;
    }

    public ConfirmationToken setConfirmedAt(LocalDateTime confirmedAt) {
        this.confirmedAt = confirmedAt;
        return this;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public ConfirmationToken setAppUser(AppUser appUser) {
        this.appUser = appUser;
        return this;
    }
}
