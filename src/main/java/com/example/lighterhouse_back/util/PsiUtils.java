package com.example.lighterhouse_back.util;

public class PsiUtils {
    public static String extractMessage(String json) {
        if (json == null) return "알 수 없는 오류";
        try {
            int msgIndex = json.indexOf("\"message\":");
            if (msgIndex != -1) {
                int start = json.indexOf("\"", msgIndex + 10) + 1;
                int end = json.indexOf("\"", start);
                return json.substring(start, end);
            }
        } catch (Exception e) {
            return "응답 메시지 파싱 실패";
        }
        return "오류 메시지를 찾을 수 없음";
    }
}
