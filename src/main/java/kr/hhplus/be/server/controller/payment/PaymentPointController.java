package kr.hhplus.be.server.controller.payment;

import kr.hhplus.be.server.dto.payment.ApiResponse;
import kr.hhplus.be.server.dto.payment.UsePointsRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 포인트 사용 API (결제)
@RestController
@RequestMapping("/api/v1/points")
public class PaymentPointController {

    @PostMapping("/use")
    public ResponseEntity<?> usePoints(@RequestBody UsePointsRequest request) {
        // 1. 주문 ID에 해당하는 주문 상태를 조회
        boolean isOrderValid = checkOrderStatus(request.getOrderId());
        if (!isOrderValid) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse(409, "비즈니스 정책을 위반한 요청입니다.", "주문 상태가 EXPIRED(결제 불가 건)입니다."));
        }

        // 2. 포인트 잔액 조회 (예시로 100,000원이라고 가정)
        long availablePoints = 100000;  // 포인트 잔액 100,000원
        long paymentAmount = request.getOrderId();  // 결제 금액 (orderId와 실제 결제 금액이 매핑되는 구조일 것)

        // 3. 결제 금액이 포인트보다 크면 실패 처리
        if (paymentAmount > availablePoints) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse(409, "비즈니스 정책을 위반한 요청입니다.",
                            "포인트 잔액이 부족합니다. 현재 잔액 : " + availablePoints + "원, 결제 금액 : " + paymentAmount + "원"));
        }

        // 4. 포인트 차감 및 결제 완료 처리 로직 (여기서는 생략)

        // 5. 성공적으로 처리되면 204 No Content 반환
        return ResponseEntity.noContent().build();
    }

    // 주문 상태 확인 (예시)
    private boolean checkOrderStatus(Long orderId) {
        // 실제로는 DB에서 주문 상태를 조회해야 합니다.
        // 예시로 orderId가 1이면 EXPIRED 상태로 처리
        if (orderId == 1) {
            return false; // EXPIRED 상태로 처리
        }

        return true; // 그 외의 상태는 정상
    }
}
