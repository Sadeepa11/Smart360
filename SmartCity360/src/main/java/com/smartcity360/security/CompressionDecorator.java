package com.smartcity360.security;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Concrete Decorator providing GZIP compression to reduce bandwidth consumption
 * across IoT mesh and cellular backhaul networks.
 */
public class CompressionDecorator extends DataStreamDecorator {

    public CompressionDecorator(DataStream wrappee) {
        super(wrappee);
    }

    @Override
    public void writeData(String data) {
        String compressed = compress(data);
        System.out.printf("[CompressionDecorator] COMPRESSED: (Orig: %d bytes -> Comp: %d bytes)%n",
                data.getBytes(StandardCharsets.UTF_8).length,
                compressed.getBytes(StandardCharsets.UTF_8).length);
        super.writeData(compressed);
    }

    @Override
    public String readData() {
        String compressedData = super.readData();
        String decompressed = decompress(compressedData);
        System.out.printf("[CompressionDecorator] DECOMPRESSED: Restored %d characters.%n", decompressed.length());
        return decompressed;
    }

    private String compress(String data) {
        try {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            try (GZIPOutputStream gzipOut = new GZIPOutputStream(byteOut)) {
                gzipOut.write(data.getBytes(StandardCharsets.UTF_8));
            }
            return Base64.getEncoder().encodeToString(byteOut.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("Compression failed: " + e.getMessage(), e);
        }
    }

    private String decompress(String compressedBase64) {
        try {
            byte[] bytes = Base64.getDecoder().decode(compressedBase64);
            ByteArrayInputStream byteIn = new ByteArrayInputStream(bytes);
            try (GZIPInputStream gzipIn = new GZIPInputStream(byteIn);
                 ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                byte[] buffer = new byte[256];
                int len;
                while ((len = gzipIn.read(buffer)) > 0) {
                    out.write(buffer, 0, len);
                }
                return out.toString(StandardCharsets.UTF_8);
            }
        } catch (Exception e) {
            throw new RuntimeException("Decompression failed: " + e.getMessage(), e);
        }
    }
}
