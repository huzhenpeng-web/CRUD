package cn.action;

import cn.domain.Product;
import cn.service.ProductService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("/product")
public class ProductAction {

    @Autowired
    private ProductService productService;

    //获取商品
/*    @RequestMapping("/getAllProduct")
    public PageInfo<Product> getAllProduct(int pageNum, int pageSize) {
        PageInfo pageInfo = productService.findAllProduct(pageNum, pageSize);
        System.out.println(pageInfo.getList());
        return pageInfo;
    }*/

    //获取模糊搜索出来的商品
    @RequestMapping("/selectLikeProduct")
    public PageInfo<Product> getLikeProduct(int pageNum, int pageSize,String name){
        PageInfo pageInfo = productService.findLikeProduct(pageNum,pageSize,name);
//        System.out.println(pageInfo);
        return  pageInfo;
    }

    //删除商品
    @RequestMapping("/deleteProduct")
    public int deleteProduct(@RequestBody int id){
        return productService.deleteProductById(id);
    }

    //添加商品
    @RequestMapping(value = "/insertProduct",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public int insertProduct(@RequestBody Product product){
        return productService.insertProduct(product);
    }

    //根据id查找商品
    @RequestMapping("/findProductById")
    public Product findProduct(int id){
        return productService.findProductById(id);
    }

    //更新商品信息
    @RequestMapping(value = "/updateProduct",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public int updateProduct(@RequestBody Product product){
        return productService.updateProduct(product);
    }
}
