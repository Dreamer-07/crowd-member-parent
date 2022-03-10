package pers.prover07.crowd.mysql.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.prover07.crowd.po.PayBackPo;
import pers.prover07.crowd.vo.PayBackVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author prover07
 * @since 2022-03-01
 */
public interface PayBackService extends IService<PayBackPo> {

    /**
     * 保存项目回报信息
     * @param payBackVOList
     * @param projectId
     */
    void savePayBackList(List<PayBackVO> payBackVOList, Integer projectId);
}
