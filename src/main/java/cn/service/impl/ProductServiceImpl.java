package cn.service.impl;

import cn.domain.Customer;
import cn.domain.Product;
import cn.mapper.ProductMapper;
import cn.service.ProductService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    public void setProductMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }


//    @Override
//    public PageInfo<Product> findAllProduct(int pageNum, int pageSize) {
//        PageHelper.startPage(pageNum, pageSize);
//        //查询所有商品
//        List<Product> productList = productMapper.findAllProduct();
//        PageInfo<Product> productPageInfo = new PageInfo<>(productList);
//        return  productPageInfo;
//    }

    @Override
    public int deleteProductById(int id) {
        return productMapper.deleteProductById(id);
    }

    @Override
    public int insertProduct(Product product) {
        return productMapper.insertProduct(product);
    }

    @Override
    public Product findProductById(int id) {
        return productMapper.findProductById(id);
    }

    @Override
    public int updateProduct(Product product) {
        return productMapper.updateProduct(product);
    }

    @Override
    public PageInfo<Product> findLikeProduct(int pageNum, int pageSize,String name) {
        PageHelper.startPage(pageNum, pageSize);
        //模糊搜索的所有商品
        List<Product> productList = productMapper.selectLikeProduct(name);
        PageInfo<Product> productPageInfo = new PageInfo<>(productList);
        return  productPageInfo;
    }
}
