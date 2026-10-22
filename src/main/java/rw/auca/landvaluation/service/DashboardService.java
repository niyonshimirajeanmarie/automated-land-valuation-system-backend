package rw.auca.landvaluation.service;

import rw.auca.landvaluation.domain.ValuationStatus;
import rw.auca.landvaluation.repository.LandownerRepository;
import rw.auca.landvaluation.repository.ValuationFactorRepository;
import rw.auca.landvaluation.repository.ValuationRequestRepository;
import rw.auca.landvaluation.repository.ValuerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class DashboardService {

    private final LandownerRepository landownerRepository;
    private final ValuerRepository valuerRepository;
    private final DistrictOfficeService districtOfficeService;
    private final ValuationRequestRepository valuationRequestRepository;
    private final ValuationFactorRepository valuationFactorRepository;

    public DashboardService(LandownerRepository landownerRepository,
                            ValuerRepository valuerRepository,
                            DistrictOfficeService districtOfficeService,
                            ValuationRequestRepository valuationRequestRepository,
                            ValuationFactorRepository valuationFactorRepository) {
        this.landownerRepository = landownerRepository;
        this.valuerRepository = valuerRepository;
        this.districtOfficeService = districtOfficeService;
        this.valuationRequestRepository = valuationRequestRepository;
        this.valuationFactorRepository = valuationFactorRepository;
    }

    public Map<String, Long> getSummary() {
        Map<String, Long> summary = new LinkedHashMap<>();
        summary.put("landowners", landownerRepository.count());
        summary.put("valuers", valuerRepository.count());
        summary.put("freeDistrictOffices", (long) districtOfficeService.getFreeDistrictOffices().size());
        summary.put("requestsToday", valuationRequestRepository.countByRequestDate(LocalDate.now()));
        summary.put("submitted", valuationRequestRepository.countByStatus(ValuationStatus.SUBMITTED));
        summary.put("valuationFactors", valuationFactorRepository.count());
        return summary;
    }
}
