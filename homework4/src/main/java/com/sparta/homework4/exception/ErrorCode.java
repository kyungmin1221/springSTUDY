package com.sparta.homework4.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    // 400 BAD_REQUEST: 잘못된 요청
    INVALID_REFRESH_TOKEN(BAD_REQUEST, "리프레시 토큰이 유효하지 않습니다"),
    MISMATCH_REFRESH_TOKEN(BAD_REQUEST, "리프레시 토큰의 유저 정보가 일치하지 않습니다"),
    WRONG_USERNAME(BAD_REQUEST, "아이디가 잘못 입력되었습니다."),
    WRONG_PASSWORD(UNAUTHORIZED, "비밀번호가 잘못 입력되었습니다."),
    WRONG_MULTIPARTFILE(BAD_REQUEST, "Multipartfile에 문제가 있습니다"),
    WRONG_DTO(BAD_REQUEST,"DTO를 다시 확인해주세요"),

    // 401 UNAUTHORIZED: 인증되지 않은 사용자
    INVALID_AUTH_TOKEN(UNAUTHORIZED, "권한 정보가 없는 토큰입니다"),
    UNAUTHORIZED_MEMBER(UNAUTHORIZED, "존재하지 않는 회원입니다."),
    UNAUTHORIZED_TEACHER(UNAUTHORIZED, "존재하지 않는 강사입니다."),
    UNAUTHORIZED_COURSE(UNAUTHORIZED, "존재하지 않는 강의입니다."),

    // 404 NOT_FOUND: 잘못된 리소스 접근
    REFRESH_TOKEN_NOT_FOUND(NOT_FOUND, "로그아웃 된 사용자입니다"),
    MEMBER_NOT_FOUND(NOT_FOUND, "해당 회원 정보를 찾을 수 없습니다."),
    TEACHER_NOT_FOUND(NOT_FOUND, "해당 강사 정보를 찾을 수 없습니다."),
    COURSE_NOT_FOUND(NOT_FOUND, "해당 강의 정보를 찾을 수 없습니다."),
    COMMENT_NOT_FOUND(NOT_FOUND, "해당 댓글을 찾을 수 없습니다."),
    LIKE_NOT_FOUND(NOT_FOUND,"해당 좋아요를 찾을 수 없습니다."),
    USER_NOT_FOUND(NOT_FOUND, "해당 사용자를 찾을 수 없습니다."),
    PROFILE_IMAGE_NOT_FOUND(NOT_FOUND,"해당 회원의 프로필 사진을 찾을 수 없습니다"),

    // 409 CONFLICT: 중복된 리소스 (요청이 현재 서버 상태와 충돌될 때)
    DUPLICATE_EMAIL(CONFLICT, "이미 존재하는 이메일입니다."),
    DUPLICATE_NICKNAME(CONFLICT, "이미 존재하는 닉네임입니다."),
    DUPLICATE_LIKE(CONFLICT, "이미 좋아요를 누르셨습니다."),

    // 500 INTERNAL SERVER ERROR
    SERVER_ERROR(INTERNAL_SERVER_ERROR, "내부 서버 에러입니다."),
    FTP_CONNECTION_FAILED(INTERNAL_SERVER_ERROR, "FTP server 연결 실패"),
    IMAGE_UPLOAD_FAILED(INTERNAL_SERVER_ERROR, "이미지 업로드 실패"),
    IMAGE_DOWNLOAD_FAILED(INTERNAL_SERVER_ERROR, "이미지 다운로드 실패"),
    IMAGE_DELETE_FAILED(INTERNAL_SERVER_ERROR,"이미지 삭제 실패");


    private final HttpStatus httpStatus;
    private final String message;

}