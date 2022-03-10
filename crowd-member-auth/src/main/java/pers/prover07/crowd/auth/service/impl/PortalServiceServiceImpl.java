package pers.prover07.crowd.auth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.prover07.crowd.auth.service.PortalService;
import pers.prover07.crowd.mysql.service.ProjectService;
import pers.prover07.crowd.mysql.service.ProjectTypeService;
import pers.prover07.crowd.mysql.service.TypeService;
import pers.prover07.crowd.po.TypePo;
import pers.prover07.crowd.vo.PortalProjectVo;
import pers.prover07.crowd.vo.PortalTypeVo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author by Prover07
 * @classname PortalServiceServiceimpl
 * @description TODO
 * @date 2022/3/10 12:46
 */
@Service
public class PortalServiceServiceImpl implements PortalService {

    @Autowired
    private TypeService typeService;

    @Autowired
    private ProjectTypeService projectTypeService;

    @Autowired
    private ProjectService projectService;

    /**
     * 获取首页分离数据
     *
     * @return
     */
    @Override

    public List<PortalTypeVo> getPortalTypeVoList() {
        // 1. 查询出所有分类
        List<TypePo> typePos = typeService.list();
        return typeService.list().stream().map(typePo -> {
            // 2. 查询中间表，获取关联的工程 id
            List<Integer> projectIds = projectTypeService.listByTypeId(typePo.getId());
            List<PortalProjectVo> portalProjectVoList;
            if (projectIds.size() > 0) {
                // 3. 根据工程 id 获取工程的简略信息
                portalProjectVoList = projectService.portalListByIds(projectIds);
            } else {
                portalProjectVoList = new ArrayList<>();
            }
            return new PortalTypeVo(typePo, portalProjectVoList);
        }).collect(Collectors.toList());
    }

}
