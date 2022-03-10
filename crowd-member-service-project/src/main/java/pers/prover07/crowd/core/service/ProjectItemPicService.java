package pers.prover07.crowd.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.prover07.crowd.po.ProjectItemPicPo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author prover07
 * @since 2022-03-01
 */
public interface ProjectItemPicService extends IService<ProjectItemPicPo> {

    /**
     * 保存 project 和 detailPic 之间的关系
     * @param projectId
     * @param detailPicturePathList
     */
    void saveProjectItemPicRelation(Integer projectId, List<String> detailPicturePathList);
}
