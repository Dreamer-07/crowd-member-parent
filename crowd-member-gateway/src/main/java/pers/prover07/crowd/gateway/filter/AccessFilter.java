package pers.prover07.crowd.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.PathMatcher;
import pers.prover.crowd.constant.CrowdAttrNameConstant;
import pers.prover.crowd.constant.HttpRespMsgConstant;
import pers.prover07.crowd.gateway.properties.IgnoreWhitesProperties;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;

/**
 * @author by Prover07
 * @classname AccessFilter
 * @description TODO
 * @date 2022/3/1 11:07
 */
@Component
public class AccessFilter extends ZuulFilter {

    @Autowired
    private PathMatcher pathMatcher;

    @Autowired
    private IgnoreWhitesProperties ignoreWhitesProperties;

    /**
     * 返回 pre 表示在请求转发到微服务之前过滤
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * filter 执行顺序
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 判断资源是否需要过滤
     * @return true：需要过滤; false: 不需要过滤
     */
    @Override
    public boolean shouldFilter() {
        // 获取当前请求路径
        // 框架底层借助了 ThreadLocal 可以获取事先绑定在当前线程上的 Request 对象
        String servletPath = RequestContext.getCurrentContext().getRequest().getServletPath();
        // 获取白名单集合
        HashSet<String> whites = ignoreWhitesProperties.getWhites();
        return !whites.stream().anyMatch(white -> pathMatcher.match(white, servletPath));
    }

    /**
     * 具体过滤业务逻辑
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        // 获取 Session 对象
        HttpSession session = RequestContext.getCurrentContext().getRequest().getSession();
        // 获取 session 中的登录对象
        Object loginMember = session.getAttribute(CrowdAttrNameConstant.LOGIN_MEMBER);
        // 如果未登录就重定向到登录页面
        if (loginMember == null) {
            try {
                RequestContext.getCurrentContext().getResponse().sendRedirect("/auth/login");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 正常的话返回 null 即可
        return null;
    }
}
