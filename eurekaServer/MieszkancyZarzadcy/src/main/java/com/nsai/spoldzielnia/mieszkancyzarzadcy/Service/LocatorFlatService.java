package com.nsai.spoldzielnia.mieszkancyzarzadcy.Service;

import com.nsai.spoldzielnia.mieszkancyzarzadcy.Entity.LocatorFlat;
import com.nsai.spoldzielnia.mieszkancyzarzadcy.Repository.LocatorFlatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LocatorFlatService {

    private LocatorFlatRepository locatorFlatRepository;

    public LocatorFlatService(LocatorFlatRepository locatorFlatRepository) {
        this.locatorFlatRepository = locatorFlatRepository;
    }

    @Transactional
    public LocatorFlat addLocatorToFlat(long locator_id, long flat_id){

    }

    @Transactional
    public LocatorFlat editLocatorToFlat(LocatorFlat locatorFlat){

    }

    @Transactional
    public List<LocatorFlat> listLocatorsFlat(){

    }

    @Transactional
    public List<Long> getFlatsFromLocator(long locator_Id){

    }

    @Transactional
    public List<Long> getLocatorsFromFlat(long flat_Id){

    }


    @Transactional
    public void removeLocatorBuilding(long id) {

    }
}
