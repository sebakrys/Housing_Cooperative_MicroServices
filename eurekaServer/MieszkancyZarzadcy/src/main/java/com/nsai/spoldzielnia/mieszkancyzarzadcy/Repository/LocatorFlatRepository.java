package com.nsai.spoldzielnia.mieszkancyzarzadcy.Repository;


import com.nsai.spoldzielnia.mieszkancyzarzadcy.Entity.LocatorFlat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface LocatorFlatRepository extends JpaRepository<LocatorFlat, Long> {

}
