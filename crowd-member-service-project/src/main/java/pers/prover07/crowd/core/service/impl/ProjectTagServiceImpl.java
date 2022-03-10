package pers.prover07.crowd.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import pers.prover07.crowd.core.mapper.ProjectTagMapper;
import pers.prover07.crowd.core.service.ProjectTagService;
import pers.prover07.crowd.po.ProjectTagPo;

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
public class ProjectTagServiceImpl extends ServiceImpl<ProjectTagMapper, ProjectTagPo> implements ProjectTagService {

    @Override
    public void saveProjectTagRelation(Integer projectId, List<Integer> tagIdList) {
        List<ProjectTagPo> projectTagPos = tagIdList.stream()
                .map(tagId -> new ProjectTagPo(null, projectId, tagId))
                .collect(Collectors.toList());
        this.saveBatch(projectTagPos);
    }
}
