package pers.prover07.crowd.mysql.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import pers.prover07.crowd.mysql.mapper.ProjectItemPicMapper;
import pers.prover07.crowd.mysql.service.ProjectItemPicService;
import pers.prover07.crowd.po.ProjectItemPicPo;

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
public class ProjectItemPicServiceImpl extends ServiceImpl<ProjectItemPicMapper, ProjectItemPicPo> implements ProjectItemPicService {

    @Override
    public void saveProjectItemPicRelation(Integer projectId, List<String> detailPicturePathList) {
        List<ProjectItemPicPo> projectItemPicPos = detailPicturePathList.stream()
                .map(detailPicturePath -> new ProjectItemPicPo(null, projectId, detailPicturePath))
                .collect(Collectors.toList());
        this.saveBatch(projectItemPicPos);
    }
}
