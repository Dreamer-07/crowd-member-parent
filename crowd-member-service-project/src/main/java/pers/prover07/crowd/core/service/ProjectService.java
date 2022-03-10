package pers.prover07.crowd.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.prover07.crowd.po.ProjectPo;
import pers.prover07.crowd.vo.ProjectVO;

public interface ProjectService extends IService<ProjectPo> {
    /**
     * 创建众筹工程信息
     * @param projectVO
     * @return
     */
    Integer createProject(ProjectVO projectVO);

    /**
     * 保存工程信息
     * @param projectVO
     * @param memberId
     */
    void save(ProjectVO projectVO, String memberId);
}
