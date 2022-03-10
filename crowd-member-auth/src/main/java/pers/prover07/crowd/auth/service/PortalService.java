package pers.prover07.crowd.auth.service;

import pers.prover07.crowd.vo.PortalTypeVo;

import java.util.List;

/**
 * @author by Prover07
 * @classname PortalService
 * @description TODO
 * @date 2022/3/10 12:46
 */
public interface PortalService {

    /**
     * 获取首页 Type 数据集合
     * @return
     */
    List<PortalTypeVo> getPortalTypeVoList();

}
