package cn.mapper;

import cn.domain.Product;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ProductMapper {
    /**
     * 获取所有商品
     */
//    @Select("select * from tb_product")
//    List<Product> findAllProduct();

    /**
     * 添加商品
     */
    @Insert("insert into tb_product(name,price,produceTime,madeFactory) values(#{name},#{price},#{produceTime},#{madeFactory})")
    int insertProduct(Product product);

    /**
     * 根据id查找商品
     */
    @Select("select * from tb_product where id = #{id}")
    Product findProductById(int id);

    /**
     * 根据id删除商品
     */
    @Delete("delete from tb_product where id = #{id}")
    int deleteProductById(int id);

    /**
     * 更新商品信息
     * */
    @Update("update tb_product set name = #{name},price=#{price},produceTime=#{produceTime},madeFactory=#{madeFactory} where id =#{id}")
    int updateProduct(Product product);

    /**
     * 模糊搜索商品
     * */
    @Select("select * from tb_product where name like concat('%',#{name,jdbcType=VARCHAR},'%')")
    List<Product> selectLikeProduct(String name);
}
