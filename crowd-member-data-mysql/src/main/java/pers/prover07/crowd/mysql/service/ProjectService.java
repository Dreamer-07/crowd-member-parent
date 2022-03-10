package pers.prover07.crowd.mysql.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.prover07.crowd.po.ProjectPo;
import pers.prover07.crowd.vo.PortalProjectVo;
import pers.prover07.crowd.vo.ProjectVO;

import java.util.List;

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

    /**
     * 根绝 projectId 获取首页列表需要的 PortalProjectVo
     * @param projectIds
     * @return
     */
    List<PortalProjectVo> portalListByIds(List<Integer> projectIds);
}
