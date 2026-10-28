
package kigali.library.rw.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import kigali.library.rw.domain.Desk;
import kigali.library.rw.repository.DeskRepository;

@Service
public class DeskService {

    private final DeskRepository deskRepository;

    public DeskService(DeskRepository deskRepository) {
        this.deskRepository = deskRepository;
    }

    public Desk save(Desk desk) {
        return deskRepository.save(desk);
    }

    public List<Desk> findAll() {
        return deskRepository.findAll();
    }

    public List<Desk> findFreeDesks() {
        return deskRepository.findFreeDesks();
    }

    public Optional<Desk> findById(UUID id) {
        return deskRepository.findById(id);
    }

    public Optional<Desk> update(UUID id, Desk desk) {
        return deskRepository.findById(id).map(existingDesk -> {
            existingDesk.setDeskNumber(desk.getDeskNumber());
            existingDesk.setName(desk.getName());
            return deskRepository.save(existingDesk);
        });
    }

    public boolean delete(UUID id) {
        if (deskRepository.existsById(id)) {
            deskRepository.deleteById(id);
            return true;
        }

        return false;
    }
}
