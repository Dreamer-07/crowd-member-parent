package pers.prover07.crowd.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author prover07
 * @since 2022-03-01
 */
@Getter
@Setter
@TableName("member_launch_info")
public class MemberLaunchInfoPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 会员 id
     */
    private String memberid;

    /**
     * 简单介绍
     */
    private String descriptionSimple;

    /**
     * 详细介绍
     */
    private String descriptionDetail;

    /**
     * 联系电话
     */
    private String phoneNum;

    /**
     * 客服电话
     */
    private String serviceNum;


}
