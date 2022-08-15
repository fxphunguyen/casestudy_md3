package com.example.product_manager.model;

import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MyError {
    private String field;
    private List<String> messages;
}
