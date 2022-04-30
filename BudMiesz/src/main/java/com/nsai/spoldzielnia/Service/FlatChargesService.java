package com.nsai.spoldzielnia.Service;

import com.nsai.spoldzielnia.Entity.FlatCharges;
import com.nsai.spoldzielnia.Repository.FlatChargesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
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
    public List<FlatCharges> getAllFlatChargesFromFlat(long flatId){
        return flatChargesRepository.findAllByFlatIdOrderByDataDesc(flatId);
    }



    @Transactional
    public FlatCharges addFlatCharges(FlatCharges flatCharges) {

        return flatChargesRepository.save(flatCharges);
    }

    @Transactional
    public FlatCharges editFlatCharges(FlatCharges flatCharges) {

        return flatChargesRepository.save(flatCharges);
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


    public long generateChecksum(String getUrl){
        byte[] bytes = getUrl.substring(0, getUrl.indexOf("ch=")).getBytes(StandardCharsets.US_ASCII);
        long checksum = 0;
        for (byte b:
                bytes) {
            checksum+=b;
        }
        return checksum;
    }

}
