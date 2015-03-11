package com.dianfeng.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dianfeng.entity.Announcement;
import com.dianfeng.service.AnnouncementService;
import com.dianfeng.utils.Constant;
import com.dianfeng.utils.JsonHelp;
import com.dianfeng.utils.PageData;

@Component
@RequestMapping("/announcement")
public class AnnouncementAction {
	
	@Resource
	private AnnouncementService announcementService;

	@RequestMapping(params="method=selectAllAnnouncement")
	public void selectAllAnnouncement(int page,int rows,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		List<Announcement> announcementList = announcementService.selectAllAnnouncement();

		List<Announcement> displyData = new ArrayList<Announcement>();
		
		int resultMaxCount = announcementList.size() ;
		
	    int startIndex = (page-1)*rows<0?0:(page-1)*rows;
	    
	    int endIndex = page*rows<resultMaxCount?page*rows:resultMaxCount;
	    
	    for (int i = startIndex; i < endIndex ; i++) {
	    	displyData.add(announcementList.get(i));
	    }
	    
		PageData<Announcement> t = new PageData<Announcement>();
		
		t.setRows(displyData);
		
		t.setTotal(resultMaxCount);
		
		String json  = JsonHelp.objectToJsonStr(t);
		
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.write(json);
		out.flush();
		out.close();
	}

	@RequestMapping(params="method=selectAnnouncementById")
	public void selectAnnouncementById(String announcementId,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		Announcement announcement = announcementService.selectAnnouncementById(announcementId);
		
		String returnJson = JsonHelp.objectToJsonStr(announcement);
		
		if(returnJson == null || returnJson.equals("")){
			returnJson="{}";
		}
		response.setCharacterEncoding("utf-8");
	    PrintWriter out = response.getWriter();
		out.print(returnJson);
		out.flush();
		out.close();
	}
	
	
	@RequestMapping(params="method=deleteAnnouncement")
	public void deleteAnnouncement(String announcementId,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		int result = announcementService.deleteAnnouncement(announcementId);
		int i = announcementService.deleteNotice(announcementId);
		response.setCharacterEncoding("utf-8");
	    PrintWriter out = response.getWriter();
	    if(result==1)
	    	out.print(Constant.STATU_SUCCESS);
	    else 
	    	out.print(Constant.STATU_ERROR);
	    
		out.flush();
		out.close();
	}
	
	@RequestMapping(params="method=addAnnouncement")
	public void addAnnouncement(Announcement announcement,String announcementId,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		if(announcement != null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			announcement.setReleaseDate(sdf.format(System.currentTimeMillis()));
		}
		
		int result = announcementService.addAnnouncement(announcement);
		
		response.setCharacterEncoding("utf-8");
		
		PrintWriter out = response.getWriter();

		if(result==1)
	    	out.print(Constant.STATU_SUCCESS);
	    else 
	    	out.print(Constant.STATU_ERROR);
	    
		out.flush();
		out.close();
	}
	
	
	public AnnouncementService getAnnouncementService() {
		return announcementService;
	}

	public void setAnnouncementService(AnnouncementService announcementService) {
		this.announcementService = announcementService;
	}
}
