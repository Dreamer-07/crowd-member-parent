package pers.prover07.crowd.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import pers.prover07.crowd.core.mapper.ProjectTypeMapper;
import pers.prover07.crowd.core.service.ProjectTypeService;
import pers.prover07.crowd.po.ProjectTypePo;

import java.util.List;
import java.util.stream.Collectors;


/**
 * <p>
 *  服务实现类
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
}
