package com.dianfeng.action;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dianfeng.entity.CustomerPhoneLinkDetailInfo;
import com.dianfeng.entity.WorkOrderDetailInfo;
import com.dianfeng.service.CustomerPhoneLinkService;
import com.dianfeng.service.WorkOrderInfoService;
import com.dianfeng.utils.JsonUtil;

@Component
@RequestMapping("/export")
public class ExportAction
{
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
	private CustomerPhoneLinkService customerPhoneLinkService;

	public CustomerPhoneLinkService getCustomerPhoneLinkService()
	{
		return customerPhoneLinkService;
	}


	public void setCustomerPhoneLinkService(CustomerPhoneLinkService customerPhoneLinkService)
	{
		this.customerPhoneLinkService = customerPhoneLinkService;
	}


	/**
	 * 导出工单详情
	 * @param work_order_detail_info
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(params="method=exportWorkOrder")
	public void exportWorkOrder(String work_order_detail_info,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		WorkOrderDetailInfo workOrderDetailInfo = new WorkOrderDetailInfo();
		if(work_order_detail_info != null)
		{
			workOrderDetailInfo = JsonUtil.fromJson(work_order_detail_info, WorkOrderDetailInfo.class);
		}
		
		workOrderDetailInfo.setPage(0);
		workOrderDetailInfo.setRows(10000);
		
		List<WorkOrderDetailInfo> workOrderDetailInfoList = workOrderInfoService.getDetailWorkOrder(workOrderDetailInfo);
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/x-download");

		String filedisplay = "工单详情.xls";
		filedisplay = URLEncoder.encode(filedisplay, "UTF-8");
		response.addHeader("Content-Disposition", "attachment;filename="+ filedisplay);
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("工单详情");
		
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth((short) 20);
		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		HSSFDataFormat format = workbook.createDataFormat();
		style.setDataFormat(format.getFormat("@"));
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 11);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		style.setFont(font);
		// 生成并设置另一个样式
		HSSFCellStyle style2 = workbook.createCellStyle();
		HSSFDataFormat format2 = workbook.createDataFormat();
		style2.setDataFormat(format2.getFormat("@"));
		style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成另一个字体
		HSSFFont font2 = workbook.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		style2.setFont(font2);
		// 生成并设置另一个样式
		HSSFCellStyle style3 = workbook.createCellStyle();
		HSSFDataFormat format3 = workbook.createDataFormat();
		style3.setDataFormat(format3.getFormat("@"));
		style3.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
		style3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style3.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style3.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style3.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style3.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style3.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style3.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成另一个字体
		HSSFFont font3 = workbook.createFont();
		font3.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		style3.setFont(font3);
		// 声明一个画图的顶级管理器
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		// 定义注释的大小和位置,详见文档
		HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0,
				0, 0, 0, (short) 4, 2, (short) 6, 5));
		// 设置注释内容
		comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
		// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
		comment.setAuthor("Dreamaker");
		
		
		//标题
		HSSFRow row = sheet.createRow(0);
		
		String titles[] = new String[29];
		{
			titles[0] = "工单流水";
			titles[1] = "来电号码";
			titles[2] = "来电时间";
			titles[3] = "标题";
			titles[4] = "优先级";
			titles[5] = "技能组";
			titles[6] = "反馈类型";
			titles[7] = "反馈渠道";
			titles[8] = "处理过程";
			titles[9] = "处理客服";
			titles[10] = "结案判定";
			titles[11] = "客户姓名";
			titles[12] = "性别";
			titles[13] = "联系电话";
			titles[14] = "归属地/国籍";
			titles[15] = "QQ号";
			titles[16] = "邮箱";
			titles[17] = "淘宝账号";
			titles[18] = "京东账号";
			titles[19] = "微信号";
			titles[20] = "微博地址";
			titles[21] = "客户类别";
			
			titles[22] = "提问大类";
			titles[23] = "问题类别";
			titles[24] = "问题描述";
			titles[25] = "IMEI号";
			titles[26] = "手机型号";
			titles[27] = "手机版本";
			titles[28] = "处理状态";
		}
		
		for(int i = 0 ; i< titles.length ; i++){
			HSSFCell cell = row.createCell(i);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(new HSSFRichTextString(titles[i]));
			cell.setCellStyle(style);
		}
		
		String preWorkOrderId = workOrderDetailInfoList.get(0).getWorkOrderId();
		
		for (int i = 0; i < workOrderDetailInfoList.size(); i++) {
			HSSFRow datarow = sheet.createRow(i + 1);
			String curWorkOrderId = workOrderDetailInfoList.get(i).getWorkOrderId();
			if(i>0)
			{
				if(preWorkOrderId.equals(curWorkOrderId))
				{
					sheet.addMergedRegion(new Region(i, (short) 0, i+1, (short)0));
					sheet.addMergedRegion(new Region(i, (short) 1, i+1, (short)1));
					sheet.addMergedRegion(new Region(i, (short) 2, i+1, (short)2));
					sheet.addMergedRegion(new Region(i, (short) 3, i+1, (short)3));
					sheet.addMergedRegion(new Region(i, (short) 4, i+1, (short)4));
					sheet.addMergedRegion(new Region(i, (short) 5, i+1, (short)5));
					sheet.addMergedRegion(new Region(i, (short) 6, i+1, (short)6));
					sheet.addMergedRegion(new Region(i, (short) 7, i+1, (short)7));
					sheet.addMergedRegion(new Region(i, (short) 8, i+1, (short)8));
					sheet.addMergedRegion(new Region(i, (short) 9, i+1, (short)9));
					sheet.addMergedRegion(new Region(i, (short) 10, i+1, (short)10));
					sheet.addMergedRegion(new Region(i, (short) 11, i+1, (short)11));
					sheet.addMergedRegion(new Region(i, (short) 12, i+1, (short)12));
					sheet.addMergedRegion(new Region(i, (short) 13, i+1, (short)13));
					sheet.addMergedRegion(new Region(i, (short) 14, i+1, (short)14));
					sheet.addMergedRegion(new Region(i, (short) 15, i+1, (short)15));
					sheet.addMergedRegion(new Region(i, (short) 16, i+1, (short)16));
					sheet.addMergedRegion(new Region(i, (short) 17, i+1, (short)17));
					sheet.addMergedRegion(new Region(i, (short) 18, i+1, (short)18));
					sheet.addMergedRegion(new Region(i, (short) 19, i+1, (short)19));
					sheet.addMergedRegion(new Region(i, (short) 20, i+1, (short)20));
					sheet.addMergedRegion(new Region(i, (short) 21, i+1, (short)21));
				}
			}
			
			preWorkOrderId = curWorkOrderId;
			
			WorkOrderDetailInfo workOrderDetailInfo1 = workOrderDetailInfoList.get(i);
			HSSFCell assemblyLine = datarow.createCell(0);
			assemblyLine.setCellType(HSSFCell.CELL_TYPE_STRING);
			assemblyLine.setCellValue(new HSSFRichTextString(workOrderDetailInfo1.getAssemblyLine()));
			assemblyLine.setCellStyle(style2);
			
			HSSFCell workOrderTel = datarow.createCell(1);
			workOrderTel.setCellType(HSSFCell.CELL_TYPE_STRING);
			workOrderTel.setCellValue(new HSSFRichTextString(workOrderDetailInfo1.getWorkOrderTel()));
			workOrderTel.setCellStyle(style2);
			
			HSSFCell telTime = datarow.createCell(2);
			telTime.setCellType(HSSFCell.CELL_TYPE_STRING);
			telTime.setCellValue(new HSSFRichTextString(workOrderDetailInfo1.getTelTime()));
			telTime.setCellStyle(style2);
			
			HSSFCell title = datarow.createCell(3);
			title.setCellType(HSSFCell.CELL_TYPE_STRING);
			title.setCellValue(new HSSFRichTextString(workOrderDetailInfo1.getTitle()));
			title.setCellStyle(style2);

			HSSFCell level = datarow.createCell(4);
			level.setCellType(HSSFCell.CELL_TYPE_STRING);
			level.setCellValue(new HSSFRichTextString(workOrderDetailInfo1.getLevel()));
			level.setCellStyle(style2);
			
			HSSFCell sumAllTimeCell = datarow.createCell(5);
			sumAllTimeCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			sumAllTimeCell.setCellValue(new HSSFRichTextString(workOrderDetailInfo1.getSkillGroup()));
			sumAllTimeCell.setCellStyle(style2);
			
			HSSFCell feedbackType = datarow.createCell(6);
			feedbackType.setCellType(HSSFCell.CELL_TYPE_STRING);
			feedbackType.setCellValue(new HSSFRichTextString(workOrderDetailInfo1.getFeedbackType()));
			feedbackType.setCellStyle(style2);
			
			HSSFCell feedbackChannel = datarow.createCell(7);
			feedbackChannel.setCellType(HSSFCell.CELL_TYPE_STRING);
			feedbackChannel.setCellValue(new HSSFRichTextString(workOrderDetailInfo1.getFeedbackChannel()));
			feedbackChannel.setCellStyle(style2);
			
			HSSFCell treatmentScheme = datarow.createCell(8);
			treatmentScheme.setCellType(HSSFCell.CELL_TYPE_STRING);
			treatmentScheme.setCellValue(new HSSFRichTextString(workOrderDetailInfo1.getTreatmentScheme()));
			treatmentScheme.setCellStyle(style2);
			
			HSSFCell userAccount = datarow.createCell(9);
			userAccount.setCellType(HSSFCell.CELL_TYPE_STRING);
			userAccount.setCellValue(new HSSFRichTextString(workOrderDetailInfo1.getUserAccount()));
			userAccount.setCellStyle(style2);
			
			HSSFCell finalDecision = datarow.createCell(10);
			finalDecision.setCellType(HSSFCell.CELL_TYPE_STRING);
			finalDecision.setCellValue(new HSSFRichTextString(workOrderDetailInfo1.getFinalDecision()));
			finalDecision.setCellStyle(style2);
			
			HSSFCell name = datarow.createCell(11);
			name.setCellType(HSSFCell.CELL_TYPE_STRING);
			name.setCellValue(new HSSFRichTextString(workOrderDetailInfo1.getName()));
			name.setCellStyle(style2);
			
			HSSFCell sex = datarow.createCell(12);
			sex.setCellType(HSSFCell.CELL_TYPE_STRING);
			sex.setCellValue(new HSSFRichTextString(workOrderDetailInfo1.getSex()));
			sex.setCellStyle(style2);
			
			HSSFCell tel = datarow.createCell(13);
			tel.setCellType(HSSFCell.CELL_TYPE_STRING);
			tel.setCellValue(new HSSFRichTextString(workOrderDetailInfo1.getTel().equals("")?"":workOrderDetailInfo1.getTel().substring(0, workOrderDetailInfo1.getTel().length()-1)));
			tel.setCellStyle(style2);
			
			HSSFCell area = datarow.createCell(14);
			area.setCellType(HSSFCell.CELL_TYPE_STRING);
			area.setCellValue(new HSSFRichTextString(workOrderDetailInfo1.getArea()));
			area.setCellStyle(style2);
			
			HSSFCell qq = datarow.createCell(15);
			qq.setCellType(HSSFCell.CELL_TYPE_STRING);
			qq.setCellValue(new HSSFRichTextString(workOrderDetailInfo1.getQq()));
			qq.setCellStyle(style2);
			
			HSSFCell email = datarow.createCell(16);
			email.setCellType(HSSFCell.CELL_TYPE_STRING);
			email.setCellValue(new HSSFRichTextString(workOrderDetailInfo1.getEmail()));
			email.setCellStyle(style2);
			
			HSSFCell taobao = datarow.createCell(17);
			taobao.setCellType(HSSFCell.CELL_TYPE_STRING);
			taobao.setCellValue(new HSSFRichTextString(workOrderDetailInfo1.getTaobao()));
			taobao.setCellStyle(style2);
			
			HSSFCell jd = datarow.createCell(18);
			jd.setCellType(HSSFCell.CELL_TYPE_STRING);
			jd.setCellValue(new HSSFRichTextString(workOrderDetailInfo1.getJd()));
			jd.setCellStyle(style2);
			
			HSSFCell wechat = datarow.createCell(19);
			wechat.setCellType(HSSFCell.CELL_TYPE_STRING);
			wechat.setCellValue(new HSSFRichTextString(workOrderDetailInfo1.getWechat()));
			wechat.setCellStyle(style2);
			
			HSSFCell weibo = datarow.createCell(20);
			weibo.setCellType(HSSFCell.CELL_TYPE_STRING);
			weibo.setCellValue(new HSSFRichTextString(workOrderDetailInfo1.getWeibo()));
			weibo.setCellStyle(style2);
			
			HSSFCell type = datarow.createCell(21);
			type.setCellType(HSSFCell.CELL_TYPE_STRING);
			type.setCellValue(new HSSFRichTextString(workOrderDetailInfo1.getType()));
			type.setCellStyle(style2);
			
			HSSFCell mold = datarow.createCell(22);
			mold.setCellType(HSSFCell.CELL_TYPE_STRING);
			mold.setCellValue(new HSSFRichTextString(workOrderDetailInfo1.getMold()));
			mold.setCellStyle(style3);
			
			HSSFCell questionType = datarow.createCell(23);
			questionType.setCellType(HSSFCell.CELL_TYPE_STRING);
			questionType.setCellValue(new HSSFRichTextString(workOrderDetailInfo1.getQuestionType()));
			questionType.setCellStyle(style3);
			
			HSSFCell describe = datarow.createCell(24);
			describe.setCellType(HSSFCell.CELL_TYPE_STRING);
			describe.setCellValue(new HSSFRichTextString(workOrderDetailInfo1.getDescribe()));
			describe.setCellStyle(style3);
			
			HSSFCell phoneImei = datarow.createCell(25);
			phoneImei.setCellType(HSSFCell.CELL_TYPE_STRING);
			phoneImei.setCellValue(new HSSFRichTextString(workOrderDetailInfo1.getPhoneImei()));
			phoneImei.setCellStyle(style3);
			
			HSSFCell phoneModel = datarow.createCell(26);
			phoneModel.setCellType(HSSFCell.CELL_TYPE_STRING);
			phoneModel.setCellValue(new HSSFRichTextString(workOrderDetailInfo1.getPhoneModel()));
			phoneModel.setCellStyle(style3);
			
			HSSFCell phoneVersion = datarow.createCell(27);
			phoneVersion.setCellType(HSSFCell.CELL_TYPE_STRING);
			phoneVersion.setCellValue(new HSSFRichTextString(workOrderDetailInfo1.getPhoneVersion()));
			phoneVersion.setCellStyle(style3);
			
			HSSFCell status = datarow.createCell(28);
			status.setCellType(HSSFCell.CELL_TYPE_STRING);
			status.setCellValue(new HSSFRichTextString(workOrderDetailInfo1.getStatus()));
			status.setCellStyle(style3);
		}
		
		try {
			OutputStream out = response.getOutputStream();
			workbook.write(out);
			out.close();
			}
			catch (Exception e) {
			e.printStackTrace();
			}
	}
	
	/**
	 * 导出客户详情
	 * @param customer_phone_info_json
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(params="method=exportCustomer")
	public void exportCustomer(String customer_phone_info_json,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		CustomerPhoneLinkDetailInfo customerPhoneLinkDetailInfo = new CustomerPhoneLinkDetailInfo();
		if(customer_phone_info_json != null)
		{
			customerPhoneLinkDetailInfo = JsonUtil.fromJson(customer_phone_info_json, CustomerPhoneLinkDetailInfo.class);
		}
		
		customerPhoneLinkDetailInfo.setPage(0);
		customerPhoneLinkDetailInfo.setRows(10000);
		
		List<CustomerPhoneLinkDetailInfo> customerPhoneLinkDetailInfoList = customerPhoneLinkService.getCustomerPhoneLinkDetailInfoByCondition(customerPhoneLinkDetailInfo);
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/x-download");

		String filedisplay = "客户详情.xls";
		filedisplay = URLEncoder.encode(filedisplay, "UTF-8");
		response.addHeader("Content-Disposition", "attachment;filename="+ filedisplay);
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("客户详情");
		
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth((short) 20);
		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		HSSFDataFormat format = workbook.createDataFormat();
		style.setDataFormat(format.getFormat("@"));
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 11);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		style.setFont(font);
		// 生成并设置另一个样式
		HSSFCellStyle style2 = workbook.createCellStyle();
		HSSFDataFormat format2 = workbook.createDataFormat();
		style2.setDataFormat(format2.getFormat("@"));
		style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成另一个字体
		HSSFFont font2 = workbook.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		style2.setFont(font2);
		// 生成并设置另一个样式
		HSSFCellStyle style3 = workbook.createCellStyle();
		HSSFDataFormat format3 = workbook.createDataFormat();
		style3.setDataFormat(format3.getFormat("@"));
		style3.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
		style3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style3.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style3.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style3.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style3.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style3.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style3.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成另一个字体
		HSSFFont font3 = workbook.createFont();
		font3.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		style3.setFont(font3);
		// 声明一个画图的顶级管理器
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		// 定义注释的大小和位置,详见文档
		HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0,
				0, 0, 0, (short) 4, 2, (short) 6, 5));
		// 设置注释内容
		comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
		// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
		comment.setAuthor("Dreamaker");
		
		
		//标题
		HSSFRow row = sheet.createRow(0);
		
		String titles[] = new String[21];
		{
			titles[0] = "客户姓名";
			titles[1] = "性别";
			titles[2] = "年龄";
			titles[3] = "地区/国籍";
			titles[4] = "联系电话";
			titles[5] = "QQ号码";
			titles[6] = "客户地址";
			titles[7] = "邮政编码";
			titles[8] = "微信号";
			titles[9] = "微博地址";
			titles[10] = "客户呢称";
			titles[11] = "淘宝账号";
			titles[12] = "电子邮件";
			titles[13] = "京东账号";
			titles[14] = "客户类别";
			titles[15] = "专属客服";
			titles[16] = "用户情况";
			titles[17] = "IMEI";
			titles[18] = "手机型号";
			titles[19] = "手机版本";
			titles[20] = "购买时间";
		}
		
		for(int i = 0 ; i< titles.length ; i++){
			HSSFCell cell = row.createCell(i);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(new HSSFRichTextString(titles[i]));
			cell.setCellStyle(style);
		}
		
		String preCustomerId = customerPhoneLinkDetailInfoList.get(0).getCustomerId();
		
		for (int i = 0; i < customerPhoneLinkDetailInfoList.size(); i++) {
			HSSFRow datarow = sheet.createRow(i + 1);
			String curCustomerId = customerPhoneLinkDetailInfoList.get(i).getCustomerId();
			if(i>0)
			{
				if(preCustomerId.equals(curCustomerId))
				{
					sheet.addMergedRegion(new Region(i, (short) 0, i+1, (short)0));
					sheet.addMergedRegion(new Region(i, (short) 1, i+1, (short)1));
					sheet.addMergedRegion(new Region(i, (short) 2, i+1, (short)2));
					sheet.addMergedRegion(new Region(i, (short) 3, i+1, (short)3));
					sheet.addMergedRegion(new Region(i, (short) 4, i+1, (short)4));
					sheet.addMergedRegion(new Region(i, (short) 5, i+1, (short)5));
					sheet.addMergedRegion(new Region(i, (short) 6, i+1, (short)6));
					sheet.addMergedRegion(new Region(i, (short) 7, i+1, (short)7));
					sheet.addMergedRegion(new Region(i, (short) 8, i+1, (short)8));
					sheet.addMergedRegion(new Region(i, (short) 9, i+1, (short)9));
					sheet.addMergedRegion(new Region(i, (short) 10, i+1, (short)10));
					sheet.addMergedRegion(new Region(i, (short) 11, i+1, (short)11));
					sheet.addMergedRegion(new Region(i, (short) 12, i+1, (short)12));
					sheet.addMergedRegion(new Region(i, (short) 13, i+1, (short)13));
					sheet.addMergedRegion(new Region(i, (short) 14, i+1, (short)14));
					sheet.addMergedRegion(new Region(i, (short) 15, i+1, (short)15));
					sheet.addMergedRegion(new Region(i, (short) 16, i+1, (short)16));
				}
			}
			
			preCustomerId = curCustomerId;
			
			CustomerPhoneLinkDetailInfo customerPhoneLinkDetailInfo1 = customerPhoneLinkDetailInfoList.get(i);
			HSSFCell name = datarow.createCell(0);
			name.setCellType(HSSFCell.CELL_TYPE_STRING);
			name.setCellValue(new HSSFRichTextString(customerPhoneLinkDetailInfo1.getName()));
			name.setCellStyle(style2);
			
			HSSFCell sex = datarow.createCell(1);
			sex.setCellType(HSSFCell.CELL_TYPE_STRING);
			sex.setCellValue(new HSSFRichTextString(customerPhoneLinkDetailInfo1.getSex()));
			sex.setCellStyle(style2);
			
			HSSFCell age = datarow.createCell(2);
			age.setCellType(HSSFCell.CELL_TYPE_STRING);
			age.setCellValue(new HSSFRichTextString(customerPhoneLinkDetailInfo1.getAge()));
			age.setCellStyle(style2);
			
			HSSFCell area = datarow.createCell(3);
			area.setCellType(HSSFCell.CELL_TYPE_STRING);
			area.setCellValue(new HSSFRichTextString(customerPhoneLinkDetailInfo1.getArea()));
			area.setCellStyle(style2);

			HSSFCell tel = datarow.createCell(4);
			tel.setCellType(HSSFCell.CELL_TYPE_STRING);
			tel.setCellValue(new HSSFRichTextString(customerPhoneLinkDetailInfo1.getTel().equals("")?"":customerPhoneLinkDetailInfo1.getTel().substring(0, customerPhoneLinkDetailInfo1.getTel().length()-1)));
			tel.setCellStyle(style2);
			
			HSSFCell qq = datarow.createCell(5);
			qq.setCellType(HSSFCell.CELL_TYPE_STRING);
			qq.setCellValue(new HSSFRichTextString(customerPhoneLinkDetailInfo1.getQq()));
			qq.setCellStyle(style2);
			
			HSSFCell address = datarow.createCell(6);
			address.setCellType(HSSFCell.CELL_TYPE_STRING);
			address.setCellValue(new HSSFRichTextString(customerPhoneLinkDetailInfo1.getAddress()));
			address.setCellStyle(style2);
			
			HSSFCell postCode = datarow.createCell(7);
			postCode.setCellType(HSSFCell.CELL_TYPE_STRING);
			postCode.setCellValue(new HSSFRichTextString(customerPhoneLinkDetailInfo1.getPostalCode()));
			postCode.setCellStyle(style2);
			
			HSSFCell weChat = datarow.createCell(8);
			weChat.setCellType(HSSFCell.CELL_TYPE_STRING);
			weChat.setCellValue(new HSSFRichTextString(customerPhoneLinkDetailInfo1.getWechat()));
			weChat.setCellStyle(style2);
			
			HSSFCell weibo = datarow.createCell(9);
			weibo.setCellType(HSSFCell.CELL_TYPE_STRING);
			weibo.setCellValue(new HSSFRichTextString(customerPhoneLinkDetailInfo1.getWeibo()));
			weibo.setCellStyle(style2);
			
			HSSFCell nickname = datarow.createCell(10);
			nickname.setCellType(HSSFCell.CELL_TYPE_STRING);
			nickname.setCellValue(new HSSFRichTextString(customerPhoneLinkDetailInfo1.getNickname()));
			nickname.setCellStyle(style2);
			
			HSSFCell taobao = datarow.createCell(11);
			taobao.setCellType(HSSFCell.CELL_TYPE_STRING);
			taobao.setCellValue(new HSSFRichTextString(customerPhoneLinkDetailInfo1.getTaobao()));
			taobao.setCellStyle(style2);
			
			HSSFCell email = datarow.createCell(12);
			email.setCellType(HSSFCell.CELL_TYPE_STRING);
			email.setCellValue(new HSSFRichTextString(customerPhoneLinkDetailInfo1.getEmail()));
			email.setCellStyle(style2);
			
			HSSFCell jd = datarow.createCell(13);
			jd.setCellType(HSSFCell.CELL_TYPE_STRING);
			jd.setCellValue(new HSSFRichTextString(customerPhoneLinkDetailInfo1.getJd()));
			jd.setCellStyle(style2);
			
			HSSFCell type = datarow.createCell(14);
			type.setCellType(HSSFCell.CELL_TYPE_STRING);
			type.setCellValue(new HSSFRichTextString(customerPhoneLinkDetailInfo1.getType()));
			type.setCellStyle(style2);
			
			HSSFCell agent = datarow.createCell(15);
			agent.setCellType(HSSFCell.CELL_TYPE_STRING);
			agent.setCellValue(new HSSFRichTextString(customerPhoneLinkDetailInfo1.getAgent()));
			agent.setCellStyle(style2);
			
			HSSFCell detail = datarow.createCell(16);
			detail.setCellType(HSSFCell.CELL_TYPE_STRING);
			detail.setCellValue(new HSSFRichTextString(customerPhoneLinkDetailInfo1.getDetail()));
			detail.setCellStyle(style2);
			
			HSSFCell imei = datarow.createCell(17);
			imei.setCellType(HSSFCell.CELL_TYPE_STRING);
			imei.setCellValue(new HSSFRichTextString(customerPhoneLinkDetailInfo1.getImei()));
			imei.setCellStyle(style3);
			
			HSSFCell model = datarow.createCell(18);
			model.setCellType(HSSFCell.CELL_TYPE_STRING);
			model.setCellValue(new HSSFRichTextString(customerPhoneLinkDetailInfo1.getModel()));
			model.setCellStyle(style3);
			
			HSSFCell version = datarow.createCell(19);
			version.setCellType(HSSFCell.CELL_TYPE_STRING);
			version.setCellValue(new HSSFRichTextString(customerPhoneLinkDetailInfo1.getVersion()));
			version.setCellStyle(style3);
			
			HSSFCell buyTime = datarow.createCell(20);
			buyTime.setCellType(HSSFCell.CELL_TYPE_STRING);
			buyTime.setCellValue(new HSSFRichTextString(customerPhoneLinkDetailInfo1.getBuyTime()));
			buyTime.setCellStyle(style3);
			
		}
		
		try {
			OutputStream out = response.getOutputStream();
			workbook.write(out);
			out.close();
			}
			catch (Exception e) {
			e.printStackTrace();
			}
	}
	
	/**
	 * 导出模板
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(params="method=exportModel")
	public void exportModel(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		try {
			
			String path = request.getSession().getServletContext().getRealPath("model");
			
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(path+"\\model.xls"));
			
			HSSFWorkbook workbook = new HSSFWorkbook(fs);
			
            String filedisplay = "客户详情.xls";
    		filedisplay = URLEncoder.encode(filedisplay, "UTF-8");
    		response.addHeader("Content-Disposition", "attachment;filename="+ filedisplay);
    		response.setContentType("application/x-download");
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            workbook.write(toClient);
            toClient.flush();
            toClient.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
