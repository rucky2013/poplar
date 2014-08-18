package cn.mob.poplar.example.controller;

import cn.mob.poplar.anno.Get;
import cn.mob.poplar.example.dao.UserDao;
import cn.mob.poplar.example.model.User;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2014/8/7.
 */
@Controller("dempe")
public class UserController {

    @Resource
    private UserDao dao;

    @Get("/hello")
    public String get(String name) {
        System.out.println(name);
        return "hello";
    }

    public List<User> list() {
        return null;
    }

    public User view(String name) {
        dao.findOne();
        return null;
    }
}
