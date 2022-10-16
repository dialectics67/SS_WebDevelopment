package com.example.helloworld.interceptor;

import com.example.helloworld.constants.Consts;
import com.example.helloworld.service.UserService;
import com.example.helloworld.utils.CookieUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Component
public class SessionInterceptor implements HandlerInterceptor {
    private final CookieUtil cookieUtil;

    private final UserService userService;

    public SessionInterceptor(UserService userService, CookieUtil cookieUtil) {
        this.userService = userService;
        this.cookieUtil = cookieUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        // Session中存储着user.userId代表着用户登录状态有效
        if (session.getAttribute(Consts.SESSION_USER_ID) != null) {
            return true;
        }
        String url = "/page/please/login";
        response.sendRedirect(request.getContextPath() + url);
        return false;
//        // 获得cookie
//        Cookie[] cookies = request.getCookies();
//        // 没有cookie信息，则重定向到登录界面
//        if (null == cookies) {
//            response.sendRedirect(request.getContextPath() + "/login");
//            return false;
//        }
//        // 尝试获取userId的string表示
//        Optional<String> userIdString = cookieUtil.getCookieValue(request, Consts.COOKIE_USER);
//        // 如果cookie里面没有包含用户的一些登录信息，则重定向到登录界面
//        if (StringUtils.hasLength(userIdString.orElse(null))) {
//
//        }
//        // 只要userId存在cookie中，即认为已经登录，这是错误的
//        // 获取HttpSession对象
//        HttpSession session = request.getSession(false);
//        if (session == null) {
//            //登录过期需要重新登录
//            response.sendRedirect(request.getContextPath() + "/login");
//            return false;
//        }
//        // 根据用户登录账号获取数据库中的用户信息
////        Optional<UserEntity> userEntity = userService.findByUserId(Long.valueOf(userIdString.get()));
//        // 将用户保存到session中
////        session.setAttribute(Consts.SESSION_USER, userEntity);
//        // 已经登录
//        return true;
    }
}