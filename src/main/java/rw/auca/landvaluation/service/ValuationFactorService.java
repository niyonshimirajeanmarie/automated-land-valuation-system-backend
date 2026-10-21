package rw.auca.landvaluation.service;

import rw.auca.landvaluation.domain.ValuationFactor;
import rw.auca.landvaluation.domain.ValuationRequest;
import rw.auca.landvaluation.domain.ValuationStatus;
import rw.auca.landvaluation.repository.ValuationFactorRepository;
import rw.auca.landvaluation.repository.ValuationRequestRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ValuationFactorService {

    private final ValuationFactorRepository valuationFactorRepository;
    private final ValuationRequestRepository valuationRequestRepository;

    public ValuationFactorService(ValuationFactorRepository valuationFactorRepository,
                                  ValuationRequestRepository valuationRequestRepository) {
        this.valuationFactorRepository = valuationFactorRepository;
        this.valuationRequestRepository = valuationRequestRepository;
    }

    public String saveValuationFactor(UUID valuationRequestId, ValuationFactor factor) {
        Optional<ValuationRequest> vrOpt = valuationRequestRepository.findById(valuationRequestId);
        if (vrOpt.isEmpty()) {
            return "The valuation request with that id does not exist";
        }
        ValuationRequest vr = vrOpt.get();
        if (vr.getStatus() != ValuationStatus.VALUED) {
            return "Valuation factors are only for VALUED valuation requests";
        }
        factor.setAssessedDate(LocalDate.now());
        factor.setValuationRequest(vr);
        valuationFactorRepository.save(factor);
        return "Valuation factor saved successfully";
    }

    public List<ValuationFactor> getAllValuationFactors() {
        return valuationFactorRepository.findAll();
    }

    public Optional<ValuationFactor> getValuationFactorById(UUID id) {
        return valuationFactorRepository.findById(id);
    }

    public List<ValuationFactor> getValuationFactorsByLandownerNationalId(String nationalId) {
        return valuationFactorRepository.findByLandownerNationalId(nationalId);
    }

    public String deleteValuationFactor(UUID id) {
        Optional<ValuationFactor> opt = valuationFactorRepository.findById(id);
        if (opt.isEmpty()) {
            return "The valuation factor with that id does not exist";
        }
        valuationFactorRepository.deleteById(id);
        return "Valuation factor deleted successfully";
    }
}
