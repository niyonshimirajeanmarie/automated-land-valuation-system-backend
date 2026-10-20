package rw.auca.landvaluation.controller;

import rw.auca.landvaluation.domain.DistrictOffice;
import rw.auca.landvaluation.service.DistrictOfficeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/district-offices")
public class DistrictOfficeController {

    private final DistrictOfficeService districtOfficeService;

    public DistrictOfficeController(DistrictOfficeService districtOfficeService) {
        this.districtOfficeService = districtOfficeService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody DistrictOffice districtOffice) {
        String message = districtOfficeService.saveDistrictOffice(districtOffice);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/all")
    public ResponseEntity<List<DistrictOffice>> getAll() {
        return ResponseEntity.ok(districtOfficeService.getAllDistrictOffices());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<DistrictOffice> districtOffice = districtOfficeService.getDistrictOfficeById(id);
        if (districtOffice.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The district office with that id does not exist");
        }
        return ResponseEntity.ok(districtOffice.get());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody DistrictOffice districtOffice) {
        String message = districtOfficeService.updateDistrictOffice(id, districtOffice);
        if (message.equals("The district office with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = districtOfficeService.deleteDistrictOffice(id);
        if (message.equals("The district office with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @GetMapping("/free")
    public ResponseEntity<List<DistrictOffice>> getFree() {
        return ResponseEntity.ok(districtOfficeService.getFreeDistrictOffices());
    }
}
