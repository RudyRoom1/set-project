package com.image.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.security.SecureRandom;

@UtilityClass
public class GeneratorUtils {
    public static int generateId() {
        int MIN_RANGE = 1000000000; // Min range limit
        final int MAX_RANGE = 1999999999;
        SecureRandom rnd = new SecureRandom();
        return rnd.nextInt(MAX_RANGE - MIN_RANGE + 1) + MIN_RANGE;
    }

    public String generateRandomFilePath(MultipartFile file) {
        String directory = "testDirectory";
        String randomFonder = RandomStringUtils.randomAlphabetic(10).toLowerCase();

        return String.format("%s/%s/%s", directory, randomFonder, file.getOriginalFilename());
    }
}
