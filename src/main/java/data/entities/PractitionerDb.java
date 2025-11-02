package data.entities;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.*;
import core.enums.UserType;

import java.util.UUID;

@Entity
@Table(
        name = "practitioners",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
        }
)
public class PractitionerDb extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID doctorId;

    @Column(nullable = false)
    public String fullName;

    @Column(nullable = false, unique = true)
    public String email;

    @Column(nullable = false)
    public String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public UserType userType;

    @Column(nullable = true)
    public String organization; // store organization name or ID directly if no separate table

    // Default constructor for JPA
    public PractitionerDb() {
    }

    // Constructor for creating practitioners
    public PractitionerDb(String fullName, String email, String password, UserType userType, String organization) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.organization = organization;
    }
}
