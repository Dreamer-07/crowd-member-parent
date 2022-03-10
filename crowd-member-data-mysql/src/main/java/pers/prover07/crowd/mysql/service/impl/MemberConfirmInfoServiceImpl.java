package pers.prover07.crowd.mysql.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import pers.prover07.crowd.mysql.mapper.MemberConfirmInfoMapper;
import pers.prover07.crowd.mysql.service.MemberConfirmInfoService;
import pers.prover07.crowd.po.MemberConfirmInfoPo;
import pers.prover07.crowd.vo.MemberConfirmInfoVO;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author prover07
 * @since 2022-03-01
 */
@Service
public class MemberConfirmInfoServiceImpl extends ServiceImpl<MemberConfirmInfoMapper, MemberConfirmInfoPo> implements MemberConfirmInfoService {

    @Override
    public void save(MemberConfirmInfoVO memberConfirmInfoVO, String memberId) {
        MemberConfirmInfoPo memberConfirmInfoPo = new MemberConfirmInfoPo();
        BeanUtils.copyProperties(memberConfirmInfoVO, memberConfirmInfoPo);
        memberConfirmInfoPo.setMemberid(memberId);

        baseMapper.insert(memberConfirmInfoPo);
    }
}
