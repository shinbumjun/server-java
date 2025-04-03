package kr.hhplus.be.server.controller.product;

import kr.hhplus.be.server.dto.product.ProductListResponse;
import kr.hhplus.be.server.dto.product.PopularProductResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

// 상품 목록 조회 API 및 인기 상품 조회 API
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    // 상품 목록 조회 API
    @GetMapping
    public ResponseEntity<ProductListResponse> getProductList() {
        // 예시 상품 목록
        List<ProductListResponse.Product> products = Arrays.asList(
                new ProductListResponse.Product(1, "Macbook Pro", 2000000, 10),
                new ProductListResponse.Product(2, "iPhone 12", 1200000, 20)
        );

        // 응답 데이터 설정
        ProductListResponse response = new ProductListResponse(200, "요청이 정상적으로 처리되었습니다.", new ProductListResponse.Data(products));
        return ResponseEntity.ok(response);
    }

    // 최근 3일간 가장 많이 팔린 인기 상품 5개 조회 API
    @GetMapping("/best")
    public ResponseEntity<PopularProductResponse> getPopularProducts() {
        // 최근 3일간 판매된 인기 상품 목록
        List<PopularProductResponse.Product> products = Arrays.asList(
                new PopularProductResponse.Product(1, "Ice Americano", 1000, 100, 100),
                new PopularProductResponse.Product(2, "iPhone 12", 1200000, 90, 100),
                new PopularProductResponse.Product(3, "Samsung Galaxy S21", 800000, 70, 50),
                new PopularProductResponse.Product(4, "Macbook Air", 1500000, 60, 30),
                new PopularProductResponse.Product(5, "Apple Watch", 500000, 50, 10)
        );

        // 응답 데이터 설정
        PopularProductResponse response = new PopularProductResponse(200, "요청이 정상적으로 처리되었습니다.", products);
        return ResponseEntity.ok(response);
    }
}
