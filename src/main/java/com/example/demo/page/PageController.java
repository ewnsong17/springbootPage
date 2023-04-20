package com.example.demo.page;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/page")
public class PageController {

    @GetMapping("index")
    public ModelAndView getPageIndex(ModelAndView mav)
    {
        log.info("getPageIndex() function Executed.");
        mav.setViewName("content/testPage");
 
        return mav;
    }
    
    @PostMapping("get")
    public void getPageController(HttpServletResponse response) throws IOException
    {
        Gson gson = new Gson();

        Map<String, Object> jsonMap = new HashMap<>();
        
        log.info("getPageController() function Executed.");

        String[] season_arr = {"봄", "여름", "가을", "겨울"};

        String[] emotion_arr = {"차가운", "따뜻한", "부드러운", "감정적인", "카리스마 있는", "침착한", "열정적인"};

        String[] target_arr = {"고양이", "강아지", "토끼", "호랑이", "사자", "말", "고슴도치", "햄스터", "거북이"};

        String comment = 
            season_arr[(int) (Math.random() * season_arr.length)] + "의 " + 
            emotion_arr[(int) (Math.random() * emotion_arr.length)] + " " +
            target_arr[(int) (Math.random() * target_arr.length)];

        jsonMap.put("comment", comment);

        response.getWriter().print(gson.toJson(jsonMap));
    }

    @GetMapping("search")
    public ModelAndView getSearchPlayer(@RequestParam String name, ModelAndView mav) throws IOException
    {
        mav.setViewName("content/failed");
        if (name == null || name.isEmpty()) {
            return mav;
        }

        String url = "https://maplestory.nexon.com/N23Ranking/World/Total?c=" + URLEncoder.encode(name, "UTF-8");

        Document doc = Jsoup.parse(new URL(url).openStream(), "UTF-8", url);

        Element rankTable = doc.body().selectFirst(".rank_table");

        if (rankTable == null) {
            return mav;
        }

        Elements trList = rankTable.getElementsByTag("tr");

        for (Element tr : trList) {
            Elements tdList = tr.getElementsByTag("td");

            if (tdList.size() < 6) {
                continue;
            }

            if (tdList.html().toString().toLowerCase().contains(name.toLowerCase())) {
                String imgSrc = tdList.get(1).getElementsByTag("img").get(0).attr("src");
                if (imgSrc == null) {
                    continue;
                }

                String[] nameAndJob = tdList.get(1).text().split(" ", 2);
                if (nameAndJob.length < 2) {
                    continue;
                }

                String realName = nameAndJob[0];
                String job = nameAndJob[1];
                String level = tdList.get(2).text();
                String exp = tdList.get(3).text();
                String fame = tdList.get(4).text();
                String guild = tdList.get(5).text();

                if (guild == null || guild.isEmpty()) {
                    guild = "없음";
                }

                mav.addObject("imgSrc", imgSrc);
                mav.addObject("name", realName);
                mav.addObject("job", job);
                mav.addObject("level", level);
                mav.addObject("exp", exp);
                mav.addObject("fame", fame);
                mav.addObject("guild", guild);
                mav.setViewName("content/search");

                return mav;
            }
        }

        return mav;
    }
}
