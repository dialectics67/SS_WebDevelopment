package com.example.helloworld.constants;


public interface Consts {
    /**
     * 各种key的名字
     */
    String SESSION_KEY = "key:session:token";

    String SESSION_USER = "key:session:user"; //-> userEntity: UserEntity
    String SESSION_USER_ID = "key:session:user:id"; //-> id: Long
    String SESSION_USER_USERID = "key:session:user:userId";//-> userId: Long

    String COOKIE_SESSION_ID = "JSESSIONID";
    String COOKIE_USER = "cookie:user";//->userId: UserEntity
    String COOKIE_USER_USERID = "cookie:userId";//->userId: Long

    Integer TEAM_MAX_LENGTH = 4;

}