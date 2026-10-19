package rw.auca.landvaluation.domain;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "district_office")
public class DistrictOffice {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "office_number", nullable = false)
    private int officeNumber;

    @Column(nullable = false)
    private String name;

    public DistrictOffice() {
    }

    public DistrictOffice(int officeNumber, String name) {
        this.officeNumber = officeNumber;
        this.name = name;
    }

    public DistrictOffice(UUID id, int officeNumber, String name) {
        this.id = id;
        this.officeNumber = officeNumber;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getOfficeNumber() {
        return officeNumber;
    }

    public void setOfficeNumber(int officeNumber) {
        this.officeNumber = officeNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
