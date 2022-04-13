package com.nsai.spoldzielnia.Service;

import com.nsai.spoldzielnia.Entity.FlatCharges;
import com.nsai.spoldzielnia.Repository.FlatChargesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FlatChargesService {

    private FlatChargesRepository flatChargesRepository;

    @Autowired
    public FlatChargesService(FlatChargesRepository flatChargesRepository) {
        this.flatChargesRepository = flatChargesRepository;
    }

    @Transactional
    public List<FlatCharges> getLast12AcceptedFlatChargesByFlat(long flatId) {
        return flatChargesRepository.findTop12ByFlatIdAndAcceptedOrderByDataDesc(flatId, true);
    }


    @Transactional
    public Long getCountOfFlatChargesAcceptedFromFlat(long flatId){
        return flatChargesRepository.getNumberOfAcceptedFlatCharges(flatId);
    }

    @Transactional
    public FlatCharges getLastFlatChargesFromFlat(long flatId){
        return flatChargesRepository.findFirstByFlatIdOrderByDataDesc(flatId);
    }


    @Transactional
    public void addFlatCharges(FlatCharges flatCharges) {

        flatChargesRepository.save(flatCharges);
    }

    @Transactional
    public void editFlatCharges(FlatCharges flatCharges) {

        flatChargesRepository.save(flatCharges);
    }

    @Transactional
    public List<FlatCharges> listFlatCharges() {
        return flatChargesRepository.findAll();
    }

    @Transactional
    public FlatCharges getFlatCharges(long id) {
        return flatChargesRepository.findById(id);
    }

    @Transactional
    public void removeFlatCharges(long id) {
        flatChargesRepository.deleteById(id);
    }


}