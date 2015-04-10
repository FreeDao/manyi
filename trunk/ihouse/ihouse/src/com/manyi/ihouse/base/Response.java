/**
 * 
 */
package com.manyi.ihouse.base;

import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * @author leo.li
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Response {
    private int errorCode = 0;
    private String message;

    /**
     * 
     */
    public Response() {
        // TODO Auto-generated constructor stub
    }
    
    
	public Response( String message) {
		super();
		this.message = message;
	}

    public Response(int errorCode, String message) {
		super();
		this.errorCode = errorCode;
		this.message = message;
	}


	public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
