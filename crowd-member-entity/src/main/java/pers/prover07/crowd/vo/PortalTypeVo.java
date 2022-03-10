package pers.prover07.crowd.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pers.prover07.crowd.po.TypePo;

import java.util.List;

/**
 * @author by Prover07
 * @classname PortalProjectVo
 * @description TODO
 * @date 2022/3/10 12:39
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PortalTypeVo {

    public PortalTypeVo(TypePo typePo, List<PortalProjectVo> portalProjectVoList) {
        this.id = typePo.getId();
        this.name = typePo.getName();
        this.remark = typePo.getRemark();
        this.portalProjectVoList = portalProjectVoList;
    }

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 分类介绍
     */
    private String remark;

    /**
     * type 关联类型的 project 集合
     */
    private List<PortalProjectVo> portalProjectVoList;

}
