package com.bankx.utilities;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    public static ResponseEntity<Object> generateResponse(HttpStatus status, boolean isSuccess , Object response){
        long dateTime = LocalDate.now().atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli();
        Map<String, Object> result = new HashMap<>();
        result.put("isSuccess", isSuccess);
        result.put("data", response);
        result.put("status", status.value());
        result.put("dateTime", dateTime);
        return new ResponseEntity<>(result,status);
    }

}
