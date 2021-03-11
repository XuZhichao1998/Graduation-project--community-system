package com.example.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

@Controller
public class EpidemicMapController {

    @GetMapping("/epidemicMapshow")
    public String epidemicMapshow(Model model)  {
        String epidemicJsonData = "{}";
        try {
            URL url = new URL("https://zaixianke.com/yq/all");
            URLConnection conn = url.openConnection();
            InputStream is = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            epidemicJsonData = br.readLine();
            System.out.println(epidemicJsonData);
            br.close();

        } catch (Exception exception) {
            System.out.println(exception.toString());
        }
        model.addAttribute("epidemicJsonData",epidemicJsonData);
        return "showEpidemicMap"; // showEpidemicMap.html
    }


}
