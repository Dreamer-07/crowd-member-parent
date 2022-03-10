package pers.prover07.crowd.mysql.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import pers.prover07.crowd.po.ProjectPo;
import pers.prover07.crowd.vo.PortalProjectVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author prover07
 * @since 2022-03-01
 */
public interface ProjectMapper extends BaseMapper<ProjectPo> {

    List<PortalProjectVo> getAllByIds(List<Integer> projectIds);

}
