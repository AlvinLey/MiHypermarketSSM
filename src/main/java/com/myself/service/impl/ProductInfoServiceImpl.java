package com.myself.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.myself.mapper.ProductInfoMapper;
import com.myself.pojo.ProductInfo;
import com.myself.pojo.ProductInfoExample;
import com.myself.pojo.vo.ProductInfoVo;
import com.myself.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author：摘到星星了吗
 * @Date:2022/2/17 -02-17-13:36
 * @Description: com.myself.service.impl
 * @version:1.0
 */
@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    //业务逻辑层一定有持久层对象

    @Autowired
    ProductInfoMapper productInfoMapper;
    @Override
    public List<ProductInfo> getAll() {

        return productInfoMapper.selectByExample(new ProductInfoExample());
    }

    //select * from product_info limit 起始记录数=（（当前页-1）*每页的条数），每页取几行

    @Override
    public PageInfo splitPage(int pageNum, int pageSize) {

        //分页插件使用pageHelper工具类完成分页设置
        //值得注意的是，一定在取集合之前设置好
        PageHelper.startPage(pageNum,pageSize);

        //进行pageInfo的数据分装
        //进行有条件的查询操作，必须要创建ProductInfoExample对象
        ProductInfoExample example = new ProductInfoExample();
        //设置排序，按主键降序排序
        //select * from product_info order by p_id desc;
        example.setOrderByClause("p_id desc");
        //设置完排序后，取集合
        List<ProductInfo> list = productInfoMapper.selectByExample(example);
        //将查询结果封装到PageInfo中
        PageInfo<ProductInfo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public int save(ProductInfo info) {
        return productInfoMapper.insert(info);
    }

    @Override
    public ProductInfo getById(int pid) {
        return productInfoMapper.selectByPrimaryKey(pid);
    }

    @Override
    public int update(ProductInfo info) {
        return productInfoMapper.updateByPrimaryKey(info);
    }

    @Override
    public int delete(int pid) {
        return productInfoMapper.deleteByPrimaryKey(pid);
    }

    @Override
    public int deleteBatch(String[] ids) {

        return productInfoMapper.deleteBatch(ids);
    }

    @Override
    public List<ProductInfo> selectCondition(ProductInfoVo vo) {
        return productInfoMapper.selectCondition(vo);
    }

    @Override
    public PageInfo<ProductInfo> splitPageVo(ProductInfoVo vo, int pageSize) {
        //取出集合之前,先要设置PageHelper.startPage()属性
        PageHelper.startPage(vo.getPage(),pageSize);
        List<ProductInfo> list = productInfoMapper.selectCondition(vo);
        return new PageInfo<>(list);
    }


}

