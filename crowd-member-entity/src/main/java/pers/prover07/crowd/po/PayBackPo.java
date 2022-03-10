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
@TableName("pay_back")
public class PayBackPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer projectid;

    /**
     * 0 - 实物回报， 1 虚拟物品回报
     */
    private Integer type;

    /**
     * 支持金额
     */
    private Integer supportmoney;

    /**
     * 回报内容
     */
    private String content;

    /**
     * 回报产品限额，“0”为不限回报数量
     */
    private Integer count;

    /**
     * 是否设置单笔限购
     */
    private Integer signalpurchase;

    /**
     * 具体限购数量
     */
    private Integer purchase;

    /**
     * 运费，“0”为包邮
     */
    private Integer freight;

    /**
     * 0 - 不开发票， 1 - 开发票
     */
    private Integer invoice;

    /**
     * 项目结束后多少天向支持者发送回报
     */
    private Integer returndate;

    /**
     * 说明图片路径
     */
    private String describPicPath;


}
