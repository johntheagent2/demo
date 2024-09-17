package com.sparkminds.elasticSearch.service;

import com.sparkminds.elasticSearch.entity.BatteryEntity;

import java.util.Optional;

public interface BatteryService {
    BatteryEntity saveBattery(BatteryEntity batteryEntity);

    Optional<BatteryEntity> getBatteryById(String id);

    Iterable<BatteryEntity> getAllBatteries();

    void deleteBatteryById(String id);
}
