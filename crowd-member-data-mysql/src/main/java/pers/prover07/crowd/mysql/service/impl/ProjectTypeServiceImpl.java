package pers.prover07.crowd.mysql.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import pers.prover07.crowd.mysql.mapper.ProjectTypeMapper;
import pers.prover07.crowd.mysql.service.ProjectTypeService;
import pers.prover07.crowd.po.ProjectTypePo;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author prover07
 * @since 2022-03-01
 */
@Service
public class ProjectTypeServiceImpl extends ServiceImpl<ProjectTypeMapper, ProjectTypePo> implements ProjectTypeService {

    @Override
    public void saveProjectTypeRelation(Integer projectId, List<Integer> typeIdList) {
        List<ProjectTypePo> typePoList = typeIdList.stream()
                .map(typeId -> new ProjectTypePo(null, projectId, typeId))
                .collect(Collectors.toList());
        this.saveBatch(typePoList);
    }

    @Override
    public List<Integer> listByTypeId(Integer typeId) {
        LambdaQueryWrapper<ProjectTypePo> lqw = Wrappers.lambdaQuery();
        lqw.eq(ProjectTypePo::getTypeid, typeId);
        lqw.select(ProjectTypePo::getProjectid);
        return this.list(lqw).stream().map(ProjectTypePo::getProjectid).collect(Collectors.toList());
    }
}
