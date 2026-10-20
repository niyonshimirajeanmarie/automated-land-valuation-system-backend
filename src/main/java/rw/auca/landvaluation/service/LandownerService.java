package rw.auca.landvaluation.service;

import rw.auca.landvaluation.domain.Landowner;
import rw.auca.landvaluation.repository.LandownerRepository;
import rw.auca.landvaluation.repository.ValuerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LandownerService {

    private final LandownerRepository landownerRepository;
    private final ValuerRepository valuerRepository;

    public LandownerService(LandownerRepository landownerRepository, ValuerRepository valuerRepository) {
        this.landownerRepository = landownerRepository;
        this.valuerRepository = valuerRepository;
    }

    public String saveLandowner(Landowner landowner) {
        landownerRepository.save(landowner);
        return "Landowner saved successfully";
    }

    public List<Landowner> getAllLandowners() {
        return landownerRepository.findAll();
    }

    public Optional<Landowner> getLandownerById(UUID id) {
        return landownerRepository.findById(id);
    }

    public String updateLandowner(UUID id, Landowner landowner) {
        Optional<Landowner> existingOpt = landownerRepository.findById(id);
        if (existingOpt.isEmpty()) {
            return "The landowner with that id does not exist";
        }
        Landowner existing = existingOpt.get();
        existing.setFirstName(landowner.getFirstName());
        existing.setLastName(landowner.getLastName());
        existing.setNationalId(landowner.getNationalId());
        existing.setPhone(landowner.getPhone());
        existing.setEmail(landowner.getEmail());
        landownerRepository.save(existing);
        return "Landowner updated successfully";
    }

    public String deleteLandowner(UUID id) {
        Optional<Landowner> existingOpt = landownerRepository.findById(id);
        if (existingOpt.isEmpty()) {
            return "The landowner with that id does not exist";
        }
        landownerRepository.deleteById(id);
        return "Landowner deleted successfully";
    }

    public List<Landowner> getLandownersByLastName(String lastName) {
        return landownerRepository.findByLastNameIgnoreCaseOrderByFirstNameAsc(lastName);
    }

    public Optional<List<Landowner>> getLandownersOfValuer(UUID valuerId) {
        if (!valuerRepository.existsById(valuerId)) {
            return Optional.empty();
        }
        return Optional.of(landownerRepository.findLandownersByValuerId(valuerId));
    }
}
