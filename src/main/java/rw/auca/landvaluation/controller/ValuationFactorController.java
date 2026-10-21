package rw.auca.landvaluation.controller;

import rw.auca.landvaluation.domain.ValuationFactor;
import rw.auca.landvaluation.service.ValuationFactorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/valuation-factors")
public class ValuationFactorController {

    private final ValuationFactorService valuationFactorService;

    public ValuationFactorController(ValuationFactorService valuationFactorService) {
        this.valuationFactorService = valuationFactorService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestParam UUID valuationRequestId, @RequestBody ValuationFactor factor) {
        String message = valuationFactorService.saveValuationFactor(valuationRequestId, factor);
        if (message.equals("The valuation request with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        if (message.equals("Valuation factors are only for VALUED valuation requests")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ValuationFactor>> getAll() {
        return ResponseEntity.ok(valuationFactorService.getAllValuationFactors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<ValuationFactor> opt = valuationFactorService.getValuationFactorById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The valuation factor with that id does not exist");
        }
        return ResponseEntity.ok(opt.get());
    }

    @GetMapping("/landowner/{nationalId}")
    public ResponseEntity<List<ValuationFactor>> getByLandowner(@PathVariable String nationalId) {
        return ResponseEntity.ok(valuationFactorService.getValuationFactorsByLandownerNationalId(nationalId));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = valuationFactorService.deleteValuationFactor(id);
        if (message.equals("The valuation factor with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.ok(message);
    }
}
