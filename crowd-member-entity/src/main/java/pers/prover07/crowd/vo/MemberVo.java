package pers.prover07.crowd.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author by Prover07
 * @classname MemberVo
 * @description TODO
 * @date 2022/2/27 16:21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberVo {

    private String loginAcct;

    private String password;

    private String nickname;

    private String email;

    private String phoneNum;

    private String code;

}
