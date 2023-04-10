package com.example.demo.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/page")
public class PageController {

    @RequestMapping("index")
    public ModelAndView getPageIndex(ModelAndView mav)
    {
        mav.setViewName("content/testPage");
        return mav;
    }
    
    @RequestMapping("test")
    public ModelAndView getPageController(ModelAndView mav)
    {
        log.info("getPageController() function Executed.");

        String[] season_arr = {"봄", "여름", "가을", "겨울"};

        String[] emotion_arr = {"차가운", "따뜻한", "부드러운", "감정적인", "카리스마 있는", "침착한", "열정적인"};

        String[] target_arr = {"고양이", "강아지", "토끼", "호랑이", "사자", "말", "고슴도치", "햄스터", "거북이"};

        String comment = 
            season_arr[(int) (Math.random() * season_arr.length)] + "의 " + 
            emotion_arr[(int) (Math.random() * emotion_arr.length)] + " " +
            target_arr[(int) (Math.random() * target_arr.length)];

        mav.setViewName("content/testPage");
        mav.addObject("comment", comment);
        return mav;
    }
}
