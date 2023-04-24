package com.example.demo.controller.game;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/game")
public class GameController {

    @GetMapping("enter")
    public ModelAndView gameEnter(HttpSession session, ModelAndView mav)
    {
        log.info("user {} entered.", session.getId());
        mav.setViewName("content/game");
        return mav;
    }
}
