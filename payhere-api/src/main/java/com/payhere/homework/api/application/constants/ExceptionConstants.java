package com.payhere.homework.api.application.constants;

public class ExceptionConstants {

    public static final String ALREADY_EXIST_USER_ID = "이미 존재하는 아이디입니다";
    public static final String ALREADY_EXIST_REG_NO = "이미 존재하는 주민등록번호입니다";
    public static final String NO_EXIST_USER_PLEASE_SIGN_UP = "존재하지 않는 사용자입니다. 회원가입해주세요";
    public static final String NO_EXIST_USER = "존재하지 않는 사용자입니다";

    public static final String NOT_MATCHED_PASSWORD = "패스워드가 일치하지 않습니다";
    public static final String NO_SCRAP_DATA_PLEASE_SCRAP_FIRST = "환급액 정보가 존재하지 않습니다. 스크랩을 먼저 진행해주세요";

    public static final String MALFORMED_JWT_EXCEPTION_MESSAGE = "토큰 형식이 유효하지 않습니다";
    public static final String EXPIRED_JWT_EXCEPTION_MESSAGE = "토큰이 만료되었습니다";
    public static final String DEFAULT_JWT_EXCEPTION_MESSAGE = "토큰 인증에 실패하였습니다";
    
}
