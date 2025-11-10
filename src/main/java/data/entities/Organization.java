package data.entities;

import core.enums.OrganizationType;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "organizations")
public class Organization extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private UUID organizationId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrganizationType organizationType;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "practitioner_id", nullable = false)
    private List<Practitioner> practitioners = new ArrayList<>();

    public Organization() {
    }

    public Organization(OrganizationType organizationType, Location location) {
        this.organizationType = organizationType;
        this.location = location;
    }

    public UUID getOrganizationId() {
        return organizationId;
    }

    public OrganizationType getOrganizationType() {
        return organizationType;
    }

    public Location getLocation() {
        return location;
    }

    public List<Practitioner> getPractitioners() {
        return practitioners;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void addPractitioner(Practitioner practitioner) {
        practitioners.add(practitioner);
        practitioner.setOrganization(this);
    }

    public void removePractitioner(Practitioner practitioner) {
        practitioners.remove(practitioner);
        practitioner.setOrganization(null);
    }

    @Override
    public String toString() {
        return "OrganizationDb{" +
                "organizationId=" + organizationId +
                ", organizationType=" + organizationType +
                '}';
    }
}
