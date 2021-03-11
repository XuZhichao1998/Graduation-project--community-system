package com.example.test.mapper;

import com.example.test.entity.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {

    //@select * from product where product_name = #{productName}
    public Product getProductByName(String productName); //通过商品名称查找

    //@select * from product where product_id = #{productId}
    public Product getProductById(String productId); //通过商品id查找某个商品

    //@select product_id,product_name,unit_price,unit,tb1.category_id,category_name
    // from product as tb1 left join product_category as tb2 on tb1.category_id=tb2.category_id
    // where 1=1 and pName like concat(concat('%',#{0}),'%')
    public List<Product> getProductList(String productName, int page, int limit); //模糊查询商品列表

    //@select count(product_id) from product;
    public int getProductCount(); //查询商品个数

    //@insert into product values(#{productId}, #{productName}, #{unitPrice}, #{unit}, #{categoryId})
    public int addProduct(Product product); //插入一条商品数据，返回数据库中被影响的行数

    //@update product set unit_price = #{unitPrice} where product_id = #{productId};
    public int updateProduct(Product product); //更新商品价格信息

    //@delete from product where product_id = #{productId};
    public int deleteProduct(String productId); //通过商品id删除商品，返回数据库中被影响的行数


}
