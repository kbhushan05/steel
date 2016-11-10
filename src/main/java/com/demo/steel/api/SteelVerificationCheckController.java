package com.demo.steel.api;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.demo.steel.domain.Report;
import com.demo.steel.domain.SteelVerificationCheck;
import com.demo.steel.service.ReportService;

@RestController
@RequestMapping("/verificationchecks")
public class SteelVerificationCheckController {

	@Autowired
	private ReportService service;
	
	@RequestMapping(method=RequestMethod.POST, consumes="multipart/form-data", path="/{verificationCheck}/report")
	public void uploadFile(@PathVariable int verificationCheck, @RequestPart MultipartFile mFile){
		try {
			service.uploadReport(verificationCheck, mFile.getOriginalFilename(), mFile.getBytes(),mFile.getContentType());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(method=RequestMethod.GET,path="/{verificationCheckId}/report")
	public ResponseEntity<byte[]> downloadFile(@PathVariable int verificationCheckId) throws IOException{
		SteelVerificationCheck verificationCheck = service.downloadReport(verificationCheckId);
		Report report = verificationCheck.getReport();
		byte[] data = report.getData();
		
		return ResponseEntity.ok()
				.contentLength(data.length)
				.contentType(MediaType.valueOf(report.getMimeType()))
				.header("Content-Disposition", "attachment; filename="+ verificationCheck.getFilename())
				.body(data);
	}
}
