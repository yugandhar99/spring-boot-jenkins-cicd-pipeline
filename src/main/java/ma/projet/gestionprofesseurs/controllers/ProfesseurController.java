package ma.projet.gestionprofesseurs.controllers;

import jakarta.validation.Valid;
import ma.projet.gestionprofesseurs.entities.Professeur;
import ma.projet.gestionprofesseurs.entities.Specialite;
import ma.projet.gestionprofesseurs.services.ProfesseurService;
import ma.projet.gestionprofesseurs.services.SpecialiteService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/professeur")
public class ProfesseurController {

    private final ProfesseurService professeurService;
    private final SpecialiteService specialiteService;

    public ProfesseurController(ProfesseurService professeurService, SpecialiteService specialiteService) {
        this.professeurService = professeurService;
        this.specialiteService = specialiteService;
    }

    @GetMapping
    public List<Professeur> findAllProfesseur() {
        return professeurService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        Professeur professeur = professeurService.findById(id);
        if (professeur == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Professeur with ID " + id + " not found");
        }
        return ResponseEntity.ok(professeur);
    }

    @PostMapping
    public ResponseEntity<Professeur> createProfesseur(@Valid @RequestBody Professeur professeur) {
        professeur.setId(0);
        return ResponseEntity.status(HttpStatus.CREATED).body(professeurService.create(professeur));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProfesseur(@PathVariable int id, @Valid @RequestBody Professeur professeur) {
        if (professeurService.findById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Professeur with ID " + id + " not found");
        }
        professeur.setId(id);
        return ResponseEntity.ok(professeurService.update(professeur));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProfesseur(@PathVariable int id) {
        Professeur professeur = professeurService.findById(id);
        if (professeur == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Professeur with ID " + id + " not found");
        }
        professeurService.delete(professeur);
        return ResponseEntity.ok("Professeur has been deleted");
    }

    @GetMapping("/specialite/{id}")
    public ResponseEntity<?> findProfesseurBySpecialite(@PathVariable Integer id) {
        Specialite specialite = specialiteService.findById(id);
        if (specialite == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Specialite with ID " + id + " not found");
        }
        return ResponseEntity.ok(professeurService.findBySpecialite(specialite));
    }

    @GetMapping("/filterByDate")
    public List<Professeur> findByDateEmbaucheBetween(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateDebut,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFin) {
        return professeurService.findByDateEmbaucheBetween(dateDebut, dateFin);
    }
}
