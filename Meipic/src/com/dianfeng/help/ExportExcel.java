package com.dianfeng.help;

import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.dianfeng.entity.WorkOrderDetailInfo;

/**导出报表*/
public class ExportExcel {
	public static HSSFWorkbook workbook;
	
	public static String titles[] = new String[6];
	{
		titles[0] = "日期";
		titles[1] = "坐席号";
		titles[2] = "在线时长";
		titles[3] = "示忙时长";
		titles[4] = "总时长";
		titles[5] = "selectDay";
	}
	//导出简报
	public static boolean exportExcel(HttpServletResponse response,List<WorkOrderDetailInfo> workOrderDetailInfoList) {
		
		try{
		workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("工单详情");
		//标题
		HSSFRow row = sheet.createRow(0);
		
		for(int i = 0 ; i< titles.length ; i++){
			HSSFCell cell = row.createCell(i);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(new HSSFRichTextString(titles[i]));
		}
		
		for (int i = 0; i < workOrderDetailInfoList.size(); i++) {
			HSSFRow datarow = sheet.createRow(i + 1);
			
			WorkOrderDetailInfo workOrderDetailInfo = workOrderDetailInfoList.get(i);
			HSSFCell selectDayCell = datarow.createCell(0);
			selectDayCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			selectDayCell.setCellValue(new HSSFRichTextString(workOrderDetailInfo.getAge()));
			
			HSSFCell seatNumberCell = datarow.createCell(1);
			seatNumberCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			seatNumberCell.setCellValue(new HSSFRichTextString(workOrderDetailInfo.getAgent()));
			
			HSSFCell sumOnLineTimeCell = datarow.createCell(2);
			sumOnLineTimeCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			sumOnLineTimeCell.setCellValue(new HSSFRichTextString(workOrderDetailInfo.getTel()));
			
			HSSFCell sumBusyTimeCell = datarow.createCell(3);
			sumBusyTimeCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			sumBusyTimeCell.setCellValue(new HSSFRichTextString(workOrderDetailInfo.getCustomerId()));

			HSSFCell sumAllTimeCell = datarow.createCell(4);
			sumAllTimeCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			sumAllTimeCell.setCellValue(new HSSFRichTextString(workOrderDetailInfo.getWorkOrderId()));
		}
		
		OutputStream out = response.getOutputStream();
    	workbook.write(out);
    	out.close();
		
		return true;
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	
	public static void outputExcel(HttpServletResponse response) {  
	          
	        try {  
	        	OutputStream out = response.getOutputStream();
	        	workbook.write(out);
	        	out.close();
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	 }
}
