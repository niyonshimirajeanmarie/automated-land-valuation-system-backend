package rw.auca.landvaluation.service;

import rw.auca.landvaluation.domain.DistrictOffice;
import rw.auca.landvaluation.repository.DistrictOfficeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DistrictOfficeService {

    private final DistrictOfficeRepository districtOfficeRepository;

    public DistrictOfficeService(DistrictOfficeRepository districtOfficeRepository) {
        this.districtOfficeRepository = districtOfficeRepository;
    }

    public String saveDistrictOffice(DistrictOffice districtOffice) {
        districtOfficeRepository.save(districtOffice);
        return "District office saved successfully";
    }

    public List<DistrictOffice> getAllDistrictOffices() {
        return districtOfficeRepository.findAll();
    }

    public Optional<DistrictOffice> getDistrictOfficeById(UUID id) {
        return districtOfficeRepository.findById(id);
    }

    public String updateDistrictOffice(UUID id, DistrictOffice districtOffice) {
        Optional<DistrictOffice> existingOpt = districtOfficeRepository.findById(id);
        if (existingOpt.isEmpty()) {
            return "The district office with that id does not exist";
        }
        DistrictOffice existing = existingOpt.get();
        existing.setOfficeNumber(districtOffice.getOfficeNumber());
        existing.setName(districtOffice.getName());
        districtOfficeRepository.save(existing);
        return "District office updated successfully";
    }

    public String deleteDistrictOffice(UUID id) {
        Optional<DistrictOffice> existingOpt = districtOfficeRepository.findById(id);
        if (existingOpt.isEmpty()) {
            return "The district office with that id does not exist";
        }
        districtOfficeRepository.deleteById(id);
        return "District office deleted successfully";
    }

    public List<DistrictOffice> getFreeDistrictOffices() {
        return districtOfficeRepository.findFreeDistrictOffices();
    }
}
