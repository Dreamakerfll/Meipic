package com.dianfeng.action;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.dianfeng.entity.CustomerInfo;
import com.dianfeng.entity.CustomerPhoneLink;
import com.dianfeng.entity.PhoneInfo;
import com.dianfeng.service.CustomerInfoService;
import com.dianfeng.service.CustomerPhoneLinkService;
import com.dianfeng.service.PhoneInfoService;
import com.dianfeng.utils.Constant;
import com.dianfeng.utils.JsonReturn;
import com.dianfeng.utils.JsonUtil;

@Component
@RequestMapping("/import")
public class ImportAction
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
	private CustomerPhoneLinkService customerPhoneLinkService;

	public CustomerPhoneLinkService getCustomerPhoneLinkService()
	{
		return customerPhoneLinkService;
	}

	public void setCustomerPhoneLinkService(CustomerPhoneLinkService customerPhoneLinkService)
	{
		this.customerPhoneLinkService = customerPhoneLinkService;
	}

	@RequestMapping(params = "method=importCustomer")
	public void importCustomer(@RequestParam(value = "customer_info_file", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		JsonReturn jsonReturn = new JsonReturn();

		try
		{
			// 判断是否有选择文件
			if (file != null)
			{
				// 判断文件后缀是否正确
				String fileName = file.getOriginalFilename();
				if (fileName.endsWith("xls"))
				{
					// 验证excel文件内容的合法性
					if ("0".equals(checkExcel(file.getInputStream())))
					{
						// 创建对Excel工作簿文件的引用
						HSSFWorkbook wookbook = new HSSFWorkbook(file.getInputStream());

						// 在Excel文档中，第一张工作表的缺省索引是0，

						// 其语句为：HSSFSheet sheet = workbook.getSheetAt(0);
						HSSFSheet sheet = wookbook.getSheet("客户详情");

						// 获取到Excel文件中的所有行数
						int rows = sheet.getPhysicalNumberOfRows();

						// 除去标题外，首行的客户数据
						String preCustomer = "";

						for (int i = 0; i < 17; i++)
						{
							if (sheet.getRow(1).getCell(i) != null)
							{
								preCustomer = preCustomer + sheet.getRow(1).getCell(i).getStringCellValue();
							}
						}

						CustomerInfo preCustomerInfo = null;

						// 遍历行
						for (int i = 1; i < rows; i++)
						{
							// 读取左上端单元格
							HSSFRow row = sheet.getRow(i);

							// 行不为空
							if (row != null)
							{
								// 获取到Excel文件中的所有的列
								int cells = row.getPhysicalNumberOfCells();

								List<String> value = new ArrayList<String>();

								// 遍历列
								for (int j = 0; j < 21; j++)
								{
									// 获取到列的值
									HSSFCell cell = row.getCell(j);

									if (cell != null)
									{
										value.add(cell.getStringCellValue());
									}
									else
									{
										value.add("");
									}
								}

								// 客户对象
								CustomerInfo customerInfo = new CustomerInfo();
								customerInfo.setName(value.get(0));
								customerInfo.setSex(value.get(1));
								customerInfo.setAge(value.get(2));
								customerInfo.setArea(value.get(3));
								customerInfo.setTel("".equals(value.get(4)) ? "" : value.get(4) + ",");
								customerInfo.setQq(value.get(5));
								customerInfo.setAddress(value.get(6));
								customerInfo.setPostalCode(value.get(7));
								customerInfo.setWechat(value.get(8));
								customerInfo.setWeibo(value.get(9));
								customerInfo.setNickname(value.get(10));
								customerInfo.setTaobao(value.get(11));
								customerInfo.setEmail(value.get(12));
								customerInfo.setJd(value.get(13));
								customerInfo.setType(value.get(14));
								customerInfo.setAgent(value.get(15));
								customerInfo.setDetail(value.get(16));

								// 手机对象
								PhoneInfo phoneInfo = new PhoneInfo();
								phoneInfo.setImei(value.get(17));
								phoneInfo.setModel(value.get(18));
								phoneInfo.setVersion(value.get(19));
								phoneInfo.setBuyTime(value.get(20));

								String curCustomer = "";

								for (int z = 0; z < 17; z++)
								{
									if (sheet.getRow(i).getCell(z) != null)
									{
										curCustomer = curCustomer + sheet.getRow(i).getCell(z).getStringCellValue();
									}
								}

								if (i == 1)
								{
									// 插入客户资料到数据库
									customerInfoService.insertCustomerInfo(customerInfo);

									// 如果手机信息存在，则增加手机信息
									if (!"".equals(value.get(17)) && !"".equals(value.get(18)) && !"".equals(value.get(19)))
									{
										// 插入手机资料到数据库
										phoneInfoService.insertOnePhoneInfo(phoneInfo);

										// 创建客户和手机信息的关联
										// 客户和手机信息关联List
										List<CustomerPhoneLink> customerPhoneLinkList = new ArrayList<CustomerPhoneLink>();
										CustomerPhoneLink customerPhoneLink = new CustomerPhoneLink();
										customerPhoneLink.setCustomerId(customerInfo.getId());
										customerPhoneLink.setPhoneId(phoneInfo.getId());
										customerPhoneLinkList.add(customerPhoneLink);

										// 插入客户手机关联信息到数据库
										customerPhoneLinkService.insertCustomerPhoneLink(customerPhoneLinkList);
									}

									preCustomerInfo = customerInfo;
								}

								if (i > 1)
								{
									if (preCustomer.equals(curCustomer))
									{
										// 如果手机信息存在，则增加手机信息
										if (!"".equals(value.get(17)) && !"".equals(value.get(18)) && !"".equals(value.get(19)))
										{
											// 插入手机资料到数据库
											phoneInfoService.insertOnePhoneInfo(phoneInfo);

											// 插入手机资料到数据库
											phoneInfoService.insertOnePhoneInfo(phoneInfo);

											// 创建客户和手机信息的关联
											// 客户和手机信息关联List
											List<CustomerPhoneLink> customerPhoneLinkList = new ArrayList<CustomerPhoneLink>();
											CustomerPhoneLink customerPhoneLink = new CustomerPhoneLink();
											customerPhoneLink.setCustomerId(preCustomerInfo.getId());
											customerPhoneLink.setPhoneId(phoneInfo.getId());
											customerPhoneLinkList.add(customerPhoneLink);

											// 插入客户手机关联信息到数据库
											customerPhoneLinkService.insertCustomerPhoneLink(customerPhoneLinkList);
										}

									}
									else
									{
										// 插入客户资料到数据库
										customerInfoService.insertCustomerInfo(customerInfo);

										// 如果手机信息存在，则增加手机信息
										if (!"".equals(value.get(17)) && !"".equals(value.get(18)) && !"".equals(value.get(19)))
										{

											// 插入手机资料到数据库
											phoneInfoService.insertOnePhoneInfo(phoneInfo);

											// 创建客户和手机信息的关联
											// 客户和手机信息关联List
											List<CustomerPhoneLink> customerPhoneLinkList = new ArrayList<CustomerPhoneLink>();
											CustomerPhoneLink customerPhoneLink = new CustomerPhoneLink();
											customerPhoneLink.setCustomerId(customerInfo.getId());
											customerPhoneLink.setPhoneId(phoneInfo.getId());
											customerPhoneLinkList.add(customerPhoneLink);

											// 插入客户手机关联信息到数据库
											customerPhoneLinkService.insertCustomerPhoneLink(customerPhoneLinkList);
										}

										preCustomerInfo = customerInfo;
									}

								}

								preCustomer = curCustomer;
							}
						}

						jsonReturn.setStatus(Constant.STATU_SUCCESS);
					}
					else
					{
						jsonReturn.setStatus(Constant.STATU_ERROR);
						jsonReturn.setSign(checkExcel(file.getInputStream()));
					}

				}
				else
				{
					jsonReturn.setStatus(Constant.STATU_ERROR);
					jsonReturn.setSign("文件类型错误，请选择正确的excel文件");
				}

			}
			else
			{
				jsonReturn.setStatus(Constant.STATU_ERROR);
				jsonReturn.setSign("请选择到导入的excel文件");
			}
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		String returnJson = JsonUtil.toJson(jsonReturn);
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.print(returnJson);
		out.flush();
		out.close();

	}

	/**
	 * 验证excel的合法性
	 * 
	 * @param inputStream
	 *            excel流信息
	 * @return
	 */
	private String checkExcel(InputStream inputStream)
	{

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
		try
		{
			// 创建对Excel工作簿文件的引用
			HSSFWorkbook wookbook = new HSSFWorkbook(inputStream);

			// 在Excel文档中，第一张工作表的缺省索引是0，

			// 其语句为：HSSFSheet sheet = workbook.getSheetAt(0);
			HSSFSheet sheet = wookbook.getSheet("客户详情");

			// 获取到Excel文件中的所有行数
			int rows = sheet.getPhysicalNumberOfRows();

			// 遍历行
			for (int i = 0; i < rows; i++)
			{
				// 读取左上端单元格
				HSSFRow row = sheet.getRow(i);

				// 行不为空
				if (row != null)
				{
					// 获取到Excel文件中的所有的列
					int cells = row.getPhysicalNumberOfCells();

					// 读取标题，判断excel模板是否正确
					if (i == 0)
					{
						// 遍历列
						for (int j = 0; j < 21; j++)
						{
							// 获取到列的值
							HSSFCell cell = row.getCell(j);

							if (!titles[j].equals(cell.getStringCellValue()))
							{
								return "模板格式不正确，请下载正确的模板";
							}
						}
					}
					else
					{
						// 判断客户信息的必输入项目
						if (row.getCell(4) == null && row.getCell(5) == null && row.getCell(8) == null && row.getCell(9) == null && row.getCell(11) == null && row.getCell(12) == null && row.getCell(13) == null)
						{
							return "第" + (i + 1) + "行，客户的联系电话、QQ号码、微信号、微博地址、淘宝账号、电子邮件、京东账号不能同时为空！";
						}

						// 判断手机信息的必输项目
						if (row.getCell(17) != null || row.getCell(18) != null || row.getCell(19) != null || row.getCell(20) != null)
						{
							if (row.getCell(17) == null || row.getCell(18) == null || row.getCell(19) == null)
							{
								return "第" + (i + 1) + "行，手机的IMEI号、手机型号、手机版本为必输项目！";
							}
						}

						// 获取到年龄列的值
						HSSFCell ageCell = row.getCell(2);
						if (ageCell != null)
						{
							String ageCheck = "[0-9]*";
							Pattern ageRegex = Pattern.compile(ageCheck);
							Matcher matcherAge = ageRegex.matcher(ageCell.getStringCellValue());
							if (!"".equals(ageCell.getStringCellValue()) && !matcherAge.matches())
							{
								return "第" + (i + 1) + "行，年龄只允许由数字组成";
							}
						}

						// 获取到联系电话列的值
						HSSFCell telCell = row.getCell(4);
						if (telCell != null)
						{
							String telCheck = "[0-9]*+([,][0-9]*)*";
							Pattern telRegex = Pattern.compile(telCheck);
							Matcher matcherTel = telRegex.matcher(telCell.getStringCellValue());

							if (!"".equals(telCell.getStringCellValue()) && !matcherTel.matches())
							{
								return "第" + (i + 1) + "行，联系电话只允许由数字和英文逗号组成";
							}
						}

						// 获取到QQ列的值
						HSSFCell qqCell = row.getCell(5);
						if (qqCell != null)
						{
							String qqCheck = "[0-9]*";
							Pattern qqRegex = Pattern.compile(qqCheck);
							Matcher matcherQq = qqRegex.matcher(qqCell.getStringCellValue());

							if (!"".equals(qqCell.getStringCellValue()) && !matcherQq.matches())
							{
								return "第" + (i + 1) + "行，QQ号只允许由数字组成";
							}
						}

						// 获取到邮政编码列的值
						HSSFCell postCodeCell = row.getCell(7);
						if (postCodeCell != null)
						{
							String postCodeCheck = "[1-9]\\d{5}(?!\\d)";
							Pattern postCodeRegex = Pattern.compile(postCodeCheck);
							Matcher matcherPostCode = postCodeRegex.matcher(postCodeCell.getStringCellValue());

							if (!"".equals(postCodeCell.getStringCellValue()) && !matcherPostCode.matches())
							{
								return "第" + (i + 1) + "行，邮政编码必须由非零开头的6位数字组成";
							}
						}

						// 获取到电子邮件列的值
						HSSFCell emailCell = row.getCell(12);
						if (emailCell != null)
						{
							String emailCheck = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
							Pattern emailRegex = Pattern.compile(emailCheck);
							Matcher matcherEmail = emailRegex.matcher(emailCell.getStringCellValue());

							if (!"".equals(emailCell.getStringCellValue()) && !matcherEmail.matches())
							{
								return "第" + (i + 1) + "行，电子邮件不合法";
							}
						}

						// 获取到购买日期列的值
						HSSFCell buyTimeCell = row.getCell(20);
						if (buyTimeCell != null)
						{
							String buyTimeCheck1 = "\\d{4}-\\d{2}-\\d{2}";
							String buyTimeCheck2 = "^((\\d{2}(([02468][048])|([13579][26]))" + "[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|" + "(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?"
									+ "((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?(" + "(((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?"
									+ "((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";

							if ((buyTimeCell.getStringCellValue() != null) && !"".equals(buyTimeCell.getStringCellValue()))
							{
								Pattern pattern = Pattern.compile(buyTimeCheck1);
								Matcher match = pattern.matcher(buyTimeCell.getStringCellValue());
								if (match.matches())
								{
									pattern = Pattern.compile(buyTimeCheck2);
									match = pattern.matcher(buyTimeCell.getStringCellValue());
									if (!match.matches())
									{
										return "第" + (i + 1) + "行，购买日期格式不正确";
									}
								}
								else
								{
									return "第" + (i + 1) + "行，购买日期格式不正确";
								}
							}
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}

		return "0";
	}

}
