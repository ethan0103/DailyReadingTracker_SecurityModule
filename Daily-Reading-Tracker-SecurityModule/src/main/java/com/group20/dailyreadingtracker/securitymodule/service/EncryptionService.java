package com.group20.dailyreadingtracker.securitymodule.service;

public interface EncryptionService {
    String encrypt(String data, String secretKey) throws Exception;
    String decrypt(String encryptedData, String secretKey) throws Exception;
    String generateSecretKey() throws Exception;
}