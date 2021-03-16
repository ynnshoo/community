package com.ynns.controller;

import com.ynns.dto.AccessTokenDTO;
import com.ynns.dto.GithubUser;
import com.ynns.mapper.UserMapper;
import com.ynns.pojo.User;
import com.ynns.provider.GithubProvider;
import com.ynns.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String client_id;
    @Value("${github.client.secret}")
    private String client_secret;
    @Value("${github.redirect.uri}")
    private String redirect_uri;

    @Autowired
    UserService userService;

    @GetMapping("/callback")
    public String callBack(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response){

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(client_id);
        accessTokenDTO.setClient_secret(client_secret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirect_uri);
        accessTokenDTO.setState(state);

        String accessToken=githubProvider.getAccessToken(accessTokenDTO);
        System.out.println("token==========>"+accessToken);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        System.out.println("获取GitHub用户信息的名字："+githubUser.getName());
        //登录成功
        if (githubUser!=null&&githubUser.getId()!=null){
//            //放入从GitHub获取到的用户信息到session中"user"
//            request.getSession().setAttribute("user",githubUser);
            //登录成功 获取session、cookie
            User user = new User();
            user.setName(githubUser.getName());
            String token=UUID.randomUUID().toString();
            System.out.println("UUID的token=====>"+token);
            user.setToken(token);
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setAvatarUrl(githubUser.getAvatar_url());
            userService.createOrUpdate(user);
            //自动写入cookie
            response.addCookie(new Cookie("token",token));
            return "redirect:/";
        }else{
            //登录失败
            return "redirect:/";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response){
        //移除session user
        request.getSession().removeAttribute("user");
        //移除cookie
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
