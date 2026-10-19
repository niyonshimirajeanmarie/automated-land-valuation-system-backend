package rw.auca.landvaluation.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "valuation_request")
public class ValuationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "request_date", nullable = false)
    private LocalDate requestDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ValuationStatus status;

    @Column(nullable = false)
    private String upi;

    @Column(name = "area_sqm", nullable = false)
    private double areaSqm;

    @ManyToOne
    @JoinColumn(name = "landowner_id", nullable = false)
    private Landowner landowner;

    @ManyToOne
    @JoinColumn(name = "valuer_id", nullable = false)
    private Valuer valuer;

    public ValuationRequest() {
    }

    public ValuationRequest(LocalDate requestDate, ValuationStatus status, String upi, double areaSqm, Landowner landowner, Valuer valuer) {
        this.requestDate = requestDate;
        this.status = status;
        this.upi = upi;
        this.areaSqm = areaSqm;
        this.landowner = landowner;
        this.valuer = valuer;
    }

    public ValuationRequest(UUID id, LocalDate requestDate, ValuationStatus status, String upi, double areaSqm, Landowner landowner, Valuer valuer) {
        this.id = id;
        this.requestDate = requestDate;
        this.status = status;
        this.upi = upi;
        this.areaSqm = areaSqm;
        this.landowner = landowner;
        this.valuer = valuer;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public ValuationStatus getStatus() {
        return status;
    }

    public void setStatus(ValuationStatus status) {
        this.status = status;
    }

    public String getUpi() {
        return upi;
    }

    public void setUpi(String upi) {
        this.upi = upi;
    }

    public double getAreaSqm() {
        return areaSqm;
    }

    public void setAreaSqm(double areaSqm) {
        this.areaSqm = areaSqm;
    }

    public Landowner getLandowner() {
        return landowner;
    }

    public void setLandowner(Landowner landowner) {
        this.landowner = landowner;
    }

    public Valuer getValuer() {
        return valuer;
    }

    public void setValuer(Valuer valuer) {
        this.valuer = valuer;
    }
}
