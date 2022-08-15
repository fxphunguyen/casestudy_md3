package com.example.product_manager.model;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product {
    private Long id;
    @NotEmpty(message = "Tên sản phẩm không được bỏ trống")
    @Length(min = 3, message = "Tên sản phẩm phải từ 3 ký tự")
    private String name;
    @Min(value = 10000, message = "Giá phải lớn hơn 10000")
    @Max(value = 100000000, message = "Giá thấp hơn 100000000")
    private BigDecimal price;
    @Min(value = 1, message = "Số lượng phải lớn hơn 0")
    private Long quantity;
    private int idCategory;
    @NotEmpty(message = "Bỏ ảnh vô")
    private String urlImg;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;


    public Product(String title, BigDecimal price, Long quantity, int idCategory) {
        this.name = title;
        this.price = price;
        this.quantity = quantity;
        this.idCategory = idCategory;
    }


    public Product(long id, String name, String urlImg, BigDecimal price, Long quantity, int idCategory, LocalDateTime createAt, LocalDateTime updateAt) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.idCategory = idCategory;
        this.urlImg = urlImg;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public Product(String name, String urlImg, BigDecimal price, Long quantity, int idCategory, LocalDateTime createAt, LocalDateTime updateAt) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.idCategory = idCategory;
        this.urlImg = urlImg;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public Product(String name, BigDecimal price, Long quantity, int idCategory, LocalDateTime createAt, LocalDateTime updateAt) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.idCategory = idCategory;
        this.urlImg = urlImg;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public Product(Long id, String name, BigDecimal price, Long quantity, int idCategory, LocalDateTime updateAt) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.idCategory = idCategory;
        this.urlImg = urlImg;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }
}
