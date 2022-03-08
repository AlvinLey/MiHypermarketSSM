package com.myself.service.impl;

import com.myself.mapper.ProductTypeMapper;
import com.myself.pojo.ProductType;
import com.myself.pojo.ProductTypeExample;
import com.myself.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author：摘到星星了吗
 * @Date:2022/3/2 -03-02-16:06
 * @Description: com.myself.service.impl
 * @version:1.0
 */

@Service("ProductTypeServiceImpl")
public class ProductTypeServiceImpl implements ProductTypeService {

    //业务逻辑层一定有数据访问层对象

    @Autowired
    ProductTypeMapper productTypeMapper;

    @Override
    public List<ProductType> getAll() {
        return productTypeMapper.selectByExample(new ProductTypeExample());
    }
}
