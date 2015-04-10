/**
 * 
 */
package com.manyi.hims.check.service;

import com.manyi.hims.Response;
import com.manyi.hims.check.model.FloorRequest;


/**
 * @author Tom
 * 
 */
public interface ReportService {
 
	public Response auditSuccess(FloorRequest floorRequest);
	
	public Response auditFail(FloorRequest floorRequest);

	
}
