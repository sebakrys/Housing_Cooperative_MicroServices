package com.nsai.spoldzielnia.Repository;


import com.nsai.spoldzielnia.Entity.Flat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface FlatRepository extends JpaRepository<Flat, Long> {
    Flat findById(long id);

}
