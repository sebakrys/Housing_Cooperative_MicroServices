package com.nsai.spoldzielnia.mieszkancyzarzadcy.Repository;


import com.nsai.spoldzielnia.mieszkancyzarzadcy.Entity.LocatorFlat;
import com.nsai.spoldzielnia.mieszkancyzarzadcy.Entity.ManagerBuilding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface ManagerBuildingRepository extends JpaRepository<ManagerBuilding, Long> {

}
