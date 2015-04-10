/**
 * 
 */
package com.ecpss.mp.uc.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

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
import com.ecpss.mp.entity.TblOperator;
import com.ecpss.mp.uc.service.TblOperatorService;
import com.leo.common.beanutil.BeanUtils;

/**
 * @author lei
 * 
 */
@Controller
@RequestMapping("/tbloperator")
@SessionAttributes(Global.SESSION_UID_KEY)
public class TblOperatorRestController extends RestController {
	private TblOperatorService tblOperatorService;

	public TblOperatorService getTblOperatorService() {
		return tblOperatorService;
	}

	@Autowired
	@Qualifier("tblOperatorService")
	public void setTblOperatorService(TblOperatorService tbloperatorService) {
		this.tblOperatorService = tbloperatorService;
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
		final List<TblOperator> tbloperators = getTblOperatorService().getTblOperatorById(id);
		@SuppressWarnings("unused")
		Response result = new Response() {
			/**
		     * did       db_column: D_ID 
		     */ 	
			
			private java.lang.Long did;
		    /**
		     * dloginname       db_column: D_LOGINNAME 
		     */ 	
			private java.lang.String dloginname;
		    /**
		     * dpassword       db_column: D_PASSWORD 
		     */ 	
			private java.lang.String dpassword;
		    /**
		     * dgender       db_column: D_GENDER 
		     */ 	
			
			private java.lang.Integer dgender;
		    /**
		     * drealname       db_column: D_REALNAME 
		     */ 	
			private java.lang.String drealname;
		    /**
		     * displatoperator       db_column: D_ISPLATOPERATOR 
		     */ 	
			
			private java.lang.Integer displatoperator;
		    /**
		     * doperatortype       db_column: D_OPERATORTYPE 
		     */ 	
			
			private java.lang.Integer doperatortype;
		    /**
		     * dcompanyid       db_column: D_COMPANYID 
		     */ 	
			
			private java.lang.Long dcompanyid;
		    /**
		     * dloginip       db_column: D_LOGINIP 
		     */ 	
			private java.lang.String dloginip;
		    /**
		     * dlogintime       db_column: D_LOGINTIME 
		     */ 	
			
			private java.util.Date dlogintime;
		    /**
		     * dcreatetime       db_column: D_CREATETIME 
		     */ 	
			
			private java.util.Date dcreatetime;
		    /**
		     * dupdatetime       db_column: D_UPDATETIME 
		     */ 	
			
			private java.util.Date dupdatetime;
		    /**
		     * demail       db_column: D_EMAIL 
		     */ 	
			private java.lang.String demail;
		    /**
		     * dtel       db_column: D_TEL 
		     */ 	
			private java.lang.String dtel;
		    /**
		     * dstatus       db_column: D_STATUS 
		     */ 	
			
			private java.lang.Integer dstatus;
		    /**
		     * derrortimes       db_column: D_ERRORTIMES 
		     */ 	
			
			private java.lang.Integer derrortimes;
			//columns END

			{
				if (tbloperators != null && tbloperators.size() > 0) {
					 BeanUtils.copyProperties(tbloperators.get(0), this);
				}
			}

			public java.lang.Long getDid() {
				return did;
			}

			public void setDid(java.lang.Long did) {
				this.did = did;
			}

			public java.lang.String getDloginname() {
				return dloginname;
			}

			public void setDloginname(java.lang.String dloginname) {
				this.dloginname = dloginname;
			}

			public java.lang.String getDpassword() {
				return dpassword;
			}

			public void setDpassword(java.lang.String dpassword) {
				this.dpassword = dpassword;
			}

			public java.lang.Integer getDgender() {
				return dgender;
			}

			public void setDgender(java.lang.Integer dgender) {
				this.dgender = dgender;
			}

			public java.lang.String getDrealname() {
				return drealname;
			}

			public void setDrealname(java.lang.String drealname) {
				this.drealname = drealname;
			}

			public java.lang.Integer getDisplatoperator() {
				return displatoperator;
			}

			public void setDisplatoperator(java.lang.Integer displatoperator) {
				this.displatoperator = displatoperator;
			}

			public java.lang.Integer getDoperatortype() {
				return doperatortype;
			}

			public void setDoperatortype(java.lang.Integer doperatortype) {
				this.doperatortype = doperatortype;
			}

			public java.lang.Long getDcompanyid() {
				return dcompanyid;
			}

			public void setDcompanyid(java.lang.Long dcompanyid) {
				this.dcompanyid = dcompanyid;
			}

			public java.lang.String getDloginip() {
				return dloginip;
			}

			public void setDloginip(java.lang.String dloginip) {
				this.dloginip = dloginip;
			}

			public java.util.Date getDlogintime() {
				return dlogintime;
			}

			public void setDlogintime(java.util.Date dlogintime) {
				this.dlogintime = dlogintime;
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

			public java.lang.String getDemail() {
				return demail;
			}

			public void setDemail(java.lang.String demail) {
				this.demail = demail;
			}

			public java.lang.String getDtel() {
				return dtel;
			}

			public void setDtel(java.lang.String dtel) {
				this.dtel = dtel;
			}

			public java.lang.Integer getDstatus() {
				return dstatus;
			}

			public void setDstatus(java.lang.Integer dstatus) {
				this.dstatus = dstatus;
			}

			public java.lang.Integer getDerrortimes() {
				return derrortimes;
			}


		};

		return result;
	}

	@RequestMapping(value = "/list.rest", produces = "application/json")
	@ResponseBody
	public PageResponse<TblOperator> tbloperator_list2(HttpSession session, Integer pageNo, Integer pageSize) {

		final List<TblOperator> tbloperators1 = getTblOperatorService().getTblOperatorByPage(pageNo, pageSize);

		@SuppressWarnings("unused")
		PageResponse<TblOperator> result = new PageResponse<TblOperator>() {
			List<TblOperator> rows = new ArrayList<TblOperator>();
			{
				rows.addAll(tbloperators1);
				// BeanUtils.copyProperties(tbloperators1, this);
			}
			public List<TblOperator> getRows() {
				return rows;
			}
			public void setRows(List<TblOperator> rows) {
				this.rows = rows;
			}

		};

		return result;
	}

}
