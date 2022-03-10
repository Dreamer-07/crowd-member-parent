package pers.prover07.crowd.mysql.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import pers.prover07.crowd.po.MemberPo;

/**
 * @author by Prover07
 * @classname MemberMapper
 * @description TODO
 * @date 2022/2/28 9:06
 */
@Mapper
public interface MemberMapper extends BaseMapper<MemberPo> {
}
