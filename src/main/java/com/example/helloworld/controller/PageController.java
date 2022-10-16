package com.example.helloworld.controller;

import com.example.helloworld.constants.Consts;
import com.example.helloworld.entity.RoomEntity;
import com.example.helloworld.entity.UserEntity;
import com.example.helloworld.service.RoomService;
import com.example.helloworld.service.UserService;
import com.example.helloworld.utils.CommonUtil;
import com.example.helloworld.utils.CookieUtil;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

/**
 *
 */

@Controller
@RequestMapping("/page")
public class PageController {
    private final UserService userService;
    private final CommonUtil commonUtil;
    private final CookieUtil cookieUtil;
    private final RoomService roomService;

    public PageController(UserService userService, CommonUtil commonUtil, CookieUtil cookieUtil, RoomService roomService) {
        this.userService = userService;
        this.commonUtil = commonUtil;
        this.cookieUtil = cookieUtil;
        this.roomService = roomService;
    }

    /**
     * 登录页
     */
    @GetMapping("/login")
    @ResponseBody
    public String login(String userId, String password, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        // 最终返回的对象
        JSONObject res = new JSONObject();
        // code 1001, 学号或者密码为空
        if (!StringUtils.hasLength(userId) || !StringUtils.hasLength(password)) {
            res.put("code", "1001");
            res.put("msg", "学号或者密码为空");
            return res.toString();
        }

        // code 1002, 学号错误
        Optional<UserEntity> userEntity = userService.findByUserId(Long.valueOf(userId));
        if (!userEntity.isPresent()) {
            res.put("code", "1002");
            res.put("msg", "学号密码错误");
            return res.toString();
        }
        // code 1002, 密码错误
        String md5Password = commonUtil.getMD5(password);
        if (!md5Password.equals(userEntity.get().getPassword())) {
            res.put("code", "1002");
            res.put("msg", "学号密码错误");
            return res.toString();
        }
        // 保存user.id到session中
        session.setAttribute(Consts.SESSION_USER_ID, userEntity.get().getId());
        // Cookie保存用户信息
        Cookie cookieUser;
        try {
            cookieUser = new Cookie("user", commonUtil.getWebUser(userEntity.get()));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        // 设置cookie的持久化时间，30min
        cookieUser.setMaxAge(30 * 60);
        // 设置为当前项目下都携带这个cookie
        cookieUser.setPath(request.getContextPath());
        // 向客户端发送cookie
        response.addCookie(cookieUser);
        Cookie cookieSessionId = new Cookie("JSESSIONID", session.getId());
        cookieSessionId.setPath("/");
        cookieSessionId.setMaxAge(30 * 60);
        response.addCookie(cookieSessionId);
        res.put("code", 0);
        res.put("msg", "登录成功");
        return res.toString();
    }


    @GetMapping("/please/login")
    @ResponseBody
    public String pleaseLogin(HttpServletResponse response) {
        JSONObject res = new JSONObject();
        res.put("code", "1001");
        res.put("msg", "请登录");
        return res.toString();
    }

    @GetMapping("/u/index")
    @ResponseBody
    public String index(@CookieValue("user") String user, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        try {
            user = URLDecoder.decode(user, "utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        JSONObject jsonUser = new JSONObject(user);
        return "Hello " + jsonUser.get("userName") + " 您已经登录";
    }

    @PostMapping("/u/password/change")
    @ResponseBody
    public String changePassword(String oldPassword, String newPassword, String newPasswordDuplicate, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        JSONObject res = new JSONObject();

        Long user_id = (Long) session.getAttribute(Consts.SESSION_USER_ID);
        UserEntity userEntity = userService.findById(user_id).get();
        if (!commonUtil.getMD5(oldPassword).equals(userEntity.getPassword())) {
            res.put("code", "1002");
            res.put("msg", "old password错误");
            return res.toString();
        }
        if (!StringUtils.hasLength(newPassword)) {
            res.put("code", "1003");
            res.put("msg", "new password不能为空");
            return res.toString();
        }
        if (!newPassword.equals(newPasswordDuplicate)) {
            res.put("code", "1004");
            res.put("msg", "两次new password不相同");
            return res.toString();
        }
        userEntity.setPassword(commonUtil.getMD5(newPassword));
        userService.save(userEntity);
        res.put("code", "0");
        res.put("msg", "修改密码成功");
        return res.toString();
    }

    @RequestMapping(value = "logout")
    @ResponseBody
    public String logout(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        JSONObject res = new JSONObject();
        // 删除session里面的用户信息
        session.invalidate();
        // 保存cookie，实现自动登录
        Cookie cookie_sessionId = new Cookie(Consts.COOKIE_SESSION_ID, "");
        cookie_sessionId.setPath("/");
        // 设置cookie的持久化时间，0
        cookie_sessionId.setMaxAge(0);
        // 向客户端发送cookie
        response.addCookie(cookie_sessionId);
        res.put("code", "0");
        res.put("msg", "注销成功");
        return res.toString();
    }

    @GetMapping("/u/floor/list")
    @ResponseBody
    public String getAllFloors(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        JSONObject res = new JSONObject();

        Long user_id = (Long) session.getAttribute(Consts.SESSION_USER_ID);
        UserEntity userEntity = userService.findById(user_id).get();
        Integer user_userSex = userEntity.getUserSex();
        List<RoomEntity> roomEntityList = roomService.findByRoomSex(user_userSex);
        List<Integer> floorList = new ArrayList<>();
        for (RoomEntity roomEntity : roomEntityList) {
            floorList.add(roomEntity.getFloorId());
        }
        res.put("code", "0");
        res.put("msg", "查询成功");
        res.putOpt("data", floorList);
        return res.toString();
    }

    @GetMapping("/u/floor/allBedCntFree")
    @ResponseBody
    public String getAllFloorBedCntFree(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        JSONObject res = new JSONObject();

        Long user_id = (Long) session.getAttribute(Consts.SESSION_USER_ID);
        UserEntity userEntity = userService.findById(user_id).get();
        Integer user_userSex = userEntity.getUserSex();
        List<RoomEntity> roomEntityList = roomService.findByRoomSex(user_userSex);

        Map<Integer, Long> floorBedCntFree = new HashMap<>();
        for (RoomEntity roomEntity : roomEntityList) {
            Long tmpBedCntFree = Long.valueOf(roomEntity.getBedCntFree());
            if (floorBedCntFree.containsKey(roomEntity.getFloorId())) {
                tmpBedCntFree = tmpBedCntFree + floorBedCntFree.get(roomEntity.getFloorId());
            }
            floorBedCntFree.put(roomEntity.getFloorId(), tmpBedCntFree);
        }
        res.put("code", "0");
        res.put("msg", "查询成功");
        res.putOpt("data", floorBedCntFree);
        return res.toString();
    }
}
