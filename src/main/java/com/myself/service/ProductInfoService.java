package com.myself.service;

import com.myself.pojo.ProductInfo;
import com.github.pagehelper.PageInfo;
import com.myself.pojo.vo.ProductInfoVo;

import java.util.List;
/**
 * @Author：摘到星星了吗
 * @Date:2022/2/17 -02-17-13:33
 * @Description: com.myself.service
 * @version:1.0
 */
public interface ProductInfoService {

     List<ProductInfo> getAll();

    //分页功能实现

    PageInfo splitPage(int pageNum,int pageSize);

    //保存商品

    int save(ProductInfo info);

    //根据主键编号选择商品

    ProductInfo getById(int pid);

    //更新商品

    int update(ProductInfo info);

    //删除单个商品

    int delete(int pid);

    //删除多个商品

    int deleteBatch(String[] ids);

    //多条件商品查询

    List<ProductInfo> selectCondition(ProductInfoVo vo);

    //多条件查询分页

    PageInfo splitPageVo(ProductInfoVo vo,int pageSize);
}
