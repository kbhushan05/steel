package com.demo.steel.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EnumType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	
	private static final Logger logger = LoggerFactory.getLogger(DtoDomainConvertor.class);
	
	public SteelOrderDto convertFrom(SteelOrder order) {
		logger.debug("converting "+ order.getClass()+" to "+ SteelOrderDto.class);
		SteelOrderDto orderDto = new SteelOrderDto();
		
		logger.debug("setting supplier");
		Supplier supplier = order.getSupplier();
		orderDto.setSupplierCode(supplier.getCode());
		orderDto.setSupplierName(supplier.getName());
		orderDto.setSupplierEmail(supplier.getEmail());
		
		logger.debug("setting other attributes.");
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
		orderDto.setCilComment(order.getCilComment());

		orderDto.setPartDetails(getPartManifacturingDetailsDto(order));
		orderDto.setCheckList(getSteelVerificationCheckDto(order));
		
		if(order.getDeviation() != null && !order.getDeviation().isEmpty()){
			Deviation dev = order.getDeviation().get(0);
			if(dev != null){
				logger.debug("setting deviation.");
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
		logger.debug("conversion successful.");
		return orderDto;
	}

	public SteelOrder convertFrom(SteelOrderDto orderDto) {
		logger.debug("converting from "+ SteelOrderDto.class +" to "+ SteelOrder.class);
		SteelOrder order = new SteelOrder();
		
		logger.debug("setting supplier");
		Supplier supplier = new Supplier();
		supplier.setCode(orderDto.getSupplierCode());
		supplier.setName(orderDto.getSupplierName());
		supplier.setEmail(orderDto.getSupplierEmail());
		order.setSupplier(supplier);
		
		logger.debug("setting other attributes.");
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
		order.setCilComment(orderDto.getCilComment());
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
		logger.debug("conversion successful.");
		return order;
	}
	
	public SteelVerificationCheckDto convertFrom(SteelVerificationCheck check) {
		logger.debug("converting "+ check.getClass()+" to "+ SteelOrderDto.class);
		SteelVerificationCheckDto dto = new SteelVerificationCheckDto();
		dto.setId(check.getPrimarykey());
		dto.setName(check.getVerificationCheck().getName());
		dto.setAttachmentName(check.getFilename());
		dto.setMimeType(check.getMimeType());
		dto.setRemark(check.getRemark());
		dto.setStatus(check.getStatus().toString());
		dto.setTestName(check.getVerificationCheck().getTestName());
		dto.setVerificationCheckId(check.getVerificationCheck().getId());
		return dto;
	}

	public SteelVerificationCheck convertFrom(SteelVerificationCheckDto dto) {
		logger.debug("converting "+ dto.getClass()+" to "+ SteelVerificationCheck.class);
		SteelVerificationCheck check = new SteelVerificationCheck();
		check.setPrimarykey(dto.getId());
		
		VerificationCheck vc = new VerificationCheck();
		vc.setName(dto.getName());
		vc.setId(dto.getVerificationCheckId());
		
		check.setVerificationCheck(vc);
		check.setRemark(dto.getRemark());
		check.setStatus(Enum.valueOf(Status.class, dto.getStatus()));
		check.setFilename(dto.getAttachmentName());
		check.setMimeType(dto.getMimeType());
		return check;
	}

	public SteelMillDto convertFrom(SteelMill mill){
		logger.debug("converting "+ mill.getClass()+" to "+ SteelMillDto.class);
		SteelMillDto dto = new SteelMillDto();
		dto.setName(mill.getName());
		return dto;
	}

	public PartManifacturingDetailsDto convertFrom(PartManifacturingDetails part) {
		logger.debug("converting "+ part.getClass()+" to "+ PartManifacturingDetailsDto.class);
		PartManifacturingDetailsDto partDto = new PartManifacturingDetailsDto();
		partDto.setPartId(part.getPrimaryKey());
		partDto.setNumber(part.getPartDetails().getPartNo());
		partDto.setCutWeight(part.getCutWeight());
		partDto.setNoOfParts(part.getPartNo());
		partDto.setWeight(part.getPartWeight());
		partDto.setStatus(Enum.valueOf(PartManifacturingDetailsDto.PartDetailsStatus.class,part.getStatus().toString()));
		return partDto;
	}

	public PartManifacturingDetails convertFrom(PartManifacturingDetailsDto dto) {
		logger.debug("converting "+ dto.getClass()+" to "+ PartManifacturingDetails.class);
		PartManifacturingDetails partManifacturingDetails  = new PartManifacturingDetails();
		PartNoDetails partNoDetails = new PartNoDetails();
		partNoDetails.setPartNo(dto.getNumber());
		partManifacturingDetails.setPrimaryKey(dto.getPartId());
		partManifacturingDetails.setPartDetails(partNoDetails);
		partManifacturingDetails.setCutWeight(dto.getCutWeight());
		partManifacturingDetails.setPartWeight(dto.getWeight());
		partManifacturingDetails.setPartNo(dto.getNoOfParts());
		partManifacturingDetails.setStatus(Enum.valueOf(PartManifacturingDetails.Status.class, dto.getStatus().toString()));
		return partManifacturingDetails;
	}

	public List<SteelOrderDto> convertSteelOrders(List<SteelOrder> orders){
		logger.debug("converting all SteelOrder.");
		List<SteelOrderDto> dtos = new ArrayList<>();
		for(SteelOrder order : orders){
			dtos.add(convertFrom(order));
		}
		return dtos;
	}

	public List<SteelMillDto> convertSteelMills(List<SteelMill> mills) {
		logger.debug("converting all SteelMill.");
		List<SteelMillDto> dtos = new ArrayList<>();
		for(SteelMill mill : mills){
			dtos.add(convertFrom(mill));
		}
		return dtos;
	}

	private Set<SteelVerificationCheckDto> getSteelVerificationCheckDto(SteelOrder order){
		logger.debug("setting SteelVerificationCheckDto.");
		Set<SteelVerificationCheckDto> set = new HashSet<>();
		
		if(order.getVerificationCheck() == null || order.getVerificationCheck().isEmpty()){
			Collections.emptySet();
		}
		
		for(SteelVerificationCheck check : order.getVerificationCheck()){
			SteelVerificationCheckDto dto = convertFrom(check);
			set.add(dto);
		}
		
		return set;
	}

	private Set<PartManifacturingDetailsDto> getPartManifacturingDetailsDto(SteelOrder order){
		logger.debug("setting PartManifacturingDetailsDto.");
		Set<PartManifacturingDetailsDto> set = new HashSet<>();
		
		if(order.getPartManifacturingDetails()== null || order.getPartManifacturingDetails().isEmpty()){
			return Collections.emptySet();
		}
		
		for(PartManifacturingDetails part : order.getPartManifacturingDetails()){
			PartManifacturingDetailsDto partDto = convertFrom(part);
			set.add(partDto);
		}
		
		return set;
	}

	private Set<PartManifacturingDetails> getPartManifacturingDetailsSet(SteelOrderDto orderDto, SteelOrder order) {
		logger.debug("setting partmanifacturing details.");
		Set<PartManifacturingDetails> partManifacturingDetailsSet = new HashSet<>();
		
		for(PartManifacturingDetailsDto dto : orderDto.getPartDetails()){
			PartManifacturingDetails partManifacturingDetails = convertFrom(dto);
			partManifacturingDetailsSet.add(partManifacturingDetails);
			partManifacturingDetails.setOrder(order);
		}
		
		return partManifacturingDetailsSet;
	}

	private Set<SteelVerificationCheck> getSteelOrderveficationCheckSet(SteelOrderDto form, SteelOrder order){
		logger.debug("setting SteelVerificationCheck details.");
		Set<SteelVerificationCheck> checkList = new HashSet<>();
		
		for(SteelVerificationCheckDto dto : form.getCheckList()){
			SteelVerificationCheck check = convertFrom(dto);
			check.setOrder(order);
			checkList.add(check);
		}
		
		return checkList;
	}

	private Deviation getDeviation(SteelOrderDto orderDto){
		logger.debug("creating deviation.");
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
}
