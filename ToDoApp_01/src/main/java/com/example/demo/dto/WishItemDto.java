
package com.example.demo.dto;

import java.math.BigDecimal;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Lob;
import lombok.Data;

@Data
public class WishItemDto {
	private Long id;
	private String name;
	private BigDecimal neededAmount;
	@Lob // 画像データをバイナリとして保存
	private MultipartFile image;
}
