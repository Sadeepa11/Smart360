package com.smartcity360.security;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Concrete Decorator adding AES-128 Symmetric Encryption to the data stream.
 * Secures sensitive energy meter telemetry against eavesdropping during transit.
 */
public class EncryptionDecorator extends DataStreamDecorator {
    private static final String ALGORITHM = "AES";
    // 16-byte static key for simulation
    private static final byte[] DEFAULT_KEY = "SmartCitySecureK".getBytes(StandardCharsets.UTF_8);
    private final byte[] secretKey;

    public EncryptionDecorator(DataStream wrappee) {
        this(wrappee, DEFAULT_KEY);
    }

    public EncryptionDecorator(DataStream wrappee, byte[] secretKey) {
        super(wrappee);
        this.secretKey = secretKey;
    }

    @Override
    public void writeData(String data) {
        String encrypted = encrypt(data);
        System.out.printf("[EncryptionDecorator] ENCRYPTED: \"%s\" -> \"%s\"%n", data, encrypted);
        super.writeData(encrypted);
    }

    @Override
    public String readData() {
        String cipherText = super.readData();
        String decrypted = decrypt(cipherText);
        System.out.printf("[EncryptionDecorator] DECRYPTED: \"%s\" -> \"%s\"%n", cipherText, decrypted);
        return decrypted;
    }

    private String encrypt(String plainText) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(secretKey, ALGORITHM);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Encryption error: " + e.getMessage(), e);
        }
    }

    private String decrypt(String cipherText) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(secretKey, ALGORITHM);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(cipherText));
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Decryption error: " + e.getMessage(), e);
        }
    }
}
