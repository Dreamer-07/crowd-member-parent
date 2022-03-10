package pers.prover07.crowd.mysql.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.prover07.crowd.po.ProjectTagPo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author prover07
 * @since 2022-03-01
 */
public interface ProjectTagService extends IService<ProjectTagPo> {

    /**
     * 保存 project 和 tag 之间的关系
     * @param projectId
     * @param tagIdList
     */
    void saveProjectTagRelation(Integer projectId, List<Integer> tagIdList);
}
