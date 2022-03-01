package pers.prover07.crowd.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.BeanUtils;
import pers.prover07.crowd.po.MemberPo;

import java.io.Serializable;

/**
 * @author by Prover07
 * @classname MemberLoginVo
 * @description TODO
 * @date 2022/2/28 11:16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberLoginVo implements Serializable {

    public static final long serialVersionUID = 231239812L;

    private String id;

    private String nickname;

    private String email;

    public static MemberLoginVo createObjByMemberPo(MemberPo memberPo) {
        MemberLoginVo memberLoginVo = new MemberLoginVo();
        BeanUtils.copyProperties(memberPo, memberLoginVo);
        return memberLoginVo;
    }

}
