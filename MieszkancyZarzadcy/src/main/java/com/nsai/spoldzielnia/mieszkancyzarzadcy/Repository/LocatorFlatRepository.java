package com.nsai.spoldzielnia.mieszkancyzarzadcy.Repository;


import com.nsai.spoldzielnia.mieszkancyzarzadcy.Entity.LocatorFlat;
import com.nsai.spoldzielnia.mieszkancyzarzadcy.Entity.ManagerBuilding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface LocatorFlatRepository extends JpaRepository<LocatorFlat, Long> {

    List<LocatorFlat> findAllByLocatorId(long locator_id);
    List<LocatorFlat> findAllByFlatId(long flat_id);
    void deleteByFlatIdAndLocatorId(long Flat_id, long Locator_id);

    List<LocatorFlat> findAllByFlatIdAndLocatorId(long flatId, long locatorId);


    void deleteAllByFlatId(long Flat_id);
    void deleteAllByLocatorId(long Locator_id);
}
