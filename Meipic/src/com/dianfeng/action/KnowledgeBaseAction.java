package com.dianfeng.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.dianfeng.entity.KnowledgeBase;
import com.dianfeng.service.KnowledgeBaseService;
import com.dianfeng.utils.Constant;
import com.dianfeng.utils.JsonHelp;
import com.dianfeng.utils.PageData;
@Component
@RequestMapping("/knowledgeBase")
public class KnowledgeBaseAction {
	@Resource
	private KnowledgeBaseService service;
	@Resource
	private ServletContext servletContext;
	
	public KnowledgeBaseService getService() {
		return service;
	}
	public void setService(KnowledgeBaseService service) {
		this.service = service;
	}
	public ServletContext getServletContext() {
		return servletContext;
	}
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	@RequestMapping(params="method=loadTree")
	public void loadTree(HttpServletRequest request,HttpServletResponse respone) throws Exception{
		StringBuffer sb = new StringBuffer("[");
		getNode(sb, 0, "知识库");
		sb.append("]");
		respone.setCharacterEncoding("utf-8");
		PrintWriter out = respone.getWriter();
		out.write(sb.toString());
		out.flush();
		out.close();
	}
	@RequestMapping(params="method=selectKnowledgeBaseById")
	public void selectKnowledgeBaseById(int page,int rows,int id ,HttpServletRequest request,HttpServletResponse respone) throws Exception{
		List<KnowledgeBase> knowledgeBaseList  = service.selectKnowledgeBaseById(id);
		
		List<KnowledgeBase> displyData = new ArrayList<KnowledgeBase>();
		
		int resultMaxCount = knowledgeBaseList.size() ;
		
		if(page==0)
			page = 1;
		
	    int startIndex = (page-1)*rows<0?0:(page-1)*rows;
	    
	    int endIndex = page*rows<resultMaxCount?page*rows:resultMaxCount;
	    
	    for (int i = startIndex; i < endIndex ; i++) {
	    	displyData.add(knowledgeBaseList.get(i));
	    }
	    
		PageData<KnowledgeBase> t = new PageData<KnowledgeBase>();
		t.setRows(displyData);
		t.setTotal(resultMaxCount);
		String returnJson  = JsonHelp.objectToJsonStr(t);
		
		respone.setCharacterEncoding("utf-8");
		PrintWriter out = respone.getWriter();
		out.write(returnJson);
		out.flush();
		out.close();
	}
	@RequestMapping(params="method=selectKnowledgeBaseByKeyWord")
	public void selectKnowledgeBaseByKeyWord(int page,int rows,String keywordChi ,HttpServletRequest request,HttpServletResponse respone) throws Exception{
		List<KnowledgeBase> knowledgeBaseList = service.selectKnowledgeBaseByKeyWordChi(keywordChi);
		
		List<KnowledgeBase> displyData = new ArrayList<KnowledgeBase>();
		
		if(page==0)
			page = 1;
		
		int resultMaxCount = knowledgeBaseList.size() ;
	    int startIndex = (page-1)*rows<0?0:(page-1)*rows;
	    int endIndex = page*rows<resultMaxCount?page*rows:resultMaxCount;
	    for (int i = startIndex; i < endIndex ; i++) {
	    	displyData.add(knowledgeBaseList.get(i));
	    }
	    
	    
		PageData<KnowledgeBase> t = new PageData<KnowledgeBase>();
		t.setRows(displyData);
		t.setTotal(resultMaxCount);
		String returnJson  = JsonHelp.objectToJsonStr(t);
		
		respone.setCharacterEncoding("utf-8");
		PrintWriter out = respone.getWriter();
		out.write(returnJson);
		out.flush();
		out.close();
	}
	@RequestMapping(params="method=delKnowledgebaseById")
	public void delKnowledgebaseById(int id, HttpServletRequest request,HttpServletResponse respone) throws Exception{
		Integer resultCount = service.delKnowledgebaseById(id);
	
		respone.setCharacterEncoding("utf-8");
		PrintWriter out = respone.getWriter();
		if (resultCount < 1) { // 没有操作的SQL
			out.print(Constant.STATU_ERROR);
		}else{
			out.print(Constant.STATU_SUCCESS);
		}
		out.flush();
		out.close();
	}
	
	@RequestMapping(params="method=findKnowledgeBaseById")
	public void findKnowledgeBaseById(int id ,HttpServletRequest request,HttpServletResponse respone) throws Exception{
		KnowledgeBase knowledgebase = service.findKnowledgeBaseById(id);
		String returnJson =JsonHelp.objectToJsonStr(knowledgebase);
		respone.setCharacterEncoding("utf-8");
	    PrintWriter out = respone.getWriter();
		out.print(returnJson);
		out.flush();
		out.close();
	}
	
	
	@RequestMapping(params="method=addKnowledgeBase")
	public void addKnowledgeBase(HttpServletRequest request,HttpServletResponse respone,KnowledgeBase knowledgeBase) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dt = new Date();
		knowledgeBase.setAddTime(sdf.format(dt));
		Integer resultCount = service.addKnowledgeBase(knowledgeBase);
		respone.setCharacterEncoding("utf-8");
	    PrintWriter out = respone.getWriter();
	    if (resultCount < 1) { // 没有操作的SQL
			out.print(Constant.STATU_ERROR);
		}else{
			out.print(Constant.STATU_SUCCESS);
		}
		out.flush();
		out.close();
	}
	
	@RequestMapping(params="method=updateKnowledgeBase")
	public void updateKnowledgeBase(HttpServletRequest request,HttpServletResponse respone,KnowledgeBase knowledgeBase) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dt = new Date();
		knowledgeBase.setAddTime(sdf.format(dt));
		Integer resultCount = service.updateKnowledgeBase(knowledgeBase);
		respone.setCharacterEncoding("utf-8");
	    PrintWriter out = respone.getWriter();
	    if (resultCount < 1) { // 没有操作的SQL
			out.print(Constant.STATU_ERROR);
		}else{
			out.print(Constant.STATU_SUCCESS);
		}
		out.flush();
		out.close();
	}
	private void getNode(StringBuffer sb,int pid,String text) {
		List<KnowledgeBase> list = service.getNods(pid); 		//执行查询,自己写吧
		sb.append("{");
		sb.append("\"id\":\""+pid+"\",");
		if (list != null && list.size() != 0) { // 判断是否查到,没有了表示小的了
			sb.append("\"text\":\""+text+"\",");
			sb.append("\"state\":\"closed\",");
			sb.append("\"children\":[");
			for (int i = 0; i < list.size(); i++){
				getNode(sb,list.get(i).getId(),list.get(i).getTitle()); 	// 根据当前id查询子
				if(i<list.size()-1){
					sb.append(",");
				}
			}
			sb.append("]");
			sb.append("}");
		}else{
			sb.append("\"text\":\""+text+"\"");
			sb.append("}");
		}
	}
	
	@RequestMapping("/save")
	public String save(@RequestParam(value = "knowledgeBaseFile", required = false)MultipartFile file,String role, HttpServletRequest request,HttpServletResponse respone, ModelMap model) throws FileNotFoundException, IOException{
		request.setCharacterEncoding("utf-8");
		String msg = "4";
		boolean flag = true;
		if(file!=null){
			String path = request.getSession().getServletContext().getRealPath("upload");  
			String fileName = file.getOriginalFilename();
			fileName = fileName.toLowerCase();
			if(fileName.endsWith("xls")){
				File targetFile = new File(path, fileName);
				if(!targetFile.exists()){  
				    targetFile.mkdirs();  
				}  
				//保存  
				try {  
				    file.transferTo(targetFile);
				    POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(targetFile));
					HSSFWorkbook rwb = new HSSFWorkbook(fs);
					int sheetCount = rwb.getNumberOfSheets();
					for (int s = 0; s < sheetCount; s++) {
						HSSFSheet sheet = rwb.getSheetAt(s);
						int rsRows = sheet.getPhysicalNumberOfRows();
						for (int r =1; r < rsRows; r++) {
							flag = false;	//有数据
							HSSFRow row = sheet.getRow(r);
							KnowledgeBase base = new KnowledgeBase();
							if(row==null){break;}
							if(row.getCell(0)!=null){base.setParentId((int)Double.parseDouble(row.getCell(0).toString()));}
							if(row.getCell(1)!=null){base.setId((int)Double.parseDouble(row.getCell(1).toString()));}
							if(row.getCell(2)!=null){base.setMyType(row.getCell(2).toString());}
							if(row.getCell(3)!=null){base.setParentType(row.getCell(3).toString());}
							if(row.getCell(4)!=null){base.setTitle(row.getCell(4).toString());}
							if(row.getCell(5)!=null){base.setKeyWordChi(row.getCell(5).toString());}
							if(row.getCell(6)!=null){base.setContent(row.getCell(6).toString());}
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							base.setAddTime(sdf.format(System.currentTimeMillis()));
							service.addKnowledgeBase(base);
						}
					}
				}catch (Exception e) {
				    msg = "2";
				    System.out.println(e.getMessage());
				}
				msg = "1";
			}else if(fileName.endsWith("xlsx")){
				File targetFile = new File(path, fileName);
				if(!targetFile.exists()){targetFile.mkdirs();  }  
				try {  
				    file.transferTo(targetFile); 
				    XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(targetFile));
			    	int sheetCount = xwb.getNumberOfSheets();
			    	for(int s = 0; s < sheetCount; s++) {
						 XSSFSheet sheet = xwb.getSheetAt(s);
						 int rsRows = sheet.getPhysicalNumberOfRows();
						 for (int r = 1; r < rsRows; r++) {
							flag = false;	//有数据
							XSSFRow row = sheet.getRow(r);
							KnowledgeBase base = new KnowledgeBase();
							if(row==null){break;}
							if(row.getCell(0)!=null){base.setParentId((int)Double.parseDouble(row.getCell(0).toString()));}
							if(row.getCell(1)!=null){base.setId((int)Double.parseDouble(row.getCell(1).toString()));}
							if(row.getCell(2)!=null){base.setMyType(row.getCell(2).toString());}
							if(row.getCell(3)!=null){base.setParentType(row.getCell(3).toString());}
							if(row.getCell(4)!=null){base.setTitle(row.getCell(4).toString());}
							if(row.getCell(5)!=null){base.setKeyWordChi(row.getCell(5).toString());}
							if(row.getCell(6)!=null){base.setContent(row.getCell(6).toString());}
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							base.setAddTime(sdf.format(System.currentTimeMillis()));
							service.addKnowledgeBase(base);
						}
			    	}
				} catch (Exception e) {  
				    msg = "2";
				    System.out.println(e.getMessage());
				}
				msg = "1";
		     }
			if(flag) msg = "3";
		}
		return "redirect:/jsp/knowledgebaselist.jsp?role=" + role+"&msg="+msg;
	}
}
