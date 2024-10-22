package com.pyeonrimium.queuing.utils.services;

import java.io.File;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileService {
	
	private final String imageUploadDir;
	
	public UploadFileService(@Value("#{upload_dev['imageUploadDir']}") String imageUploadDir) {
		this.imageUploadDir = imageUploadDir;
	}
	
	public FileSystemResource getFile(String path) {
		File file = new File(imageUploadDir + "\\" + path);
		
		if (!file.exists()) {
			return null;
		}
		
		return new FileSystemResource(file);
	}

	public String upload(MultipartFile file) {

		// 파일명 중복 방지
		final UUID uuid = UUID.randomUUID();
		final String uniqueName = uuid.toString().replace("-", "");
		
		// 확장자 확인
		final String fileOriname = file.getOriginalFilename();
		final String fileExtension = fileOriname.substring(fileOriname.lastIndexOf('.'));
		
		File savedFile = new File(imageUploadDir + "\\" + uniqueName + fileExtension);
		
		if (savedFile.exists() == false) {
			savedFile.mkdirs();
		}
		
		try {
			file.transferTo(savedFile);

			System.out.println("[UploadFileService] 파일 업로드 성공");
			return uniqueName + fileExtension;
		} catch (Exception e) {
			e.printStackTrace();

			System.out.println("[UploadFileService] 파일 업로드 실패");
			return null;
		}
	}
}
