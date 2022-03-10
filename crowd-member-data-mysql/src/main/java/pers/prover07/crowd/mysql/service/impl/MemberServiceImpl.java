package pers.prover07.crowd.mysql.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import pers.prover07.crowd.mysql.mapper.MemberMapper;
import pers.prover07.crowd.mysql.service.MemberService;
import pers.prover07.crowd.po.MemberPo;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author prover07
 * @since 2022-03-01
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, MemberPo> implements MemberService {

}
