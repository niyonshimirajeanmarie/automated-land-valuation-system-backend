package rw.auca.landvaluation.service;

import rw.auca.landvaluation.domain.Landowner;
import rw.auca.landvaluation.domain.ValuationRequest;
import rw.auca.landvaluation.domain.ValuationStatus;
import rw.auca.landvaluation.domain.Valuer;
import rw.auca.landvaluation.repository.LandownerRepository;
import rw.auca.landvaluation.repository.ValuationFactorRepository;
import rw.auca.landvaluation.repository.ValuationRequestRepository;
import rw.auca.landvaluation.repository.ValuerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ValuationRequestService {

    private final ValuationRequestRepository valuationRequestRepository;
    private final LandownerRepository landownerRepository;
    private final ValuerRepository valuerRepository;
    private final ValuationFactorRepository valuationFactorRepository;

    public ValuationRequestService(ValuationRequestRepository valuationRequestRepository,
                                   LandownerRepository landownerRepository,
                                   ValuerRepository valuerRepository,
                                   ValuationFactorRepository valuationFactorRepository) {
        this.valuationRequestRepository = valuationRequestRepository;
        this.landownerRepository = landownerRepository;
        this.valuerRepository = valuerRepository;
        this.valuationFactorRepository = valuationFactorRepository;
    }

    public String saveValuationRequest(ValuationRequest request) {
        if (request.getRequestDate() != null && request.getRequestDate().isBefore(LocalDate.now())) {
            return "Valuation request date cannot be in the past";
        }

        if (request.getLandowner() != null && request.getLandowner().getId() != null) {
            landownerRepository.findById(request.getLandowner().getId()).ifPresent(request::setLandowner);
        }

        if (request.getValuer() != null && request.getValuer().getId() != null) {
            Optional<Valuer> valuerOpt = valuerRepository.findById(request.getValuer().getId());
            if (valuerOpt.isPresent()) {
                Valuer valuer = valuerOpt.get();
                request.setValuer(valuer);
                boolean hasActive = valuationRequestRepository.existsByValuerAndRequestDateAndStatusNot(
                        valuer, request.getRequestDate(), ValuationStatus.DECLINED);
                if (hasActive) {
                    return "Valuer already has a valuation request on that date";
                }
            }
        }

        request.setStatus(ValuationStatus.SUBMITTED);
        valuationRequestRepository.save(request);
        return "Valuation request saved successfully";
    }

    public List<ValuationRequest> getAllValuationRequests() {
        return valuationRequestRepository.findAll();
    }

    public Optional<ValuationRequest> getValuationRequestById(UUID id) {
        return valuationRequestRepository.findById(id);
    }

    public String updateValuationRequest(UUID id, ValuationRequest updated) {
        Optional<ValuationRequest> existingOpt = valuationRequestRepository.findById(id);
        if (existingOpt.isEmpty()) {
            return "The valuation request with that id does not exist";
        }
        ValuationRequest existing = existingOpt.get();
        existing.setRequestDate(updated.getRequestDate());
        existing.setUpi(updated.getUpi());
        existing.setAreaSqm(updated.getAreaSqm());
        if (updated.getLandowner() != null && updated.getLandowner().getId() != null) {
            landownerRepository.findById(updated.getLandowner().getId()).ifPresent(existing::setLandowner);
        }
        if (updated.getValuer() != null && updated.getValuer().getId() != null) {
            valuerRepository.findById(updated.getValuer().getId()).ifPresent(existing::setValuer);
        }
        valuationRequestRepository.save(existing);
        return "Valuation request updated successfully";
    }

    public String deleteValuationRequest(UUID id) {
        Optional<ValuationRequest> existingOpt = valuationRequestRepository.findById(id);
        if (existingOpt.isEmpty()) {
            return "The valuation request with that id does not exist";
        }
        ValuationRequest request = existingOpt.get();
        if (valuationFactorRepository.existsByValuationRequest(request)) {
            return "That valuation request has valuation factors, delete them first";
        }
        valuationRequestRepository.deleteById(id);
        return "Valuation request deleted successfully";
    }

    public List<ValuationRequest> getValuationRequestsByStatus(ValuationStatus status) {
        return valuationRequestRepository.findByStatusOrderByRequestDateAsc(status);
    }

    public List<ValuationRequest> getValuationRequestsBetween(LocalDate start, LocalDate end) {
        return valuationRequestRepository.findByRequestDateBetweenOrderByRequestDateAsc(start, end);
    }

    public String updateStatus(UUID id, ValuationStatus newStatus) {
        Optional<ValuationRequest> existingOpt = valuationRequestRepository.findById(id);
        if (existingOpt.isEmpty()) {
            return "The valuation request with that id does not exist";
        }
        ValuationRequest request = existingOpt.get();
        ValuationStatus current = request.getStatus();

        boolean allowed = false;
        if (current == ValuationStatus.SUBMITTED && (newStatus == ValuationStatus.VERIFIED || newStatus == ValuationStatus.DECLINED)) {
            allowed = true;
        } else if (current == ValuationStatus.VERIFIED && (newStatus == ValuationStatus.VALUED || newStatus == ValuationStatus.DECLINED)) {
            allowed = true;
        }

        if (!allowed) {
            return "Cannot change status from " + current + " to " + newStatus;
        }

        request.setStatus(newStatus);
        valuationRequestRepository.save(request);
        return "Valuation request status updated successfully";
    }

    public List<Object[]> getStatsByStatus() {
        return valuationRequestRepository.countValuationRequestsByStatus();
    }

    @Transactional
    public String cancelValuerDay(UUID valuerId, LocalDate date) {
        int count = valuationRequestRepository.cancelDay(
                valuerId, date, ValuationStatus.DECLINED, ValuationStatus.VALUED);
        return count + " valuation requests cancelled";
    }

    public Page<ValuationRequest> getValuationRequestsPage(Pageable pageable) {
        return valuationRequestRepository.findAll(pageable);
    }
}
