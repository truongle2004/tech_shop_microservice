package com.example.product.infra.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.product.entities.VendorEntity;

@Repository
public interface VendorRepository extends JpaRepository<VendorEntity, Byte> {

}
