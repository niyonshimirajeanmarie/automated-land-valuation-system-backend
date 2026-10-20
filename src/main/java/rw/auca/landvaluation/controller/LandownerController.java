package rw.auca.landvaluation.controller;

import rw.auca.landvaluation.domain.Landowner;
import rw.auca.landvaluation.service.LandownerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/landowners")
public class LandownerController {

    private final LandownerService landownerService;

    public LandownerController(LandownerService landownerService) {
        this.landownerService = landownerService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Landowner landowner) {
        String message = landownerService.saveLandowner(landowner);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Landowner>> getAll() {
        return ResponseEntity.ok(landownerService.getAllLandowners());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<Landowner> landowner = landownerService.getLandownerById(id);
        if (landowner.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The landowner with that id does not exist");
        }
        return ResponseEntity.ok(landowner.get());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Landowner landowner) {
        String message = landownerService.updateLandowner(id, landowner);
        if (message.equals("The landowner with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = landownerService.deleteLandowner(id);
        if (message.equals("The landowner with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @GetMapping("/by-last-name")
    public ResponseEntity<List<Landowner>> getByLastName(@RequestParam String lastName) {
        return ResponseEntity.ok(landownerService.getLandownersByLastName(lastName));
    }

    @GetMapping("/of-valuer/{valuerId}")
    public ResponseEntity<?> getLandownersOfValuer(@PathVariable UUID valuerId) {
        Optional<List<Landowner>> result = landownerService.getLandownersOfValuer(valuerId);
        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The valuer with that id does not exist");
        }
        return ResponseEntity.ok(result.get());
    }
}
