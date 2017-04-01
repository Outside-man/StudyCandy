package com.studycandy.controller;

import com.studycandy.annotation.Role;
import com.studycandy.core.BaseController;
import com.studycandy.model.Post;
import com.studycandy.service.PostService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Coding with Intellij IDEA
 * Author: Chenls
 * Time: 2017/3/22
 */


/*
     *广场首页 ：campusSquare
     *显示用户所有帖子界面 ：userpostlist
     *帖子详细界面 ：postview
     *帖子修改界面 ：modifypostview
     *添加帖子界面 ：addpostview
     *
     */
@RequestMapping("square")
@Controller
public class SquareController extends BaseController {
    @Autowired
    private PostService postService;

    @RequestMapping(value = "", method = GET)
    public String square(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("allpostlist", postService.getAllPost());
        return "square/campusSquare";
    }
    @RequestMapping(value = "/addpost", method = POST)
    public String addPost(HttpServletRequest request, HttpServletResponse response, Model model,
                          @RequestParam String title,
                          @RequestParam String content) {
        Post entity = new Post();
        entity.setPostTitle(title);
        entity.setPostContent(content);
        entity.setGmtCreate(
                new Timestamp(new Date().getTime())
        );
        entity.setGmtModified(new Timestamp(new Date().getTime()));
        entity.setUserId(this.getCurrentUser(request).getId());

        try {
            postService.save(entity);
        } catch (Exception e) {
            return ajaxReturn(response, null, "", -1);
        }
        return ajaxReturn(response, null, "发帖成功！", 0);
    }
    //支持ajax取出帖子最新
    @RequestMapping(value = "/postList", method = GET)
    public String postList(HttpServletRequest request, HttpServletResponse response, Model model) {
            List<Post> list= new ArrayList<Post>();
            List<Post> t =  postService.getAllPost();
            int t_size = t.size(),m = 0;
            m=t_size>5 ? 5 : t_size;
            for(int i =1;i<=m;i++){
                list.add(t.get(t.size()-i));
            }
        return ajaxReturn(response, list, "", 0);
    }
    //获取用户发布的所有帖子
    @RequestMapping(value = "/allpost")
    public String postList(HttpServletRequest request, HttpServletResponse response, Model model,
                           @RequestParam Integer userId) {
        model.addAttribute("allpostlist", postService.getUserPostList(userId));
        return "userpostlist";
    }
    //删除帖子
    @RequestMapping(value = "/deletePost", method = POST)
    public String deletePost(HttpServletRequest request, HttpServletResponse response, Model model,
                             @RequestParam Integer id) {
        //判断是否是帖子主人删除
        if(this.getCurrentUser(request).getId()==postService.getPostById(id).getUserId())
            postService.deleteById(id);
        return "square/campusSquare";
    }
    //获取单个帖子详细界面
    @RequestMapping(value = "/postview/{id}")
    public String getPost(HttpServletRequest request, HttpServletResponse response, Model model,
                          @PathVariable("id") Integer id) {
        model.addAttribute("post",postService.getPostById(id));
        return "postview";
    }
    //进入修改帖子界面
    @RequestMapping(value = "/modify/{id}", method = POST)
    public String modify(HttpServletRequest request, HttpServletResponse response, Model model,
                         @PathVariable("id") Integer id) {
        Post t = postService.getPostById(id);
        //判断是否是帖子主人要进行修改
        if(this.getCurrentUser(request).getId()==t.getUserId()){
            t.setGmtModified(new Timestamp(new Date().getTime()));
            model.addAttribute("post",t);
            return "modifypostview";
        }else{
            return "campusSquare";
        }
    }
    //提交帖子修改
    @RequestMapping(value = "/postmodify", method = POST)
    public String modifyPost(HttpServletRequest request, HttpServletResponse response, Model model,
                             @RequestParam Post post) {
        //判断是否是帖子主人修改
        if(this.getCurrentUser(request).getId()==post.getUserId())
            postService.modifyPost(post);
        model.addAttribute("post",post);
        //修改完之后转到帖子详细界面
        return "redirect:square/postview/"+post.getId();
    }
}
