package kr.hhplus.be.server.dto.payment;

// 포인트 사용 API (결제) 요청 DTO
public class UsePointsRequest {
    private Long orderId;

    // Getter, Setter
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}

