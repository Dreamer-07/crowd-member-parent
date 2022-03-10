package pers.prover07.crowd.mysql.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import pers.prover07.crowd.mysql.mapper.TagMapper;
import pers.prover07.crowd.mysql.service.TagService;
import pers.prover07.crowd.po.TagPo;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author prover07
 * @since 2022-03-01
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, TagPo> implements TagService {

}
