package kr.hhplus.be.server.dto.product;

import java.util.List;

// 인기 상품 조회 응답 DTO
public class PopularProductResponse {
    private int code;
    private String message;
    private List<Product> data;

    public PopularProductResponse(int code, String message, List<Product> data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public List<Product> getData() {
        return data;
    }

    // 인기 상품 객체
    public static class Product {
        private int id;
        private String name;
        private long price;
        private int sales;
        private int stock;

        public Product(int id, String name, long price, int sales, int stock) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.sales = sales;
            this.stock = stock;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public long getPrice() {
            return price;
        }

        public int getSales() {
            return sales;
        }

        public int getStock() {
            return stock;
        }
    }
}
