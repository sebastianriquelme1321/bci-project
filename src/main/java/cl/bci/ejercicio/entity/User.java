package cl.bci.ejercicio.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Phone> phones = new ArrayList<>();

    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Column(name = "token")
    private String token;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    @PrePersist
    protected void onCreate() {
        created = LocalDateTime.now();
        lastLogin = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        lastLogin = LocalDateTime.now();
    }
} 