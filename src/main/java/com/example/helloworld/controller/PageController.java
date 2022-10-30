package com.example.helloworld.controller;

import com.example.helloworld.constants.Consts;
import com.example.helloworld.entity.*;
import com.example.helloworld.service.*;
import com.example.helloworld.utils.CommonUtil;
import com.example.helloworld.utils.CookieUtil;
import com.example.helloworld.webObject.TeamMember;
import com.example.helloworld.webObject.WebSubmission;
import com.fasterxml.jackson.core.JsonProcessingException;
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
    private final SubmissionService submissionService;
    private final SubmissionUserService submissionUserService;
    private final RoomBedUserService roomBedUserService;

    public PageController(UserService userService, CommonUtil commonUtil, CookieUtil cookieUtil, RoomService roomService, SubmissionService submissionService, SubmissionUserService submissionUserService, RoomBedUserService roomBedUserService) {
        this.userService = userService;
        this.commonUtil = commonUtil;
        this.cookieUtil = cookieUtil;
        this.roomService = roomService;
        this.submissionService = submissionService;
        this.submissionUserService = submissionUserService;
        this.roomBedUserService = roomBedUserService;
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
        Set<Integer> floorSet = roomService.findAllFloorIdBySex(user_userSex);
        res.put("code", "0");
        res.put("msg", "查询成功");
        res.putOpt("data", floorSet);
        return res.toString();
    }

    @GetMapping("/u/floor/allBedCntFree")
    @ResponseBody
    public String getAllFloorBedCntFree(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        JSONObject res = new JSONObject();

        Long user_id = (Long) session.getAttribute(Consts.SESSION_USER_ID);
        UserEntity userEntity = userService.findById(user_id).get();
        Integer user_userSex = userEntity.getUserSex();
        // 找到所有含有性别的楼，并统计该性别的床位数量
        Map<Integer, Long> floorBedCntFree = roomService.sumAllFreeBedBySex(user_userSex);
        res.put("code", "0");
        res.put("msg", "查询成功");
        res.putOpt("data", floorBedCntFree);
        return res.toString();
    }

    @PostMapping(value = "/u/submission/submit", headers = {"content-type=application/json"})
    @ResponseBody
    public String submitSubmission(@RequestBody(required = true) WebSubmission webSubmission, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
        // 返回 结果
        JSONObject res = new JSONObject();
        res.put("code", "0");
        res.put("msg", "办理成功");

        // 获取main user
        Long user_id = (Long) session.getAttribute(Consts.SESSION_USER_ID);
        UserEntity mainUserEntity = userService.findById(user_id).get();
        // 订单信息。 问题是自增的主键是什么时候获取的
        SubmissionEntity submissionEntity = new SubmissionEntity();
        List<SubmissionUserEntity> submissionUserEntityList = new ArrayList<>();
        // 初始化订单信息，默认为失败
        commonUtil.webSubmission2DbSubmission(mainUserEntity, webSubmission, submissionEntity, submissionUserEntityList);
        // 判断楼号是否为空
        if (res.get("code") == "0") {
            if (submissionEntity.getFloorId() == null) {
                res.put("code", "1012");
                res.put("msg", "未选择楼号");
            }
        }
        // 判断订单人数是否超限
        if (res.get("code") == "0") {
            if (submissionEntity.getUserCnt() > Consts.TEAM_MAX_LENGTH) {
                res.put("code", "1003");
                res.put("msg", "订单人数超限");
            }
        }
        // 生成用户列表userEntityList
        ArrayList<UserEntity> userEntityList = new ArrayList<>();
        // userEntityList加入main User
        userEntityList.add(mainUserEntity);
        // userEntityList加入其他用户，同步判断用户是否存在
        if (res.get("code") == "0") {
            for (TeamMember teamMember : webSubmission.getTeamMemberList()) {
                // 获取 学号
                Long teamMemberUserId = teamMember.getUserId();
                Optional<UserEntity> teamUserOptional = userService.findByUserId(teamMemberUserId);
                // 检查是否获取到用户
                if (!teamUserOptional.isPresent()) {
                    res.put("code", "1011");
                    res.put("msg", "队友学号错误");
                    break;
                }
                UserEntity teamUserEntity = teamUserOptional.get();
                // 检查验证码是否一致
                if (!teamUserEntity.getCheckCode().equals(teamMember.getCheckCode())) {
                    res.put("code", "1007");
                    res.put("msg", "队友验证码错误");
                    break;
                }
                // 检查成功，teamUserEntity
                userEntityList.add(teamUserEntity);
            }
        }
        // 判断是否有人已经住宿
        if (res.get("code") == "0") {
            for (UserEntity userEntity : userEntityList) {
                Long user_userId = userEntity.getId();
                // 已经住宿
                if (roomBedUserService.findByUserId(user_userId).isPresent()) {
                    res.put("code", "1006");
                    res.put("msg", "队友或自己已经办理住宿");
                    break;
                }
            }
        }
        // 判断是否存在重复user
        if (res.get("code") == "0") {
            Set<Long> user_userIdSet = new HashSet<Long>();
            for (UserEntity userEntity : userEntityList) {
                if (user_userIdSet.contains(userEntity.getId())) {
                    res.put("code", "1004");
                    res.put("msg", "订单中包含重复人");
                    break;
                }
                user_userIdSet.add(userEntity.getId());
            }
        }
        // 判断订单中所有人的性别是否与mainUser一致
        if (res.get("code") == "0") {
            for (UserEntity userEntity : userEntityList) {
                if (!Objects.equals(userEntity.getUserSex(), mainUserEntity.getUserSex())) {
                    res.put("code", "1005");
                    res.put("msg", "订单性别不一致");
                    break;
                }
            }
        }
        // 订单合法，开始处理订单
        if (res.get("code") == "0") {
            // 获取合法房间列表
            List<RoomEntity> roomEntityList = roomService.findAllByFloorIdIsAndRoomSexIsAndBedCntFreeGreaterThanEqualAndRoomAvailableIsTrue(webSubmission.getFloorId(), mainUserEntity.getUserSex(), submissionEntity.getUserCnt());
            if (roomEntityList.isEmpty()) {
                res.put("code", "1010");
                res.put("msg", "无符合条件的房间");
            } else {
                // 随机获取一个房间
                RoomEntity roomEntity = roomEntityList.get(0);
                // 根据roomEntity 获取可用的床位列表
                List<RoomBedUserEntity> roomBedUserEntityList = roomBedUserService.findAllByRoomIdIsAndUserIdIsNull(roomEntity.getId());
                while (roomBedUserEntityList.size() > submissionEntity.getUserCnt()) {
                    roomBedUserEntityList.remove(0);
                }
                // 更改房间剩余床位的数量
                roomEntity.setBedCntFree(roomEntity.getBedCntFree() - submissionEntity.getUserCnt());
                // 将user_id写入roomBedUserEntityList
                for (int i = 0; i < roomBedUserEntityList.size(); i++) {
                    roomBedUserEntityList.get(i).setUserId(userEntityList.get(i).getId());
                }
                // 写回room
                roomService.save(roomEntity);
                // 写回roomBedUser
                for (RoomBedUserEntity roomBedUserEntity : roomBedUserEntityList) {
                    roomBedUserService.save(roomBedUserEntity);
                }
                submissionEntity.setRoomRoomId(roomEntity.getRoomId());
            }
        }
        // 先提交submissionEntity，获取订单id，然后写入submissionUserEntity
        submissionService.save(submissionEntity);
        for (SubmissionUserEntity submissionUserEntity : submissionUserEntityList) {
            submissionUserEntity.setSubmissionId(submissionEntity.getId());
            submissionUserService.save(submissionUserEntity);
        }
        Map<String, Object> rstData = new HashMap<>();
        rstData.put("submission", submissionEntity);
        rstData.put("submissionTeamMembers", submissionUserEntityList);
        res.putOpt("data", rstData);
        return res.toString();
    }

    @PostMapping(value = "/u/submission/submitNoSave", headers = {"content-type=application/json"})
    @ResponseBody
    public String submitSubmissionNoSave(@RequestBody(required = true) WebSubmission webSubmission, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
        // 返回 结果
        JSONObject res = new JSONObject();
        res.put("code", "0");
        res.put("msg", "办理成功");

        // 获取main user
        Long user_id = (Long) session.getAttribute(Consts.SESSION_USER_ID);
        UserEntity mainUserEntity = userService.findById(user_id).get();
        // 订单信息。 问题是自增的主键是什么时候获取的
        SubmissionEntity submissionEntity = new SubmissionEntity();
        List<SubmissionUserEntity> submissionUserEntityList = new ArrayList<>();
        // 初始化订单信息，默认为失败
        commonUtil.webSubmission2DbSubmission(mainUserEntity, webSubmission, submissionEntity, submissionUserEntityList);
        // 判断楼号是否为空
        if (res.get("code") == "0") {
            if (submissionEntity.getFloorId() == null) {
                res.put("code", "1012");
                res.put("msg", "未选择楼号");
            }
        }
        // 判断订单人数是否超限
        if (res.get("code") == "0") {
            if (submissionEntity.getUserCnt() > Consts.TEAM_MAX_LENGTH) {
                res.put("code", "1003");
                res.put("msg", "订单人数超限");
            }
        }
        // 生成用户列表userEntityList
        ArrayList<UserEntity> userEntityList = new ArrayList<>();
        // userEntityList加入main User
        userEntityList.add(mainUserEntity);
        // userEntityList加入其他用户，同步判断用户是否存在
        if (res.get("code") == "0") {
            for (TeamMember teamMember : webSubmission.getTeamMemberList()) {
                // 获取 学号
                Long teamMemberUserId = teamMember.getUserId();
                Optional<UserEntity> teamUserOptional = userService.findByUserId(teamMemberUserId);
                // 检查是否获取到用户
                if (!teamUserOptional.isPresent()) {
                    res.put("code", "1011");
                    res.put("msg", "队友学号错误");
                    break;
                }
                UserEntity teamUserEntity = teamUserOptional.get();
                // 检查验证码是否一致
                if (!teamUserEntity.getCheckCode().equals(teamMember.getCheckCode())) {
                    res.put("code", "1007");
                    res.put("msg", "队友验证码错误");
                    break;
                }
                // 检查成功，teamUserEntity
                userEntityList.add(teamUserEntity);
            }
        }
        // 判断是否有人已经住宿
        if (res.get("code") == "0") {
            for (UserEntity userEntity : userEntityList) {
                Long user_userId = userEntity.getId();
                // 已经住宿
                if (roomBedUserService.findByUserId(user_userId).isPresent()) {
                    res.put("code", "1006");
                    res.put("msg", "队友或自己已经办理住宿");
                    break;
                }
            }
        }
        // 判断是否存在重复user
        if (res.get("code") == "0") {
            Set<Long> user_userIdSet = new HashSet<Long>();
            for (UserEntity userEntity : userEntityList) {
                if (user_userIdSet.contains(userEntity.getId())) {
                    res.put("code", "1004");
                    res.put("msg", "订单中包含重复人");
                    break;
                }
                user_userIdSet.add(userEntity.getId());
            }
        }
        // 判断订单中所有人的性别是否与mainUser一致
        if (res.get("code") == "0") {
            for (UserEntity userEntity : userEntityList) {
                if (!Objects.equals(userEntity.getUserSex(), mainUserEntity.getUserSex())) {
                    res.put("code", "1005");
                    res.put("msg", "订单性别不一致");
                    break;
                }
            }
        }
        // 订单合法，开始处理订单
        if (res.get("code") == "0") {
            // 获取合法房间列表
            List<RoomEntity> roomEntityList = roomService.findAllByFloorIdIsAndRoomSexIsAndBedCntFreeGreaterThanEqualAndRoomAvailableIsTrue(webSubmission.getFloorId(), mainUserEntity.getUserSex(), submissionEntity.getUserCnt());
            if (roomEntityList.isEmpty()) {
                res.put("code", "1010");
                res.put("msg", "无符合条件的房间");
            } else {
                // 随机获取一个房间
                RoomEntity roomEntity = roomEntityList.get(0);
                // 根据roomEntity 获取可用的床位列表
                List<RoomBedUserEntity> roomBedUserEntityList = roomBedUserService.findAllByRoomIdIsAndUserIdIsNull(roomEntity.getId());
                while (roomBedUserEntityList.size() > submissionEntity.getUserCnt()) {
                    roomBedUserEntityList.remove(0);
                }
                // 更改房间剩余床位的数量
                roomEntity.setBedCntFree(roomEntity.getBedCntFree() - submissionEntity.getUserCnt());
                // 将user_id写入roomBedUserEntityList
                for (int i = 0; i < roomBedUserEntityList.size(); i++) {
                    roomBedUserEntityList.get(i).setUserId(userEntityList.get(i).getId());
                }
                // 写回room
//                roomService.save(roomEntity);
                // 写回roomBedUser
                for (RoomBedUserEntity roomBedUserEntity : roomBedUserEntityList) {
//                    roomBedUserService.save(roomBedUserEntity);
                }
                submissionEntity.setRoomRoomId(roomEntity.getRoomId());
            }
        }
        // 先提交submissionEntity，获取订单id，然后写入submissionUserEntity
//        submissionService.save(submissionEntity);
        for (SubmissionUserEntity submissionUserEntity : submissionUserEntityList) {
            submissionUserEntity.setSubmissionId(submissionEntity.getId());
//            submissionUserService.save(submissionUserEntity);
        }
        Map<String, Object> rstData = new HashMap<>();
        rstData.put("submission", submissionEntity);
        rstData.put("submissionTeamMembers", submissionUserEntityList);
        res.putOpt("data", rstData);
        return res.toString();
    }

}
