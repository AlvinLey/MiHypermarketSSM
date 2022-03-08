package com.myself.listener;

import com.myself.pojo.ProductType;
import com.myself.service.ProductTypeService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;

/**
 * @Author：摘到星星了吗
 * @Date:2022/3/2 -03-02-16:16
 * @Description: com.myself.listener
 * @version:1.0
 */
@WebListener
public class ProductTypeListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //因为之前已经创建过监听器对象，不确定那个监听器会先执行，所以进行手动获取ProductTypeServiceImple对象

        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext_*.xml");
        ProductTypeService productTypeService = (ProductTypeService) context.getBean("ProductTypeServiceImpl");
        List<ProductType> typeList = productTypeService.getAll();
        //放入全局应用作用域中，供新增页面，修改页面，前台查询功能提供全部商品类别集合
        servletContextEvent.getServletContext().setAttribute("typeList",typeList);
     }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
