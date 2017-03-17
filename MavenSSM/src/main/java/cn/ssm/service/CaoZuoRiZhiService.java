package cn.ssm.service;

import java.util.List;
import cn.ssm.entity.CaoZuoRiZhi;

public interface CaoZuoRiZhiService {
	Integer insertSelective(CaoZuoRiZhi record);

	List<CaoZuoRiZhi> selectAllBySelective(CaoZuoRiZhi record);
}
