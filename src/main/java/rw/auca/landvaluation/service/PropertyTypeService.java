package rw.auca.landvaluation.service;

import rw.auca.landvaluation.domain.PropertyType;
import rw.auca.landvaluation.repository.PropertyTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PropertyTypeService {

    private final PropertyTypeRepository propertyTypeRepository;

    public PropertyTypeService(PropertyTypeRepository propertyTypeRepository) {
        this.propertyTypeRepository = propertyTypeRepository;
    }

    public String savePropertyType(PropertyType propertyType) {
        propertyTypeRepository.save(propertyType);
        return "Property type saved successfully";
    }

    public List<PropertyType> getAllPropertyTypes() {
        return propertyTypeRepository.findAll();
    }

    public Optional<PropertyType> getPropertyTypeById(UUID id) {
        return propertyTypeRepository.findById(id);
    }

    public String updatePropertyType(UUID id, PropertyType propertyType) {
        Optional<PropertyType> existingOpt = propertyTypeRepository.findById(id);
        if (existingOpt.isEmpty()) {
            return "The property type with that id does not exist";
        }
        PropertyType existing = existingOpt.get();
        existing.setName(propertyType.getName());
        propertyTypeRepository.save(existing);
        return "Property type updated successfully";
    }

    public String deletePropertyType(UUID id) {
        Optional<PropertyType> existingOpt = propertyTypeRepository.findById(id);
        if (existingOpt.isEmpty()) {
            return "The property type with that id does not exist";
        }
        propertyTypeRepository.deleteById(id);
        return "Property type deleted successfully";
    }

    public List<PropertyType> getUnusedPropertyTypes() {
        return propertyTypeRepository.findUnusedPropertyTypes();
    }
}
