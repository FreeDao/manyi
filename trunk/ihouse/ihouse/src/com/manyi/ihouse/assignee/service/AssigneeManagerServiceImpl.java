package com.manyi.ihouse.assignee.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Service;

import com.manyi.ihouse.assignee.model.AssigneeModel;
import com.manyi.ihouse.assignee.model.AssigneeRequest;
import com.manyi.ihouse.assignee.model.AssigneeResponse;
import com.manyi.ihouse.base.BaseService;
import com.manyi.ihouse.base.PageResponse;
import com.manyi.ihouse.entity.Agent;
import com.manyi.ihouse.entity.Agent_;
import com.manyi.ihouse.user.model.PageListRequest;

/**
 * 经济人信息管理Service实现类
 * @author hubin
 *
 */
@Service(value="assigneeManagerService")
public class AssigneeManagerServiceImpl extends BaseService implements
		AssigneeManagerService {

	/**
	 * 添加经纪人
	 */
	@Override
	public void addAssignee(AssigneeRequest assigneeRequest) {
		Agent assignee = new Agent();
		
		assignee.setName(assigneeRequest.getName());
		assignee.setMemo(assigneeRequest.getInformation());
		assignee.setMobile(assigneeRequest.getMobile());
		assignee.setSerialNumber(assigneeRequest.getSerialNumber());
		
		this.getEntityManager().persist(assignee);
		this.getEntityManager().flush();
	}

	/**
	 * 更新经纪人信息
	 */
	@Override
	public void updateAssignee(AssigneeRequest assigneeRequest) {
		Agent assignee = this.getEntityManager().find(Agent.class, assigneeRequest.getId());
		
		assignee.setMemo(assigneeRequest.getInformation());
		assignee.setMobile(assigneeRequest.getMobile());
		assignee.setName(assigneeRequest.getName());
		assignee.setSerialNumber(assigneeRequest.getSerialNumber());
		
		getEntityManager().merge(assignee);
		
	}

	@Override
	public AssigneeResponse getAssignee(int id) {
		Agent assignee = this.getEntityManager().find(Agent.class, id);
		
		AssigneeResponse assigneeResponse = new AssigneeResponse();
		
//		assigneeResponse.setId(assignee.getId());
		assigneeResponse.setInformation(assignee.getMemo());
		assigneeResponse.setMobile(assignee.getMobile());
		assigneeResponse.setName(assignee.getName());
		assigneeResponse.setSerialNumber(assignee.getSerialNumber());
		
		return assigneeResponse;
	}

	@Override
	public PageResponse<AssigneeModel> getAssigneeList(PageListRequest request) {
		
//		Integer page = request.getPage();
		Integer pageSize = request.getPageSize();
		
		PageResponse<AssigneeModel> pages = new PageResponse<AssigneeModel>();
//		pages.setCurrentPage(page);
		pages.setPageSize(pageSize);
//		AssigneeListResponse assigneeListResponse = new AssigneeListResponse();

//		String jpql = "from Assignee";
//		Query query = this.getEntityManager().createQuery(jpql);
		
		EntityManager em = getEntityManager();
		
		//从EntityManager中获取CriteriaBuilder工厂
		CriteriaBuilder cb = em.getCriteriaBuilder();
		
		
		//计算行数
		CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
		Root<Agent> ass = countQuery.from(Agent.class);
		countQuery.select(cb.count(ass));

		Expression<Boolean> exp = cb.and(cb.like(ass.get(Agent_.name), "%测试%"));
		countQuery.where(exp);
		
		int totalSize = em.createQuery(countQuery).getSingleResult().intValue();
		System.out.println("totalSize="+totalSize);
		pages.setTotal(totalSize);
//		pages.setTotalPage((int)Math.ceil((double)totalSize/(double)pageSize));
		//计算行数结束
		
		
		//构造一个CriteriaQuery对象
		CriteriaQuery<Agent> cq = cb.createQuery(Agent.class);
		
		// 为CriteriaQuery对象指定一个或多个查询源，查询源表示查询基于的实体
		ass = cq.from(Agent.class);
		cq.select(ass);
		cq.where(exp);
		
		cq.orderBy(cb.desc(ass.get(Agent_.id)));
		
		//构造一个查询表达式
		TypedQuery<Agent> query = em.createQuery(cq);
		
		query.setFirstResult(0);
		query.setMaxResults(3);
		List<Agent> qlist = query.getResultList();
		
		List<AssigneeModel> assigneeModelList = new ArrayList<AssigneeModel>(qlist.size());
		
		for(Agent assignee : qlist){
			AssigneeModel assigneeModel = new AssigneeModel();
			
//			assigneeModel.setId(assignee.getId());
			assigneeModel.setInformation(assignee.getMemo());
			assigneeModel.setMobile(assignee.getMobile());
			assigneeModel.setName(assignee.getName());
			assigneeModel.setSerialNumber(assignee.getSerialNumber());
			
			assigneeModelList.add(assigneeModel);
		}
		pages.setRows(assigneeModelList);
		
//		assigneeListResponse.setAssigneeList(assigneeModelList);
		
		return pages;
	}

}
