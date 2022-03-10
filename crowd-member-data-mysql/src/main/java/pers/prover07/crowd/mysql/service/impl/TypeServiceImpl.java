package pers.prover07.crowd.mysql.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import pers.prover07.crowd.mysql.mapper.TypeMapper;
import pers.prover07.crowd.mysql.service.TypeService;
import pers.prover07.crowd.po.TypePo;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author prover07
 * @since 2022-03-01
 */
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, TypePo> implements TypeService {

}
