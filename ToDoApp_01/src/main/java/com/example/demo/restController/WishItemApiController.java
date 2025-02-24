package com.example.demo.restController;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.WishItemDto;
import com.example.demo.model.WishItem;
import com.example.demo.service.AuthenticationService;
import com.example.demo.service.FileStorageService;
import com.example.demo.service.UserAccountService;
import com.example.demo.service.WishItemService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/savings/api/wishItem")
@RequiredArgsConstructor
public class WishItemApiController {

	private final FileStorageService fileStorageService;

	private final UserAccountService userAccountService;

	private final WishItemService wishItemService;
	
	private final AuthenticationService authService;

	private Long userId;

	@ModelAttribute
	public void setUser() {
		this.userId = authService.getAuthenticatedUserId();
	}
	
	@PostMapping("/delete/{id}")
	@ResponseBody
	public ResponseEntity<?> deleteWishItem(
			@PathVariable Long id) {
		try {
			wishItemService.deleteWishItem(id);

			return ResponseEntity.ok("/savings/user");
		} catch (Exception e) {
			// エラーログを出力
			e.printStackTrace();
			// 適切なエラーメッセージを返却するか例外を再スロー
			throw new RuntimeException("ファイル保存中にエラーが発生しました", e);
		}
	}

	@PostMapping("/add")
	@ResponseBody
	public ResponseEntity<?> addWishItem(
			@ModelAttribute WishItemDto dto) {
		try {
			// 画像ファイルを外部に保存
			String fileName = fileStorageService.saveFile(dto.getImage());

			//			//WishItemエンティティを作成
			//			WishItem wishItem = new WishItem();
			//			wishItem.setName(dto.getName());
			//			wishItem.setNeededAmount(dto.getNeededAmount());
			//			wishItem.setImagePath(fileName);

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

	@PostMapping("/update/{id}")
	@ResponseBody
	public ResponseEntity<?> updateWishItem(
			@PathVariable Long id,
			@ModelAttribute WishItemDto dto,
			@RequestParam("keepCurrentImage") Optional<Boolean> keepCurrentImage) {
		try {
			//エンティティを取得し
			WishItem entity = wishItemService.getWishItembyId(id);

			//画像変更なしの場合
			if (keepCurrentImage.orElse(false)) {
				BeanUtils.copyProperties(dto, entity, "imagePath", "userAccount");
			} else {
				BeanUtils.copyProperties(dto, entity, "userAccount");

				// 画像ファイルを外部に保存
				String fileName = fileStorageService.saveFile(dto.getImage());
				entity.setImagePath(fileName);
			}
			//adddだと更新にならなそう
			wishItemService.saveWishItem(entity);

			return ResponseEntity.ok("/savings/user");

		} catch (Exception e) {
			// エラーログを出力
			e.printStackTrace();
			// 適切なエラーメッセージを返却するか例外を再スロー
			throw new RuntimeException("ファイル保存中にエラーが発生しました", e);
		}
	}

	@GetMapping("/image")
	public ResponseEntity<Resource> getImage(@RequestParam String fileName) throws IOException {

		Resource file = fileStorageService.loadFile(fileName);
		return ResponseEntity.ok()
				.contentType(MediaType.IMAGE_JPEG) // 適切なMIMEタイプに変更
				.body(file);
	}
}
