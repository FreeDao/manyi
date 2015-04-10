package com.manyi.hims.uc.service;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.util.HSSFColor.GOLD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.leo.common.util.DataUtil;
import com.leo.common.util.DateUtil;
import com.leo.common.util.MD5Digest;
import com.leo.jaxrs.fault.LeoFault;
import com.manyi.hims.BaseService;
import com.manyi.hims.Global;
import com.manyi.hims.area.service.AreaService;
import com.manyi.hims.common.service.CommonService;
import com.manyi.hims.entity.Address;
import com.manyi.hims.entity.Area;
import com.manyi.hims.entity.AreaHistory;
import com.manyi.hims.entity.Estate;
import com.manyi.hims.entity.EstateHistory;
import com.manyi.hims.entity.Estate_;
import com.manyi.hims.entity.House;
import com.manyi.hims.entity.HouseResource;
import com.manyi.hims.entity.HouseResourceHistory;
import com.manyi.hims.entity.HouseResourceTemp;
import com.manyi.hims.entity.HouseResourceViewCount;
import com.manyi.hims.entity.HouseResourceViewCount_;
import com.manyi.hims.entity.Pay;
import com.manyi.hims.entity.Residence;
import com.manyi.hims.entity.ResidenceResourceHistory;
import com.manyi.hims.entity.User;
import com.manyi.hims.entity.User_;
import com.manyi.hims.houseresource.service.HouseResourceService;
import com.manyi.hims.sourcelog.SourceLogConst;
import com.manyi.hims.uc.PinYin;
import com.manyi.hims.uc.UcConst;
import com.manyi.hims.uc.controller.UserRestController.AgainRegistRequest;
import com.manyi.hims.uc.controller.UserRestController.AwardPageRequest;
import com.manyi.hims.uc.controller.UserRestController.BindPaypalRequest;
import com.manyi.hims.uc.controller.UserRestController.ChangePaypalRequest;
import com.manyi.hims.uc.controller.UserRestController.FailedDetailRes;
import com.manyi.hims.uc.controller.UserRestController.RegistRequest;
import com.manyi.hims.util.EntityUtils;
import com.manyi.hims.util.InfoUtils;
import com.manyi.hims.util.EntityUtils.ActionTypeEnum;
import com.manyi.hims.util.EntityUtils.StatusEnum;
import com.manyi.hims.util.MailUtils;
import com.manyi.hims.util.PushUtils;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

@Service(value = "userService")
@Scope(value = "singleton")
public class UserServiceImpl extends BaseService implements UserService {
	Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Value("${constants.uploadBasePath}")
	private String imgPath;

	@Autowired
	private HouseResourceService houseResourceService;
	
	@Autowired
	private AreaService areaService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private PushUtils pushUtils;
	
	
	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	/**
	 * 验证获取用户信息包括密码
	 */
	public User getUser(int uid, String passwd) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		Root<User> user = cq.from(User.class);
		cq.where(cb.and(cb.equal(user.get(User_.uid), uid), cb.equal(user.get(User_.password),  MD5Digest.getMD5Digest(passwd)))).select(user);
		try {
			User userResult = getEntityManager().createQuery(cq).getSingleResult();
			if (userResult == null) {
				throw new LeoFault(UcConst.UC_ERROR100019);
			}
			if (userResult.getState() == 2) {
				throw new LeoFault(UcConst.UC_ERROR100002);
			}
			return userResult;
		} catch (NoResultException e) {
			throw new LeoFault(UcConst.UC_ERROR100019);
		}
	}

	public User login(String loginName, String password) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		Root<User> user = cq.from(User.class);
		cq.where(cb.and(cb.equal(user.get(User_.mobile), loginName), cb.equal(user.get(User_.password),  MD5Digest.getMD5Digest(password)))).select(user);
		try {
			User userResult = getEntityManager().createQuery(cq).getSingleResult();
			if (userResult.getState() == 0 || userResult.getState() == 2) {
				throw new LeoFault(UcConst.UC_ERROR100002);
			}
			return userResult;
		} catch (NoResultException e) {
			e.printStackTrace();
			throw new LeoFault(UcConst.UC_EMPTY_USERNAME_PASSWORD);
		}
	}

	@Override
	public int checkPassword(String password) {
		if (!DataUtil.checkNumAndLetter(6, 20, password)) {
			// 密码在6-20位, 数字+字母的组合
			throw new LeoFault(UcConst.UC_EMPTY_REGIST_1000040);
		} else {
			return 1;
		}
	}

	@Override
	public UserInfo userLogin(String loginName, String password) {
		if (StringUtils.isBlank(loginName) || StringUtils.isBlank(password)) {
			throw new LeoFault(UcConst.UC_EMPTY_USERNAME_PASSWORD);
		}
		if (!DataUtil.checkMobile(loginName)) {
			throw new LeoFault(UcConst.UC_EMPTY_USERNAME_PASSWORD);
		}
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		Root<User> user = cq.from(User.class);

		cq.where(cb.and(cb.equal(user.get(User_.mobile), loginName), cb.equal(user.get(User_.password), MD5Digest.getMD5Digest(password)))).select(user);

		try {
			User userResult = getEntityManager().createQuery(cq).getSingleResult();
			String jpql = "from Area area where area.areaId=?";
			Query query = getEntityManager().createQuery(jpql);
			query.setParameter(1, userResult.getAreaId());
			List<Area> areaList = query.getResultList();
			Area area = null;
			if(areaList!=null || areaList.size()>0){
				area = areaList.get(0);
			}
			// if(userResult.getState()==1 || userResult.getState()==2){
			// throw new LeoFault(UcConst.UC_ERROR100002);
			// }
			if (userResult.getDisable() == false) {
				throw new LeoFault(UcConst.UC_ERROR100018);
			}
			CriteriaQuery<HouseResourceViewCount> cq_pub = cb.createQuery(HouseResourceViewCount.class);
			Root<HouseResourceViewCount> root_pub = cq_pub.from(HouseResourceViewCount.class);
			cq_pub.where(cb.and(cb.equal(root_pub.get(HouseResourceViewCount_.userId), userResult.getUid()))).select(root_pub);

			HouseResourceViewCount count = getEntityManager().createQuery(cq_pub).getSingleResult();
			UserInfo info = new UserInfo();
			// BeanUtils.copyProperties(userResult, info);
			info.setSumCount(count.getSumCount());
			info.setState(userResult.getState());
			info.setUserName(userResult.getMobile());
			info.setUid(userResult.getUid());
			info.setSumCount(count.getSumCount());
			info.setPublishCount(count.getPublishCount());
			info.setAlipayAccount(userResult.getAccount());
			info.setAreaId(area.getParentId());
			return info;
		} catch (NoResultException e) {
			throw new LeoFault(UcConst.UC_EMPTY_USERNAME_PASSWORD);
		}
	}

	@Deprecated
	public User login1(String loginName, String password) {
		final String jpql = "from User as me where me.userName=? and me.password=?";
		Query loginQuery = getEntityManager().createQuery(jpql);
		loginQuery.setParameter(1, loginName);
		loginQuery.setParameter(2, password);
		try {
			return (User) loginQuery.getSingleResult();
		} catch (NoResultException e) {
			e.printStackTrace();
			throw new LeoFault(UcConst.ERROR_10100001);
		}
	}

	/**
	 * 检测手机号是否已经注册
	 * 
	 * @param mobile
	 * @return
	 */
	public int checkMobile(String mobile) {
		if (StringUtils.isBlank(mobile)) {
			// 请输入手机号
			throw new LeoFault(UcConst.UC_EMPTY_REGIST_1000048);
		}
		// 电话号码的验证 13[0-9] , 15[0-9] , 18[0-9], 11位数字
		if (!DataUtil.checkMobile(mobile)) {
			// 手机号码格式不正确!
			throw new LeoFault(UcConst.UC_EMPTY_REGIST_1000034);
		}
		final String jpql = "select count(1) from User u where u.mobile= ? ";
		Query query = getEntityManager().createQuery(jpql);
		query.setParameter(1, mobile);
		Long count = (Long) query.getSingleResult();
		if (count > 0) {
			// 手机号码已经注册
			throw new LeoFault(UcConst.UC_EMPTY_REGIST_1000031);
		} else {
			return 0;
		}
	}

	/**
	 * 注册是获取验证码
	 */
	@Override
	public String findMsgCode(String mobile) {
		if (StringUtils.isBlank(mobile)) {
			// 请输入手机号
			throw new LeoFault(UcConst.UC_EMPTY_REGIST_1000048);
		}
		// 电话号码的验证 13[0-9] , 15[0-9] , 18[0-9], 11位数字
		if (!DataUtil.checkMobile(mobile)) {
			// 手机号码格式不正确!
			throw new LeoFault(UcConst.UC_EMPTY_REGIST_1000034);
		}
		String num = DataUtil.createRandomNum(6);// 得到六位随机数
		// 发送短息
		String content = "房源宝注册验证码,校验码为:" + num + Global.registAfterV;
		
		log.info("注册-->findMsgCode : "+mobile+","+content );
		try {
			boolean inputLine = InfoUtils.sendSMS(mobile, content);
			// boolean inputLine = true;
			log.info("注册-->短信返回状态 : "+inputLine);
			String str = "";
			if(inputLine==true){
				str = "短信发送成功，发送内容:";
			}else{
				str = "短信发送失败,发送内容:";
			}
			 
			sendMail(new Date(), str+content, mobile);
			if (inputLine == false) {
				throw new LeoFault(UcConst.UC_ERROR100017);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 注册验证码发送失败
			throw new LeoFault(UcConst.UC_ERROR100017);
		}
		return num;
	}
	
	public void sendMail(Date date,String content,String mobile){
		MailUtils.setSendMail(Global.MAIL_USER_FANG, content, null, mobile+"房源宝注册验证码");
		MailUtils.setSendMail(Global.MAIL_USER_ZHENG, content, null, mobile+"房源宝注册验证码");
		MailUtils.setSendMail(Global.MAIL_USER_TAOYE, content, null, mobile+"房源宝注册验证码");
		//MailUtils.setSendMail(Global.MAIL_USER_LEO, content, null, mobile+"房源宝注册验证码");
	}
//
//	@Override
//	public int testRegist(RegistRequest req) {
//		// 注册用户信息
//		User user = new User();
//		user.setAreaId(req.getAreaId());
//		user.setBlance(0);// 账户 余额
//		user.setCardUrl("D5241631205");
//		user.setCode(req.getCode());
//		user.setCodeUrl("420168198609122541");
//		user.setDisable(true);
//		user.setMobile(req.getMobile());
//		user.setPassword(req.getPassword());
//		user.setRealName(req.getRealName());
//		// user.setSpreadId("7485");// 生成一个 自己的推广码
//		if (StringUtils.isBlank(req.getSpreadName())) {
//			user.setSpreadName(req.getSpreadName());
//		}
//		user.setState(0);// 状态为: 0 审核中
//		getEntityManager().persist(user);
//		// 新增用户的每日 查看 房源详情的数量
//		HouseResourceViewCount pc = new HouseResourceViewCount();
//		pc.setPublishCount(0);
//		pc.setSumCount(10);
//		pc.setUserId(user.getUid());
//		getEntityManager().persist(pc);
//		this.getEntityManager().flush();
//		return 0;
//	}

	/**
	 * 用户注册
	 */
	@Override
	public int regist(RegistRequest req, String sessionCheckMsg) {
		// if (StringUtils.isBlank(req.getVilidate())) {
		// // 验证码不能为空
		// throw new LeoFault(UcConst.UC_EMPTY_REGIST_1000032);
		// }
		// if(!req.getVilidate().equals(sessionCheckMsg)){
		// // 验证码不正确
		// throw new LeoFault(UcConst.UC_EMPTY_REGIST_1000037);
		// }

		if (StringUtils.isBlank(req.getRealName())) {
			// 1000041;// 请输入真实姓名
			throw new LeoFault(UcConst.UC_EMPTY_REGIST_1000041);
		}
		if (req.getRealName().length() < 2) {
			// 1000047;// 真实姓名至少包含两个字符
			throw new LeoFault(UcConst.UC_EMPTY_REGIST_1000047);
		}
		if (StringUtils.isBlank(req.getCode())) {
			// 1000043;// 请输入身份证号
			throw new LeoFault(UcConst.UC_EMPTY_REGIST_1000043);
		}
		// 身份证号码 15数字 ,18(18位数字, 17位数字+1个X)
		if (!DataUtil.checkCode(req.getCode())) {
			// 身份证号码格式不正确!
			throw new LeoFault(UcConst.UC_EMPTY_REGIST_1000035);
		}
		if (req.getAreaId() == 0) {
			// 1000042;// 请输入工作区域
			throw new LeoFault(UcConst.UC_EMPTY_REGIST_1000042);
		}
		// 验证身份证号 是否已经注册过
		CriteriaBuilder cb = this.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		Root<User> root = cq.from(User.class);
		cq.where(cb.equal(root.get(User_.code), req.getCode()));
		List<User> users = this.getEntityManager().createQuery(cq).getResultList();
		if (users != null && users.size() > 0) {
			// 身份证号已经注册
			throw new LeoFault(UcConst.UC_EMPTY_REGIST_1000046);
		}
		if (req.getCodeFile() == null) {
			// 1000044;// 请为身份证拍照
			throw new LeoFault(UcConst.UC_EMPTY_REGIST_1000044);
		}
		if (req.getCardFile() == null) {
			// 1000045;// 请为名片拍照
			throw new LeoFault(UcConst.UC_EMPTY_REGIST_1000045);
		}
		if (!DataUtil.checkNumAndLetter(6, 20, req.getPassword())) {
			// 密码在6-20位, 数字+字母的组合
			throw new LeoFault(UcConst.UC_EMPTY_REGIST_1000040);
		}

		// 验证电话 格式/ 是否已经注册
		checkMobile(req.getMobile());

		// 验证邀请码
		if (StringUtils.isNotBlank(req.getSpreadName())) {
			// 查看 邀请码 是否 正确的
			String jpql = "from User user where user.spreadId =?";
			Query query = this.getEntityManager().createQuery(jpql);
			query.setParameter(1, req.getSpreadName().trim().toUpperCase());
			List<User> users1 = query.getResultList();
			if (users1 != null && users1.size() > 0) {

			} else {
				// 1000050;// 请输入正确的邀请码!
				throw new LeoFault(UcConst.UC_EMPTY_REGIST_1000050);
			}
		}

		/*
		 * //使用阿里云的 oss 处理上传到的图片 String cardKey="fangyb-user-card-img-"+req.getMobile(); String codeKey
		 * ="fangyb-user-code-img-"+req.getMobile(); OSSClient client= OSSUtil.loginOSSClient(Global.ACCESSKEYID, Global.ACCESSKEYSECRET);
		 * OSSUtil.findBucket(client, Global.BUCKETNAME); try { OSSUtil.putObject(client, Global.BUCKETNAME, cardKey, req.getCardFile());
		 * OSSUtil.putObject(client, Global.BUCKETNAME, codeKey, req.getCodeFile()); } catch (IOException e1) { throw new
		 * LeoFault(UcConst.UC_EMPTY_REGIST_1000039); }
		 */

		/*
		 * //方案二. 图片保存在本地 Date date = new Date(); SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd"); String path =
		 * this.imgPath+sdf.format(date)+"/"; //文件目录 File dpathFile= new File(path); if(!dpathFile.exists()){ //不存在的目录就创建 多级目录
		 * dpathFile.mkdirs(); } String cardName = req.getCardFile().getName().replace(".tmp", ".jpg"); String codeName =
		 * req.getCodeFile().getName().replace(".tmp", ".jpg"); File cardFile =new File(path+"/"+cardName); File codeFile =new
		 * File(path+"/"+codeName);
		 * 
		 * try { if(cardFile.exists()){ cardFile.delete(); } cardFile.createNewFile(); if(codeFile.exists()){ codeFile.delete(); }
		 * codeFile.createNewFile(); //向文件中写入数据 FileInputStream fis = new FileInputStream(req.getCardFile()); FileOutputStream fos = new
		 * FileOutputStream(cardFile); int i=-1; while ((i=fis.read())>-1) { fos.write(i); } fis.close(); fos.flush(); fos.close();
		 * 
		 * fis = new FileInputStream(req.getCodeFile()); fos = new FileOutputStream(codeFile); i=-1; while ((i=fis.read())>-1) {
		 * fos.write(i); } fis.close(); fos.flush(); fos.close(); } catch (FileNotFoundException e1) { e1.printStackTrace(); throw new
		 * LeoFault(UcConst.UC_EMPTY_REGIST_1000039); } catch (IOException e1) { e1.printStackTrace(); throw new
		 * LeoFault(UcConst.UC_EMPTY_REGIST_1000039); }
		 */

		// 方案三 copy本地文件到制定的文件下
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String path = this.imgPath + sdf.format(date) + "/";
		// 文件目录
		File dpathFile = new File(path);
		if (!dpathFile.exists()) {
			// 不存在的目录就创建 多级目录
			dpathFile.mkdirs();
		}
		String cardPath = path + req.getCardFile().getName().replace(".tmp", ".jpg");
		String codePath = path + req.getCodeFile().getName().replace(".tmp", ".jpg");
		File cardFile = new File(cardPath);
		File codeFile = new File(codePath);
		if (cardFile.exists()) {
			cardFile.delete();
		}
		if (codeFile.exists()) {
			codeFile.delete();
		}
		
	/*	try {
			cardFile.createNewFile();
			codeFile.createNewFile();

			int i = 0;
			boolean falg = false;
			do {
				// 重命名文件失败
				falg = req.getCardFile().renameTo(cardFile);
				i++;
				if (i == 3) {
					break;
				}
			} while (!falg);

			falg = false;
			i = 0;
			do {
				// 重命名文件失败
				falg = req.getCodeFile().renameTo(codeFile);
				i++;
				if (i == 3) {
					break;
				}
			} while (!falg);

		} catch (Exception e1) {
			System.out.println("rename文件出现异常");
			e1.printStackTrace();
			throw new LeoFault(UcConst.UC_EMPTY_REGIST_1000039);
		}*/

		try {
			cardFile.createNewFile();
			codeFile.createNewFile();
			
			FileUtils.copyFile(req.getCardFile(), cardFile);
			FileUtils.copyFile(req.getCodeFile(), codeFile);
		} catch (IOException e1) {
			e1.printStackTrace();
			throw new LeoFault(UcConst.UC_EMPTY_REGIST_1000039);
		}

		// 生成一个 自己的推广码
		String spreadId = req.getMobile();
	/*	boolean flag = true;
		while (flag) {
			final String jpql = "select count(1) from  User u where u.spreadId =? ";
			Query query = this.getEntityManager().createQuery(jpql);
			spreadId = DataUtil.createRandomAlphanum(6);
			query.setParameter(1, spreadId);
			Long count = (Long) query.getSingleResult();
			if (new Long(0).equals(count)) {
				flag = false;
			}
		}*/

		// 注册用户信息
		User user = new User();
		user.setAreaId(req.getAreaId());
		user.setBlance(0);// 账户 余额
		user.setCode(req.getCode());
		user.setCardUrl(cardPath);
		user.setCodeUrl(codePath);

		// user.setCardUrl(cardKey);
		// user.setCodeUrl(codeKey);
		user.setDisable(true);
		user.setMobile(req.getMobile());
		user.setPassword(MD5Digest.getMD5Digest(req.getPassword()));
		user.setRealName(req.getRealName());
		user.setSpreadName(req.getSpreadName());
		user.setSpreadId(spreadId);// 生成一个 自己的推广码
		user.setCreateTime(new Date());

		// 发布条数
		user.setCreateLogCount(0);
		//我的奖金条数
		user.setCreateAwardCount(0);
		
		user.setState(EntityUtils.StatusEnum.ING.getValue());// 状态为: 2 审核中
		try {
			getEntityManager().persist(user);
			// 新增用户的每日 查看 房源详情的数量
			HouseResourceViewCount pc = new HouseResourceViewCount();
			pc.setPublishCount(0);
			pc.setSumCount(50);
			pc.setUserId(user.getUid());
			getEntityManager().persist(pc);
			this.getEntityManager().flush();
		} catch (Exception e) {
			e.printStackTrace();
			throw new LeoFault(UcConst.UC_EMPTY_REGIST_1000036);
		}
		return 0;
	}

	
	@Override
	public int againRegist(AgainRegistRequest req) {
		if(req.getUserId() == 0){
			//必选项不能为空
			throw new LeoFault(UcConst.UC_EMPTY_REGIST_1000033);
		}
		if(StringUtils.isBlank(req.getRealName())){
			//1000041;// 请输入真实姓名
			throw new LeoFault(UcConst.UC_EMPTY_REGIST_1000041);
		}
		if(req.getRealName().length()<2){
			//1000047;// 真实姓名至少包含两个字符
			throw new LeoFault(UcConst.UC_EMPTY_REGIST_1000047);
		}
		
		if(StringUtils.isBlank(req.getCode())){
			//1000043;// 请输入身份证号
			throw new LeoFault(UcConst.UC_EMPTY_REGIST_1000043);
		}
		// 身份证号码 15数字 ,18(18位数字, 17位数字+1个X)
		if (!DataUtil.checkCode(req.getCode())) {
			// 身份证号码格式不正确!
			throw new LeoFault(UcConst.UC_EMPTY_REGIST_1000035);
		}
		if(req.getAreaId() == 0){
			//1000042;// 请输入工作区域
			throw new LeoFault(UcConst.UC_EMPTY_REGIST_1000042);
		}
		
		//验证邀请码
		if (StringUtils.isNotBlank(req.getSpreadName())) {
			//查看 邀请码 是否 正确的
			String jpql="from User user where user.spreadId =?";
			Query query = this.getEntityManager().createQuery(jpql);
			query.setParameter(1, req.getSpreadName().trim().toUpperCase());
			List<User> users1= query.getResultList();
			if(users1 != null && users1.size()>0){
				
			}else{
				//1000050;// 请输入正确的邀请码!
				throw new LeoFault(UcConst.UC_EMPTY_REGIST_1000050);
			}
		}
		if(req.getCodeFile() == null){
			//1000044;// 请为身份证拍照
			throw new LeoFault(UcConst.UC_EMPTY_REGIST_1000044);
		}
		if(req.getCardFile() == null){
			//1000045;// 请为名片拍照
			throw new LeoFault(UcConst.UC_EMPTY_REGIST_1000045);
		}
		
		//方案三 copy本地文件到制定的文件下
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String path = this.imgPath+sdf.format(date)+"/";
		//文件目录
		File dpathFile= new File(path);
		if(!dpathFile.exists()){
			//不存在的目录就创建 多级目录
			dpathFile.mkdirs();
		}
		String cardPath = path+req.getCardFile().getName().replace(".tmp", ".jpg");
		String codePath = path+req.getCodeFile().getName().replace(".tmp", ".jpg");
		File cardFile =new File(cardPath); 
		File codeFile =new File(codePath);
		if(cardFile.exists()){
			cardFile.delete();
		}
		if(codeFile.exists()){
			codeFile.delete();
		}
		
		/*
		try {
			cardFile.createNewFile();
			codeFile.createNewFile();

			int i= 0;
			boolean falg = false;
			do {
				// 重命名文件失败
				falg = req.getCardFile().renameTo(cardFile);
				i++;
				if (i == 3) {
					break;
				}
			} while (!falg);

			falg= false;
			i=0;
			do {
				// 重命名文件失败
				falg = req.getCodeFile().renameTo(codeFile);
				i++;
				if (i == 3) {
					break;
				}
			} while (!falg);
			
		} catch (Exception e1) {
			System.out.println("rename文件出现异常");
			e1.printStackTrace();
			throw new LeoFault(UcConst.UC_EMPTY_REGIST_1000039);
		}
		*/
		try {
			cardFile.createNewFile();
			codeFile.createNewFile();
			
			FileUtils.copyFile(req.getCardFile(), cardFile);
			FileUtils.copyFile(req.getCodeFile(), codeFile);
		} catch (IOException e1) {
			e1.printStackTrace();
			throw new LeoFault(UcConst.UC_EMPTY_REGIST_1000039);
		}
		
		
		User user = this.getEntityManager().find(User.class, req.getUserId());
		if(user != null){
			user.setCityId(req.getCityId());
			user.setAreaId(req.getAreaId());
			user.setSpreadName(req.getSpreadName());
			user.setCode(req.getCode());
			user.setCodeUrl(codePath);
			user.setCardUrl(cardPath);
			user.setRealName(req.getRealName());
			user.setUid(req.getUserId());
			user.setState(EntityUtils.StatusEnum.ING.getValue());// 状态为: 2 审核中//修改成审核中..
			this.getEntityManager().merge(user);
			//注册成功之后, 添加一条 奖励 记录(审核通过的时候加)
		}
		return 0;
	}
	
	@Override
	public void insertResidence(Residence residence) {
		getEntityManager().persist(residence);
	}

	/**
	 * 验证短信验证码
	 */
	@Override
	public boolean checkVerify(String verifyCode, String sessionCode) {
		if (StringUtils.isBlank(verifyCode)) {
			throw new LeoFault(UcConst.UC_ERROR100005);
		}
		if (StringUtils.isBlank(sessionCode)) {
			throw new LeoFault(UcConst.UC_ERROR100005);
		}
		if (!verifyCode.equals(sessionCode)) {
			throw new LeoFault(UcConst.UC_ERROR100005);

		}
		return true;
	}

	/**
	 * 忘记密码，发送短信验证码
	 */
	@Override
	public long findLoginPwd(String phone) {
		if (StringUtils.isBlank(phone)) {
			throw new LeoFault(UcConst.UC_EMPTY_REGIST_1000034);
		}
		if (!DataUtil.checkMobile(phone)) {
			// 手机号码格式不正确!
			throw new LeoFault(UcConst.UC_EMPTY_REGIST_1000034);
		}
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		Root<User> user = cq.from(User.class);
		cq.where(cb.and(cb.equal(user.get(User_.mobile), phone))).select(user);
		// long random = Math.round(Math.random() * 1000000 + 999999);
		long random = DataUtil.createRandom();
		try {
			List<User> userList = getEntityManager().createQuery(cq).getResultList();
			if (userList.size() <= 0) {
				throw new LeoFault(UcConst.UC_ERROR100004);
			}

		} catch (NoResultException e) {
			e.printStackTrace();
			throw new LeoFault(UcConst.UC_ERROR100004);
		}
		try {
			boolean inputLine = InfoUtils.sendSMS(phone, Global.registBeforeV + random + Global.registAfterV);
			if (inputLine == false) {
				throw new LeoFault(UcConst.UC_ERROR100017);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new LeoFault(UcConst.UC_ERROR100017);
		}

		System.out.println(Global.registBeforeV + random + Global.registAfterV);
		return random;
	}

	/**
	 * 修改登录密码
	 */
	@Override
	public void modifyLoginPwd(int uid,String oldPwd, String newPwd) {
		if (StringUtils.isBlank(oldPwd)) {
			throw new LeoFault(UcConst.UC_ERROR100003);
		}
		if (StringUtils.isBlank(newPwd)) {
			throw new LeoFault(UcConst.UC_ERROR100006);
		}
		if (!DataUtil.checkNumAndLetter(6, 20, newPwd)) {
			throw new LeoFault(UcConst.UC_EMPTY_REGIST_1000040);// 密码在6-20位, 数字+字母的组合
		}
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		Root<User> user = cq.from(User.class);
		cq.where(cb.and(cb.equal(user.get(User_.password), MD5Digest.getMD5Digest(oldPwd))),cb.and(cb.equal(user.get(User_.uid), uid))).select(user);
		try {
			User userResult = getEntityManager().createQuery(cq).getSingleResult();
			userResult.setPassword(MD5Digest.getMD5Digest(newPwd));
			getEntityManager().merge(userResult);
		} catch (NoResultException e) {
			throw new LeoFault(UcConst.UC_ERROR100003);
		}
	}
	
	/**
	 * 修改登录密码
	 */
	@Override
	public void updateLoginPwd(String phone, String newPwd) {
		if (StringUtils.isBlank(phone)) {
			throw new LeoFault(UcConst.UC_ERROR100003);
		}
		if (StringUtils.isBlank(newPwd)) {
			throw new LeoFault(UcConst.UC_ERROR100006);
		}
		if (!DataUtil.checkNumAndLetter(6, 20, newPwd)) {
			throw new LeoFault(UcConst.UC_EMPTY_REGIST_1000040);// 密码在6-20位, 数字+字母的组合
		}
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		Root<User> user = cq.from(User.class);
		cq.where(cb.and(cb.equal(user.get(User_.mobile), phone))).select(user);
		try {
			User userResult = getEntityManager().createQuery(cq).getSingleResult();
			userResult.setPassword(MD5Digest.getMD5Digest(newPwd));
			getEntityManager().merge(userResult);
		} catch (NoResultException e) {
			throw new LeoFault(UcConst.UC_ERROR100003);
		}
	}

	/**
	 * 我的账户
	 */
	@Override
	public MyAccountResponse myAccount(int uid) {
		List<User> userList = execEqualQueryList(uid,User.class,User_.uid);
		User user = new User() ;
		if(userList != null && userList.size()>0) {
			user = userList.get(0);
		}
		// 查询推广人数
		String spread_count = "select count(1) from User as user left join User u1 on user.spreadName = u1.spreadId  where u1.uid =?  and user.state = "+EntityUtils.StatusEnum.SUCCESS.getValue();
		Query query = getEntityManager().createNativeQuery(spread_count);
		query.setParameter(1, uid);
		BigInteger big_count = (BigInteger) query.getSingleResult();
		int count = big_count.intValue();
		// 查询发布记录 审核信息
		// String sql
		// ="select count(1) from SourceLog log where log.sourceState =0  or log.sourceState = 2  and log.userId=?";
		// Query query_log = this.getEntityManager().createNativeQuery(sql);
		// query_log.setParameter(1, uid);
		// BigInteger big_cou = (BigInteger) query_log.getSingleResult();
		// int cou = big_cou.bitCount();

		MyAccountResponse account = new MyAccountResponse();
		// 查询我的奖金数
		account.setAwardCount(user.getCreateAwardCount());
		// 当经纪人已经清除发布记录后，将该经纪人的发布记录改为0
		if (user.getShowHistoryData() == 255) {
			account.setCreateCount(0);
		} else {
			account.setCreateCount(user.getCreateLogCount());
		}
		account.setBlance(user.getBlance());
		account.setSpreadId(user.getSpreadId());
		account.setPaypalAccount(user.getAccount());
		account.setSpreanCount(count);

		return account;
	}

	/**
	 * 新增小区
	 */
	@Override
	public void addEsate(String esateName, int estateId, String address, int uid) {
		// 验证非空 
		if (uid == 0) {
			throw new LeoFault(UcConst.UC_ERROR100010);
		}
		
		if (StringUtils.isBlank(esateName)) {
			throw new LeoFault(UcConst.UC_ERROR100010);
		}
		if (estateId == 0) {
			throw new LeoFault(UcConst.UC_ERROR100011);
		}
		if (StringUtils.isBlank(address)) {
			throw new LeoFault(UcConst.UC_ERROR100012);
		}
		if(DataUtil.isChinese(esateName)==false || DataUtil.isChinese(address)==false){
			throw new LeoFault(UcConst.UC_ERROR100026);
		}
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Estate> cq_estate = cb.createQuery(Estate.class);
		Root<Estate> root_estate = cq_estate.from(Estate.class);
		cq_estate.where(cb.and(cb.equal(root_estate.get(Estate_.parentId), estateId)),
				cb.and(cb.equal(root_estate.get(Estate_.name), esateName))).select(root_estate);
		List<Estate> estateList = getEntityManager().createQuery(cq_estate).getResultList();
		if (estateList != null && estateList.size() > 0) {
			// 该小区已存在！
			throw new LeoFault(UcConst.UC_ERROR100009);
		} else {
			Estate estate = new Estate();
			estate.setName(esateName);
			//增加小区首字母
			String nameAcronym = PinYin.converterToFirstSpell(esateName);
			estate.setNameAcronym(nameAcronym);
			estate.setParentId(estateId);
			estate.setUserId(uid);
			estate.setStatus(2);
			estate.setSerialCode(commonService.getSerialCode4Area(estateId));
			getEntityManager().persist(estate);
			
			Address ads = new Address();
			ads.setAddress(address);
			ads.setEstateId(estate.getAreaId());
			getEntityManager().persist(ads);
			
			EstateHistory areaHistory = new EstateHistory();
			BeanUtils.copyProperties(estate, areaHistory);
			areaHistory.setCreateTime(new Date());
			getEntityManager().persist(areaHistory);
			
		}


	}

	/**
	 * 举报
	 */
	@Override
	public ReportResponse report(int houseId) {
		ReportResponse result = new ReportResponse();
		
		HouseResource houseResource = getEntityManager().find(HouseResource.class, houseId);
		if(houseResource.getStatus()==2){
			if(houseResource.getActionType()==2){
				//已有改盘审核中
				throw new LeoFault(UcConst.UC_ERROR100014);
			}
			if(houseResource.getActionType()==3 || houseResource.getActionType()==4 || houseResource.getActionType()==5){
				//已有举报审核中
				throw new LeoFault(UcConst.UC_ERROR100015);
			}
		}else{
			//1出租，2出售，3即租又售，4即不租也不售
			if (houseResource.getHouseState() == 1) {
				result.setRentEnabled(true);
			} else if (houseResource.getHouseState() == 2) {
				result.setSellEnabled(true);
			} else if (houseResource.getHouseState() == 3) {
				result.setRentEnabled(true);
				result.setSellEnabled(true);
			} else if (houseResource.getHouseState() == 4) {
				result.setRentEnabled(false);
				result.setSellEnabled(false);
			}

			// 房屋信息
			Residence residence = getEntityManager().find(Residence.class, houseId);
			// 小区信息
			Estate estate = getEntityManager().find(Estate.class, residence.getEstateId());
			result.setEstateName(estate.getName());
			result.setBuilding(residence.getBuilding());
			result.setRoom(residence.getRoom());
		}
		return result;
	}
	@Override
	public void reportSubmit(int houseId, int reportLogTypeId, String remark, int uid) {
		
		if (reportLogTypeId == 0 || houseId == 0 || uid == 0) {
			throw new LeoFault(UcConst.UC_ERROR100016);
		}
		if (reportLogTypeId == 3 && remark == null) {
			throw new LeoFault(UcConst.UC_ERROR100016);
		}
		HouseResource houseResource = getEntityManager().find(HouseResource.class, houseId);
		
		
		Residence house = getEntityManager().find(Residence.class, houseId);
		if(houseResource==null){
			throw new LeoFault(UcConst.UC_ERROR100016);
		}
		
		if(houseResource.getStatus()==2){
			if(houseResource.getActionType()==2){
				//已有改盘审核中
				throw new LeoFault(UcConst.UC_ERROR100014);
			}
			if(houseResource.getActionType()==3 || houseResource.getActionType()==4 || houseResource.getActionType()==5){
				//已有举报审核中
				throw new LeoFault(UcConst.UC_ERROR100015);
			}
		}
		HouseResourceTemp t = getEntityManager().find(HouseResourceTemp.class, houseResource.getHouseId());
		HouseResourceTemp temp = null;
		if(t == null){
			temp = new HouseResourceTemp();
		}else{
			temp = t;
		}
		
		
		BeanUtils.copyProperties(houseResource, temp);
		temp.setSpaceArea(house.getSpaceArea());
		temp.setBedroomSum(house.getBedroomSum());
		temp.setLivingRoomSum(house.getLivingRoomSum());
		temp.setWcSum(house.getWcSum());
		temp.setBalconySum(house.getBalconySum());
		
		if(t== null){
			getEntityManager().persist(temp);
		}else{
			getEntityManager().merge(temp);
		}
		switch (reportLogTypeId) {
		case 1:
			houseResource.setStateReason(7);
			houseResource.setRemark("该房源未出售/出租");
			break;
		case 2:
			houseResource.setStateReason(8);
			houseResource.setRemark("该房源的地址不存在");
			break;
		case 3:
			houseResource.setStateReason(9);
			if(DataUtil.isChinese(remark)==false){
				throw new LeoFault(UcConst.UC_ERROR100026);
			}
			houseResource.setRemark(remark);
			break;
		}
		// 状态，1审核通过,2审核中，3审核失败, 4无效/删除
		houseResource.setStatus(StatusEnum.ING.getValue());
		// 举报
		houseResource.setActionType(ActionTypeEnum.REPORT.getValue());
		houseResource.setResultDate(null);
		houseResource.setOperatorId(0);
		houseResource.setPublishDate(new Date());
		houseResource.setUserId(uid);
		houseResource.setCheckNum(0);
		getEntityManager().merge(houseResource);
		
		ResidenceResourceHistory history =  new ResidenceResourceHistory();
		//HouseResourceHistory history = new HouseResourceHistory();
		BeanUtils.copyProperties(houseResource, history);
		history.setCheckNum(0);
		getEntityManager().persist(history);
		
//		houseResourceService.publicAudit(houseId);
	}
	/*
	 * 根据付款类型id获得付款类型描述
	 */
	private String getPayTypeBySourceId(int sourceId) {
		EntityUtils.AwardTypeEnum awardTypeEnum = EntityUtils.AwardTypeEnum.getByValue(sourceId);
		if(awardTypeEnum == null) {
			return "";
		}
		return awardTypeEnum.getSource();
	}
	/**
	 * 我的奖金
	 */
	@Override
	@SuppressWarnings("unchecked") 
	public List<AwardResponse> awardForMe(int uid,Long markTime) {
		//根据uid 获得user 对象把createAwardCount 设置为0
		User user = this.getEntityManager().find(User.class, uid);
		if(user != null) {
			user.setCreateAwardCount(0);
			this.getEntityManager().merge(user);
			//this.getEntityManager().merge(user);
		}
		if(markTime == null){
			markTime = System.currentTimeMillis();
		}
		String jqpl = "FROM Pay pay WHERE pay.userId=? and pay.payState=1 AND pay.payTime<? ORDER BY pay.payTime DESC";
		Query query = getEntityManager().createQuery(jqpl);
		query.setParameter(1, uid);
		//Calendar.getInstance().getTime();
		query.setParameter(2,new Date(markTime));
		List<Pay> pays = query.setFirstResult(0).setMaxResults(11).getResultList();
		List<AwardResponse> awardList = new ArrayList<AwardResponse>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		DecimalFormat   df = new DecimalFormat("#.00");
		for (Pay pay : pays) {
			AwardResponse award = new AwardResponse();
			award.setAwardNum(df.format(pay.getPaySum()));
			award.setAwardDate(sdf.format(pay.getPayTime()));
			award.setPayType(getPayTypeBySourceId(pay.getSource()));
			award.setMarkTime(markTime);
			awardList.add(award);
			//获取成功以后吧我的奖金的State 设置为0
		}
		return awardList;
	}
	
	/**
	 * 我的奖金 下拉列表
	 */
	@Override
	@SuppressWarnings("unchecked") 
	public List<AwardResponse> findAwardPage(AwardPageRequest awardPage) {
		CriteriaBuilder cb= this.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<User> cq_user = cb.createQuery(User.class);
		Root<User> root_user = cq_user.from(User.class);
		cq_user.where(cb.and(cb.equal(root_user.get(User_.uid), awardPage.getUserId())));
		List<User> users = getEntityManager().createQuery(cq_user).getResultList();
		if (users != null && users.size() > 0) {
			User u = users.get(0);
			u.setCreateAwardCount(0);
			getEntityManager().merge(u);
			//getEntityManager().merge(u);
		} else {
			//1300033;//请传入正确的用户ID
			throw new LeoFault(SourceLogConst.SOURCELOG_NOTDATA_1300033);
		}
		Long currTime = awardPage.getMarkTime();
		if(currTime == null) {
			currTime = System.currentTimeMillis();
		}
		String jqpl = "FROM Pay pay WHERE pay.userId=? and pay.payState=1 AND pay.payTime<? ORDER BY pay.payTime DESC";
		Query query = getEntityManager().createQuery(jqpl);
		query.setParameter(1, awardPage.getUserId());
		query.setParameter(2,new Date(currTime));
		List<Pay> pays = query.setFirstResult(awardPage.getStart()).setMaxResults(awardPage.getEnd()).getResultList();
		List<AwardResponse> awardList = new ArrayList<AwardResponse>();
		DecimalFormat   df = new DecimalFormat("#.00");
		for (Pay pay : pays) {
			AwardResponse award = new AwardResponse();
			award.setAwardNum(df.format(pay.getPaySum()));
			award.setAwardDate(com.leo.common.util.DateUtil.formatDate("yyyy-MM-dd", pay.getPayTime()));
			award.setPayType(getPayTypeBySourceId(pay.getSource()));
			award.setMarkTime(awardPage.getMarkTime());
			awardList.add(award);
			//获取成功以后吧我的奖金的State 设置为0
		}
		return awardList;
	}
	
	/**
	 * 绑定支付宝
	 */
	@Override
	@SuppressWarnings("unchecked")
	public int bindingPaypal(BindPaypalRequest req) {
		if (req.getPaypalAccount() == null || "".equals(req.getPaypalAccount())) {
			// 必选项不能为空!
			throw new LeoFault(UcConst.UC_EMPTY_REGIST_1000033);
		}
		if(!DataUtil.checkMobile(req.getPaypalAccount()) && !DataUtil.checkEmail(req.getPaypalAccount()) ) {
			//支付宝账号格式不对
			throw new LeoFault(UcConst.UC_ERROR100021);
		}
		CriteriaBuilder cb = this.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<User> cq_user = cb.createQuery(User.class);
		Root<User> root_user = cq_user.from(User.class);
		cq_user.where(cb.and(cb.equal(root_user.get(User_.uid), req.getUid())));
		List<User> users = getEntityManager().createQuery(cq_user).getResultList();
		if (users != null && users.size() > 0) {
			// 支付宝账号是否已经被绑定
			String jpql = "from User user  where  user.account = ? ";
			Query query = getEntityManager().createQuery(jpql);
			query.setParameter(1, req.getPaypalAccount());
			List<User> userList = query.getResultList();
			if (userList != null && userList.size() > 0) {
				throw new LeoFault(UcConst.UC_ERROR100020);
			} else {
				User u = users.get(0);
				u.setAccount(req.getPaypalAccount());
				this.getEntityManager().merge(u);
			}
		} else {
			throw new LeoFault(SourceLogConst.SOURCELOG_NOTDATA_1300033);
		}
		return 0;
	}
	
	/**
	 * 修改支付宝绑定
	 */
	@Override
	@SuppressWarnings("unchecked")
	public int changePaypal(ChangePaypalRequest req) {
		if (req.getPaypalAccount() == null || "".equals(req.getPaypalAccount()) || req.getPassword() == null
				|| "".equals(req.getPassword())) {
			// 必选项不能为空!
			throw new LeoFault(UcConst.UC_EMPTY_REGIST_1000033);
		}
		if(!DataUtil.checkMobile(req.getPaypalAccount()) && !DataUtil.checkEmail(req.getPaypalAccount()) ) {
			//支付宝账号格式不对
			throw new LeoFault(UcConst.UC_ERROR100021);
		}
		CriteriaBuilder cb = this.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<User> cq_user = cb.createQuery(User.class);
		Root<User> root_user = cq_user.from(User.class);
		cq_user.where(cb.and(cb.equal(root_user.get(User_.uid), req.getUid())));
		List<User> users = getEntityManager().createQuery(cq_user).getResultList();
		if (users != null && users.size() > 0) {
			User u = users.get(0);
			// 查询用户的密码是否正确
			if (!u.getPassword().equals(MD5Digest.getMD5Digest(req.getPassword()))) {
				throw new LeoFault(UcConst.UC_ERROR100019);
			}
			// 支付宝账号是否已经被绑定
			String jpql = "from User user  where  user.account = ? ";
			Query query = getEntityManager().createQuery(jpql);
			query.setParameter(1, req.getPaypalAccount());
			List<User> userList = query.getResultList();
			if (userList != null && userList.size() > 0) {
				throw new LeoFault(UcConst.UC_ERROR100020);
			} else {
				u.setAccount(req.getPaypalAccount());
				u.setPassword(MD5Digest.getMD5Digest(req.getPassword()));
				this.getEntityManager().merge(u);
			}
		} else {
			throw new LeoFault(SourceLogConst.SOURCELOG_NOTDATA_1300033);
		}
		return 0;
	}

	@Override
	@SuppressWarnings("unchecked")
	public int initAcronymData() {
		System.out.println("/***********************************/");
		System.out.println("开始初始化数据");
		System.out.println("/***********************************/");
		String jqpl = "FROM Estate sub";
		Query query = this.getEntityManager().createQuery(jqpl);
		List<Estate> subEstates = query.getResultList();
		if (subEstates != null && subEstates.size() > 0) {
			for (Estate sub : subEstates) {
				if(sub.getName() != null && !"".equals(sub.getName())) {
					String nameAcronym = PinYin.converterToFirstSpell(sub.getName());
					sub.setNameAcronym(nameAcronym);
				}
				getEntityManager().merge(sub);
			}
		}
		System.out.println("/***********************************/");
		System.out.println("初始化数据完成");
		System.out.println("/***********************************/");
		return 0;
	}

	
	
	/**
	 * 注册使用验证失败之后 , 返回app具体的数据
	 */
	@Override
	public FailedDetailRes getFailedDetail(String mobile) {
		if (StringUtils.isBlank(mobile)) {
			// 请输入手机号
			throw new LeoFault(UcConst.UC_EMPTY_REGIST_1000048);
		}
		// 电话号码的验证 13[0-9] , 15[0-9] , 18[0-9], 11位数字
		if (!DataUtil.checkMobile(mobile)) {
			// 手机号码格式不正确!
			throw new LeoFault(UcConst.UC_EMPTY_REGIST_1000034);
		}
		
		FailedDetailRes res = new FailedDetailRes();
		//检测是否已经注册
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		Root<User> root = cq.from(User.class);
		cq.where(cb.and(cb.equal(root.get(User_.mobile), mobile)));
		List<User> userList = getEntityManager().createQuery(cq).getResultList();
		if(userList != null && userList.size()>0){
			User user = userList.get(0);
			if(user.getState() == EntityUtils.StatusEnum.FAILD.getValue()){
				// 处于 审核失败 状态的 用户
				String sql ="select t.name townName, p.areaId,p.name from Area p join Area t on p.areaId = t.parentId where t.areaId = ?";
				Query query =this.getEntityManager().createNativeQuery(sql);
				query.setParameter(1, user.getAreaId());
				List<Object[]> rows= query.getResultList();
				if(rows != null && rows.size()>0){
					Object[] row = rows.get(0);
					res.setTownName(row[0]+"");
					res.setAreaId(Integer.valueOf(row[1]+""));//城市
					res.setAreaName(row[2]+"");
				}
				res.setUserId(user.getUid());
				res.setTownId(user.getAreaId());//行政区域
				res.setRealName(user.getRealName());
				res.setRemark(user.getRemark());
				res.setSpreadName(user.getSpreadName());
				res.setCode(user.getCode());
				File codeFile = new File(user.getCodeUrl());
				res.setCodeFile(codeFile);
				File cardFile =new File(user.getCardUrl());
				res.setCardFile(cardFile);
			}else{
				// 100020;//帐号处于非审核失败状态
				throw new LeoFault(UcConst.UC_ERROR100025);
			}
		}else{
			// 手机号码未注册
			throw new LeoFault(UcConst.UC_ERROR100004);
		}
		
		return res;
	}
	
}
