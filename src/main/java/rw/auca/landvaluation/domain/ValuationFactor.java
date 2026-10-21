package rw.auca.landvaluation.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "valuation_factor")
public class ValuationFactor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "factor_name", nullable = false)
    private String factorName;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private int score;

    @Column(name = "assessed_date", nullable = false)
    private LocalDate assessedDate;

    @ManyToOne
    @JoinColumn(name = "valuation_request_id", nullable = false)
    private ValuationRequest valuationRequest;

    public ValuationFactor() {
    }

    public ValuationFactor(String factorName, String comment, int score, LocalDate assessedDate, ValuationRequest valuationRequest) {
        this.factorName = factorName;
        this.comment = comment;
        this.score = score;
        this.assessedDate = assessedDate;
        this.valuationRequest = valuationRequest;
    }

    public ValuationFactor(UUID id, String factorName, String comment, int score, LocalDate assessedDate, ValuationRequest valuationRequest) {
        this.id = id;
        this.factorName = factorName;
        this.comment = comment;
        this.score = score;
        this.assessedDate = assessedDate;
        this.valuationRequest = valuationRequest;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFactorName() {
        return factorName;
    }

    public void setFactorName(String factorName) {
        this.factorName = factorName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public LocalDate getAssessedDate() {
        return assessedDate;
    }

    public void setAssessedDate(LocalDate assessedDate) {
        this.assessedDate = assessedDate;
    }

    public ValuationRequest getValuationRequest() {
        return valuationRequest;
    }

    public void setValuationRequest(ValuationRequest valuationRequest) {
        this.valuationRequest = valuationRequest;
    }
}
