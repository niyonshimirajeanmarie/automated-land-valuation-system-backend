package rw.auca.landvaluation.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "valuer")
public class Valuer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "license_number", nullable = false)
    private String licenseNumber;

    @ManyToOne
    @JoinColumn(name = "district_office_id", nullable = true)
    private DistrictOffice districtOffice;

    @ManyToMany
    @JoinTable(
        name = "valuer_property_type",
        joinColumns = @JoinColumn(name = "valuer_id"),
        inverseJoinColumns = @JoinColumn(name = "property_type_id")
    )
    private List<PropertyType> propertyTypes = new ArrayList<>();

    public Valuer() {
    }

    public Valuer(String firstName, String lastName, String licenseNumber, DistrictOffice districtOffice, List<PropertyType> propertyTypes) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.licenseNumber = licenseNumber;
        this.districtOffice = districtOffice;
        if (propertyTypes != null) {
            this.propertyTypes = propertyTypes;
        }
    }

    public Valuer(UUID id, String firstName, String lastName, String licenseNumber, DistrictOffice districtOffice, List<PropertyType> propertyTypes) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.licenseNumber = licenseNumber;
        this.districtOffice = districtOffice;
        if (propertyTypes != null) {
            this.propertyTypes = propertyTypes;
        }
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public DistrictOffice getDistrictOffice() {
        return districtOffice;
    }

    public void setDistrictOffice(DistrictOffice districtOffice) {
        this.districtOffice = districtOffice;
    }

    public List<PropertyType> getPropertyTypes() {
        return propertyTypes;
    }

    public void setPropertyTypes(List<PropertyType> propertyTypes) {
        this.propertyTypes = propertyTypes;
    }
}
