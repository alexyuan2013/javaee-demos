package cn.ssm.dao;

import java.util.List;
import cn.ssm.entity.CaoZuoRiZhi;

public interface CaoZuoRiZhiMapper {
	Integer insertSelective(CaoZuoRiZhi record);

	List<CaoZuoRiZhi> selectAllBySelective(CaoZuoRiZhi record);

}
