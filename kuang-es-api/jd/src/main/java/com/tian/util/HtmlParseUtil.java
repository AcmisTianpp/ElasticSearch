package com.tian.util;

import com.tian.entity.Content;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Component
public class HtmlParseUtil {

    public List<Content> parseGoods(String keyword) throws IOException {
        // 获取请求  https://search.jd.com/Search?keyword=java
        // 京东已经设置了登录后才能搜索，改用苏宁
        List<Content> list = new ArrayList<>();
        String url = "https://search.suning.com/"+keyword+"/";
        Document document = Jsoup.parse(new URL(new String(url.getBytes(),"utf-8")), 30000);
        Element element = document.getElementById("product-list");
        //System.out.println(element.html());
        // 获取所有的li元素
        Elements lis = element.getElementsByTag("li");
        // 获取元素中的内容
        for (Element li : lis) {
            String img = li.getElementsByTag("img").eq(0).attr("src");
            String price = li.getElementsByClass("def-price").eq(0).text();
            String title = li.getElementsByClass("title-selling-point").eq(0).text();
            list.add(new Content(img,price,title));
        }
        return list;
    }

    public static void main(String[] args) throws IOException {
//        String url = "https://search.suning.com/java/";
//        // 解析网页
//        Document document = Jsoup.parse(new URL(url),30000);
//        Element element = document.getElementById("product-list");
//        System.out.println(element.html());
//        // 获取所有的li元素
//        Elements lis = element.getElementsByTag("li");
//        // 获取元素中的内容
//        for (Element li : lis) {
//            String img = li.getElementsByTag("img").eq(0).attr("src");
//            String price = li.getElementsByClass("def-price").eq(0).text();// 打印的html里面没有价格，所以拿不到价格
//            String title = li.getElementsByClass("title-selling-point").eq(0).text();
//
//            System.out.println("===========================");
//            System.out.println(img);
//            System.out.println(price);
//            System.out.println(title);
//        }
        new HtmlParseUtil().parseGoods("vue").forEach(System.out::println);

    }
}
