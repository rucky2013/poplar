package cn.mob.poplar.example.controller;

import cn.mob.poplar.example.dao.UserDao;
import cn.mob.poplar.example.model.User;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * Created by Administrator on 2014/8/7.
 */
@Controller
public class UserController {

    private UserDao dao;

    public void form() {

    }

    public String add() {
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
