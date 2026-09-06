package apiTests.models.store;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InventoryResponse{

	@JsonProperty("approved")
	private int approved;

	@JsonProperty("placed")
	private int placed;

	@JsonProperty("delivered")
	private int delivered;

	public void setApproved(int approved){
		this.approved = approved;
	}

	public int getApproved(){
		return approved;
	}

	public void setPlaced(int placed){
		this.placed = placed;
	}

	public int getPlaced(){
		return placed;
	}

	public void setDelivered(int delivered){
		this.delivered = delivered;
	}

	public int getDelivered(){
		return delivered;
	}
}