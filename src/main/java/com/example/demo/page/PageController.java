package com.example.demo.page;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/page")
public class PageController {

    @GetMapping("search")
    public ModelAndView getSearchPlayer(@RequestParam(required = false) String name, ModelAndView mav) throws IOException
    {
        mav.setViewName("content/result");
        mav.addObject("search", "false");
        if (name == null || name.isEmpty()) {
            log.error("name is null or empty");
            return mav;
        }

        if (!name.matches("^[가-힣0-9a-zA-Z]+$") || name.getBytes("EUC-KR").length < 4) {
            log.error("name not matches the regex");
            return mav;
        }

        String url = "https://maplestory.nexon.com/N23Ranking/World/Total?c=" + URLEncoder.encode(name, "UTF-8");

        Document doc = Jsoup.parse(new URL(url).openStream(), "UTF-8", url);

        Element rankTable = doc.body().selectFirst(".rank_table");

        if (rankTable == null) {
            log.error("rankTable is null or empty");
            return mav;
        }

        Elements trList = rankTable.getElementsByTag("tr");

        for (Element tr : trList) {
            Elements tdList = tr.getElementsByTag("td");

            if (tdList.size() < 6) {
                log.warn("tdList size is not enough. passed");
                continue;
            }

            if (tdList.html().toString().toLowerCase().contains(name.toLowerCase())) {
                String imgSrc = tdList.get(1).getElementsByTag("img").get(0).attr("src");
                if (imgSrc == null) {
                    log.warn("character image is not valid. passed");
                    continue;
                }

                String[] nameAndJob = tdList.get(1).text().split(" ", 2);
                if (nameAndJob.length < 2) {
                    log.warn("cannot parse character name or job. passed");
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

                mav.addObject("search", "true");
                
                return mav;
            }
        }
        
        return mav;
    }
}
