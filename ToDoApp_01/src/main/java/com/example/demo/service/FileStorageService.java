package com.example.demo.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {
	@Value("${file.upload-dir}")
	private String uploadDir;

	public String saveFile(MultipartFile file) throws IOException {
		if (file == null || file.isEmpty() || file.getOriginalFilename().isBlank()) {
			return "no_image"; // Return default placeholder filename
		}
		
		// ディレクトリを作成（存在しない場合）
		Files.createDirectories(Paths.get(uploadDir));

		// ファイル名をユニークにする
		String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

		// 保存先パス
		Path filePath = Paths.get(uploadDir, fileName);
		file.transferTo(filePath.toFile());

		// ファイルの相対パスを返却
		return fileName;
	}

	public Resource loadFile(String fileName) {
		try {
			Path filePath = Paths.get(uploadDir, fileName);
			return new UrlResource(filePath.toUri());
		} catch (Exception e) {
			throw new RuntimeException("ファイルを読み込む際にエラーが発生しました: " + fileName, e);

		}
	}
}
