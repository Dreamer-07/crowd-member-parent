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
@TableName("project")
public class ProjectPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 项目描述
     */
    private String projectDescription;

    /**
     * 筹集金额
     */
    private Integer money;

    /**
     * 筹集天数
     */
    private Integer day;

    /**
     * 0-即将开始，1-众筹中，2-众筹成功，3-众筹失败
     */
    private Integer status;

    /**
     * 项目发起时间
     */
    private String deploydate;

    /**
     * 已筹集到的金额
     */
    private Long supportmoney;

    /**
     * 支持人数
     */
    private Integer supporter;

    /**
     * 百分比完成度
     */
    private Integer completion;

    /**
     * 发起人的会员 id
     */
    private String memberid;

    /**
     * 项目创建时间
     */
    private String createdate;

    /**
     * 关注人数
     */
    private Integer follower;

    /**
     * 头图路径
     */
    private String headerPicturePath;

    /**
     * 工程状态，1-开始创建; 2-创建完成
     */
    private Integer projectStatus;

}
