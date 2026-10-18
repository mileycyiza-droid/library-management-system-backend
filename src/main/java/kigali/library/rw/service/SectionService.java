
package kigali.library.rw.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import kigali.library.rw.domain.Section;
import kigali.library.rw.repository.SectionRepository;

@Service
public class SectionService {

    private final SectionRepository sectionRepository;

    public SectionService(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }

    public Section save(Section section) {
        return sectionRepository.save(section);
    }

    public List<Section> findAll() {
        return sectionRepository.findAll();
    }

    public Optional<Section> findById(UUID id) {
        return sectionRepository.findById(id);
    }

    public Optional<Section> update(UUID id, Section section) {
        return sectionRepository.findById(id).map(existingSection -> {
            existingSection.setName(section.getName());
            existingSection.setLibrarians(section.getLibrarians());
            return sectionRepository.save(existingSection);
        });
    }

    public boolean delete(UUID id) {
        if (sectionRepository.existsById(id)) {
            sectionRepository.deleteById(id);
            return true;
        }

        return false;
    }
}

