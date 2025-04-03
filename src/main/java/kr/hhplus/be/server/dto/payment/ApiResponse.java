package kr.hhplus.be.server.dto.payment;

// 포인트 사용 API (결제) 응답 DTO
public class ApiResponse {
    private int code;
    private String message;
    private String detail;

    // Constructor, Getters, Setters
    public ApiResponse(int code, String message, String detail) {
        this.code = code;
        this.message = message;
        this.detail = detail;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
