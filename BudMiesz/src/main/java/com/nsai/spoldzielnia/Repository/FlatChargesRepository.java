package com.nsai.spoldzielnia.Repository;



import com.nsai.spoldzielnia.Entity.Flat;
import com.nsai.spoldzielnia.Entity.FlatCharges;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface FlatChargesRepository extends JpaRepository<FlatCharges, Long> {
    FlatCharges findByFlat(Flat flat);
    FlatCharges findById(long id);
    void deleteById(long id);

    List<FlatCharges> findAllByFlatIdOrderByDataDesc(long flatID);

    FlatCharges findFirstByFlatIdOrderByDataDesc(long flatID);
    List<FlatCharges> findTop12ByFlatIdAndAcceptedOrderByDataDesc(long flatID, boolean accepted);

    @Query( value = "select count(fc) from FlatCharges fc JOIN fc.flat f where f.id = :flatId and fc.accepted=true")
    long getNumberOfAcceptedFlatCharges(@Param("flatId") long flatId);

}
