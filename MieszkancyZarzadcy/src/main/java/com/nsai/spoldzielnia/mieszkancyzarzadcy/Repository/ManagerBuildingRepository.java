package com.nsai.spoldzielnia.mieszkancyzarzadcy.Repository;


import com.nsai.spoldzielnia.mieszkancyzarzadcy.Entity.ManagerBuilding;
import com.nsai.spoldzielnia.mieszkancyzarzadcy.Entity.ManagerBuilding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface ManagerBuildingRepository extends JpaRepository<ManagerBuilding, Long> {

    List<ManagerBuilding> findAllByManagerId(long managerId);
    List<ManagerBuilding> findAllByBuildingId(long buildingId);
    void deleteByBuildingIdAndManagerId(long BuildingId, long ManagerId);

    void deleteAllByBuildingId(long BuildingId);
    void deleteAllByManagerId(long ManagerId);
}
