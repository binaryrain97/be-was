package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Util {

    @DisplayName("LocalDateTime 형식의 시간을 깔끔하게 문자열로 변경")
    @Test
    void convertLocalDateTime() {
        LocalDateTime localDateTime = LocalDateTime.now();
        String convertedDate = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        System.out.println(convertedDate);
    }
}
