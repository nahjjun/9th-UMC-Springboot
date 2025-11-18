package com.example.umc_springboot.global.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
// json 필드 순서를 고정하는 어노테이션
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
@Schema(title="GlobalResponse DTO", description = "공통 API 응답 형식")
public class GlobalResponse<T> {
    @JsonProperty("isSuccess")
    @Schema(description = "요청 성공 여부",  example = "true")
    private final Boolean isSuccess;
    
    @JsonProperty("code")
    @Schema(description = "HTTP 상태 코드", example = "200")
    private  final String code;
    
    @JsonProperty("message")
    @Schema(description = "응답 메시지", example = "요청이 성공적으로 처리되었습니다.")
    private final String message;

    // result값이 null이 아니어야 결과에 JSON에 포함하는 어노테이션 설정
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "응답 데이터")
    private final T result;

    // 성공 응답 생성 함수
    public static <T> GlobalResponse<T> success(T result) {
        return new GlobalResponse<T>(true, "200", "요청이 성공적으로 처리되었습니다.", result);
    }
    public static <T> GlobalResponse<T> success(String message, T result) {
        return new GlobalResponse<T>(true, "200", message, result);
    }
    public static <T> GlobalResponse<T> success(String message) {
        return new GlobalResponse<T>(true, "200", message, null);
    }
    public static <T> GlobalResponse<T> success(String code, String message) {
        return new GlobalResponse<T>(true, "200", message, null);
    }
    // 실패 응답 생성 함수
    public static <T> GlobalResponse<T> error(String code, String message){
        return new GlobalResponse<T>(false, code, message, null);
    }
}
