package cn.ssm.service.imple;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ssm.dao.CaoZuoRiZhiMapper;
import cn.ssm.entity.CaoZuoRiZhi;
import cn.ssm.service.CaoZuoRiZhiService;

@Service
public class CaoZuoRiZhiServiceImple implements CaoZuoRiZhiService{

	@Autowired
	private CaoZuoRiZhiMapper dao;
	
	public Integer insertSelective(CaoZuoRiZhi record) {
		// TODO Auto-generated method stub
		return dao.insertSelective(record);
	}

	public List<CaoZuoRiZhi> selectAllBySelective(CaoZuoRiZhi record) {
		// TODO Auto-generated method stub
		return dao.selectAllBySelective(record);
	}

}
