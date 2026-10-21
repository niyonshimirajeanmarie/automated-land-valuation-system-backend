package rw.auca.landvaluation.controller;

import rw.auca.landvaluation.domain.ValuationRequest;
import rw.auca.landvaluation.domain.ValuationStatus;
import rw.auca.landvaluation.service.ValuationRequestService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/valuation-requests")
public class ValuationRequestController {

    private final ValuationRequestService valuationRequestService;

    public ValuationRequestController(ValuationRequestService valuationRequestService) {
        this.valuationRequestService = valuationRequestService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody ValuationRequest request) {
        String message = valuationRequestService.saveValuationRequest(request);
        if (message.equals("Valuation request date cannot be in the past")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        if (message.equals("Valuer already has a valuation request on that date")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ValuationRequest>> getAll() {
        return ResponseEntity.ok(valuationRequestService.getAllValuationRequests());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<ValuationRequest> request = valuationRequestService.getValuationRequestById(id);
        if (request.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The valuation request with that id does not exist");
        }
        return ResponseEntity.ok(request.get());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody ValuationRequest request) {
        String message = valuationRequestService.updateValuationRequest(id, request);
        if (message.equals("The valuation request with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = valuationRequestService.deleteValuationRequest(id);
        if (message.equals("The valuation request with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        if (message.equals("That valuation request has valuation factors, delete them first")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @GetMapping("/by-status")
    public ResponseEntity<List<ValuationRequest>> getByStatus(@RequestParam ValuationStatus status) {
        return ResponseEntity.ok(valuationRequestService.getValuationRequestsByStatus(status));
    }

    @GetMapping("/between")
    public ResponseEntity<List<ValuationRequest>> getBetween(@RequestParam String start, @RequestParam String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return ResponseEntity.ok(valuationRequestService.getValuationRequestsBetween(startDate, endDate));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updateStatus(@PathVariable UUID id, @RequestParam ValuationStatus status) {
        String message = valuationRequestService.updateStatus(id, status);
        if (message.equals("The valuation request with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        if (message.startsWith("Cannot change status")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @GetMapping("/stats/by-status")
    public ResponseEntity<List<Object[]>> getStatsByStatus() {
        return ResponseEntity.ok(valuationRequestService.getStatsByStatus());
    }

    @PatchMapping("/cancel-day")
    public ResponseEntity<String> cancelDay(@RequestParam UUID valuerId, @RequestParam String date) {
        LocalDate localDate = LocalDate.parse(date);
        String result = valuationRequestService.cancelValuerDay(valuerId, localDate);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<ValuationRequest>> getValuationRequestsPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "requestDate,desc") String sort) {
        
        String[] sortParts = sort.split(",");
        String sortProperty = sortParts[0];
        Sort.Direction direction = Sort.Direction.DESC;
        if (sortParts.length > 1 && sortParts[1].equalsIgnoreCase("asc")) {
            direction = Sort.Direction.ASC;
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortProperty));
        return ResponseEntity.ok(valuationRequestService.getValuationRequestsPage(pageable));
    }
}
