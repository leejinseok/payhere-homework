package com.payhere.homework.api.application.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HangulUtilTest {

    @Test
    void 한글초성추출() {
        String hangul = "에스프레소";
        char[] initials = new char[]{
                'ㅇ', 'ㅅ', 'ㅍ', 'ㄹ', 'ㅅ'
        };

        String[] split = hangul.split("");
        for (int i = 0; i < split.length; i++) {
            char initial = HangulUtil.splitHangul(split[i].toCharArray()[0])[0];
            assertThat(initial).isEqualTo(initials[i]);
        }
    }

}