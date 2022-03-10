package pers.prover07.crowd.mysql.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.prover07.crowd.po.MemberConfirmInfoPo;
import pers.prover07.crowd.vo.MemberConfirmInfoVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author prover07
 * @since 2022-03-01
 */
public interface MemberConfirmInfoService extends IService<MemberConfirmInfoPo> {

    /**
     * 保存项目确认信息
     * @param memberConfirmInfoVO
     * @param memberId
     */
    void save(MemberConfirmInfoVO memberConfirmInfoVO, String memberId);
}
