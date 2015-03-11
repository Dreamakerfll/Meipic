package com.dianfeng.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dianfeng.entity.AreaInfo;
import com.dianfeng.entity.CustomerInfo;
import com.dianfeng.entity.CustomerPhoneLink;
import com.dianfeng.entity.CustomerWorkOrderLink;
import com.dianfeng.entity.FollowHistoryInfo;
import com.dianfeng.entity.PhoneInfo;
import com.dianfeng.entity.QuestionInfo;
import com.dianfeng.entity.TelNumber;
import com.dianfeng.entity.UserInfo;
import com.dianfeng.entity.WorkOrderDetailInfo;
import com.dianfeng.entity.WorkOrderInfo;
import com.dianfeng.entity.WorkOrderQuestionLink;
import com.dianfeng.entity.WorkOrderRecordLink;
import com.dianfeng.service.AreaInfoService;
import com.dianfeng.service.CustomerInfoService;
import com.dianfeng.service.CustomerPhoneLinkService;
import com.dianfeng.service.CustomerWorkOrderLinkService;
import com.dianfeng.service.FollowHistoryInfoService;
import com.dianfeng.service.PhoneInfoService;
import com.dianfeng.service.QuestionInfoService;
import com.dianfeng.service.RecordService;
import com.dianfeng.service.UserInfoService;
import com.dianfeng.service.WorkOrderInfoService;
import com.dianfeng.service.WorkOrderQuestionLinkService;
import com.dianfeng.utils.Constant;
import com.dianfeng.utils.JsonReturn;
import com.dianfeng.utils.JsonUtil;
import com.dianfeng.utils.PageData;
import com.google.gson.reflect.TypeToken;

@Component
@RequestMapping("/work_order")
public class WorkOrderAction
{
	@Resource
	private CustomerInfoService customerInfoService;

	public CustomerInfoService getCustomerInfoService()
	{
		return customerInfoService;
	}

	public void setCustomerInfoService(CustomerInfoService customerInfoService)
	{
		this.customerInfoService = customerInfoService;
	}
	
	@Resource
	private CustomerPhoneLinkService customerPhoneLinkService;

	public CustomerPhoneLinkService getCustomerPhoneLinkService()
	{
		return customerPhoneLinkService;
	}

	public void setCustomerPhoneLinkService(CustomerPhoneLinkService customerPhoneLinkService)
	{
		this.customerPhoneLinkService = customerPhoneLinkService;
	}
	
	@Resource
	private PhoneInfoService phoneInfoService;

	public PhoneInfoService getPhoneInfoService()
	{
		return phoneInfoService;
	}

	public void setPhoneInfoService(PhoneInfoService phoneInfoService)
	{
		this.phoneInfoService = phoneInfoService;
	}
	
	@Resource
	private WorkOrderInfoService workOrderInfoService;
	
	public WorkOrderInfoService getWorkOrderInfoService()
	{
		return workOrderInfoService;
	}

	public void setWorkOrderInfoService(WorkOrderInfoService workOrderInfoService)
	{
		this.workOrderInfoService = workOrderInfoService;
	}
	
	@Resource
	private QuestionInfoService questionInfoService;

	public QuestionInfoService getQuestionInfoService()
	{
		return questionInfoService;
	}

	public void setQuestionInfoService(QuestionInfoService questionInfoService)
	{
		this.questionInfoService = questionInfoService;
	}

	@Resource
	private WorkOrderQuestionLinkService workOrderQuestionLinkService;
	
	public WorkOrderQuestionLinkService getWorkOrderQuestionLinkService()
	{
		return workOrderQuestionLinkService;
	}

	public void setWorkOrderQuestionLinkService(WorkOrderQuestionLinkService workOrderQuestionLinkService)
	{
		this.workOrderQuestionLinkService = workOrderQuestionLinkService;
	}
	
	@Resource
	private CustomerWorkOrderLinkService customerWorkOrderLinkService;

	public CustomerWorkOrderLinkService getCustomerWorkOrderLinkService()
	{
		return customerWorkOrderLinkService;
	}

	public void setCustomerWorkOrderLinkService(CustomerWorkOrderLinkService customerWorkOrderLinkService)
	{
		this.customerWorkOrderLinkService = customerWorkOrderLinkService;
	}
	
	@Resource
	private RecordService recordService;
	
	public RecordService getRecordService()
	{
		return recordService;
	}

	public void setRecordService(RecordService recordService)
	{
		this.recordService = recordService;
	}
	
	@Resource
	private AreaInfoService areaInfoService;

	public AreaInfoService getAreaInfoService()
	{
		return areaInfoService;
	}

	public void setAreaInfoService(AreaInfoService areaInfoService)
	{
		this.areaInfoService = areaInfoService;
	}
	
	@Resource
	private FollowHistoryInfoService followHistoryInfoService;

	public FollowHistoryInfoService getFollowHistoryInfoService()
	{
		return followHistoryInfoService;
	}

	public void setFollowHistoryInfoService(FollowHistoryInfoService followHistoryInfoService)
	{
		this.followHistoryInfoService = followHistoryInfoService;
	}
	
	@Resource
	private UserInfoService userInfoService;

	public UserInfoService getUserInfoService()
	{
		return userInfoService;
	}

	public void setUserInfoService(UserInfoService userInfoService)
	{
		this.userInfoService = userInfoService;
	}

	/**
	 * 页面打开时预加载数据
	 * @param tel 来电号码
	 * @param request
	 * @param response
	 * @return 工单页面
	 * @throws IOException
	 */
	@RequestMapping(params="method=pageToTra")
	public String pageToTransfer(String tel,String agent,String account,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		//获取客服的部门用来显示技能组
		UserInfo userInfo = new UserInfo();
		userInfo.setAccount(account);
		List<UserInfo> userInfoList = userInfoService.getUserByCondition(userInfo);
		if(userInfoList.size()>0&&userInfoList != null&&userInfoList.get(0).getDepartmentDisplay()!=null)
		{
			request.setAttribute("department", userInfoList.get(0).getDepartmentDisplay());
		}

		//通过来电号码查询客户详细信息
		List<CustomerInfo> customerInfoList = customerInfoService.getCustomerInfoByTel(tel);
		if(customerInfoList.size()>0)
		{
			request.setAttribute("customerInfo", customerInfoList.get(0));
			
			//联系电话
			String[] telArr = customerInfoList.get(0).getTel().split(",");
			List<TelNumber> telNumberList = new ArrayList<TelNumber>();
			for (int i = 0; i < telArr.length; i++)
			{
				TelNumber telNumber = new TelNumber();
				telNumber.setTelNumber(telArr[i]);
				telNumberList.add(telNumber);
			}
			
			String telNumberJson = JsonUtil.toJson(telNumberList);
			request.setAttribute("telNumberJson", telNumberJson);
		}
		
		//查询来电号码的归属地
		List<AreaInfo> areaInfoList = null;
		
		areaInfoList = areaInfoService.getAreaByCode(tel.substring(0,3));
		
		if(areaInfoList.size()>1)
		{
			areaInfoList.clear();
			areaInfoList = areaInfoService.getAreaByCode(tel.substring(0,4));
			if(areaInfoList.size()==1)
			{
				request.setAttribute("area", areaInfoList.get(0).getCity());
				request.setAttribute("postCode", areaInfoList.get(0).getPostCode());
			}
			else
			{
				request.setAttribute("area", "");
				request.setAttribute("postCode", "");
			}
		}
		else if(areaInfoList.size()==1)
		{
			request.setAttribute("area", areaInfoList.get(0).getCity());
			request.setAttribute("postCode", areaInfoList.get(0).getPostCode());
		}
		else
		{
			areaInfoList.clear();
			areaInfoList = areaInfoService.getAreaByPhone(tel.substring(0,7));
			if(areaInfoList.size()==1)
			{
				request.setAttribute("area", areaInfoList.get(0).getCity());
				request.setAttribute("postCode", areaInfoList.get(0).getPostCode());
			}
			else
			{
				request.setAttribute("area", "");
				request.setAttribute("postCode", "");
			}
		}
		
		request.setAttribute("default_tel", tel);
		return "work_order";
	}
	
	/**
	 * 页面打开时预加载数据
	 * @param request
	 * @param response
	 * @return 工单维护页面
	 * @throws IOException
	 */
	@RequestMapping(params="method=pageToTraManager")
	public String pageToTraManager(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		return "work_order_manager";
	}
	
	/**
	 * 新增/更新客户和手机信息
	 * @param tel 联系电话1
	 * @param customer_info_json 用户信息
	 * @param phone_info_insert_json 新增的手机信息
	 * @param phone_info_update_json 更改的手机信息
	 * @param phone_info_delete_json 删除的手机信息
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(params="method=updateCustomerPhoneInfo")
	public void updateCustomerPhoneInfo(String tel,String customer_info_json,String phone_info_insert_json,String phone_info_update_json,String phone_info_delete_json,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		//页面传过来的客户信息转成客户实体
		CustomerInfo customerInfo = JsonUtil.fromJson(customer_info_json, CustomerInfo.class);
		
		//拼接新的联系方式
		List<TelNumber> telNumberList = JsonUtil.fromJson(customerInfo.getTel(), new TypeToken<List<TelNumber>>(){}.getType());
		
		String newTel = "";
		for (int i = 0; i < telNumberList.size(); i++)
		{
			newTel = newTel+telNumberList.get(i).getTelNumber()+",";
		}
		if(!"".equals(tel))
		{
			newTel = newTel+tel+",";
		}
		
		customerInfo.setTel(newTel);
		
		//新增的手机信息
		List<PhoneInfo> phoneInfoInsertList = JsonUtil.fromJson(phone_info_insert_json,new TypeToken<List<PhoneInfo>>(){}.getType());
		
		//更新的手机信息
		List<PhoneInfo> phoneInfoUpdateList = JsonUtil.fromJson(phone_info_update_json,new TypeToken<List<PhoneInfo>>(){}.getType());
		
		//删除的手机信息
		List<PhoneInfo> phoneInfoDeleteList = JsonUtil.fromJson(phone_info_delete_json,new TypeToken<List<PhoneInfo>>(){}.getType());
		
		int i = 0;
		
		//如果客户ID不存在，做新增操作，否则做更新操作
		if("".equals(customerInfo.getId()))
		{
			//新增客户信息
			i = customerInfoService.insertCustomerInfo(customerInfo);
		}
		else
		{
			//更新客户信息
			i = customerInfoService.updateCustomerInfo(customerInfo);
		}
		
		//如果手机信息有新增，则新增手机信息和新增客户手机关联表信息
		if(phoneInfoInsertList.size()>0)
		{
			//获取手机信息表中最大的phoneId
			long maxPhoneId = phoneInfoService.getMaxPhoneId();
			
			//客户和手机信息关联List
			List<CustomerPhoneLink> customerPhoneLinkList = new ArrayList<CustomerPhoneLink>();
			
			for (int j = 0; j < phoneInfoInsertList.size(); j++)
			{
				//设置要插入手机的ID
				phoneInfoInsertList.get(j).setId((maxPhoneId+j+1)+"");
				
				//创建客户和手机信息的关联
				CustomerPhoneLink customerPhoneLink = new CustomerPhoneLink();
				customerPhoneLink.setCustomerId(customerInfo.getId());
				customerPhoneLink.setPhoneId(phoneInfoInsertList.get(j).getId());
				customerPhoneLinkList.add(customerPhoneLink);
			}
			
			//新增手机信息
			int in = phoneInfoService.insertPhoneInfo(phoneInfoInsertList);
			
			//新增手机客户关联信息
			int inii = customerPhoneLinkService.insertCustomerPhoneLink(customerPhoneLinkList);
		}
		
		//如果手机信息有变更，则更新手机信息
		if(phoneInfoUpdateList.size()>0)
		{
			//更新手机信息
			int in = phoneInfoService.updatePhoneInfo(phoneInfoUpdateList);
		}
		
		//如果手机信息删除，则删除手机信息
		if(phoneInfoDeleteList.size()>0)
		{
			//删除手机信息
			int inn = phoneInfoService.deletePhoneInfo(phoneInfoDeleteList);
			
			//客户和手机信息关联List
			List<CustomerPhoneLink> customerPhoneLinkList = new ArrayList<CustomerPhoneLink>();
			
			for (int j = 0; j < phoneInfoDeleteList.size(); j++)
			{
				//创建客户和手机信息的关联
				CustomerPhoneLink customerPhoneLink = new CustomerPhoneLink();
				customerPhoneLink.setCustomerId(customerInfo.getId());
				customerPhoneLink.setPhoneId(phoneInfoDeleteList.get(j).getId());
				customerPhoneLinkList.add(customerPhoneLink);
			}
			
			//删除手机客户关联信息
			int innn = customerPhoneLinkService.deleteCustomerPhoneLink(customerPhoneLinkList);
		}
		
		JsonReturn jsonReturn=new JsonReturn();
		if(i==1){
			jsonReturn.setStatus(Constant.STATU_SUCCESS);
			jsonReturn.setSign(customerInfo.getId());
		}else{
			
			jsonReturn.setStatus(Constant.STATU_ERROR);
		}
		String returnJson =JsonUtil.toJson(jsonReturn);
		response.setCharacterEncoding("utf-8");
	    PrintWriter out = response.getWriter();
		out.print(returnJson);
		out.flush();
		out.close();
	}
	
	/**
	 * 新增/更新工单和问题信息
	 * @param workOrderInfo 工单信息
	 * @param question_info_insert_json 新增的问题信息
	 * @param question_info_update_json 更改的问题信息
	 * @param question_info_delete_json 删除的问题信息
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(params="method=updateWorkOrderQuestionInfo")
	public void updateWorkOrder(String customerId,String workOrderInfo,String question_info_insert_json,String question_info_update_json,String question_info_delete_json,String follow_history_info_json,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		//页面传过来的工单信息转成工单实体
		WorkOrderInfo workOrder = JsonUtil.fromJson(workOrderInfo, WorkOrderInfo.class);
		
		//新增的问题信息
		List<QuestionInfo> questionInfoInsertList = JsonUtil.fromJson(question_info_insert_json,new TypeToken<List<QuestionInfo>>(){}.getType());
		
		//更新的问题信息
		List<QuestionInfo> questionInfoUpdateList = JsonUtil.fromJson(question_info_update_json,new TypeToken<List<QuestionInfo>>(){}.getType());
		
		//删除的问题信息
		List<QuestionInfo> questionInfoDeleteList = JsonUtil.fromJson(question_info_delete_json,new TypeToken<List<QuestionInfo>>(){}.getType());
		
		int i = 0;
		
		//如果工单ID不存在，做新增操作，否则做更新操作
		if("".equals(workOrder.getId()))
		{
			//新增工单信息
			i = workOrderInfoService.insertWorkOrderInfo(workOrder);
			
			//新增客户工单关联信息
			CustomerWorkOrderLink customerWorkOrderLink = new CustomerWorkOrderLink();
			customerWorkOrderLink.setCustomerId(customerId);
			customerWorkOrderLink.setWorkOrderId(workOrder.getId());
			
			customerWorkOrderLinkService.insertCustomerWorkOrderLink(customerWorkOrderLink);
		}
		else
		{
			//跟进工单信息
			i = workOrderInfoService.updateWorkOrderInfo(workOrder);
		}
		
		//如果问题信息有新增，则新增问题信息和新增工单问题关联表信息
		if(questionInfoInsertList.size()>0)
		{
			//获取问题信息表中最大的questionId
			long maxQuestionId = questionInfoService.getMaxQuestionId();
			
			//工单和问题信息关联List
			List<WorkOrderQuestionLink> workOrderQuestionLinkList = new ArrayList<WorkOrderQuestionLink>();
			
			for (int j = 0; j < questionInfoInsertList.size(); j++)
			{
				//设置要插入问题的ID
				questionInfoInsertList.get(j).setId((maxQuestionId+j+1)+"");
				
				//创建工单和问题信息的关联
				WorkOrderQuestionLink workOrderQuestionLink = new WorkOrderQuestionLink();
				workOrderQuestionLink.setWorkOrderId(workOrder.getId());
				workOrderQuestionLink.setQuestionId(questionInfoInsertList.get(j).getId());
				workOrderQuestionLinkList.add(workOrderQuestionLink);
			}
			
			//新增问题信息
			int in = questionInfoService.insertQuestionInfo(questionInfoInsertList);
			
			//新增工单问题关联信息
			int inii = workOrderQuestionLinkService.insertWorkOrderQuestionLink(workOrderQuestionLinkList);
		}
		
		//如果问题信息有变更，则更新问题信息
		if(questionInfoUpdateList.size()>0)
		{
			//更新问题信息
			int ine = questionInfoService.updateQuestionInfo(questionInfoUpdateList);
		}
		
		//如果问题信息删除，则删除问题信息
		if(questionInfoDeleteList.size()>0)
		{
			//删除问题信息
			int inn = questionInfoService.deleteQuestionInfo(questionInfoDeleteList);
			
			//工单和问题信息关联List
			List<WorkOrderQuestionLink> workOrderQuestionLinkList = new ArrayList<WorkOrderQuestionLink>();
			
			for (int j = 0; j < questionInfoDeleteList.size(); j++)
			{
				//创建工单和问题信息的关联
				WorkOrderQuestionLink workOrderQuestionLink = new WorkOrderQuestionLink();
				workOrderQuestionLink.setWorkOrderId(workOrder.getId());
				workOrderQuestionLink.setQuestionId(questionInfoDeleteList.get(j).getId());
				workOrderQuestionLinkList.add(workOrderQuestionLink);
			}
			
			//删除工单问题关联信息
			int innn = workOrderQuestionLinkService.deleteWorkOrderQuestionLink(workOrderQuestionLinkList);
		}
		
		//关联录音
		if(workOrder.getUniqueId() != null && !"".equals(workOrder.getUniqueId()))
		{
			//查找关联表是否已经有数据
			List<WorkOrderRecordLink> workOrderRecordLinkList = recordService.getRecordByUniqueId(workOrder.getUniqueId(),workOrder.getAssemblyLine());
			
			if(workOrderRecordLinkList.size()==0)
			{
				int zz = recordService.insertWordOrderRecordLink(workOrder.getAssemblyLine(), workOrder.getUniqueId());
			}
		}
		
		//保存跟进历史
		//页面传过来的工单历史信息转成实体
		FollowHistoryInfo followHistoryInfo = JsonUtil.fromJson(follow_history_info_json, FollowHistoryInfo.class);
		followHistoryInfo.setWorkOrderId(workOrder.getId());
		
		int kk = followHistoryInfoService.insertFollowHistoryInfo(followHistoryInfo);
		
		JsonReturn jsonReturn=new JsonReturn();
		if(i==1){
			jsonReturn.setStatus(Constant.STATU_SUCCESS);
		}else{
			
			jsonReturn.setStatus(Constant.STATU_ERROR);
		}
		String returnJson =JsonUtil.toJson(jsonReturn);
		response.setCharacterEncoding("utf-8");
	    PrintWriter out = response.getWriter();
		out.print(returnJson);
		out.flush();
		out.close();
	}
	
	/**
	 * 获取工单的详情包括用户信息，手机信息，问题信息
	 * @param page
	 * @param rows
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(params="method=findDetailWorkOrder")
	public void getDetailWorkOrder(int page,int rows,String work_order_detail_info,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		WorkOrderDetailInfo workOrderDetailInfo = new WorkOrderDetailInfo();
		if(work_order_detail_info != null)
		{
			workOrderDetailInfo = JsonUtil.fromJson(work_order_detail_info, WorkOrderDetailInfo.class);
		}
		
		workOrderDetailInfo.setPage((page-1)*rows);
		workOrderDetailInfo.setRows(rows);
		
		List<WorkOrderDetailInfo> workOrderDetailInfoList = workOrderInfoService.getDetailWorkOrder(workOrderDetailInfo);
		
		int count = workOrderInfoService.getDetailWorkOrderCount(workOrderDetailInfo);
		//设置分页
		List<WorkOrderDetailInfo> displyData = new ArrayList<WorkOrderDetailInfo>();
		int resultMaxCount =  count;
	    int startIndex = 0;
	    int endIndex = workOrderDetailInfoList.size();
	   
	    for (int i = startIndex; i < endIndex ; i++) {
	    	displyData.add(workOrderDetailInfoList.get(i));
	    }
	    
		PageData<WorkOrderDetailInfo> t = new PageData<WorkOrderDetailInfo>();
		t.setRows(displyData);
		t.setTotal(resultMaxCount);
		
		String returnJson =JsonUtil.toJson(t);
		if(returnJson==null){
			returnJson="[]";
		}
		
		response.setCharacterEncoding("utf-8");
	    PrintWriter out = response.getWriter();
		out.print(returnJson);
		out.flush();
		out.close();
	}
	
	/**
	 * 删除工单
	 * @param workOrderId
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(params="method=deleteWorkOrder")
	public void deleteWorkOrder(String workOrderId,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		
		//删除工单信息
		int j = workOrderInfoService.deleteWorkOrderInfo(workOrderId);
		
		//删除问题信息
		int x = questionInfoService.deleteQuestionInfoByWorkOrderId(workOrderId);
		
		//删除客户工单关联信息
		int i = customerWorkOrderLinkService.deleteCustomerWorkOrderLinkByWorkOrderId(workOrderId);
		
		//删除工单问题关联信息
		int k = workOrderQuestionLinkService.deleteWorkOrderQuestionLinkByWorkOrderId(workOrderId);
		
		JsonReturn jsonReturn=new JsonReturn();
		if(j>0){
			jsonReturn.setStatus(Constant.STATU_SUCCESS);
		}else{
			
			jsonReturn.setStatus(Constant.STATU_ERROR);
		}
		String returnJson =JsonUtil.toJson(jsonReturn);
		response.setCharacterEncoding("utf-8");
	    PrintWriter out = response.getWriter();
		out.print(returnJson);
		out.flush();
		out.close();
	}
	
}
