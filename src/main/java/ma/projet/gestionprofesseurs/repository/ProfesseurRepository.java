package ma.projet.gestionprofesseurs.repository;

import ma.projet.gestionprofesseurs.entities.Professeur;
import ma.projet.gestionprofesseurs.entities.Specialite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ProfesseurRepository extends JpaRepository<Professeur, Integer> {

    List<Professeur> findBySpecialite(Specialite specialite);

    List<Professeur> findByDateEmbaucheBetween(Date dateDebut, Date dateFin);
}
