package com.nsai.spoldzielnia.Repository;

import com.nsai.spoldzielnia.Entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface BuildingRepository extends JpaRepository<Building, Long> {
    Building findById(long id);
    Building findByNazwa(String nazwa);
    void deleteById(long bId);

}
