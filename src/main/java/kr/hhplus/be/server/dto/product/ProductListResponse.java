package kr.hhplus.be.server.dto.product;

import java.util.List;

// 상품 목록 조회 응답 DTO
public class ProductListResponse {
    private int code;
    private String message;
    private Data data;

    public ProductListResponse(int code, String message, Data data) {
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

    public Data getData() {
        return data;
    }

    // 내포된 데이터 구조
    public static class Data {
        private List<Product> products;

        public Data(List<Product> products) {
            this.products = products;
        }

        public List<Product> getProducts() {
            return products;
        }
    }

    // 상품 객체
    public static class Product {
        private int id;
        private String name;
        private long price;
        private int stock;

        public Product(int id, String name, long price, int stock) {
            this.id = id;
            this.name = name;
            this.price = price;
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

        public int getStock() {
            return stock;
        }
    }
}
