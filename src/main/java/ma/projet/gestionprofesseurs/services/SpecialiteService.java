package ma.projet.gestionprofesseurs.services;

import ma.projet.gestionprofesseurs.dao.IDao;
import ma.projet.gestionprofesseurs.entities.Specialite;
import ma.projet.gestionprofesseurs.repository.SpecialiteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecialiteService implements IDao<Specialite> {

    private final SpecialiteRepository specialiteRepository;

    public SpecialiteService(SpecialiteRepository specialiteRepository) {
        this.specialiteRepository = specialiteRepository;
    }

    @Override
    public Specialite create(Specialite specialite) {
        return specialiteRepository.save(specialite);
    }

    @Override
    public boolean delete(Specialite specialite) {
        try {
            specialiteRepository.delete(specialite);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public Specialite update(Specialite specialite) {
        return specialiteRepository.save(specialite);
    }

    @Override
    public List<Specialite> findAll() {
        return specialiteRepository.findAll();
    }

    @Override
    public Specialite findById(int id) {
        return specialiteRepository.findById(id).orElse(null);
    }
}
