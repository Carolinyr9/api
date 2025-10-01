package com.eventostec.api.repositories;

import com.eventostec.api.domain.cupon.Cupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CuponRepository extends JpaRepository<Cupon, UUID> {
}
