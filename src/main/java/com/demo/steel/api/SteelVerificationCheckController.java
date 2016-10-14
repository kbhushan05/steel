package com.demo.steel.api;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/verificationchecks")
public class SteelVerificationCheckController {

	@RequestMapping(method=RequestMethod.POST, consumes="multipart/mixed", path="/{verificationCheck}/report")
	public void uploadFile(@PathVariable int verificationCheck, @RequestPart(required=false) MultipartFile mFile){
		System.out.println("hello world");
	}
	
	@RequestMapping(method=RequestMethod.GET,path="/{verificationCheck}/report")
	public ResponseEntity<byte[]> downloadFile(@PathVariable int verificationCheck) throws IOException{
		
		String type = "application/text";
		String s = new String("ahbcdhbhdcbshcbds hello world");
		byte[] data = s.getBytes();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentDispositionFormData("attachment", "abc.txt");
		
		return ResponseEntity.ok()
				.contentLength(data.length)
				.contentType(MediaType.valueOf(type))
				.header("Content-disposition", "attachment; filename="+ "abc.txt")
				.body(data);
		
	}
}
