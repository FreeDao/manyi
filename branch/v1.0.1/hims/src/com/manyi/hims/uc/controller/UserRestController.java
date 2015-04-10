/**
 * 
 */
package com.manyi.hims.uc.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import com.leo.jackson.FileDeserializer;
import com.leo.jackson.FileSerializer;
import com.manyi.hims.Global;
import com.manyi.hims.Response;
import com.manyi.hims.RestController;
import com.manyi.hims.entity.Residence;
import com.manyi.hims.entity.User;
import com.manyi.hims.uc.UcConst;
import com.manyi.hims.uc.service.UserService;
import com.manyi.hims.uc.service.UserService.AwardResponse;
import com.manyi.hims.uc.service.UserService.MyAccountResponse;
import com.manyi.hims.uc.service.UserService.ReportResponse;
import com.manyi.hims.uc.service.UserService.UserInfo;
import com.manyi.hims.util.CommonUtils;

/**
 * @author lei
 * 
 */
@Controller
@RequestMapping("/rest/uc")
public class UserRestController extends RestController {
	final String REGIST_MSG_CODE = "regist_msg_code";

	private UserService userService;

	public UserService getUserService() {
		return userService;
	}

	@Autowired
	@Qualifier("userService")
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public class MsgCodeResponse extends Response {
		private String msgCode;

		public String getMsgCode() {
			return msgCode;
		}

		public void setMsgCode(String msgCode) {
			this.msgCode = msgCode;
		}
	}

	@RequestMapping(value = "/insertResidence.rest", produces = "application/json")
	@ResponseBody
	public DeferredResult<Response> insertResidence() {

		Residence residence = new Residence();
		// residence.set

		getUserService().insertResidence(residence);
		return null;

	}

	/**
	 * 注册时 获取短信验证码
	 * 
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/findMsgCode.rest", produces = "application/json")
	@ResponseBody
	public DeferredResult<Response> findMsgCode(HttpSession session,
			@RequestBody RegistRequest req) {

		session.removeAttribute(REGIST_MSG_CODE);
		String code = this.userService.findMsgCode(req.getMobile());
		System.out.println(session.getId() + " " + code);
		session.setAttribute(REGIST_MSG_CODE, code);

		// 返回 注册 验证码
		MsgCodeResponse reponse = new MsgCodeResponse();
		reponse.setMsgCode(code);
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(reponse);
		return dr;
	}

	/**
	 * 验证 注册 短信验证码
	 * 
	 * @param session
	 * @param phone
	 * @return
	 */
	@RequestMapping(value = "/checkMsgCode.rest", produces = "application/json")
	@ResponseBody
	public DeferredResult<Response> checkMsgCode(HttpSession session,
			@RequestBody CheckMsgCodeRequest params) {
		String sessionCode = (String) session.getAttribute(REGIST_MSG_CODE);
		System.out.println("sessionId:" + session.getId() + " "
				+ session.getAttribute(REGIST_MSG_CODE));

		if (userService.checkVerify(params.getCheckVerifyCode(), sessionCode)) {
			userService.checkPassword(params.getPassword());
		}
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(new Response());

		return dr;
	}

	/**
	 * 检查手机号码是否已经注册
	 * 
	 * @param session
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/checkMobile.rest", produces = "application/json")
	@ResponseBody
	public DeferredResult<Response> checkMobile(HttpSession session,
			@RequestBody RegistRequest req) {
		this.userService.checkMobile(req.getMobile());
		Response result = new Response();
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(result);
		return dr;
	}

	/**
	 * 房源宝用户注册
	 * 
	 * @param session
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/regist.rest", produces = "application/json")
	@ResponseBody
	public DeferredResult<Response> regist(HttpServletRequest request,
			HttpSession session, @RequestBody RegistRequest params) {
		Response result = new Response();
		int num = this.userService.regist(params, null);
		if (num == 0) {
			result.setMessage("注册成功,审核中...");
			request.getSession().setAttribute(Global.REGIST_MSG_CODE, null);
		}
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(result);
		return dr;
	}

	/**
	 * 房源宝用户注册修改
	 * @param session
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/againRegist.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> againRegist(HttpServletRequest request,HttpSession session, @RequestBody AgainRegistRequest params) {
		Response result =  new Response();
		int num = this.userService.againRegist(params);
		if(num == 0){
			result.setMessage("信息修改成功,审核中...");
		}
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(result);
		return dr;
	}
	
	/**
	 * 注册使用验证失败之后 , 返回app具体的数据
	 * @param session
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/getFailedDetail.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<FailedDetailRes> getFailedDetail(HttpServletRequest request,HttpSession session, @RequestBody RegistRequest params) {
		FailedDetailRes res = this.userService.getFailedDetail(params.getMobile());
		//System.out.println(JSONObject.fromObject(res).toString());
		DeferredResult<FailedDetailRes> dr = new DeferredResult<FailedDetailRes>();
		dr.setResult(res);
		return dr;
	}
	
	
	public static class FailedDetailRes extends Response{
		private int userId;//用户ID
		private String realName;
		private int areaId;//城市ID
		private String areaName;//城市
		private int townId;//区域ID
		private String townName;//区域名称
		private String code;//身份证号码
		private String spreadName;//邀请码
		private File cardFile;//名片地址
		private File codeFile;//身份证照片
		private String remark;//失败原因
		
		public int getUserId() {
			return userId;
		}
		public void setUserId(int userId) {
			this.userId = userId;
		}
		public String getRealName() {
			return realName;
		}
		public void setRealName(String realName) {
			this.realName = realName;
		}
		public int getAreaId() {
			return areaId;
		}
		public void setAreaId(int areaId) {
			this.areaId = areaId;
		}
		public String getAreaName() {
			return areaName;
		}
		public void setAreaName(String areaName) {
			this.areaName = areaName;
		}
		public int getTownId() {
			return townId;
		}
		public void setTownId(int townId) {
			this.townId = townId;
		}
		public String getTownName() {
			return townName;
		}
		public void setTownName(String townName) {
			this.townName = townName;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getSpreadName() {
			return spreadName;
		}
		public void setSpreadName(String spreadName) {
			this.spreadName = spreadName;
		}
		@JsonSerialize(using=FileSerializer.class)
		public File getCardFile() {
			return cardFile;
		}
		public void setCardFile(File cardFile) {
			this.cardFile = cardFile;
		}
		@JsonSerialize(using=FileSerializer.class)
		public File getCodeFile() {
			return codeFile;
		}
		public void setCodeFile(File codeFile) {
			this.codeFile = codeFile;
		}
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
		}
		
	}
	
	
	public static class AgainRegistRequest {
		private int userId;
		private String spreadName;//推广人推广码
		private String realName;//身份证姓名
		private String code;//身份证号码
		private File cardFile;
		private File codeFile;
		private int areaId ;//所属区域
		private int cityId;//城市ID
		public int getUserId() {
			return userId;
		}
		public void setUserId(int userId) {
			this.userId = userId;
		}
		public String getSpreadName() {
			return spreadName;
		}
		public void setSpreadName(String spreadName) {
			this.spreadName = spreadName;
		}
		public String getRealName() {
			return realName;
		}
		public void setRealName(String realName) {
			this.realName = realName;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		@JsonDeserialize(using=FileDeserializer.class)
		public File getCardFile() {
			return cardFile;
		}
		public void setCardFile(File cardFile) {
			this.cardFile = cardFile;
		}
		@JsonDeserialize(using=FileDeserializer.class)
		public File getCodeFile() {
			return codeFile;
		}
		public void setCodeFile(File codeFile) {
			this.codeFile = codeFile;
		}
		public int getAreaId() {
			return areaId;
		}
		public void setAreaId(int areaId) {
			this.areaId = areaId;
		}
		public int getCityId() {
			return cityId;
		}
		public void setCityId(int cityId) {
			this.cityId = cityId;
		}
		
	}
	/**
	 * 注册信息 填充数据依赖对象
	 * 
	 * @author tiger
	 * 
	 */
	public static class RegistRequest {
		private String mobile;// 手机号
		private String password;// 登录密码
		private String spreadName;// 推广人推广码
		private String realName;// 身份证姓名
		private String code;// 身份证号码
		// private String codeUrl ; //身份证图片地址
		// private String cardUrl;//名片图片地址
		private File cardFile;
		private File codeFile;
		private int areaId;// 所属区域
		private String vilidate;// 验证码

		@JsonDeserialize(using = FileDeserializer.class)
		public File getCardFile() {
			return cardFile;
		}

		public void setCardFile(File cardFile) {
			this.cardFile = cardFile;
		}

		@JsonDeserialize(using = FileDeserializer.class)
		public File getCodeFile() {
			return codeFile;
		}

		public void setCodeFile(File codeFile) {
			this.codeFile = codeFile;
		}

		public String getMobile() {
			return mobile;
		}

		public void setMobile(String mobile) {
			this.mobile = mobile;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getSpreadName() {
			return spreadName;
		}

		public void setSpreadName(String spreadName) {
			this.spreadName = spreadName;
		}

		public String getRealName() {
			return realName;
		}

		public void setRealName(String realName) {
			this.realName = realName;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public int getAreaId() {
			return areaId;
		}

		public void setAreaId(int areaId) {
			this.areaId = areaId;
		}

		public String getVilidate() {
			return vilidate;
		}

		public void setVilidate(String vilidate) {
			this.vilidate = vilidate;
		}

	}

	/**
	 * 如果访问/xxxxx转发到表单输入页面 如果访问/xxxxxSubmit则进行xxxxx处理
	 * 
	 * @param suffix
	 * @param session
	 * @param command
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/login2.rest", produces = "application/json")
	@ResponseBody
	public DeferredResult<Response> login2(HttpSession session,
			@RequestBody LoginRequest params) {

		final User user = getUserService().login(params.getLoginName(),
				params.getPassword());
		session.setAttribute("uid", user.getUid());

		@SuppressWarnings("unused")
		Response result = new Response() {
			private int uid;
			private String userName;
			private String mobile;
			private String email;
			private int digitalAccount;
			private boolean disable;
			private String realName;
			private int gender;

			{
				BeanUtils.copyProperties(user, this);
			}

			public int getUid() {
				return uid;
			}

			public void setUid(int uid) {
				this.uid = uid;
			}

			public String getUserName() {
				return userName;
			}

			public void setUserName(String userName) {
				this.userName = userName;
			}

			public String getMobile() {
				return mobile;
			}

			public void setMobile(String mobile) {
				this.mobile = mobile;
			}

			public String getEmail() {
				return email;
			}

			public void setEmail(String email) {
				this.email = email;
			}

			public int getDigitalAccount() {
				return digitalAccount;
			}

			public void setDigitalAccount(int digitalAccount) {
				this.digitalAccount = digitalAccount;
			}

			public boolean isDisable() {
				return disable;
			}

			public void setDisable(boolean disable) {
				this.disable = disable;
			}

			public String getRealName() {
				return realName;
			}

			public void setRealName(String realName) {
				this.realName = realName;
			}

			public int getGender() {
				return gender;
			}

			public void setGender(int gender) {
				this.gender = gender;
			}
		};
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(result);

		return dr;
	}

	/**
	 * 如果访问/xxxxx转发到表单输入页面 如果访问/xxxxxSubmit则进行xxxxx处理
	 * 
	 * @param suffix
	 * @param session
	 * @param command
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/login.rest", produces = "application/json")
	@ResponseBody
	public DeferredResult<Response> login(HttpSession session,
			@NotEmpty(message = "{ec_10100002}") String loginName,
			@NotEmpty(message = "{ec_10100002}") String password) {

		final User user = getUserService().login(loginName, password);
		session.setAttribute("uid", user.getUid());

		@SuppressWarnings("unused")
		Response result = new Response() {
			private int uid;
			private String userName;
			private String mobile;
			private String email;
			private int digitalAccount;
			private boolean disable;
			private String realName;
			private int gender;

			{
				BeanUtils.copyProperties(user, this);
			}

			public int getUid() {
				return uid;
			}

			public void setUid(int uid) {
				this.uid = uid;
			}

			public String getUserName() {
				return userName;
			}

			public void setUserName(String userName) {
				this.userName = userName;
			}

			public String getMobile() {
				return mobile;
			}

			public void setMobile(String mobile) {
				this.mobile = mobile;
			}

			public String getEmail() {
				return email;
			}

			public void setEmail(String email) {
				this.email = email;
			}

			public int getDigitalAccount() {
				return digitalAccount;
			}

			public void setDigitalAccount(int digitalAccount) {
				this.digitalAccount = digitalAccount;
			}

			public boolean isDisable() {
				return disable;
			}

			public void setDisable(boolean disable) {
				this.disable = disable;
			}

			public String getRealName() {
				return realName;
			}

			public void setRealName(String realName) {
				this.realName = realName;
			}

			public int getGender() {
				return gender;
			}

			public void setGender(int gender) {
				this.gender = gender;
			}
		};
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(result);

		return dr;
	}

	/**
	 * 登录
	 * 
	 * @param session
	 * @param loginName
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/userLogin.rest", produces = "application/json")
	@ResponseBody
	public DeferredResult<Response> userLogin(HttpSession session,
			@RequestBody LoginRequest params) {

		final UserInfo user = getUserService().userLogin(params.getLoginName(),
				params.getPassword());
		session.setAttribute(UcConst.SESSION_UID_KEY, user.getUid());
		DeferredResult<Response> dr = new DeferredResult<Response>();

		@SuppressWarnings("unused")
		Response result = new Response() {

			private int uid;
			private String userName;// 用户名
			private int state;// 账户状态 1 审核中、 0 已审核、2 审核失败
			private int sumCount; // 每日限制查看的额的总数量
			private int PublishCount;// 今天已经使用的数量
			private String alipayAccount;
			private int areaId;
			{
				BeanUtils.copyProperties(user, this);
			}

			public int getUid() {
				return uid;
			}

			public void setUid(int uid) {
				this.uid = uid;
			}

			public String getUserName() {
				return userName;
			}

			public void setUserName(String userName) {
				this.userName = userName;
			}

			public int getState() {
				return state;
			}

			public void setState(int state) {
				this.state = state;
			}

			public int getSumCount() {
				return sumCount;
			}

			public void setSumCount(int sumCount) {
				this.sumCount = sumCount;
			}

			public int getPublishCount() {
				return PublishCount;
			}

			public void setPublishCount(int publishCount) {
				PublishCount = publishCount;
			}

			public String getAlipayAccount() {
				return alipayAccount;
			}

			public void setAlipayAccount(String alipayAccount) {
				this.alipayAccount = alipayAccount;
			}

			public int getAreaId() {
				return areaId;
			}

			public void setAreaId(int areaId) {
				this.areaId = areaId;
			}

		};
		dr.setResult(result);
		return dr;
	}

	/**
	 * 发送短信
	 * 
	 * @param session
	 * @param phone
	 * @return
	 */
	@RequestMapping(value = "/findLoginPwd.rest", produces = "application/json")
	@ResponseBody
	public DeferredResult<Response> findLoginPwd(HttpSession session,
			@RequestBody FindLoginPwdRequest params) {
		final long verifyCode = userService.findLoginPwd(params.getPhone());
		session.setAttribute(Global.SESSION_FINDPWD_KEY,
				String.valueOf(verifyCode));

		DeferredResult<Response> dr = new DeferredResult<Response>();
		@SuppressWarnings("unused")
		Response result = new Response() {
			private long random;

			{
				random = verifyCode;
			}

			public long getRandom() {
				return random;
			}

			public void setRandom(long random) {
				this.random = random;
			}
		};
		dr.setResult(result);

		return dr;
	}

	/**
	 * 验证短信验证码
	 * 
	 * @param session
	 * @param phone
	 * @return
	 */
	@RequestMapping(value = "/checkVerifyCode.rest", produces = "application/json")
	@ResponseBody
	public DeferredResult<Response> checkVerifyCode(HttpSession session,/*
																		 * @
																		 * ModelAttribute
																		 * (
																		 * Global
																		 * .
																		 * SESSION_FINDPWD_KEY
																		 * )
																		 * String
																		 * sessionCode
																		 * ,
																		 */
			@RequestBody CheckVerifyCodeRequest params) {
		String sessionCode = (String) session
				.getAttribute(Global.SESSION_FINDPWD_KEY);
		System.out.println(sessionCode);
		userService.checkVerify(params.getCheckVerifyCode(), sessionCode);
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(new Response());

		return dr;
	}

	/**
	 * 修改密码,使用旧密码验证
	 * 
	 * @param session
	 * @param phone
	 * @return
	 */
	@RequestMapping(value = "/modifyLoginPwd.rest", produces = "application/json")
	@ResponseBody
	public DeferredResult<Response> modifyLoginPwd(
			@RequestBody ModifyLoginPwdRequest params) {
		userService.getUser(params.getUid(), params.getOldPwd());
		userService.modifyLoginPwd(params.getUid(),params.getOldPwd(), params.getNewPwd());
		return CommonUtils.deferredResult(new Response());
	}
	
	/**
	 * 验证码修改密码
	 * @param session
	 * @param phone
	 * @return
	 */
	@RequestMapping(value = "/updateLoginPwd.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> updateLoginPwd(
			@RequestBody UpdateLoginPwdRequest params) {
		userService.updateLoginPwd(params.getPhone(), params.getNewPwd());
		return CommonUtils.deferredResult(new Response());
	}

	/**
	 * 退出登录
	 * 
	 * @param session
	 * @param phone
	 * @return
	 */

	@RequestMapping(value = "/exit.rest", produces = "application/json")
	@ResponseBody
	public DeferredResult<Response> exit(HttpServletRequest request,
			HttpSession session) {
		@SuppressWarnings("rawtypes")
		Enumeration enumeration = session.getAttributeNames();
		while (enumeration.hasMoreElements()) {
			String sessionName = (String) enumeration.nextElement();
			session.removeAttribute(sessionName);
		}
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(new Response());

		return dr;
	}

	/**
	 * 我的
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/myAccount.rest", produces = "application/json")
	@ResponseBody
	public DeferredResult<Response> myAccount(
			@RequestBody MyAccountRequest params) {

		final MyAccountResponse account = userService
				.myAccount(params.getUid());
		DeferredResult<Response> dr = new DeferredResult<Response>();
		@SuppressWarnings("unused")
		Response result = new Response() {
			private double blance;// 账户余额
			private String spreadId;// 注册成功自动生成推广码
			private int spreanCount;// 已推广人数
			private int awardCount; // 我的奖金记录数
			private String paypalAccount; // 我的支付宝账号(如果支付宝账号为空未绑定否则为绑定)
			private int createCount;// 发布记录

			{
				BeanUtils.copyProperties(account, this);
			}

			public double getBlance() {
				return blance;
			}

			public void setBlance(double blance) {
				this.blance = blance;
			}

			public String getSpreadId() {
				return spreadId;
			}

			public void setSpreadId(String spreadId) {
				this.spreadId = spreadId;
			}

			public int getSpreanCount() {
				return spreanCount;
			}

			public void setSpreanCount(int spreanCount) {
				this.spreanCount = spreanCount;
			}

			public int getCreateCount() {
				return createCount;
			}

			public int getAwardCount() {
				return awardCount;
			}

			public void setAwardCount(int awardCount) {
				this.awardCount = awardCount;
			}

			public String getPaypalAccount() {
				return paypalAccount;
			}

			public void setPaypalAccount(String paypalAccount) {
				this.paypalAccount = paypalAccount;
			}

			public void setCreateCount(int createCount) {
				this.createCount = createCount;
			}

		};
		dr.setResult(result);

		return dr;
	}
	/**
	 * 我的奖金
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/award.rest", produces = "application/json")
	@ResponseBody
	public DeferredResult<Response> awardForMe(
			@RequestBody MyAccountRequest params) {
		final List<AwardResponse> awards = this.userService.awardForMe(params
				.getUid(),params.getMarkTime());
		Response result = new Response() {
			private List<AwardResponse> awardList = new ArrayList<AwardResponse>();
			{
				this.awardList = awards;
			}

			public List<AwardResponse> getAwardList() {
				return awardList;
			}

			public void setAwardList(List<AwardResponse> awardList) {
				this.awardList = awardList;
			}
		};

		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(result);
		return dr;
	}
	/**
	 * 我的奖金加载列表
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/findAwardPage.rest", produces = "application/json")
	@ResponseBody
	public DeferredResult<Response> findAwardPage(
			@RequestBody AwardPageRequest awardPage) {
		final List<AwardResponse> awards = this.userService.findAwardPage(awardPage);
		Response result = new Response() {
			private List<AwardResponse> awardList = new ArrayList<AwardResponse>();
			{
				this.awardList = awards;
			}

			public List<AwardResponse> getAwardList() {
				return awardList;
			}

			public void setAwardList(List<AwardResponse> awardList) {
				this.awardList = awardList;
			}
		};

		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(result);
		return dr;
	}
	@RequestMapping(value = "/bindPaypal.rest", produces = "application/json")
	@ResponseBody
	public DeferredResult<Response> bindingPaypal(
			@RequestBody BindPaypalRequest bindPaypal) {
		final int resultNum = this.userService.bindingPaypal(bindPaypal);
		Response result = new Response() {
			{
				if (resultNum == 0) {
					// 绑定成功
				}
			}
		};
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(result);
		return dr;
	}

	@RequestMapping(value = "/changePaypal.rest", produces = "application/json")
	@ResponseBody
	public DeferredResult<Response> changePaypal(
			@RequestBody ChangePaypalRequest changePaypal) {
		final int resultNum = this.userService.changePaypal(changePaypal);
		Response result = new Response() {
			{
				if (resultNum == 0) {
					// 绑定成功
				}
			}
		};
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(result);
		return dr;
	}

	/**
	 * init DB data for acronym
	 */
	@RequestMapping(value = "/initAcronymData.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> initAcronymData() {
		final int resultNum = userService.initAcronymData();
		Response result = new Response(){
			{
				if(resultNum == 0){
					//绑定成功
				}
			}
		};
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(result);
		return dr;
	}
	/**
	 * 新增小区
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/addEstate.rest", produces = "application/json")
	@ResponseBody
	public DeferredResult<Response> addEstate(@RequestBody AddEstateRequest params) {
		userService.addEsate(params.getEsateName(), params.getEstateId(), params.getAddress(), params.getUid());
		DeferredResult<Response> dr = new DeferredResult<Response>();

		dr.setResult(new Response());

		return dr;
	}

	/**
	 * 举报
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/reportDis.rest", produces = "application/json")
	@ResponseBody
	public DeferredResult<Response> reportDis(@RequestBody ReportRequest params) {
		final ReportResponse report = userService.report(params.getHouseId());
		DeferredResult<Response> dr = new DeferredResult<Response>();
		@SuppressWarnings("unused")
		Response result = new Response() {
			private int houseId;// 房子ID
			private boolean sellEnabled;// 是否在租
			private boolean rentEnabled;// 是否在售
			private String estateName;
			private String building;
			private String room;
			{
				BeanUtils.copyProperties(report, this);
			}

			public int getHouseId() {
				return houseId;
			}

			public void setHouseId(int houseId) {
				this.houseId = houseId;
			}

			public boolean isSellEnabled() {
				return sellEnabled;
			}

			public void setSellEnabled(boolean sellEnabled) {
				this.sellEnabled = sellEnabled;
			}

			public boolean isRentEnabled() {
				return rentEnabled;
			}

			public void setRentEnabled(boolean rentEnabled) {
				this.rentEnabled = rentEnabled;
			}

			public String getEstateName() {
				return estateName;
			}

			public void setEstateName(String estateName) {
				this.estateName = estateName;
			}

			public String getBuilding() {
				return building;
			}

			public void setBuilding(String building) {
				this.building = building;
			}

			public String getRoom() {
				return room;
			}

			public void setRoom(String room) {
				this.room = room;
			}
		};
		dr.setResult(result);

		return dr;
	}

	/**
	 * 提交举报
	 * 
	 * @author fangyouhui
	 * 
	 */
	@RequestMapping(value = "/reportSubmit.rest", produces = "application/json")
	@ResponseBody
	public DeferredResult<Response> reportSubmit(
			@RequestBody ReportSubmitRequest params) {
		userService.reportSubmit(params.getHouseId(),
				params.getReportLogTypeId(), params.getRemark(),
				params.getUid());
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(new Response());
		return dr;
	}

	/**
	 * 奖惩说明具体内容
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/rpTips.rest", produces = "application/json")
	@ResponseBody
	public DeferredResult<Response> rpTips(@RequestBody RPTipsRequest params) {
		return CommonUtils.deferredResult(new Response());
	}

	public static class BindPaypalRequest {
		private String paypalAccount; // 支付宝账号
		@NotEmpty(message = "{ec_100003}")
		private int uid;

		public int getUid() {
			return uid;
		}

		public void setUid(int uid) {
			this.uid = uid;
		}

		public String getPaypalAccount() {
			return paypalAccount;
		}

		public void setPaypalAccount(String paypalAccount) {
			this.paypalAccount = paypalAccount;
		}
	}

	public static class ChangePaypalRequest {
		private String paypalAccount; // 支付宝账号
		private String password; // 个人账号密码
		@NotEmpty(message = "{ec_100003}")
		private int uid;

		public int getUid() {
			return uid;
		}

		public void setUid(int uid) {
			this.uid = uid;
		}

		public String getPaypalAccount() {
			return paypalAccount;
		}

		public void setPaypalAccount(String paypalAccount) {
			this.paypalAccount = paypalAccount;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

	}

	public static class ReportSubmitRequest {
		private int houseId;
		private int reportLogTypeId;
		private String remark;
		private int uid;

		public int getHouseId() {
			return houseId;
		}

		public void setHouseId(int houseId) {
			this.houseId = houseId;
		}

		public int getReportLogTypeId() {
			return reportLogTypeId;
		}

		public void setReportLogTypeId(int reportLogTypeId) {
			this.reportLogTypeId = reportLogTypeId;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

		public int getUid() {
			return uid;
		}

		public void setUid(int uid) {
			this.uid = uid;
		}
	}

	public static class ReportRequest {
		private int houseId;

		public int getHouseId() {
			return houseId;
		}

		public void setHouseId(int houseId) {
			this.houseId = houseId;
		}
	}

	public static class AddEstateRequest {
		private String esateName;
		private int estateId;
		private String address;
		private int uid;

		public String getEsateName() {
			return esateName;
		}

		public void setEsateName(String esateName) {
			this.esateName = esateName;
		}

		public int getEstateId() {
			return estateId;
		}

		public void setEstateId(int estateId) {
			this.estateId = estateId;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public int getUid() {
			return uid;
		}

		public void setUid(int uid) {
			this.uid = uid;
		}
	}

	public static class MyAccountRequest {
		@NotEmpty(message = "{ec_100003}")
		private int uid;
		private Long markTime;
		public int getUid() {
			return uid;
		}
		public void setUid(int uid) {
			this.uid = uid;
		}
		public Long getMarkTime() {
			return markTime;
		}
		public void setMarkTime(Long markTime) {
			this.markTime = markTime;
		}
	}

	public static class RPTipsRequest {
		@NotEmpty(message = "{ec_100003}")
		private int uid;

		public int getUid() {
			return uid;
		}

		public void setUid(int uid) {
			this.uid = uid;
		}
	}
	
	public static class UpdateLoginPwdRequest{
		private String phone;
		private String newPwd;
		
		@NotEmpty(message="{ec_100003}")
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		@NotEmpty(message="{ec_100003}")
		public String getNewPwd() {
			return newPwd;
		}
		public void setNewPwd(String newPwd) {
			this.newPwd = newPwd;
		}
	}

	public static class ModifyLoginPwdRequest {

		@NotEmpty(message = "{ec_100003}")
		private int uid;
		private String oldPwd;
		private String newPwd;
		private String confPwd;

		@NotEmpty(message = "{ec_100003}")
		public String getOldPwd() {
			return oldPwd;
		}

		public int getUid() {
			return uid;
		}

		public void setUid(int uid) {
			this.uid = uid;
		}

		@NotEmpty(message = "{ec_100003}")
		public String getConfPwd() {
			return confPwd;
		}

		public void setConfPwd(String confPwd) {
			this.confPwd = confPwd;
		}

		public void setOldPwd(String oldPwd) {
			this.oldPwd = oldPwd;
		}

		@NotEmpty(message = "{ec_100003}")
		public String getNewPwd() {
			return newPwd;
		}

		public void setNewPwd(String newPwd) {
			this.newPwd = newPwd;
		}
	}

	public static class CheckMsgCodeRequest {
		private String checkVerifyCode;
		private String password;

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getCheckVerifyCode() {
			return checkVerifyCode;
		}

		public void setCheckVerifyCode(String checkVerifyCode) {
			this.checkVerifyCode = checkVerifyCode;
		}
	}

	public static class CheckVerifyCodeRequest {
		private String checkVerifyCode;

		@NotEmpty(message = "{ec_100003}")
		public String getCheckVerifyCode() {
			return checkVerifyCode;
		}

		public void setCheckVerifyCode(String checkVerifyCode) {
			this.checkVerifyCode = checkVerifyCode;
		}
	}

	public static class FindLoginPwdRequest {
		private String phone;

		@NotEmpty(message = "{ec_100003}")
		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}
	}
	public static class AwardPageRequest{
		private int userId;
		private int start;
		private int end;
		private Long markTime;
		public int getUserId() {
			return userId;
		}
		public void setUserId(int userId) {
			this.userId = userId;
		}
		public int getStart() {
			return start;
		}
		public void setStart(int start) {
			this.start = start;
		}
		public int getEnd() {
			return end;
		}
		public void setEnd(int end) {
			this.end = end;
		}
		public Long getMarkTime() {
			return markTime;
		}
		public void setMarkTime(Long markTime) {
			this.markTime = markTime;
		}
	}
	public static class LoginRequest {
		private String loginName;
		private String password;

		@NotEmpty(message = "{ec_100003}")
		public String getLoginName() {
			return loginName;
		}

		public void setLoginName(String loginName) {
			this.loginName = loginName;
		}

		@NotEmpty(message = "{ec_100003}")
		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
	}

}