package com.nsai.spoldzielnia.mieszkancyzarzadcy.Service;

import com.nsai.spoldzielnia.mieszkancyzarzadcy.Entity.ManagerBuilding;
import com.nsai.spoldzielnia.mieszkancyzarzadcy.Repository.ManagerBuildingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManagerBuildingService {

    private ManagerBuildingRepository managerBuildingRepository;

    public ManagerBuildingService(ManagerBuildingRepository managerBuildingRepository) {
        this.managerBuildingRepository = managerBuildingRepository;
    }

    @Transactional
    public ManagerBuilding addManagerToBuilding(ManagerBuilding managerBuilding){
        return managerBuildingRepository.save(managerBuilding);
    }

    @Transactional
    public ManagerBuilding editManagerToBuilding(ManagerBuilding managerBuilding){
        return managerBuildingRepository.save(managerBuilding);
    }


    @Transactional
    public List<ManagerBuilding> listAllManagersBuilding(){
        return managerBuildingRepository.findAll();
    }

    @Transactional
    public List<Long> getAllBuildingsFromManager(long manager_Id){
        return managerBuildingRepository.findAllByManagerId(manager_Id).stream()
                .map(ManagerBuilding::getBuildingId)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<Long> getAllManagersFromBuilding(long building_Id){
        return managerBuildingRepository.findAllByBuildingId(building_Id).stream()
                .map(ManagerBuilding::getManagerId)
                .collect(Collectors.toList());
    }

    @Transactional
    public Boolean isManagerFromBuilding(long building_Id, long manager_Id){
        List<ManagerBuilding> mb = managerBuildingRepository.findAllByBuildingIdAndManagerId(building_Id, manager_Id);
        if(mb.size()!=0)return true;
        else return false;
    }


    @Transactional
    public void removeManagerBuilding(long id) {
        managerBuildingRepository.deleteById(id);
    }

    @Transactional
    public void removeManagerFromBuilding(long managerId, long buildingId) {
        managerBuildingRepository.deleteByBuildingIdAndManagerId(buildingId, managerId);
    }

    @Transactional
    public void removeAllBuildingsFromManager(long managerId) {
        managerBuildingRepository.deleteAllByManagerId(managerId);
    }

    @Transactional
    public void removeAllManagersFromBuilding(long buildingId) {
        managerBuildingRepository.deleteAllByBuildingId(buildingId);
    }
}
