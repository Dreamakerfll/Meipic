package com.dianfeng.entity;


/**
 * 知识库
 */
public class KnowledgeBase {
	private Integer id;
	private Integer parentId;
	private String myType;		// 类型
	private String parentType;	// 所属类型
	private String title;	   	// 标题
	private String keyWordChi; 	// 关键字中文
//	private String keyWordEng; 	// 关键字英文
	private String addTime; 	// 发布日期
	private String content; 	// 文本

	public KnowledgeBase(){
		
	}
	public KnowledgeBase(Integer id,Integer parentId,String myType,String parentType,String title,
			String keyWordChi,String addTime,String content){
		this.id = id;
		this.parentId = parentId;
		this.myType = myType;
		this.parentType = parentType;
		this.title = title;
		this.keyWordChi = keyWordChi;
		this.addTime = addTime;
		this.content = content;
	}
	public KnowledgeBase(Integer parentId,String myType,String parentType,String title,
			String keyWordChi,String addTime,String content){
		this.parentId = parentId;
		this.myType = myType;
		this.parentType = parentType;
		this.title = title;
		this.keyWordChi = keyWordChi;
		this.addTime = addTime;
		this.content = content;
	}
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}


	public String getKeyWordChi() {
		return keyWordChi;
	}

	public void setKeyWordChi(String keyWordChi) {
		this.keyWordChi = keyWordChi;
	}

//	public String getKeyWordEng() {
//		return keyWordEng;
//	}
//
//	public void setKeyWordEng(String keyWordEng) {
//		this.keyWordEng = keyWordEng;
//	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}


	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	public String getMyType() {
		return myType;
	}
	public void setMyType(String myType) {
		this.myType = myType;
	}
	
	public String getParentType() {
		return parentType;
	}

	public void setParentType(String parentType) {
		this.parentType = parentType;
	}
}
