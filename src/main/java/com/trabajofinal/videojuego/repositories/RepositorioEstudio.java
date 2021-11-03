package com.trabajofinal.videojuego.repositories;

import com.trabajofinal.videojuego.entities.Estudio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioEstudio extends JpaRepository<Estudio, Long> {
}