package pers.prover07.crowd.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import pers.prover07.crowd.auth.mapper.MemberMapper;
import pers.prover07.crowd.auth.service.MemberService;
import pers.prover07.crowd.po.MemberPo;

/**
 * @author by Prover07
 * @classname MemberServiceImpl
 * @description TODO
 * @date 2022/2/28 9:06
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, MemberPo> implements MemberService {

}
