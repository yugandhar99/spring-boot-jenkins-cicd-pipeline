package ma.projet.gestionprofesseurs.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Specialite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Speciality code is required")
    @Size(max = 50, message = "Speciality code must not exceed 50 characters")
    @Column(nullable = false, unique = true)
    private String code;

    @NotBlank(message = "Speciality name is required")
    @Size(max = 150, message = "Speciality name must not exceed 150 characters")
    @Column(nullable = false)
    private String libelle;

    public Specialite() {
    }

    public Specialite(String code, String libelle) {
        this.code = code;
        this.libelle = libelle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
