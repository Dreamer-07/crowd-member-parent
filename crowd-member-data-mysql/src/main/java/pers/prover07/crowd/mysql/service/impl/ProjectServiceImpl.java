package pers.prover07.crowd.mysql.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pers.prover07.crowd.mysql.mapper.ProjectMapper;
import pers.prover07.crowd.mysql.service.*;
import pers.prover07.crowd.po.ProjectPo;
import pers.prover07.crowd.vo.PortalProjectVo;
import pers.prover07.crowd.vo.ProjectVO;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author prover07
 * @since 2022-03-01
 */
@Service
@AllArgsConstructor
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, ProjectPo> implements ProjectService {

    private final MemberLaunchInfoService memberLaunchInfoService;

    private final ProjectItemPicService projectItemPicService;

    private final ProjectTagService projectTagService;

    private final ProjectTypeService projectTypeService;

    private final MemberConfirmInfoService memberConfirmInfoService;

    private final PayBackService payBackService;



    @Override
    public Integer createProject(ProjectVO projectVO) {
        ProjectPo projectPo = new ProjectPo();
        BeanUtils.copyProperties(projectVO, projectPo);
        projectPo.setProjectStatus(1);
        baseMapper.insert(projectPo);
        return projectPo.getId();
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    @Override
    public void save(ProjectVO projectVO, String memberId) {
        // 1. 保存 ProjectPo
        ProjectPo projectPo = new ProjectPo();
        BeanUtils.copyProperties(projectVO, projectPo);
        projectPo.setMemberid(memberId);
        projectPo.setCreatedate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        projectPo.setStatus(0);
        baseMapper.insert(projectPo);
        Integer projectId = projectPo.getId();

        // 2. 保存项目，分类的关联关系信息
        List<Integer> typeIdList = projectVO.getTypeIdList();
        projectTypeService.saveProjectTypeRelation(projectId, typeIdList);

        // 3. 保存项目，标签的关联关系
        projectTagService.saveProjectTagRelation(projectId, projectVO.getTagIdList());

        // 4. 保存项目中图片详情的信息
        projectItemPicService.saveProjectItemPicRelation(projectId, projectVO.getDetailPicturePathList());

        // 5. 保存项目发起人信息
        memberLaunchInfoService.save(projectVO.getMemberLauchInfoVO(), memberId);

        // 6. 保存项目回报信息
        payBackService.savePayBackList(projectVO.getPayBackVOList(), projectId);

        // 7. 保存项目确认信息
        memberConfirmInfoService.save(projectVO.getMemberConfirmInfoVO(), memberId);

    }

    @Override
    public List<PortalProjectVo> portalListByIds(List<Integer> projectIds) {
        return baseMapper.getAllByIds(projectIds);
    }
}
