package com.alura.challengeliteratura.repository;

import com.alura.challengeliteratura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Autor findByNombre(String nombre);
}
