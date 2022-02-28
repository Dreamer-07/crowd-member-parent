package pers.prover07.crowd.auth.controller;

import com.netflix.discovery.converters.Auto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pers.prover.crowd.constant.CrowdAttrNameConstant;
import pers.prover.crowd.constant.HttpRespMsgConstant;
import pers.prover.crowd.constant.RedisKeyConstant;
import pers.prover.crowd.util.CrowdUtil;
import pers.prover.crowd.util.ResultEntity;
import pers.prover07.crowd.auth.config.properties.ShortMessageProperties;
import pers.prover07.crowd.auth.service.MemberService;
import pers.prover07.crowd.po.MemberPo;
import pers.prover07.crowd.redis.cache.IGlobalCache;
import pers.prover07.crowd.vo.MemberVo;

import java.util.Objects;

/**
 * @author by Prover07
 * @classname AuthController
 * @description TODO
 * @date 2022/2/27 14:01
 */
@RequestMapping("/auth/member")
@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private ShortMessageProperties shortMessageProperties;

    @Autowired
    private IGlobalCache globalCache;

    @ResponseBody
    @GetMapping("/phoneCode")
    public ResultEntity getMemberPhoneCode(@RequestParam("phoneNum") String phoneNum) {
        // 得到验证码
        String phoneCode = CrowdUtil.generatedCode(4);
        // 发送手机验证码
        ResultEntity result = CrowdUtil.sendPhoneCode(
                shortMessageProperties.getHost(),
                shortMessageProperties.getPath(),
                shortMessageProperties.getMethod(),
                shortMessageProperties.getAppcode(),
                phoneNum, phoneCode,
                shortMessageProperties.getTemplateId()
        );
        // 根据结果判断是否需要返回保存到 redis
        if (result.isSuccess()) {
            boolean success = globalCache.set(RedisKeyConstant.AUTH_PHONE_CODE + phoneNum, phoneCode, shortMessageProperties.getExpiredMinute());
            if (success) {
                return ResultEntity.success();
            }
            return ResultEntity.fail(HttpRespMsgConstant.SYSTEM_ERROR_REDIS_SAVE);
        }
        return ResultEntity.fail(HttpRespMsgConstant.AUTH_SEND_MESSAGE_ERROR);
    }

    @PostMapping("/register")
    public String registerMember(MemberVo memberVo, Model model) {
        // 获取 Redis 中的验证码
        String phoneNum = memberVo.getPhoneNum();
        String key = RedisKeyConstant.AUTH_PHONE_CODE + phoneNum;
        String cachedCode = (String) globalCache.get(key);

        // 比较两个验证码是否相同
        if (!Objects.equals(cachedCode, memberVo.getCode())){
            model.addAttribute(CrowdAttrNameConstant.EXCEPTION_INFO, "验证码不正确/已过期");
            return "register";
        };

        // 删除 redis 中的验证码
        globalCache.del(key);

        // 密码加密
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodePassword = passwordEncoder.encode(memberVo.getPassword());
        memberVo.setPassword(encodePassword);

        // 将 memberVo 转换成 memberPO 后保存到 Database
        MemberPo memberPo = new MemberPo();
        BeanUtils.copyProperties(memberVo, memberPo);
        boolean isSuccess = memberService.save(memberPo);
        if (isSuccess) {
            model.addAttribute(CrowdAttrNameConstant.EXCEPTION_INFO, "登录账号(loginAcct)重复");
            return "register";
        }
        return "";
    }

}
