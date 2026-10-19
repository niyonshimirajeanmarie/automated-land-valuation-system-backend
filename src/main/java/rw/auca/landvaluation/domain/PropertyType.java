package rw.auca.landvaluation.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "property_type")
public class PropertyType {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "propertyTypes")
    @JsonIgnore
    private List<Valuer> valuers = new ArrayList<>();

    public PropertyType() {
    }

    public PropertyType(String name) {
        this.name = name;
    }

    public PropertyType(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Valuer> getValuers() {
        return valuers;
    }

    public void setValuers(List<Valuer> valuers) {
        this.valuers = valuers;
    }
}
