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

import com.dianfeng.entity.Transfer;
import com.dianfeng.service.AreaInfoService;
import com.dianfeng.service.TransferService;
import com.dianfeng.utils.Constant;
import com.dianfeng.utils.JsonHelp;
import com.dianfeng.utils.PageData;
import com.dianfeng.utils.ResponseFormat;

/**
 * 转接管理
 * @author Dreamaker
 *
 */
@Component
@RequestMapping("/transfer")
public class TransferAction
{

	@Resource
	private TransferService transferService;

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

	public TransferService getTransferService()
	{
		return transferService;
	}

	public void setTransferService(TransferService transferService)
	{
		this.transferService = transferService;
	}
	
	@RequestMapping(params="method=pageToTra")
	public String pageToTra(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
//		//设置页面的区域选择
//		List<AreaInfo> areaList = areaInfoService.getAllArea();
//		Map<Integer,String> provinceMap = new HashMap<Integer, String>();
//		Map<String,String> cityMap = null;
//		Map<Integer,Map<String,String>> provinceWithCity = new HashMap<Integer, Map<String,String>>();
//		
//		int k=-1;
//		
//		for (int i = 0; i < areaList.size(); i++)
//		{
//			if(!provinceMap.containsValue(areaList.get(i).getProvince()))
//			{
//				provinceMap.put(++k, areaList.get(i).getProvince());
//				cityMap = new HashMap<String, String>();
//			}
//			
//			cityMap.put(areaList.get(i).getCode(), areaList.get(i).getCity());
//			
//			if(i==areaList.size()-1 || !provinceMap.containsValue(areaList.get(i+1).getProvince()))
//			{
//				provinceWithCity.put(k, cityMap);
//			}
//		}
//		
//		StringBuilder sb=new StringBuilder();
//		StringBuilder sb1=new StringBuilder();
//		
//		sb.append("<div id='selectSub'><select id='AreaCombo' class='easyui-combobox' name='state' style='width:200px;' onchange='showSelect(this.value)'>");
//		sb1.append("<div id='currentSelectSub'><select id='currentAreaCombo' class='easyui-combobox' name='state' style='width:200px;' onchange='showSelect(this.value)'>");
//		for (int i = 0; i < provinceMap.size(); i++)
//		{
//			sb.append("<option value='");
//			sb.append(i);
//			sb.append("'>");
//			sb.append(provinceMap.get(i));
//			sb.append("</option>");
//			
//			sb1.append("<option value='");
//			sb1.append(i);
//			sb1.append("'>");
//			sb1.append(provinceMap.get(i));
//			sb1.append("</option>");
//		}
//		
//		sb.append("</select>");
//		sb1.append("</select>");
//		
//		for (int i = 0; i < provinceWithCity.size(); i++)
//		{
//			sb.append("<div id='c0");
//			sb.append(i);
//			sb.append("'>");
//			
//			sb1.append("<div id='c00");
//			sb1.append(i);
//			sb1.append("'>");
//			
//			//遍历键集合
//			Iterator it=provinceWithCity.get(i).keySet().iterator();
//			while(it.hasNext())
//			{
//				String key = it.next()+"";
//				sb.append("<input type='checkbox' name='");
//				sb.append(provinceWithCity.get(i).get(key));
//				sb.append("' onclick='addPreItem()' value='");
//				sb.append(key);
//				sb.append("'/>");
//				sb.append(provinceWithCity.get(i).get(key));
//				
//				sb1.append("<input type='checkbox' name='");
//				sb1.append(provinceWithCity.get(i).get(key));
//				sb1.append("' onclick='addCurrentPreItemAndSureItem()' value='");
//				sb1.append(key);
//				sb1.append("'/>");
//				sb1.append(provinceWithCity.get(i).get(key));
//			}
//			
//			sb.append("</div>");
//			sb1.append("</div>");
//			
//		}
//		
//		sb.append("</div><div id='preview'><div>您已选择的城市</div><div id='previewItem'></div></div>");
//		sb1.append("</div><div id='currentPreview'><div>您已选择的城市</div><div id='currentPreviewItem'></div></div>");
//		
//		request.setAttribute("selectArea", sb.toString());
//		request.setAttribute("currentSelectArea", sb1.toString());
		
		return "holiday_transfer";
	}


	@RequestMapping(params="method=findAll")
	public void getAllTransfer(int page,int rows,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		List<Transfer> transferList = transferService.getAllTransfer();
		
		//设置分页
		List<Transfer> displyData = new ArrayList<Transfer>();
		int resultMaxCount = transferList.size() ;
	    int startIndex = (page-1)*rows<0?0:(page-1)*rows;
	    int endIndex = page*rows<resultMaxCount?page*rows:resultMaxCount;
	    for (int i = startIndex; i < endIndex ; i++) {
	    	displyData.add(transferList.get(i));
	    }
	    
		PageData<Transfer> t = new PageData<Transfer>();
		t.setRows(displyData);
		t.setTotal(resultMaxCount);
		
		String returnJson =JsonHelp.objectToJsonStr(t);
		if(returnJson==null){
			returnJson="[]";
		}
		response.setCharacterEncoding("utf-8");
	    PrintWriter out = response.getWriter();
		out.print(returnJson);
		out.flush();
		out.close();
	}
	
	@RequestMapping(params="method=isAreaExist")
	public void isAreaExist(String area,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		List<String> areaList = new ArrayList<String>();
		
		for (int i = 0; i < area.split(",").length; i++)
		{
			areaList.add(area.split(",")[i]);
		}
		
		String existArea = transferService.isAreaExist(areaList);
		
		ResponseFormat rf=new ResponseFormat();
		if(existArea == null || "".equals(existArea)){
			rf.setStatu(Constant.STATU_SUCCESS);
		}else{
			
			rf.setStatu(existArea);
		}
		String returnJson =JsonHelp.objectToJsonStr(rf);
		response.setCharacterEncoding("utf-8");
	    PrintWriter out = response.getWriter();
		out.print(returnJson);
		out.flush();
		out.close();
	}
	
	@RequestMapping(params="method=findTransferByAccount")
	public void getTransferByAccount(int page,int rows,String account,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		List<Transfer> transferList = transferService.getTransferByAccount(account);
		
		//设置分页
		List<Transfer> displyData = new ArrayList<Transfer>();
		int resultMaxCount = transferList.size() ;
	    int startIndex = (page-1)*rows<0?0:(page-1)*rows;
	    int endIndex = page*rows<resultMaxCount?page*rows:resultMaxCount;
	    for (int i = startIndex; i < endIndex ; i++) {
	    	displyData.add(transferList.get(i));
	    }
	    
		PageData<Transfer> t = new PageData<Transfer>();
		t.setRows(displyData);
		t.setTotal(resultMaxCount);
		
		String returnJson =JsonHelp.objectToJsonStr(t);
		if(returnJson==null){
			returnJson="[]";
		}
		response.setCharacterEncoding("utf-8");
	    PrintWriter out = response.getWriter();
		out.print(returnJson);
		out.flush();
		out.close();
	}
	
	@RequestMapping(params="method=findByCondition")
	public void getByCondition(int page,int rows,String beginTime,String endTime,String phoneNumber,String area,String account,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		List<Transfer> transferList = transferService.getTransferByCondition(beginTime, endTime, phoneNumber, area,account);
		
		//设置分页
		List<Transfer> displyData = new ArrayList<Transfer>();
		int resultMaxCount = transferList.size() ;
	    int startIndex = (page-1)*rows<0?0:(page-1)*rows;
	    int endIndex = page*rows<resultMaxCount?page*rows:resultMaxCount;
	    for (int i = startIndex; i < endIndex ; i++) {
	    	displyData.add(transferList.get(i));
	    }
	    
		PageData<Transfer> t = new PageData<Transfer>();
		t.setRows(displyData);
		t.setTotal(resultMaxCount);
		
		String returnJson =JsonHelp.objectToJsonStr(t);
		if(returnJson==null){
			returnJson="[]";
		}
		response.setCharacterEncoding("utf-8");
	    PrintWriter out = response.getWriter();
		out.print(returnJson);
		out.flush();
		out.close();
	}
	
	@RequestMapping(params="method=addTransfer")
	public void addTransfer(String beginTime,String endTime,String phoneNumber,String account,String status,String areaId,String area,String submitTime,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		String[] areaIdArr = areaId.split(",");
		String[] areaArr = area.split(",");
		
		String batchId = System.currentTimeMillis()+"";
		
		List<Transfer> transferList = new ArrayList<Transfer>();
		
		for (int i = 0; i < areaIdArr.length; i++)
		{
			Transfer transfer = new Transfer();
			transfer.setAccount(account);
			transfer.setBeginTime(beginTime);
			transfer.setEndTime(endTime);
			transfer.setSubmitTime(submitTime);
			transfer.setTransferNumber(phoneNumber);
			transfer.setStatus(status);
			transfer.setTransferAreaId(areaIdArr[i]);
			transfer.setTransferArea(areaArr[i]);
			transfer.setTransferBatch(batchId);
			
			transferList.add(transfer);
			
		}
		int i = transferService.addTransfer(transferList);
		ResponseFormat rf=new ResponseFormat();
		if(i>0){
			rf.setStatu(Constant.STATU_SUCCESS);
		}else{
			
			rf.setStatu(Constant.STATU_ERROR);
		}
		String returnJson =JsonHelp.objectToJsonStr(rf);
		response.setCharacterEncoding("utf-8");
	    PrintWriter out = response.getWriter();
		out.print(returnJson);
		out.flush();
		out.close();
	}
	
	@RequestMapping(params="method=updateTransfer")
	public void updateTransfer(String beginTime,String endTime,String phoneNumber,String account,String status,String areaId,String area,String submitTime,String batchId,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		
		
		String[] areaIdArr = areaId.split(",");
		String[] areaArr = area.split(",");
		
		String afterBatchId = System.currentTimeMillis()+"";
		
		List<Transfer> transferList = new ArrayList<Transfer>();
		
		for (int i = 0; i < areaIdArr.length; i++)
		{
			Transfer transfer = new Transfer();
			transfer.setAccount(account);
			transfer.setBeginTime(beginTime);
			transfer.setEndTime(endTime);
			transfer.setSubmitTime(submitTime);
			transfer.setTransferNumber(phoneNumber);
			transfer.setStatus(status);
			transfer.setTransferAreaId(areaIdArr[i]);
			transfer.setTransferArea(areaArr[i]);
			transfer.setTransferBatch(afterBatchId);
			
			transferList.add(transfer);
			
		}
		int j = transferService.deleteTransferByBatchId(batchId);
		
		int i = transferService.addTransfer(transferList);
		ResponseFormat rf=new ResponseFormat();
		if(i>0&&j>0){
			rf.setStatu(Constant.STATU_SUCCESS);
		}else{
			
			rf.setStatu(Constant.STATU_ERROR);
		}
		String returnJson =JsonHelp.objectToJsonStr(rf);
		response.setCharacterEncoding("utf-8");
	    PrintWriter out = response.getWriter();
		out.print(returnJson);
		out.flush();
		out.close();
	}
	
	@RequestMapping(params="method=deleteTransferByBatchId")
	public void deleteTransferByBatchId(String batchId,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		int i = transferService.deleteTransferByBatchId(batchId);
		ResponseFormat rf=new ResponseFormat();
		if(i>0){
			rf.setStatu(Constant.STATU_SUCCESS);
		}else{
			
			rf.setStatu(Constant.STATU_ERROR);
		}
		String returnJson =JsonHelp.objectToJsonStr(rf);
		response.setCharacterEncoding("utf-8");
	    PrintWriter out = response.getWriter();
		out.print(returnJson);
		out.flush();
		out.close();
	}
	
	@RequestMapping(params="method=updateStatus")
	public void updateStatus(String batchId,String status,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		int i = transferService.updateStatusByBatchId(batchId, status);
		ResponseFormat rf=new ResponseFormat();
		if(i>0){
			rf.setStatu(Constant.STATU_SUCCESS);
		}else{
			
			rf.setStatu(Constant.STATU_ERROR);
		}
		String returnJson =JsonHelp.objectToJsonStr(rf);
		response.setCharacterEncoding("utf-8");
	    PrintWriter out = response.getWriter();
		out.print(returnJson);
		out.flush();
		out.close();
	}
	
	
}
