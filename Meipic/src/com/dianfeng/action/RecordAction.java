package com.dianfeng.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dianfeng.entity.Record;
import com.dianfeng.service.RecordService;
import com.dianfeng.utils.Constant;
import com.dianfeng.utils.ExcelUtils;
import com.dianfeng.utils.JsonHelp;
import com.dianfeng.utils.JsonReturn;
import com.dianfeng.utils.PageData;

/**
 * 录音调听
 */
@Component
@RequestMapping("/record")
public class RecordAction {
	@Resource
	private RecordService recordService;

	public RecordService getRecordService() {
		return recordService;
	}
	public void setRecordService(RecordService recordService) {
		this.recordService = recordService;
	}
	@RequestMapping(params="method=loadScoreType")
	public void loadScoreType(HttpServletRequest request,HttpServletResponse response)throws IOException, ParseException{
		List<ScoreType> scoreTypeList = new ArrayList<ScoreType>();
		{
			scoreTypeList.add(new ScoreType("0","全部"));
			scoreTypeList.add(new ScoreType("-1","坐席未点"));
			scoreTypeList.add(new ScoreType("-2","用户未评"));
			scoreTypeList.add(new ScoreType("1","1"));
			scoreTypeList.add(new ScoreType("2","2"));
			scoreTypeList.add(new ScoreType("3","3"));
			scoreTypeList.add(new ScoreType("4","4"));
			scoreTypeList.add(new ScoreType("5","5"));
		}
		String returnJson =JsonHelp.listToJsonStr(scoreTypeList);
		if(returnJson==null){
			returnJson="[]";
		}
		response.setCharacterEncoding("utf-8");
	    PrintWriter out = response.getWriter();
		out.print(returnJson);
		out.flush();
		out.close();
	}
	
	public class ScoreType {
		public ScoreType(){}
		public ScoreType(String id,String score){
			this.id = id; 
			this.score = score;
		}
		private String id;
		private String score;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getScore() {
			return score;
		}
		public void setScore(String score) {
			this.score = score;
		}
	}
	
	@RequestMapping(params="method=selectAllRecord")
	public void selectAllRecord(int page,int rows,String seatNumber,String role,HttpServletRequest request,HttpServletResponse response) throws IOException, ParseException{
		String inPhone = request.getParameter("select_inPhone")==null?"":request.getParameter("select_inPhone");
		String firstTime = request.getParameter("select_first_time")==null?"":request.getParameter("select_first_time");
		String endTime = request.getParameter("select_end_time")==null?"":request.getParameter("select_end_time");
		String score = request.getParameter("select_score")==null?"":request.getParameter("select_score");
		List<Record> recordList ;
		
		if(!endTime.equals("")){
//			endTime = endTime + " 23:59:59";
		}
		if(score.equals("-1")){
			recordList = recordService.selectAllRecordSeatNotClick(role.equals("1")?null:seatNumber,firstTime, endTime,inPhone);
		}else if(score.equals("-2")){
			recordList = recordService.selectAllRecordUserNotClick(role.equals("1")?null:seatNumber,firstTime, endTime,inPhone);
		}else{
			recordList = recordService.selectAllRecord(role.equals("1")?null:seatNumber,firstTime, endTime,inPhone,score.equals("0")?null:score);
		}
		
		
		PageData<Record> pageData = new PageData<Record>();		//页面数据
		
		List<Record> displyData = new ArrayList<Record>();
		int resultMaxCount = recordList.size() ;
		int startIndex = (page-1)*rows<0?0:(page-1)*rows;
	    int endIndex = page*rows<resultMaxCount?page*rows:resultMaxCount;
	    for (int i = startIndex; i < endIndex ; i++) {
	    	if(recordList.get(i).getAnswers()!=null && !recordList.get(i).getAnswers().equals("") && recordList.get(i).getAnswers().length()!=4){
	    		recordList.get(i).setAnswers(recordList.get(i).getAnswers().substring(1));
	    	}
	    	//分数为空的时候
	    	if(recordList.get(i).getScore() == null||recordList.get(i).getScore().equals("")||recordList.get(i).getScore().equals("null")){
	    		if(recordList.get(i).getRecordType() == null || recordList.get(i).getRecordType().equals("")){
	    			recordList.get(i).setScore("坐席未点");
	    		}else{
	    			recordList.get(i).setScore("用户未评");
	    		}
	    	}
	    	displyData.add(recordList.get(i));
	    }
	    
	    pageData.setRows(displyData);
	    pageData.setTotal(resultMaxCount);
	    String json  = JsonHelp.objectToJsonStr(pageData);
		response.setCharacterEncoding("utf-8");
	    PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
		out.close();
	}
	/** 导出报表 */
	@RequestMapping(params="method=exportExcel")
	public void exportExcel(String seatNumber,String role,String firstTime,String endTime,String inPhone,String score,String ids,String type,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		List<Record> recordList = recordService.selectAllRecord(role.equals("1")?null:seatNumber,firstTime, endTime,inPhone,score);
		List<Record> exportList = new ArrayList<Record>();
		if(type.equals("1")){
			String [] idArray = ids.split("_");
			if(ids != null && ids !=""){
				for(Record record : recordList){
					boolean flag = false;
					for(String id : idArray){
						if(id.equals(record.getId())){flag = true;}
					}
					if(flag){exportList.add(record);}
				}
			}
		}
		String json  = JsonHelp.listToJsonStr(type.equals("1")?exportList:recordList);
		response.setCharacterEncoding("utf-8");
	    PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
		out.close();
	}
	
	@RequestMapping(params="method=linkRecord")
	public void linkRecord(String recordId,String assemblyLine,HttpServletRequest request,HttpServletResponse response) throws IOException, ParseException{
		List<Record> recordList = recordService.getRecordByRecordId(recordId);
		
		int i = 0;
		if(recordList.size()>0)
		{
			System.out.println(recordList.get(0).getUniqueId());
			String uniqueId = recordList.get(0).getUniqueId();
			i = recordService.insertWordOrderRecordLink(assemblyLine, uniqueId);
		}
		JsonReturn jsonReturn = new JsonReturn();
		
		if(i>0)
		{
			jsonReturn.setStatus(Constant.STATU_SUCCESS);
		}
		else
		{
			jsonReturn.setStatus(Constant.STATU_ERROR);
		}
		
		String json  = JsonHelp.objectToJsonStr(jsonReturn);
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
		out.close();
	}
	//导出报表
	@RequestMapping(params="method=exportRecord")
	public void exportRecord(String role,String seatNumber,HttpServletRequest request,HttpServletResponse response) throws Exception{
		String inPhone = request.getParameter("select_inPhone")==null?"":request.getParameter("select_inPhone");
		String firstTime = request.getParameter("select_first_time")==null?"":request.getParameter("select_first_time");
		String endTime = request.getParameter("select_end_time")==null?"":request.getParameter("select_end_time");
		String score = request.getParameter("select_score")==null?"":request.getParameter("select_score");
		String ids = request.getParameter("select_ids")==null?"":request.getParameter("select_ids");
		String type = request.getParameter("select_type")==null?"":request.getParameter("select_type");
		
		List<Record> recordList = recordService.selectAllRecord(role.equals("1")?null:seatNumber,firstTime, endTime,inPhone,score);
		List<Record> exportList = new ArrayList<Record>();
		if(type.equals("1")){
			String [] idArray = ids.split("_");
			if(ids != null && ids !=""){
				for(Record record : recordList){
					boolean flag = false;
					for(String id : idArray){
						if(id.equals(record.getId())){flag = true;}
					}
					if(flag){exportList.add(record);}
				}
			}
		}
		String fileName = URLEncoder.encode(Constant.RECORD_TAKE, "UTF-8");
	    response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xls");
	    List<String[]> rowDatas = recordToStringArray(Constant.title.length,type.equals("1")?exportList:recordList);
		ServletOutputStream outputStream = response.getOutputStream();  
		load(fileName,Constant.title, rowDatas, outputStream);
	}
	
	private void load(String fileName, String[] titles,List<String[]> rowDatas, ServletOutputStream outputStream) {
		HSSFWorkbook workBook = new HSSFWorkbook();  
        HSSFSheet sheet = workBook.createSheet();  
        ExcelUtils exportUtil = new ExcelUtils(workBook, sheet);  
        HSSFCellStyle headStyle = exportUtil.getHeadStyle();  
        HSSFCellStyle bodyStyle = exportUtil.getBodyStyle();  
        HSSFRow headRow = sheet.createRow(0);  
        HSSFCell cell = null;  
        for (int i = 0; i < titles.length; i++)  {  
            cell = headRow.createCell(i);  
            cell.setCellStyle(headStyle);  
            cell.setCellValue(titles[i]);  
        }  
        // 构建表体数据  
        if (rowDatas != null && rowDatas.size() > 0)  
        {  
            for (int j = 0; j < rowDatas.size(); j++)
            {  
                HSSFRow bodyRow = sheet.createRow(j + 1);  
                String [] row = rowDatas.get(j);
                for(int k = 0 ; k<row.length ; k++){
	                cell = bodyRow.createCell(k);  
	                cell.setCellStyle(bodyStyle);  
	                cell.setCellValue(row[k]);  
                }
            }  
        }  
        	try {
				workBook.write(outputStream);
				outputStream.flush();  
	        	outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}  
		
	}
	
	private List<String[]> recordToStringArray(int size,List<Record> rowDatas) {
		List<String[]> returnArray = new ArrayList<String[]>();
		for(Record record:rowDatas){
			String[] temp = new String[size];
			temp[0] = record.getAssemblyLine();
			temp[1] = record.getRecordDay();
			temp[2] = record.getStartTime();
			temp[3] = record.getRecordTime();
			temp[4] = record.getCallType();
			temp[5] = record.getCallers();
			temp[6] = record.getAnswers();
			temp[7] = record.getScore();
			temp[8] = record.getRecordPath();
			returnArray.add(temp);
		}
		return returnArray;
	}
}

