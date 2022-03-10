package pers.prover07.crowd.mysql.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import pers.prover07.crowd.mysql.mapper.PayBackMapper;
import pers.prover07.crowd.mysql.service.PayBackService;
import pers.prover07.crowd.po.PayBackPo;
import pers.prover07.crowd.vo.PayBackVO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author prover07
 * @since 2022-03-01
 */
@Service
public class PayBackServiceImpl extends ServiceImpl<PayBackMapper, PayBackPo> implements PayBackService {

    @Override
    public void savePayBackList(List<PayBackVO> payBackVOList, Integer projectId) {
        List<PayBackPo> payBackPos = payBackVOList.stream()
                .map(payBackVO -> {
                    PayBackPo payBackPo = new PayBackPo();
                    BeanUtils.copyProperties(payBackVO, payBackPo);
                    payBackPo.setProjectid(projectId);
                    return payBackPo;
                }).collect(Collectors.toList());
        this.saveBatch(payBackPos);
    }
}
