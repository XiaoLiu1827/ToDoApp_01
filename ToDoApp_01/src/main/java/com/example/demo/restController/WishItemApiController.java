package com.example.demo.restController;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.demo.dto.WishItemDto;
import com.example.demo.model.WishItem;
import com.example.demo.service.FileStorageService;
import com.example.demo.service.UserAccountService;

@RestController
@RequestMapping("/savings/api/wishItem")
@SessionAttributes("userId")
public class WishItemApiController {
	@Autowired
	private FileStorageService fileStorageService;
	@Autowired
	private UserAccountService userAccountService;
	private Long userId;

	@ModelAttribute
	public void setUserId(@SessionAttribute("userId") Long userId) {
		this.userId = userId;
	}

	@PostMapping("/add")
	@ResponseBody
	public ResponseEntity<?> addWishItem(
			@ModelAttribute WishItemDto dto) {
		try {
			// 画像ファイルを保存
			String fileName = fileStorageService.saveFile(dto.getImage());

			//WishItemエンティティを作成
			WishItem wishItem = new WishItem();
			wishItem.setName(dto.getName());
			wishItem.setNeededAmount(dto.getNeededAmount());
			wishItem.setImagePath(fileName);

			userAccountService.addWishItem(userId, dto.getName(),
					dto.getNeededAmount(), fileName);

			return ResponseEntity.ok("/savings/user");
		} catch (Exception e) {
			// エラーログを出力
			e.printStackTrace();
			// 適切なエラーメッセージを返却するか例外を再スロー
			throw new RuntimeException("ファイル保存中にエラーが発生しました", e);
		}
	}

	@GetMapping("/image/{fileName}")
	public ResponseEntity<Resource> getImage(@PathVariable String fileName) throws IOException {
		Resource file = fileStorageService.loadFile(fileName);
		return ResponseEntity.ok()
				.contentType(MediaType.IMAGE_JPEG) // 適切なMIMEタイプに変更
				.body(file);
	}
}
