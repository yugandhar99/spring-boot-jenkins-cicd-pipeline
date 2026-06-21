package ma.projet.gestionprofesseurs.controllers;

import jakarta.validation.Valid;
import ma.projet.gestionprofesseurs.entities.Specialite;
import ma.projet.gestionprofesseurs.services.SpecialiteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/specialite")
public class SpecialiteController {

    private final SpecialiteService specialiteService;

    public SpecialiteController(SpecialiteService specialiteService) {
        this.specialiteService = specialiteService;
    }

    @GetMapping
    public List<Specialite> findAllSpecialite() {
        return specialiteService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        Specialite specialite = specialiteService.findById(id);
        if (specialite == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Specialite with ID " + id + " not found");
        }
        return ResponseEntity.ok(specialite);
    }

    @PostMapping
    public ResponseEntity<Specialite> createSpecialite(@Valid @RequestBody Specialite specialite) {
        specialite.setId(0);
        return ResponseEntity.status(HttpStatus.CREATED).body(specialiteService.create(specialite));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSpecialite(@PathVariable int id, @Valid @RequestBody Specialite specialite) {
        if (specialiteService.findById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Specialite with ID " + id + " not found");
        }
        specialite.setId(id);
        return ResponseEntity.ok(specialiteService.update(specialite));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSpecialite(@PathVariable int id) {
        Specialite specialite = specialiteService.findById(id);
        if (specialite == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Specialite with ID " + id + " not found");
        }
        specialiteService.delete(specialite);
        return ResponseEntity.ok("Specialite has been deleted");
    }
}
