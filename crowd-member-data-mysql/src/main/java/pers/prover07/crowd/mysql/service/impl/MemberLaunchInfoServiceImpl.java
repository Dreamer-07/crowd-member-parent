package pers.prover07.crowd.mysql.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import pers.prover07.crowd.mysql.mapper.MemberLaunchInfoMapper;
import pers.prover07.crowd.mysql.service.MemberLaunchInfoService;
import pers.prover07.crowd.po.MemberLaunchInfoPo;
import pers.prover07.crowd.vo.MemberLauchInfoVO;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author prover07
 * @since 2022-03-01
 */
@Service
public class MemberLaunchInfoServiceImpl extends ServiceImpl<MemberLaunchInfoMapper, MemberLaunchInfoPo> implements MemberLaunchInfoService {

    @Override
    public void save(MemberLauchInfoVO memberLauchInfoVO, String memberId) {
        MemberLaunchInfoPo memberLaunchInfoPo = new MemberLaunchInfoPo();
        BeanUtils.copyProperties(memberLauchInfoVO, memberLaunchInfoPo);
        memberLaunchInfoPo.setMemberid(memberId);

        baseMapper.insert(memberLaunchInfoPo);
    }
}
