package pers.prover07.crowd.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author by Prover07
 * @classname MemberPo
 * @description TODO
 * @date 2022/2/27 16:04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@TableName(value = "member")
public class MemberPo {


    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    private String loginAcct;

    private String password;

    private String nickname;

    private String email;

    private Timestamp createTime;

    private Timestamp updateTime;

}
