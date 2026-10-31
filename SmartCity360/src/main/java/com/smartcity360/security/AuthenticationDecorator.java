package com.smartcity360.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;

/**
 * Concrete Decorator providing integrity and authenticity verification.
 * Attaches a cryptographic checksum (SHA-256 HMAC simulator) to outgoing streams
 * and validates the signature on incoming streams to reject tampered data.
 */
public class AuthenticationDecorator extends DataStreamDecorator {
    private static final String AUTH_PREFIX = "AUTH-SIG:";
    private static final String DELIMITER = "::PAYLOAD::";
    private final String authSecret;

    public AuthenticationDecorator(DataStream wrappee) {
        this(wrappee, "SmartCity-Auth-Key-2026");
    }

    public AuthenticationDecorator(DataStream wrappee, String authSecret) {
        super(wrappee);
        this.authSecret = authSecret;
    }

    @Override
    public void writeData(String data) {
        String signature = generateSignature(data);
        String authenticatedPayload = AUTH_PREFIX + signature + DELIMITER + data;
        System.out.printf("[AuthenticationDecorator] ATTACHED AUTH TOKEN: %s...%n", signature.substring(0, 12));
        super.writeData(authenticatedPayload);
    }

    @Override
    public String readData() {
        String rawWithAuth = super.readData();
        if (!rawWithAuth.startsWith(AUTH_PREFIX) || !rawWithAuth.contains(DELIMITER)) {
            throw new SecurityException("Authentication Failed: Missing or corrupt authentication token header!");
        }

        int delimiterIdx = rawWithAuth.indexOf(DELIMITER);
        String signature = rawWithAuth.substring(AUTH_PREFIX.length(), delimiterIdx);
        String payload = rawWithAuth.substring(delimiterIdx + DELIMITER.length());

        String expectedSignature = generateSignature(payload);
        if (!expectedSignature.equals(signature)) {
            throw new SecurityException("Tampering Detected! Expected signature: " + expectedSignature + " but got: " + signature);
        }

        System.out.println("[AuthenticationDecorator] AUTHENTICATION VERIFIED: Signature matches payload integrity.");
        return payload;
    }

    private String generateSignature(String data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest((data + authSecret).getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (Exception e) {
            throw new RuntimeException("Error computing authentication signature: " + e.getMessage(), e);
        }
    }
}
