package com.payhere.homework.api.application.util;

public class HangulUtil {

    // 한글 유니코드 시작 값
    private static final int HANGUL_UNICODE_START = 44032;
    // 초성 유니코드 시작 값
    private static final int INITIAL_UNICODE_START = 4352;
    // 중성 유니코드 시작 값
    private static final int MEDIAL_UNICODE_START = 4449;
    // 종성 유니코드 시작 값
    private static final int FINAL_UNICODE_START = 4520;

    // 초성 리스트
    private static final char[] INITIALS = {
            'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
    };

    // 중성 리스트
    private static final char[] MEDIALS = {
            'ㅏ', 'ㅐ', 'ㅑ', 'ㅒ', 'ㅓ', 'ㅔ', 'ㅕ', 'ㅖ', 'ㅗ', 'ㅘ', 'ㅙ', 'ㅚ', 'ㅛ', 'ㅜ', 'ㅝ', 'ㅞ', 'ㅟ', 'ㅠ', 'ㅡ', 'ㅢ', 'ㅣ'
    };

    // 종성 리스트
    private static final char[] FINALS = {
            '\u0000', 'ㄱ', 'ㄲ', 'ㄳ', 'ㄴ', 'ㄵ', 'ㄶ', 'ㄷ', 'ㄹ', 'ㄺ', 'ㄻ', 'ㄼ', 'ㄽ', 'ㄾ', 'ㄿ', 'ㅀ', 'ㅁ', 'ㅂ', 'ㅄ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
    };

    // 한글 초성, 중성, 종성을 분리하는 메서드
    public static char[] splitHangul(char hangulChar) {
        int code = (int) hangulChar - HANGUL_UNICODE_START;
        int initialIndex = code / (21 * 28);
        int medialIndex = (code % (21 * 28)) / 28;
        int finalIndex = code % 28;

        return new char[] {INITIALS[initialIndex], MEDIALS[medialIndex], FINALS[finalIndex]};
    }

    // 한글 초성, 중성, 종성을 합치는 메서드
    public static char combineHangul(char initial, char medial, char finalChar) {
        int initialIndex = indexOf(INITIALS, initial);
        int medialIndex = indexOf(MEDIALS, medial);
        int finalIndex = indexOf(FINALS, finalChar);

        int unicode = HANGUL_UNICODE_START + initialIndex * 21 * 28 + medialIndex * 28 + finalIndex;
        return (char) unicode;
    }

    // 배열에서 특정 문자의 인덱스를 찾는 메서드
    private static int indexOf(char[] array, char target) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == target) {
                return i;
            }
        }
        return -1;
    }

}