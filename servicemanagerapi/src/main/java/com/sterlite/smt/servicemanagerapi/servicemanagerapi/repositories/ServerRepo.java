package com.sterlite.smt.servicemanagerapi.servicemanagerapi.repositories;
/*
 * author: deepkumar.sherathiya
 * version: 1.0
 * date: 21/02/2022
 */
import com.sterlite.smt.servicemanagerapi.servicemanagerapi.model.Server;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServerRepo extends JpaRepository<Server, Long> {
    Server findByIpAddress(String ipAddress);

//    Server findById(Long id);
}
