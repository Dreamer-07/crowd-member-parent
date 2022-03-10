package pers.prover07.crowd.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.prover07.crowd.po.ProjectTypePo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author prover07
 * @since 2022-03-01
 */
public interface ProjectTypeService extends IService<ProjectTypePo> {

    /**
     * 保存 project 和 type 的关系信息
     * @param projectId
     * @param typeIdList
     */
    void saveProjectTypeRelation(Integer projectId, List<Integer> typeIdList);
}
