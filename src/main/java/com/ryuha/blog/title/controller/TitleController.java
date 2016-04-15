package com.ryuha.blog.title.controller;

import com.ryuha.blog.category.service.CategoryService;
import com.ryuha.blog.title.service.TitleService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by Misaka on 2016-03-16.
 */

@Controller(value = "titleController")
@RequestMapping("/title")
public class TitleController {
    @Resource(name = "titleService")
    public TitleService titleService;

    @RequestMapping(method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    public @ResponseBody String getName() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("title", this.titleService.getName());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }
}