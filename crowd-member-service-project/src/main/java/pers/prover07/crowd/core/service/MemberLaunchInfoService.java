package pers.prover07.crowd.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.prover07.crowd.po.MemberLaunchInfoPo;
import pers.prover07.crowd.vo.MemberLauchInfoVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author prover07
 * @since 2022-03-01
 */
public interface MemberLaunchInfoService extends IService<MemberLaunchInfoPo> {

    /**
     * 保存项目发起人信息
     * @param memberLauchInfoVO
     * @param memberId
     */
    void save(MemberLauchInfoVO memberLauchInfoVO, String memberId);
}
