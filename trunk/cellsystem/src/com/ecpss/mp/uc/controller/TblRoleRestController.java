/**
 * 
 */
package com.ecpss.mp.uc.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.ecpss.mp.Global;
import com.ecpss.mp.PageResponse;
import com.ecpss.mp.Response;
import com.ecpss.mp.RestController;
import com.ecpss.mp.entity.TblRole;
import com.ecpss.mp.uc.service.TblRoleService;
import com.leo.common.beanutil.BeanUtils;

/**
 * @author lei
 * 
 */
@Controller
@RequestMapping("/tblrole")
@SessionAttributes(Global.SESSION_UID_KEY)
public class TblRoleRestController extends RestController {
	private TblRoleService tblRoleService;

	public TblRoleService getTblRoleService() {
		return tblRoleService;
	}

	@Autowired
	@Qualifier("tblRoleService")
	public void setTblRoleService(TblRoleService tblroleService) {
		this.tblRoleService = tblroleService;
	}

	@RequestMapping(value = "/view.rest", produces = "application/json")
	@ResponseBody
	public Response view(String id) {
		try {
			String s = new String(id.getBytes("iso-8859-1"), "utf-8");
			id = s;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		final List<TblRole> tblroles = getTblRoleService().getTblRoleById(id);
		@SuppressWarnings("unused")
		Response result = new Response() {
			  /**
		     * did       db_column: D_ID 
		     */ 	
			
			private java.lang.Long did;
		    /**
		     * dname       db_column: D_NAME 
		     */ 	
			@Length(max=32)
			private java.lang.String dname;
		    /**
		     * ddesc       db_column: D_DESC 
		     */ 	
			@Length(max=1024)
			private java.lang.String ddesc;
		    /**
		     * dstatus       db_column: D_STATUS 
		     */ 	
			
			private java.lang.Integer dstatus;
		    /**
		     * dcreatetime       db_column: D_CREATETIME 
		     */ 	
			
			private java.util.Date dcreatetime;
		    /**
		     * dupdatetime       db_column: D_UPDATETIME 
		     */ 	
			
			private java.util.Date dupdatetime;
		    /**
		     * dtype       db_column: D_TYPE 
		     */ 	
			
			private java.lang.Integer dtype;
		    /**
		     * dinitvalue       db_column: D_INITVALUE 
		     */ 	
			
			private java.lang.Integer dinitvalue;
			//columns END



			{
				if (tblroles != null && tblroles.size() > 0) {
					 BeanUtils.copyProperties(tblroles.get(0), this);
				}
			}



			public java.lang.Long getDid() {
				return did;
			}



			public void setDid(java.lang.Long did) {
				this.did = did;
			}



			public java.lang.String getDname() {
				return dname;
			}



			public void setDname(java.lang.String dname) {
				this.dname = dname;
			}



			public java.lang.String getDdesc() {
				return ddesc;
			}



			public void setDdesc(java.lang.String ddesc) {
				this.ddesc = ddesc;
			}



			public java.lang.Integer getDstatus() {
				return dstatus;
			}



			public void setDstatus(java.lang.Integer dstatus) {
				this.dstatus = dstatus;
			}



			public java.util.Date getDcreatetime() {
				return dcreatetime;
			}



			public void setDcreatetime(java.util.Date dcreatetime) {
				this.dcreatetime = dcreatetime;
			}



			public java.util.Date getDupdatetime() {
				return dupdatetime;
			}



			public void setDupdatetime(java.util.Date dupdatetime) {
				this.dupdatetime = dupdatetime;
			}



			public java.lang.Integer getDtype() {
				return dtype;
			}



			public void setDtype(java.lang.Integer dtype) {
				this.dtype = dtype;
			}



			public java.lang.Integer getDinitvalue() {
				return dinitvalue;
			}



			public void setDinitvalue(java.lang.Integer dinitvalue) {
				this.dinitvalue = dinitvalue;
			}
			
			

		};

		return result;
	}

	@RequestMapping(value = "/list.rest", produces = "application/json")
	@ResponseBody
	public PageResponse<TblRole> tblrole_list(HttpSession session, Integer pageNo, Integer pageSize) {

		final List<TblRole> tblroles1 = getTblRoleService().getTblRoleByPage(pageNo, pageSize);

		@SuppressWarnings("unused")
		PageResponse<TblRole> result = new PageResponse<TblRole>() {
			List<TblRole> rows = new ArrayList<TblRole>();
			{
				rows.addAll(tblroles1);
				// BeanUtils.copyProperties(tblroles1, this);
			}
			public List<TblRole> getRows() {
				return rows;
			}
			public void setRows(List<TblRole> rows) {
				this.rows = rows;
			}

		};

		return result;
	}

}
