package cn.service;

import cn.domain.Product;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    //获取所有商品
//    PageInfo<Product> findAllProduct(int pageNum,int PageSize);
    //根据id删除商品
    int deleteProductById(int id);
    //添加商品
    int insertProduct(Product product);
    //根据id查找商品
    Product findProductById(int id);
    //更新商品信息
    int updateProduct(Product product);
    //模糊搜索商品信息
    PageInfo<Product> findLikeProduct(int pageNum,int pageSize,String name);
}
