package com.dianfeng.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dianfeng.entity.AreaInfo;
import com.dianfeng.service.AreaInfoService;

@Component
@RequestMapping("/manager")
public class ManagerAction
{
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
	
	
	@RequestMapping(params="method=pageToTra")
	public String pageToTra(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		//设置页面的区域选择
		List<AreaInfo> areaList = areaInfoService.getAllArea();
		Map<Integer,String> provinceMap = new HashMap<Integer, String>();
		Map<String,String> cityMap = null;
		Map<Integer,Map<String,String>> provinceWithCity = new HashMap<Integer, Map<String,String>>();
		
		int k=-1;
		
		for (int i = 0; i < areaList.size(); i++)
		{
			if(!provinceMap.containsValue(areaList.get(i).getProvince()))
			{
				provinceMap.put(++k, areaList.get(i).getProvince());
				cityMap = new HashMap<String, String>();
			}
			
			cityMap.put(areaList.get(i).getCode(), areaList.get(i).getCity());
			
			if(i==areaList.size()-1 || !provinceMap.containsValue(areaList.get(i+1).getProvince()))
			{
				provinceWithCity.put(k, cityMap);
			}
		}
		
		StringBuilder sb=new StringBuilder();
		StringBuilder sb1=new StringBuilder();
		
		sb.append("<div id='selectSub'><select id='AreaCombo' class='easyui-combobox' name='state' style='width:200px;' onchange='showSelect(this.value)'>");
		sb1.append("<div id='currentSelectSub'><select id='currentAreaCombo' class='easyui-combobox' name='state' style='width:200px;' onchange='showSelect(this.value)'>");
		for (int i = 0; i < provinceMap.size(); i++)
		{
			sb.append("<option value='");
			sb.append(i);
			sb.append("'>");
			sb.append(provinceMap.get(i));
			sb.append("</option>");
			
			sb1.append("<option value='");
			sb1.append(i);
			sb1.append("'>");
			sb1.append(provinceMap.get(i));
			sb1.append("</option>");
		}
		
		sb.append("</select>");
		sb1.append("</select>");
		
		for (int i = 0; i < provinceWithCity.size(); i++)
		{
			sb.append("<div id='c0");
			sb.append(i);
			sb.append("'>");
			
			sb1.append("<div id='c00");
			sb1.append(i);
			sb1.append("'>");
			
			//遍历键集合
			Iterator<String> it=provinceWithCity.get(i).keySet().iterator();
			while(it.hasNext())
			{
				String key = it.next()+"";
				sb.append("<input type='checkbox' name='");
				sb.append(provinceWithCity.get(i).get(key));
				sb.append("' onclick='addPreItem()' value='");
				sb.append(key);
				sb.append("'/>");
				sb.append(provinceWithCity.get(i).get(key));
				
				sb1.append("<input type='checkbox' name='");
				sb1.append(provinceWithCity.get(i).get(key));
				sb1.append("' onclick='addCurrentPreItemAndSureItem()' value='");
				sb1.append(key);
				sb1.append("'/>");
				sb1.append(provinceWithCity.get(i).get(key));
			}
			
			sb.append("</div>");
			sb1.append("</div>");
			
		}
		
		sb.append("</div><div id='preview'><div>您已选择的城市</div><div id='previewItem'></div></div>");
		sb1.append("</div><div id='currentPreview'><div>您已选择的城市</div><div id='currentPreviewItem'></div></div>");
		
		request.setAttribute("selectArea", sb.toString());
		request.setAttribute("currentSelectArea", sb1.toString());
		
		return "manager";
	}
}
