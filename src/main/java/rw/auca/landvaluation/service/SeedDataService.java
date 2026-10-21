package rw.auca.landvaluation.service;

import rw.auca.landvaluation.domain.*;
import rw.auca.landvaluation.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class SeedDataService {

    private final PropertyTypeRepository propertyTypeRepository;
    private final DistrictOfficeRepository districtOfficeRepository;
    private final ValuerRepository valuerRepository;
    private final LandownerRepository landownerRepository;
    private final ValuationRequestRepository valuationRequestRepository;
    private final ValuationFactorRepository valuationFactorRepository;

    public SeedDataService(PropertyTypeRepository propertyTypeRepository,
                           DistrictOfficeRepository districtOfficeRepository,
                           ValuerRepository valuerRepository,
                           LandownerRepository landownerRepository,
                           ValuationRequestRepository valuationRequestRepository,
                           ValuationFactorRepository valuationFactorRepository) {
        this.propertyTypeRepository = propertyTypeRepository;
        this.districtOfficeRepository = districtOfficeRepository;
        this.valuerRepository = valuerRepository;
        this.landownerRepository = landownerRepository;
        this.valuationRequestRepository = valuationRequestRepository;
        this.valuationFactorRepository = valuationFactorRepository;
    }

    @Transactional
    public String seed() {
        if (propertyTypeRepository.count() > 0) {
            return "Seed data already loaded";
        }

        PropertyType ptResidential = propertyTypeRepository.save(new PropertyType("Residential"));
        PropertyType ptCommercial = propertyTypeRepository.save(new PropertyType("Commercial"));
        PropertyType ptAgricultural = propertyTypeRepository.save(new PropertyType("Agricultural"));
        PropertyType ptIndustrial = propertyTypeRepository.save(new PropertyType("Industrial"));

        DistrictOffice do1 = districtOfficeRepository.save(new DistrictOffice(1, "Kigali Central District Office"));
        DistrictOffice do2 = districtOfficeRepository.save(new DistrictOffice(2, "Gasabo District Office"));
        DistrictOffice do3 = districtOfficeRepository.save(new DistrictOffice(3, "Kicukiro District Office"));
        DistrictOffice do4 = districtOfficeRepository.save(new DistrictOffice(4, "Nyarugenge District Office"));

        List<PropertyType> valuer1Types = new ArrayList<>();
        valuer1Types.add(ptResidential);
        valuer1Types.add(ptCommercial);
        Valuer v1 = valuerRepository.save(new Valuer("Eric", "Mugisha", "VAL-001", do1, valuer1Types));

        List<PropertyType> valuer2Types = new ArrayList<>();
        valuer2Types.add(ptAgricultural);
        Valuer v2 = valuerRepository.save(new Valuer("Diane", "Ingabire", "VAL-002", do2, valuer2Types));

        List<PropertyType> valuer3Types = new ArrayList<>();
        valuer3Types.add(ptResidential);
        Valuer v3 = valuerRepository.save(new Valuer("Claude", "Habimana", "VAL-003", do3, valuer3Types));

        Landowner l1 = landownerRepository.save(new Landowner("Jean", "Niyonsaba", "1198070023456789", "0788111222", "jean.niyonsaba@example.com"));
        Landowner l2 = landownerRepository.save(new Landowner("Alice", "Niyonsaba", "1199080012345678", "0788222333", "alice.niyonsaba@example.com"));
        Landowner l3 = landownerRepository.save(new Landowner("Emmanuel", "Bizimana", "1198570034567890", "0788333444", "emmanuel.bizimana@example.com"));
        Landowner l4 = landownerRepository.save(new Landowner("Claudine", "Mukamana", "1199270045678901", "0788444555", "claudine.mukamana@example.com"));
        Landowner l5 = landownerRepository.save(new Landowner("Patrick", "Gatete", "1198870056789012", "0788555666", "patrick.gatete@example.com"));

        ValuationRequest vr1 = valuationRequestRepository.save(new ValuationRequest(LocalDate.of(2026, 10, 18), ValuationStatus.SUBMITTED, "1/01/01/01/101", 350.0, l1, v1));
        ValuationRequest vr2 = valuationRequestRepository.save(new ValuationRequest(LocalDate.of(2026, 10, 20), ValuationStatus.DECLINED, "1/01/01/01/102", 420.0, l1, v1));
        ValuationRequest vr3 = valuationRequestRepository.save(new ValuationRequest(LocalDate.of(2026, 10, 20), ValuationStatus.VERIFIED, "1/01/01/01/103", 500.0, l1, v1));
        ValuationRequest vr4 = valuationRequestRepository.save(new ValuationRequest(LocalDate.of(2026, 10, 22), ValuationStatus.VALUED, "1/02/02/02/201", 600.0, l2, v1));
        ValuationRequest vr5 = valuationRequestRepository.save(new ValuationRequest(LocalDate.of(2026, 10, 25), ValuationStatus.VALUED, "1/03/03/03/301", 800.0, l3, v2));
        ValuationRequest vr6 = valuationRequestRepository.save(new ValuationRequest(LocalDate.of(2026, 11, 2), ValuationStatus.SUBMITTED, "1/03/03/03/302", 450.0, l3, v2));
        ValuationRequest vr7 = valuationRequestRepository.save(new ValuationRequest(LocalDate.of(2026, 11, 5), ValuationStatus.VERIFIED, "1/04/04/04/401", 520.0, l4, v2));
        ValuationRequest vr8 = valuationRequestRepository.save(new ValuationRequest(LocalDate.of(2026, 11, 10), ValuationStatus.SUBMITTED, "1/04/04/04/402", 310.0, l4, v3));
        ValuationRequest vr9 = valuationRequestRepository.save(new ValuationRequest(LocalDate.of(2026, 11, 12), ValuationStatus.DECLINED, "1/05/05/05/501", 700.0, l5, v3));
        ValuationRequest vr10 = valuationRequestRepository.save(new ValuationRequest(LocalDate.of(2026, 11, 15), ValuationStatus.VALUED, "1/01/01/01/104", 900.0, l1, v3));

        valuationFactorRepository.save(new ValuationFactor("Road accessibility", "Tarmac road within 200 metres", 8, LocalDate.now(), vr10));
        valuationFactorRepository.save(new ValuationFactor("Water and electricity supply", "Direct access to grid utilities", 9, LocalDate.now(), vr10));
        valuationFactorRepository.save(new ValuationFactor("Topography", "Gentle slope ideal for construction", 7, LocalDate.now(), vr4));

        return "Seed data loaded successfully";
    }
}
