package com.demo.steel.api;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.demo.steel.domain.SteelVerificationCheck;
import com.demo.steel.service.SteelVerificationCheckService;

@Service
public class SteelVerificationCheckApiService {

	@Autowired
	private SteelVerificationCheckService steelverificationCheckService;
	
	public void uploadReport(int verificationCheckId, MultipartFile file){
		try {
			byte[] bytes = file.getBytes();
			steelverificationCheckService.uploadReport(verificationCheckId, file.getOriginalFilename(), bytes, file.getContentType());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public SteelVerificationCheck downloadReport(int verificationCheckId){
		return steelverificationCheckService.downloadReport(verificationCheckId);
	}
}
