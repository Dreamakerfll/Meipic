package com.dianfeng.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dianfeng.entity.QuestionType;
import com.dianfeng.service.QuestionTypeService;
import com.dianfeng.utils.JsonUtil;

@Component
@RequestMapping("/question_type")
public class QuestionTypeAction
{

	@Resource
	private QuestionTypeService questionTypeService;
	
	/**
	 * 获取所有问题大类
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(params="method=findAllQuestionMold")
	public void getAllQuestionMold(String type,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		List<QuestionType> questionTypeList = questionTypeService.getAllQuestionMold();
		if("0".equals(type))
		{
			QuestionType questionType = new QuestionType();
			questionType.setMold("");
			questionType.setMoldDisplay("　");
			questionTypeList.add(0,questionType);
		}
		String returnJson =JsonUtil.toJson(questionTypeList);
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
	 * 根据问题大类获取问题类别
	 * @param questionMold
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(params="method=findQuestionTypeByQuestionMold")
	public void getQuestionTypeByQuestionMold(String type,String questionMold,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		List<QuestionType> questionTypeList = questionTypeService.getQuestionTypeByQuestionMold(questionMold);
		if("0".equals(type))
		{
			QuestionType questionType = new QuestionType();
			questionType.setType("");
			questionType.setTypeDisplay("　");
			questionTypeList.add(0,questionType);
		}
		String returnJson =JsonUtil.toJson(questionTypeList);
		if(returnJson==null){
			returnJson="[]";
		}
		response.setCharacterEncoding("utf-8");
	    PrintWriter out = response.getWriter();
		out.print(returnJson);
		out.flush();
		out.close();
	}
}
