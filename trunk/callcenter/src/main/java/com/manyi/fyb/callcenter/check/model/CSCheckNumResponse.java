package com.manyi.fyb.callcenter.check.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.manyi.fyb.callcenter.base.model.Response;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class CSCheckNumResponse extends Response{
	
	private int checkChange;
	
	private int checkPublishSell;
	
	private int checkPublishRent;
	
	private int checkLoop;
	

}
