package com.sparkminds.elasticSearch.service.impl;

import com.sparkminds.elasticSearch.entity.BatteryEntity;
import com.sparkminds.elasticSearch.repository.BatteryRepository;
import com.sparkminds.elasticSearch.service.BatteryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BatteryServiceImpl implements BatteryService {

    private final BatteryRepository batteryRepository;


    @Override
    public BatteryEntity saveBattery(BatteryEntity batteryEntity) {
        return batteryRepository.save(batteryEntity);
    }

    @Override
    public Optional<BatteryEntity> getBatteryById(String id) {
        return batteryRepository.findById(id);
    }

    @Override
    public Iterable<BatteryEntity> getAllBatteries() {
        return batteryRepository.findAll();
    }

    @Override
    public void deleteBatteryById(String id) {
        batteryRepository.deleteById(id);
    }
}
