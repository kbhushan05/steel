package com.demo.steel.dto;

public class PartManifacturingDetailsDto {
	public enum PartDetailsStatus{
		CHECKED,UNCHECKED;
	}
		private int partId;
		private int number;
		private float weight;
		private float cutWeight;
		private float noOfParts;
		private PartDetailsStatus status = PartDetailsStatus.UNCHECKED;
		
		public PartManifacturingDetailsDto(){}
		
		public PartManifacturingDetailsDto(int number, float weight, float cutWeight,
				int noOfParts) {
			super();
			this.number = number;
			this.weight = weight;
			this.cutWeight = cutWeight;
			this.noOfParts = noOfParts;
		}

		public int getNumber() {
			return number;
		}

		public void setNumber(int number) {
			this.number = number;
		}

		public float getWeight() {
			return weight;
		}

		public void setWeight(float weight) {
			this.weight = weight;
		}

		public float getCutWeight() {
			return cutWeight;
		}

		public void setCutWeight(float cutWeight) {
			this.cutWeight = cutWeight;
		}

		public float getNoOfParts() {
			return noOfParts;
		}

		public void setNoOfParts(float noOfParts) {
			this.noOfParts = noOfParts;
		}

		public PartDetailsStatus getStatus() {
			return status;
		}

		public void setStatus(PartDetailsStatus status) {
			this.status = status;
		}

		public int getPartId() {
			return partId;
		}

		public void setPartId(int partId) {
			this.partId = partId;
		}

		@Override
		public String toString() {
			return "PartManifacturingDetailsDto [partId=" + partId
					+ ", number=" + number + ", weight=" + weight
					+ ", cutWeight=" + cutWeight + ", noOfParts=" + noOfParts
					+ ", status=" + status + "]";
		}
}
