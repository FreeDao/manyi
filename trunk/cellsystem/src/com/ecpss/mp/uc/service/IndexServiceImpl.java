package com.ecpss.mp.uc.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ecpss.mp.BaseService;
import com.ecpss.mp.entity.TblOperator;
import com.leo.common.util.SafeUtils;

@Service(value = "indexService")
@Scope(value = "singleton")
public class IndexServiceImpl extends BaseService implements IndexService{
	
public Map<String,Object> index(TblOperator operator){
	List<Sysmenu> menus=new ArrayList<Sysmenu>();//菜单
	Map<String,Integer> menuPermissionMap=new HashMap<String, Integer>();//权限
	String sql = null;
 	boolean isSupperAdmin=operator.getDisplatoperator()==1 && operator.getDoperatortype()==1;//不是系统超管情况
	if(!isSupperAdmin){
	List permissionGroupList=null;
	sql ="SELECT DISTINCT TP.D_PERMISSIONS PERMISSIONS FROM tbl_permissiongroup TP JOIN tbl_role_permissiongroup TRP ON TP.D_ID=TRP.D_PERMISSIONGROUPID JOIN tbl_role TR ON TRP.D_RELEID=TR.D_ID JOIN tbl_operator_role TOR ON TR.D_ID=TOR.D_ROLEID JOIN tbl_operator TBLO ON TOR.D_OPERATORID=TBLO.D_ID WHERE TP.D_STATUS=1 AND TR.D_STATUS=1 AND TBLO.D_STATUS=1 AND TBLO.D_ID = "+operator.getDid();
	permissionGroupList = getEntityManager().createNativeQuery(sql).getResultList();
	if(permissionGroupList==null||permissionGroupList.size()>0){
		Map permissionMap=null;
		String[] str=null;
		String[] str1=null;
		for(int i=0,len=permissionGroupList.size();i<len;i++){
			permissionMap=(Map)permissionGroupList.get(i);
			str=SafeUtils.getString(permissionMap.get("PERMISSIONS")).split(";");
			for(int j=0,strLen=str.length;j<strLen;j++){
				str1=str[j].split(":");
				if(menuPermissionMap.containsKey(str1[0])){
					for(int operateValue=1;operateValue<=128;operateValue=operateValue*2){
						if((menuPermissionMap.get(str1[0])&operateValue)!=operateValue&&(SafeUtils.getInteger(str1[1])&operateValue)==operateValue){
							menuPermissionMap.put(str1[0], menuPermissionMap.get(str1[0])+operateValue);
						}
					}							
				}else{
					menuPermissionMap.put(str1[0], SafeUtils.getInteger(str1[1]));
				}
			}
		}
		Iterator<String> ite=menuPermissionMap.keySet().iterator();
		StringBuilder str3=new StringBuilder();
		while(ite.hasNext()){
			str3.append("'").append(ite.next()).append("'").append(",");
		}
		//根据权限menuPermissionMap中的键，查找出当前操作员的菜单
		sql="SELECT D_ID,D_MENUNAME,D_NAVIGATEURL,D_IMAGE,D_PARENTID,D_SEQUENCE,D_OPERATE_PERMISSIONS,D_PERMISSIONOBJECT FROM tbl_sysmenu WHERE D_STATUS=1 AND D_PERMISSIONOBJECT IN("+str3.substring(0, str3.length()-1)+") ORDER BY D_PARENTID,D_SEQUENCE";			
	  }
	}
	List menuList=null;
	if(isSupperAdmin){
		sql="SELECT D_ID,D_MENUNAME,D_NAVIGATEURL,D_IMAGE,D_PARENTID,D_SEQUENCE,D_OPERATE_PERMISSIONS,D_PERMISSIONOBJECT FROM tbl_sysmenu WHERE D_STATUS=1 ORDER BY D_PARENTID,D_SEQUENCE";
	}
	if(sql!=null){
		menuList = getEntityManager().createNativeQuery(sql).getResultList();
	}
	if(menuList!=null&&menuList.size()>0){
		Map map=null;
		Sysmenu menu=null;
		Map<String,Sysmenu> menumap=new HashMap<String,Sysmenu>();
		for(int i=0;i<menuList.size();i++){
			Object[] obj = (Object[]) menuList.get(i);
			menu=new Sysmenu();
			menu.setId(SafeUtils.getString(obj[0]));
			menu.setMenuid(menu.getId());
			menu.setMenuno(menu.getId());
			menu.setMenuparentno(SafeUtils.getString(obj[4]));
			menu.setIcon(SafeUtils.getString(obj[3]));
			menu.setMenuicon(menu.getIcon());
			menu.setMenuname(SafeUtils.getString(obj[1]));
			menu.setText(menu.getMenuname());
			menu.setMenuurl(SafeUtils.getString(obj[2])+"?menuno="+menu.getId());
			menu.setPermissions(SafeUtils.getInteger(obj[6]));
			menu.setPermissionobject(SafeUtils.getString(obj[7]));
			menu.setChildren(new ArrayList<Sysmenu>());
			menumap.put(menu.getId(), menu);
			if(isSupperAdmin){
				menuPermissionMap.put(SafeUtils.getString(obj[7]), SafeUtils.getInteger(obj[6]));
			}
			if("0".equals(menu.getMenuparentno().toString())){
				if(operator.getDisplatoperator()==1&&(menu.getPermissionobject().equals("User")||
				   menu.getPermissionobject().equals("Content")||menu.getPermissionobject().equals("Statistics")||menu.getPermissionobject().equals("Dept"))){
					
				}else
				{
					menus.add(menu);
				}						
			}else{
				menumap.get(menu.getMenuparentno()).getChildren().add(menu);
			}
		}
	}
	
	Map<String,Object> returnMap =new HashMap<String,Object>();
	returnMap.put("menus", menus);
	returnMap.put("permission", menuPermissionMap);
	return returnMap;
}

}
