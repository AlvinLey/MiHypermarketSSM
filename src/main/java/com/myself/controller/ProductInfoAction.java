package com.myself.controller;

import com.github.pagehelper.PageInfo;
import com.myself.pojo.ProductInfo;
import com.myself.pojo.vo.ProductInfoVo;
import com.myself.service.ProductInfoService;
import com.myself.utils.FileNameUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @Author：摘到星星了吗
 * @Date:2022/2/17 -02-17-13:45
 * @Description: com.myself.controller
 * @version:1.0
 */
@Controller
@RequestMapping("/prod")
public class ProductInfoAction {

    //每页显示的记录条数

    public static final int PAGE_SIZE  = 5;

    //需要跨方法使用
    String saveFileName = "";

    //界面层一定有业务逻辑层对象

    @Autowired
    ProductInfoService productInfoService;

    //显示全部商品不分页

    @RequestMapping("/getAll")
    public String getAll(HttpServletRequest request){
        List<ProductInfo> list = productInfoService.getAll();
        request.setAttribute("list",list);
        return "product";
    }

    //显示每页的记录数

    @RequestMapping("/split")
    public String split(HttpServletRequest request){
        PageInfo pageInfo = productInfoService.splitPage(1,PAGE_SIZE);
        request.setAttribute("info",pageInfo);
        return "product";
    }

    //使用ajax分页翻页处理

    /*
    只要不进行视图跳转就必须加这个注释，并且这里数据存放的作用域必须是session。因为生命周期不同并且
    在这里把新的五条数据返回到ajax是ajax会重新刷新table标签，而table中有el表达式要从作用域中获取新的
    存放新pageInfo对象的request作用于已经在刷新的table是被销毁了，而session作用域可以长期保存保存数据
    所以这是必须使用session
    * */

    @ResponseBody
    @RequestMapping("/ajaxsplit")
    public void ajaxSplit(ProductInfoVo vo, HttpSession session){
        //取得当前page参数的页面的数据

        PageInfo info = productInfoService.splitPageVo(vo,PAGE_SIZE);
        session.setAttribute("info",info);
    }

    @ResponseBody
    @RequestMapping("/condition")
    public void condition(ProductInfoVo vo,HttpSession session){
        List<ProductInfo> list = productInfoService.selectCondition(vo);
        session.setAttribute("list",list);
    }
    //异步ajax文件上传处理
    //使用ajax就要使用@ResponseBody注释

    @ResponseBody
    @RequestMapping("/ajaxImg")
    public Object ajaxImg(MultipartFile pimage , HttpServletRequest request){

        //提取生成文件名UUID＋文件上传图片的后缀
        saveFileName = FileNameUtil.getUUIDFileName()+FileNameUtil.getFileType(pimage.getOriginalFilename());

        //得到项目中图片的存储路径
        String path = request.getSession().getServletContext().getRealPath("/image_big");

        //转存
        try {
            pimage.transferTo(new File(path+File.separator+saveFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //返回客户端JSON对象,封装图片的路径,为了在页面实现立即回显
        JSONObject object = new JSONObject();
        object.put("imgurl", saveFileName);
        return object.toString();
    }

    @RequestMapping("/save")
    public String save(ProductInfo info,HttpServletRequest request){

        info.setpImage(saveFileName);
        info.setpDate(new Date());

        int num = -1;
        try {
            num = productInfoService.save(info);
        }catch (Exception e){
            e.printStackTrace();
        }
        if (num > 0){
            request.setAttribute("msg","添加成功");
        }else {
            request.setAttribute("msg","添加失败");
        }

        //清空saveFileName变量中的内容,为了下次增加或修改的异步ajax的上传处理
        saveFileName = "";

        //增加成功后应该重新访问数据库,所以跳转到分页显示的action上
        return "forward:/prod/split.action";
    }

    //根据pid选择商品

    @RequestMapping("/one")
    public String one(int pid,  Model model){
        ProductInfo info = productInfoService.getById(pid);
        model.addAttribute("prod",info);
        return "update";
    }

    //修改所选择的pid内容

    @RequestMapping("/update")
    public String update(ProductInfo info, HttpServletRequest request){
        //如果进行过了ajax的异步上传则savaFileName中会有新的图片名称
        //如果照片没有改变，则saveFileName为空，实体类info使用隐藏表单域提供上来的pImage原始图片名称
        if(!saveFileName.equals("")){
            info.setpImage(saveFileName);
        }
        //完成更新处理
        int num = -1;
        try {
            num = productInfoService.update(info);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //更新提示
        if (num>0){
            request.setAttribute("msg","商品更新成功");
        }else{
            request.setAttribute("msg","商品更新失败！");
        }
        //处理完成后saveFileName中可能会有数据，而下次更新时需要根据这个变量作为更新依据
        //所以需要把它清空
        saveFileName = "";
        return "forward:/prod/split.action";
    }

    //删除单个商品

    @RequestMapping("/delete")
    public String delete(int pid,HttpServletRequest request){
        int num = -1 ;
        try {
            num = productInfoService.delete(pid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (num>0){
            request.setAttribute("msg","删除成功");
        }else {
            request.setAttribute("msg","删除成功");
        }
        return "forward:/prod/deleteAjaxSplit.action";
    }

    //删除后的页面显示

    @ResponseBody
    @RequestMapping(value = "deleteAjaxSplit",produces = "text/html;charset=UTF-8")
    public Object deleteAjaxSplit(HttpServletRequest request){
        //取得第一页的数据
    PageInfo info = productInfoService.splitPage(1,PAGE_SIZE);
    request.getSession().setAttribute("info",info);
    return request.getAttribute("msg");
    }

    //批量删除

    @RequestMapping("/deleteBatch")
    public String deleteBatch(String pids,HttpServletRequest request){

        //将上传来的字符串截开，行成商品id的字符串组
        String[] split = pids.split(",");
        try {
            int num = productInfoService.deleteBatch(split);
            if (num>0){
                request.setAttribute("msg","商品批量删除成功");
            }else {
                request.setAttribute("msg","商品批量删除失败！");
            }
        } catch (Exception e) {
            request.setAttribute("msg","商品不可删除！");
        }

        return "forward:/prod/deleteAjaxSplit.action";
    }


}
