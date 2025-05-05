package com.group20.dailyreadingtracker.securitymodule.service;

public interface IntegrityService {
    String generateChecksum(String data) throws Exception;
    boolean verifyChecksum(String data, String checksum) throws Exception;
}