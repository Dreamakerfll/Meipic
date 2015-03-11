package com.dianfeng.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dianfeng.entity.KnowledgeBase;

public interface KnowledgeBaseService {
	List<KnowledgeBase> getNods(@Param("parentId")Integer parentId);
	List<KnowledgeBase> selectKnowledgeBaseById(@Param("id")Integer id);
	List<KnowledgeBase> selectKnowledgeBaseByKeyWordChi(@Param("keyWordChi")String keyWordChi);
	Integer delKnowledgebaseById(@Param("id")Integer id);
	KnowledgeBase findKnowledgeBaseById(@Param("id")Integer id);
	Integer addKnowledgeBase(KnowledgeBase knowledgeBase);
	Integer updateKnowledgeBase(KnowledgeBase knowledgeBase);
}
