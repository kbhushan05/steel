package com.demo.steel.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EnumType;

import com.demo.steel.domain.Deviation;
import com.demo.steel.domain.PartManifacturingDetails;
import com.demo.steel.domain.PartNoDetails;
import com.demo.steel.domain.SteelMill;
import com.demo.steel.domain.SteelOrder;
import com.demo.steel.domain.SteelVerificationCheck;
import com.demo.steel.domain.SteelVerificationCheck.Status;
import com.demo.steel.domain.Supplier;
import com.demo.steel.domain.VerificationCheck;
import com.demo.steel.dto.PartManifacturingDetailsDto;
import com.demo.steel.dto.SteelMillDto;
import com.demo.steel.dto.SteelOrderDto;
import com.demo.steel.dto.SteelVerificationCheckDto;

public class DtoDomainConvertor {
	
	public SteelOrderDto createDto(SteelOrder order) {
		SteelOrderDto orderDto = new SteelOrderDto();
		
		Supplier supplier = order.getSupplier();
		orderDto.setSupplierCode(supplier.getCode());
		orderDto.setSupplierName(supplier.getName());
		orderDto.setSupplierEmail(supplier.getEmail());
		
		orderDto.setPoNumber(order.getPoNumber());
		orderDto.setSteelHeatNumber(order.getSteelHeatNumber());
		orderDto.setStatus(order.getStatus().name());
		orderDto.setOrderId(order.getId());
		orderDto.setSteelMill(order.getSteelMill());
		orderDto.setAlreadyAvailableSteelTonage(order.getAlreadyAvailableSteelTonage());
		orderDto.setCilRemark(order.getCilRemark());
		orderDto.setCilStatus(order.getCilStatus());
		orderDto.setNewSteelToBuy(order.getNewSteelToBuy());
		orderDto.setSteelTonage(order.getSteelTonage());
		orderDto.setRefStandard(order.getRefStandard());
		orderDto.setComment(order.getComments());
		orderDto.setForgerSupplierCode(order.getForgerSupplierCode());
		orderDto.setCourierCompany(order.getCourierCompany());
		orderDto.setCourierReceiptName(order.getCourierReceiptName());
		orderDto.setCourierDeliveryDate(order.getCourierDeliveryDate());

		orderDto.setPartDetails(getPartManifacturingDetailsDto(order));
		orderDto.setCheckList(getSteelVerificationCheckDto(order));
		
		if(order.getDeviation() != null && !order.getDeviation().isEmpty()){
			Deviation dev = order.getDeviation().get(0);
			if(dev != null){
				orderDto.setDeviationType(dev.getType().toString());
				orderDto.setDeviationId(dev.getId());
				orderDto.setCilDevitionNumber(dev.getCilDevitionNumber());
				orderDto.setRequesterName(dev.getRequesterName());
				orderDto.setRequestDate(dev.getRequestDate());
				orderDto.setAttachmentName(dev.getAttachmentName());
				orderDto.setRmSection(dev.getRmSection());
				orderDto.setRmGarde(dev.getRmGarde());
				orderDto.setQuantityForDeviation(dev.getQuantityForDeviation());
				orderDto.setDelivaryAffected(dev.getDelivaryAffected());
				orderDto.setDescription(dev.getDescription());
				orderDto.setPartDescription(dev.getPartDescription());
				orderDto.setRequestDate(dev.getRequestDate());
			}
		}
		
		return orderDto;
	}

	public SteelOrder createOrder(SteelOrderDto orderDto) {
		SteelOrder order = new SteelOrder();
		order.setPoNumber(orderDto.getPoNumber());
		order.setId(orderDto.getOrderId());
		order.setDate(orderDto.getDate());
		order.setSteelMill(orderDto.getSteelMill());
		order.setAlreadyAvailableSteelTonage(orderDto.getAlreadyAvailableSteelTonage());
		order.setCilRemark(orderDto.getCilRemark());
		order.setCilStatus(orderDto.getCilStatus());
		order.setNewSteelToBuy(orderDto.getNewSteelToBuy());
		order.setSteelTonage(orderDto.getSteelTonage());
		order.setRefStandard(orderDto.getRefStandard());
		order.setComments(orderDto.getComment());
		order.setForgerSupplierCode(orderDto.getForgerSupplierCode());
		order.setSteelHeatNumber(orderDto.getSteelHeatNumber());
		order.setCourierCompany(orderDto.getCourierCompany());
		order.setCourierReceiptName(orderDto.getCourierReceiptName());
		order.setCourierDeliveryDate(orderDto.getCourierDeliveryDate());
		
		Supplier supplier = new Supplier();
		supplier.setCode(orderDto.getSupplierCode());
		supplier.setName(orderDto.getSupplierName());
		supplier.setEmail(orderDto.getSupplierEmail());
		order.setSupplier(supplier);
		
		order.setStatus(Enum.valueOf(SteelOrder.Status.class,orderDto.getStatus()));
		
		Set<PartManifacturingDetails> partManifacturingDetailsSet = getPartManifacturingDetailsSet(orderDto,order);
		order.setPartManifacturingDetails(partManifacturingDetailsSet);
		
		Set<SteelVerificationCheck> checks = getSteelOrderveficationCheckSet(orderDto,order);
		order.setVerificationCheck(checks);
		
		List<Deviation> devs = new ArrayList<>();
		Deviation dev = getDeviation(orderDto);
		dev.setOrder(order);
		devs.add(dev);
		order.setDeviation(devs);
		
		return order;
	}
	
	public List<SteelOrderDto> createDtos(List<SteelOrder> orders){
		List<SteelOrderDto> dtos = new ArrayList<>();
		for(SteelOrder order : orders){
			dtos.add(createDto(order));
		}
		return dtos;
	}

	private Set<SteelVerificationCheckDto> getSteelVerificationCheckDto(SteelOrder order){
		Set<SteelVerificationCheckDto> set = new HashSet<>();
		
		if(order.getVerificationCheck() == null || order.getVerificationCheck().isEmpty()){
			Collections.emptySet();
		}
		
		for(SteelVerificationCheck check : order.getVerificationCheck()){
			SteelVerificationCheckDto dto = new SteelVerificationCheckDto();
			dto.setId(check.getPrimarykey());
			dto.setName(check.getVerificationCheck().getName());
			dto.setAttachment(check.getFile());
			dto.setAttachmentName(dto.getAttachmentName());
			dto.setRemark(check.getRemark());
			dto.setStatus(check.getStatus().toString());
			dto.setTestName(check.getVerificationCheck().getTestName());
			dto.setVerificationCheckId(check.getVerificationCheck().getId());
			set.add(dto);
		}
		
		return set;
	}

	private Set<PartManifacturingDetailsDto> getPartManifacturingDetailsDto(SteelOrder order){
		Set<PartManifacturingDetailsDto> set = new HashSet<>();
		
		if(order.getPartManifacturingDetails()== null || order.getPartManifacturingDetails().isEmpty()){
			return Collections.emptySet();
		}
		
		for(PartManifacturingDetails part : order.getPartManifacturingDetails()){
			PartManifacturingDetailsDto partDto = new PartManifacturingDetailsDto();
			partDto.setPartId(part.getPrimaryKey());
			partDto.setNumber(part.getPartDetails().getPartNo());
			partDto.setCutWeight(part.getCutWeight());
			partDto.setNoOfParts(part.getPartNo());
			partDto.setWeight(part.getPartWeight());
			partDto.setStatus(Enum.valueOf(PartManifacturingDetailsDto.PartDetailsStatus.class,part.getStatus().toString()));
			set.add(partDto);
		}
		
		return set;
	}

	private Set<PartManifacturingDetails> getPartManifacturingDetailsSet(SteelOrderDto orderDto, SteelOrder order) {
		
		Set<PartManifacturingDetails> partManifacturingDetailsSet = new HashSet<>();
		
		for(PartManifacturingDetailsDto dto : orderDto.getPartDetails()){
			
			PartManifacturingDetails partManifacturingDetails  = new PartManifacturingDetails();
			PartNoDetails partNoDetails = new PartNoDetails();
			partNoDetails.setPartNo(dto.getNumber());
			partManifacturingDetails.setPrimaryKey(dto.getPartId());
			partManifacturingDetails.setPartDetails(partNoDetails);
			partManifacturingDetails.setCutWeight(dto.getCutWeight());
			partManifacturingDetails.setPartWeight(dto.getWeight());
			partManifacturingDetails.setPartNo(dto.getNoOfParts());
			partManifacturingDetails.setStatus(Enum.valueOf(PartManifacturingDetails.Status.class, dto.getStatus().toString()));
			partManifacturingDetailsSet.add(partManifacturingDetails);
			partManifacturingDetails.setOrder(order);
			
		}
		
		return partManifacturingDetailsSet;
	}

	private Set<SteelVerificationCheck> getSteelOrderveficationCheckSet(SteelOrderDto form, SteelOrder order){
		Set<SteelVerificationCheck> checkList = new HashSet<>();
		
		for(SteelVerificationCheckDto dto : form.getCheckList()){
			SteelVerificationCheck check = new SteelVerificationCheck();
			check.setPrimarykey(dto.getId());
			
			VerificationCheck vc = new VerificationCheck();
			vc.setName(dto.getName());
			vc.setId(dto.getVerificationCheckId());
			
			check.setVerificationCheck(vc);
			check.setRemark(dto.getRemark());
			check.setStatus(Enum.valueOf(Status.class, dto.getStatus()));
			check.setFile(dto.getAttachment());
			check.setFilename(dto.getAttachmentName());
			check.setOrder(order);
			checkList.add(check);
		}
		
		return checkList;
	}
	
	private Deviation getDeviation(SteelOrderDto orderDto){
		
		Deviation dev = new Deviation();
		dev.setId(orderDto.getDeviationId());
		dev.setCilDevitionNumber(orderDto.getCilDevitionNumber());
		dev.setRequesterName(orderDto.getRequesterName());
		dev.setRequestDate(orderDto.getRequestDate());
		dev.setAttachmentName(orderDto.getAttachmentName());
		dev.setRmSection(orderDto.getRmSection());
		dev.setRmGarde(orderDto.getRmGarde());
		dev.setQuantityForDeviation(orderDto.getQuantityForDeviation());
		dev.setDelivaryAffected(orderDto.getDelivaryAffected());
		dev.setDescription(orderDto.getDescription());
		dev.setPartDescription(orderDto.getPartDescription());
		dev.setType(EnumType.valueOf(Deviation.Type.class, orderDto.getDeviationType()));
		dev.setRequestDate(orderDto.getRequestDate());
		return dev;
	}

	public List<SteelMillDto> createSteelMillDto(List<SteelMill> mills) {
		List<SteelMillDto> dtos = new ArrayList<>();
		for(SteelMill mill : mills){
			dtos.add(createSteelMillDto(mill));
		}
		return dtos;
	}
	
	public SteelMillDto createSteelMillDto(SteelMill mill){
		SteelMillDto dto = new SteelMillDto();
		dto.setName(mill.getName());
		return dto;
	}
}
