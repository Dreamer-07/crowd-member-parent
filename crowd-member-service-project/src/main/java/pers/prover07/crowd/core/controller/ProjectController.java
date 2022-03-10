package pers.prover07.crowd.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import pers.prover.crowd.constant.CrowdAttrNameConstant;
import pers.prover.crowd.util.CrowdUtil;
import pers.prover.crowd.util.ResultEntity;
import pers.prover07.crowd.core.config.properties.OSSClientProperties;
import pers.prover07.crowd.core.service.ProjectService;
import pers.prover07.crowd.vo.MemberConfirmInfoVO;
import pers.prover07.crowd.vo.MemberLoginVo;
import pers.prover07.crowd.vo.PayBackVO;
import pers.prover07.crowd.vo.ProjectVO;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author by Prover07
 * @classname ProjectController
 * @description TODO
 * @date 2022/3/1 21:24
 */
@Controller
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private OSSClientProperties ossClientProperties;

    @Autowired
    private ProjectService projectService;

    /**
     * 创建众筹项目(project)
     * @param projectVO             project info
     * @param headerPicture         头图
     * @param detailPictureList     详细图片
     * @param model                 存储 request data
     * @return
     */
    @PostMapping("/create")
    public String createProject(ProjectVO projectVO,
                                MultipartFile headerPicture,
                                List<MultipartFile> detailPictureList,
                                HttpSession session,
                                Model model
    ) throws Exception {
        // 上传头图
        if (headerPicture.isEmpty() || detailPictureList.stream().anyMatch(MultipartFile::isEmpty)) {
            model.addAttribute(CrowdAttrNameConstant.EXCEPTION_INFO, "文件没有大小");
            return "start-step1";
        }
        // 上传文件到 OSS
        String headerPicUrl = "";
        List<String> detailPicUrls = new ArrayList<>();
        try {
            headerPicUrl = uploadMultipartFile2OSS(Objects.requireNonNull(headerPicture.getOriginalFilename()), headerPicture.getInputStream());
            for (MultipartFile detailPic : detailPictureList) {
                String detailPicUrl = uploadMultipartFile2OSS(Objects.requireNonNull(detailPic.getOriginalFilename()), detailPic.getInputStream());
                detailPicUrls.add(detailPicUrl);
            }
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute(CrowdAttrNameConstant.EXCEPTION_INFO, "头图上传失败");
            return "start-step1";
        }

        projectVO.setHeaderPicturePath(headerPicUrl);
        projectVO.setDetailPicturePathList(detailPicUrls);

        // 创建 ProjectPo 对象保存到 Session 中
        session.setAttribute("project:tempProject",projectVO);

        return "start-step2";
    }

    @ResponseBody
    @PostMapping("/add/pay_back/img")
    public ResultEntity<String> uploadPayBackImg(MultipartFile returnPicture) throws IOException {
        if (returnPicture.isEmpty()) {
            return ResultEntity.fail("文件小大为空");
        }
        String fileUrl = uploadMultipartFile2OSS(Objects.requireNonNull(returnPicture.getOriginalFilename()), returnPicture.getInputStream());
        return ResultEntity.success(fileUrl);
    }

    /**
     * 添加回报信息
     * @return
     */
    @PostMapping("/add/pay_back")
    @ResponseBody
    public ResultEntity<PayBackVO> addPayBackInfo(PayBackVO payBackVO, HttpSession session) {
        // 从 session 中获取 ProjectVo
        ProjectVO projectVo = (ProjectVO) session.getAttribute("project:tempProject");
        if (projectVo == null) {
            return ResultEntity.fail("获取工程信息失败，请重新创建");
        }
        List<PayBackVO> payBackVOList = projectVo.getPayBackVOList();
        // 如果集合为 null 就需要新建集合并保存到对象中
        if (CollectionUtils.isEmpty(payBackVOList)){
            payBackVOList = new ArrayList<>();
            projectVo.setPayBackVOList(payBackVOList);
        }
        payBackVOList.add(payBackVO);
        // 重新保存到 session 中
        session.setAttribute("project:tempProject", projectVo);
        return ResultEntity.success(payBackVO);
    }

    /**
     * 添加确认信息
     * @return
     */
    @PostMapping("/add/confirm")
    public String addConfirmInfo(MemberConfirmInfoVO memberConfirmInfoVO, HttpSession session, Model model) {
        // 获取 session 中保存的工程信息
        ProjectVO projectVO = (ProjectVO) session.getAttribute("project:tempProject");
        // 判断数据是否存在
        if (memberConfirmInfoVO == null) {
            model.addAttribute(CrowdAttrNameConstant.EXCEPTION_INFO, "项目信息已过期，请重新输入");
            return "start-step1";
        }
        projectVO.setMemberConfirmInfoVO(memberConfirmInfoVO);
        // 获取当前用户信息和项目信息一同保存到数据库中
        MemberLoginVo memberLoginVo = (MemberLoginVo) session.getAttribute(CrowdAttrNameConstant.LOGIN_MEMBER);
        projectService.save(projectVO, memberLoginVo.getId());
        return "start-success";
    }

    /**
     * 上传 MultipartFile 文件到 OSS
     * @param originalFilename
     * @param is
     * @return
     */
    private String uploadMultipartFile2OSS(String originalFilename, InputStream is) {
        int split = originalFilename.lastIndexOf('.');
        String suffix = originalFilename.substring(split);
        String fileName = UUID.randomUUID().toString().replaceAll("-","") + suffix;
        return CrowdUtil.uploadFileToOSS(
                ossClientProperties.getEndpoint(),
                ossClientProperties.getBucketName(),
                ossClientProperties.getAccessKeyId(),
                ossClientProperties.getAccessKeySecret(),
                is,fileName
        );
    }
}
