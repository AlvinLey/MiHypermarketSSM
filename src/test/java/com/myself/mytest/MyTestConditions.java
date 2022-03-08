package com.myself.mytest;

import com.myself.mapper.ProductInfoMapper;
import com.myself.pojo.ProductInfo;
import com.myself.pojo.vo.ProductInfoVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.PropertyMatches;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @Author：摘到星星了吗
 * @Date:2022/3/3 -03-03-17:29
 * @Description: com.myself.mytest
 * @version:1.0
 */
//用这个类接管单元测试，使用spring的单元测试
    //测试文件在xml中，所以需要spring的依赖注入
@RunWith(SpringJUnit4ClassRunner.class)
//加入配置文件
@ContextConfiguration(locations = {"classpath:applicationContext_dao.xml","classpath:applicationContext_service.xml"})
public class MyTestConditions {

    @Autowired
    ProductInfoMapper mapper;

    @Test
    public void testSelectConditions(){
        ProductInfoVo vo = new ProductInfoVo();
        //vo.setPname("4");
        //vo.setTypeid(3);
        vo.setLprice(3000);
        vo.setHprice(6999);
        List<ProductInfo> list = mapper.selectCondition(vo);
        list.forEach(productInfo -> System.out.println(productInfo));
    }
}
