package com.lab.service.impl;

import com.lab.model.GuidResponse;
import com.lab.service.GuidService;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class GuidServiceImpl implements GuidService {

    private static final String GUID_REGEX =
            "^\\(?([0-9A-Fa-f]{8})-([0-9A-Fa-f]{4})-([0-9A-Fa-f]{4})-([0-9A-Fa-f]{4})-([0-9A-Fa-f]{12})\\)?$";

    private static final Pattern GUID_PATTERN = Pattern.compile(GUID_REGEX);

    @Override
    public GuidResponse validateGuid(String input) {
        if (input == null || input.trim().isEmpty()) {
            return new GuidResponse(false, "Строка пустая или null", null);
        }

        String trimmed = input.trim();
        Matcher matcher = GUID_PATTERN.matcher(trimmed);

        if (matcher.matches()) {
            String normalized = matcher.group(1) + "-"
                    + matcher.group(2) + "-"
                    + matcher.group(3) + "-"
                    + matcher.group(4) + "-"
                    + matcher.group(5);

            return new GuidResponse(true, "Строка является корректным GUID", normalized);
        } else {
            return new GuidResponse(false, "Строка НЕ является корректным GUID", null);
        }
    }
}