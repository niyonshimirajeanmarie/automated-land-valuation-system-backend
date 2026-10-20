package rw.auca.landvaluation.controller;

import rw.auca.landvaluation.domain.PropertyType;
import rw.auca.landvaluation.service.PropertyTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/property-types")
public class PropertyTypeController {

    private final PropertyTypeService propertyTypeService;

    public PropertyTypeController(PropertyTypeService propertyTypeService) {
        this.propertyTypeService = propertyTypeService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody PropertyType propertyType) {
        String message = propertyTypeService.savePropertyType(propertyType);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PropertyType>> getAll() {
        return ResponseEntity.ok(propertyTypeService.getAllPropertyTypes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<PropertyType> propertyType = propertyTypeService.getPropertyTypeById(id);
        if (propertyType.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The property type with that id does not exist");
        }
        return ResponseEntity.ok(propertyType.get());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody PropertyType propertyType) {
        String message = propertyTypeService.updatePropertyType(id, propertyType);
        if (message.equals("The property type with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = propertyTypeService.deletePropertyType(id);
        if (message.equals("The property type with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @GetMapping("/unused")
    public ResponseEntity<List<PropertyType>> getUnused() {
        return ResponseEntity.ok(propertyTypeService.getUnusedPropertyTypes());
    }
}
