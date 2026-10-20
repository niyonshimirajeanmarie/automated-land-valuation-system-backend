package rw.auca.landvaluation.controller;

import rw.auca.landvaluation.domain.Valuer;
import rw.auca.landvaluation.service.ValuerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/valuers")
public class ValuerController {

    private final ValuerService valuerService;

    public ValuerController(ValuerService valuerService) {
        this.valuerService = valuerService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Valuer valuer) {
        String message = valuerService.saveValuer(valuer);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Valuer>> getAll() {
        return ResponseEntity.ok(valuerService.getAllValuers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<Valuer> valuer = valuerService.getValuerById(id);
        if (valuer.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The valuer with that id does not exist");
        }
        return ResponseEntity.ok(valuer.get());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Valuer valuer) {
        String message = valuerService.updateValuer(id, valuer);
        if (message.equals("The valuer with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = valuerService.deleteValuer(id);
        if (message.equals("The valuer with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @GetMapping("/by-property-type")
    public ResponseEntity<List<Valuer>> getByPropertyType(@RequestParam String name) {
        return ResponseEntity.ok(valuerService.getValuersByPropertyType(name));
    }

    @PutMapping("/{valuerId}/property-types/{propertyTypeId}")
    public ResponseEntity<String> addPropertyType(@PathVariable UUID valuerId, @PathVariable UUID propertyTypeId) {
        String message = valuerService.addPropertyTypeToValuer(valuerId, propertyTypeId);
        if (message.equals("The valuer with that id does not exist") || message.equals("That property type does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        if (message.equals("The valuer already has that property type")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @PatchMapping("/{valuerId}/district-office")
    public ResponseEntity<String> moveDistrictOffice(@PathVariable UUID valuerId, @RequestParam int officeNumber) {
        String message = valuerService.moveDistrictOffice(valuerId, officeNumber);
        if (message.equals("The valuer with that id does not exist") || message.equals("The district office with that officeNumber does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        if (message.equals("That district office is taken by another valuer")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }
        return ResponseEntity.ok(message);
    }
}
