package com.nsai.spoldzielnia.mieszkancyzarzadcy.Service;

import com.nsai.spoldzielnia.mieszkancyzarzadcy.Entity.LocatorFlat;
import com.nsai.spoldzielnia.mieszkancyzarzadcy.Repository.LocatorFlatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocatorFlatService {

    private LocatorFlatRepository locatorFlatRepository;

    public LocatorFlatService(LocatorFlatRepository locatorFlatRepository) {
        this.locatorFlatRepository = locatorFlatRepository;
    }

    @Transactional
    public LocatorFlat addLocatorToFlat(LocatorFlat locatorFlat){
        return locatorFlatRepository.save(locatorFlat);
    }

    @Transactional
    public LocatorFlat editLocatorToFlat(LocatorFlat locatorFlat){
        return locatorFlatRepository.save(locatorFlat);
    }


    @Transactional
    public List<LocatorFlat> listAllLocatorsFlat(){
        return locatorFlatRepository.findAll();
    }

    @Transactional
    public List<Long> getAllFlatsFromLocator(long locator_Id){
        return locatorFlatRepository.findAllByLocatorId(locator_Id).stream()
                .map(LocatorFlat::getFlatId)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<Long> getAllLocatorsFromFlat(long flat_Id){
        return locatorFlatRepository.findAllByFlatId(flat_Id).stream()
                .map(LocatorFlat::getFlatId)
                .collect(Collectors.toList());
    }


    @Transactional
    public void removeLocatorFlat(long id) {
        locatorFlatRepository.deleteById(id);
    }

    @Transactional
    public void removeLocatorFromFlat(long locatorId, long flatId) {
        locatorFlatRepository.deleteByFlatIdAndLocatorId(flatId, locatorId);
    }

    @Transactional
    public void removeAllFlatsFromLocator(long locatorId) {
        locatorFlatRepository.deleteAllByLocatorId(locatorId);
    }

    @Transactional
    public void removeAllLocatorsFromFlat(long flatId) {
        locatorFlatRepository.deleteAllByFlatId(flatId);
    }
}
