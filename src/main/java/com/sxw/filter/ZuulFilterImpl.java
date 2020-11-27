package com.sxw.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Sunxiangwei on 2020-11-24.
 */
@Component
public class ZuulFilterImpl extends ZuulFilter {

    private static final Logger logger = LoggerFactory.getLogger(ZuulFilterImpl.class);

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        logger.info("******* 执行过滤器，请求URL：{}" + "访问方法：{}"
                ,request.getRequestURL(),request.getMethod());
        String accessToken = request.getHeader("accessToken");

        if (StringUtils.isEmpty(accessToken)) {
            logger.info("******* 当前请求头没有accessToken");
            context.setSendZuulResponse(false);
            context.setResponseStatusCode(401);
            return null;
        }
        logger.info("******* 请求正常通过过滤器");
        return null;
    }
}
