package com.dianfeng.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dianfeng.dao.KnowledgeBaseDao;
import com.dianfeng.entity.KnowledgeBase;
import com.dianfeng.service.KnowledgeBaseService;
@Service("KnowledgeBaseService")
public class KnowledgeBaseServiceImpl implements KnowledgeBaseService {
	@Autowired
	KnowledgeBaseDao dao;
	public List<KnowledgeBase> getNods(Integer parentId) {
		return dao.getNods(parentId);
	}
	public List<KnowledgeBase> selectKnowledgeBaseById(Integer id) {
		return dao.selectKnowledgeBaseById(id);
	}
	public List<KnowledgeBase> selectKnowledgeBaseByKeyWordChi(String keyWordChi) {
		return dao.selectKnowledgeBaseByKeyWordChi(keyWordChi);
	}
	public Integer delKnowledgebaseById(Integer id) {
		return dao.delKnowledgebaseById(id);
	}
	public KnowledgeBase findKnowledgeBaseById(Integer id) {
		return dao.findKnowledgeBaseById(id);
	}
	public Integer addKnowledgeBase(KnowledgeBase knowledgeBase) {
		return dao.addKnowledgeBase(knowledgeBase);
	}
	public Integer updateKnowledgeBase(KnowledgeBase knowledgeBase) {
		return dao.updateKnowledgeBase(knowledgeBase);
	}

}
