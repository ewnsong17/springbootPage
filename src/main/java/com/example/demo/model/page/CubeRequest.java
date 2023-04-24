package com.example.demo.model.page;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.Map;

import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;

@ToString
@Slf4j
public class CubeRequest {
    private int itemID;
    private int cubeCnt;
    private int cubePotential;

    public void tryRePotential() throws FileNotFoundException, ParseException
    {

        Reader reader = new FileReader("cube_rate.json");
        JSONParser parser = new JSONParser(reader);

        Map<?, ?> jsonMap = (Map<?, ?>) parser.parse();
        String key = getCubeKeyFromItemID();

        double rate = 0;

        if (!key.isEmpty()) {
            Map<?, ?> cubeInfoMap = (Map<?, ?>) jsonMap.get(key);

            if (cubeInfoMap != null) {
                Map<?, ?> upInfoMap = (Map<?, ?>) cubeInfoMap.get("up_info");
                String potentialInfo = getNextPotentialInfo();

                if (upInfoMap != null && !potentialInfo.isEmpty()) {
                    rate = Double.parseDouble(upInfoMap.get(potentialInfo).toString());
                }
            }
        }

        log.info("rate : " + rate);

        if (rate > 0) {
            cubeCnt++;

            if (rate > Math.random() * 100) {
                cubePotential++;
                
                log.info("cube rate up : " + cubePotential);
            }
        }
    }

    public String getCubeKeyFromItemID()
    {
        String key = "";
        switch (itemID) {
            case 5062009:
                key = "red_cube";
                break;
            case 5062010:
                key = "black_cube";
                break;
        }
        return key;
    }

    public String getNextPotentialInfo()
    {
        String info = "";
        switch (cubePotential) {
            case 1:
                info = "epic";
                break;
            case 2:
                info = "unique";
                break;
            case 3:
                info = "legendary";
                break;
        }
        return info;
    }
}
