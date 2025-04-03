package kr.hhplus.be.server.dto.product;

import java.time.LocalDate;

// 인기 상품 조회 요청 DTO
public class PopularProductRequest {
    private String startDate;  // 조회 시작일 (yyyy-MM-dd 형식)
    private String endDate;    // 조회 종료일 (yyyy-MM-dd 형식)

    // Getters and Setters
    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
