package rw.auca.landvaluation.service;

import rw.auca.landvaluation.domain.DistrictOffice;
import rw.auca.landvaluation.domain.PropertyType;
import rw.auca.landvaluation.domain.Valuer;
import rw.auca.landvaluation.repository.DistrictOfficeRepository;
import rw.auca.landvaluation.repository.PropertyTypeRepository;
import rw.auca.landvaluation.repository.ValuerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ValuerService {

    private final ValuerRepository valuerRepository;
    private final DistrictOfficeRepository districtOfficeRepository;
    private final PropertyTypeRepository propertyTypeRepository;

    public ValuerService(ValuerRepository valuerRepository,
                         DistrictOfficeRepository districtOfficeRepository,
                         PropertyTypeRepository propertyTypeRepository) {
        this.valuerRepository = valuerRepository;
        this.districtOfficeRepository = districtOfficeRepository;
        this.propertyTypeRepository = propertyTypeRepository;
    }

    public String saveValuer(Valuer valuer) {
        if (valuer.getDistrictOffice() != null && valuer.getDistrictOffice().getId() != null) {
            Optional<DistrictOffice> officeOpt = districtOfficeRepository.findById(valuer.getDistrictOffice().getId());
            officeOpt.ifPresent(valuer::setDistrictOffice);
        }
        if (valuer.getPropertyTypes() != null && !valuer.getPropertyTypes().isEmpty()) {
            List<PropertyType> managedTypes = new ArrayList<>();
            for (PropertyType pt : valuer.getPropertyTypes()) {
                if (pt.getId() != null) {
                    propertyTypeRepository.findById(pt.getId()).ifPresent(managedTypes::add);
                }
            }
            valuer.setPropertyTypes(managedTypes);
        }
        valuerRepository.save(valuer);
        return "Valuer saved successfully";
    }

    public List<Valuer> getAllValuers() {
        return valuerRepository.findAll();
    }

    public Optional<Valuer> getValuerById(UUID id) {
        return valuerRepository.findById(id);
    }

    public String updateValuer(UUID id, Valuer valuer) {
        Optional<Valuer> existingOpt = valuerRepository.findById(id);
        if (existingOpt.isEmpty()) {
            return "The valuer with that id does not exist";
        }
        Valuer existing = existingOpt.get();
        existing.setFirstName(valuer.getFirstName());
        existing.setLastName(valuer.getLastName());
        existing.setLicenseNumber(valuer.getLicenseNumber());
        if (valuer.getDistrictOffice() != null && valuer.getDistrictOffice().getId() != null) {
            districtOfficeRepository.findById(valuer.getDistrictOffice().getId()).ifPresent(existing::setDistrictOffice);
        }
        valuerRepository.save(existing);
        return "Valuer updated successfully";
    }

    public String deleteValuer(UUID id) {
        Optional<Valuer> existingOpt = valuerRepository.findById(id);
        if (existingOpt.isEmpty()) {
            return "The valuer with that id does not exist";
        }
        valuerRepository.deleteById(id);
        return "Valuer deleted successfully";
    }

    public List<Valuer> getValuersByPropertyType(String name) {
        return valuerRepository.findByPropertyTypeName(name);
    }

    public String addPropertyTypeToValuer(UUID valuerId, UUID propertyTypeId) {
        Optional<Valuer> valuerOpt = valuerRepository.findById(valuerId);
        if (valuerOpt.isEmpty()) {
            return "The valuer with that id does not exist";
        }
        Optional<PropertyType> ptOpt = propertyTypeRepository.findById(propertyTypeId);
        if (ptOpt.isEmpty()) {
            return "That property type does not exist";
        }
        Valuer valuer = valuerOpt.get();
        PropertyType pt = ptOpt.get();
        for (PropertyType existingPt : valuer.getPropertyTypes()) {
            if (existingPt.getId().equals(propertyTypeId)) {
                return "The valuer already has that property type";
            }
        }
        valuer.getPropertyTypes().add(pt);
        valuerRepository.save(valuer);
        return "Property type added to the valuer";
    }

    public String moveDistrictOffice(UUID valuerId, int officeNumber) {
        Optional<Valuer> valuerOpt = valuerRepository.findById(valuerId);
        if (valuerOpt.isEmpty()) {
            return "The valuer with that id does not exist";
        }
        Optional<DistrictOffice> officeOpt = districtOfficeRepository.findByOfficeNumber(officeNumber);
        if (officeOpt.isEmpty()) {
            return "The district office with that officeNumber does not exist";
        }
        DistrictOffice targetOffice = officeOpt.get();
        boolean taken = valuerRepository.existsByDistrictOffice(targetOffice);
        if (taken) {
            return "That district office is taken by another valuer";
        }
        Valuer valuer = valuerOpt.get();
        valuer.setDistrictOffice(targetOffice);
        valuerRepository.save(valuer);
        return "District office changed successfully";
    }
}
