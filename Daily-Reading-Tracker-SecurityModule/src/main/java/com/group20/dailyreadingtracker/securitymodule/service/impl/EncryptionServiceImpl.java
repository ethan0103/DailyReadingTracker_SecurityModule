package com.group20.dailyreadingtracker.securitymodule.service.impl;

import com.group20.dailyreadingtracker.securitymodule.service.EncryptionService;
import com.group20.dailyreadingtracker.securitymodule.util.EncryptionUtil;
import org.springframework.stereotype.Service;

@Service
public class EncryptionServiceImpl implements EncryptionService {

    @Override
    public String encrypt(String data, String secretKey) throws Exception {
        return EncryptionUtil.encrypt(data, secretKey);
    }

    @Override
    public String decrypt(String encryptedData, String secretKey) throws Exception {
        return EncryptionUtil.decrypt(encryptedData, secretKey);
    }

    @Override
    public String generateSecretKey() throws Exception {
        return EncryptionUtil.generateSecretKey();
    }
}