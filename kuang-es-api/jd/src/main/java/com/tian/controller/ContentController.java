package com.tian.controller;

import com.alibaba.fastjson.JSON;
import com.tian.service.ContentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ContentController {

    @Autowired
    private ContentService contentService;

    /**
     * 解析 并存储到es中
     * @param keyword
     * @return
     * @throws IOException
     */
    @GetMapping("/parse/{keyword}")
    public String parse(@PathVariable String keyword) throws IOException {
        Boolean flag = contentService.parseContent(keyword);
        if (flag){
            return "批量插入es操作成功";
        }else {
            return "批量插入es操作失败";
        }
    }

    /**
     * 分页搜索
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/search/{keyword}/{pageNum}/{pageSize}")
    public String searchPage(@PathVariable String keyword,
                             @PathVariable int pageNum,
                             @PathVariable int pageSize) throws IOException {
        log.warn("接受到请求");
        List<Map<String, Object>> list = contentService.searchPageHighlighter(keyword, pageNum,pageSize);
        return JSON.toJSONString(list);
    }
}
