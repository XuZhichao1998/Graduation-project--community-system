package com.example.test.controller;

import com.example.test.entity.Product;
import com.example.test.service.ProductService;
import com.example.test.util.LayuiData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

@Controller
public class ProductController {
    //将Service注入web层
    @Autowired
    private ProductService productService;

    /**
     * 商品管理页面的入口请求
     * 通过Session进行权限访问控制
     * */
    @RequestMapping("/productList1")
    public String prodcutList1(HttpServletRequest request) {
        if(request.getSession().getAttribute("curAdminName")==null)
        {
            return "404"; //没有管理员登陆，妄图访问投诉管理页面，直接跳到404
        }
        return "productListPage";
    }

    /**
     * 跳转到商城信息的请求
     * */
    @RequestMapping("/shopIndex1") public String showIndex1() {
        return "shopIndex";
    }


    /**
     * 查询商品列表
     * @return
     */
    @RequestMapping("/getList")
    @ResponseBody
    public LayuiData getProductList (HttpServletRequest request){
        String productName = request.getParameter("productName");
        if(productName==null){
            productName="";
        }
        int page = Integer.parseInt(request.getParameter("page"));
        int limit = Integer.parseInt(request.getParameter("limit"));
        if(page>0){
            page = (page-1)*limit;
        }
        LayuiData layuiData = new LayuiData();
        List<Product> productList = productService.getProductList(productName,page,limit);
        int count = productService.getProductCount();
        layuiData.setCode(0);
        layuiData.setCount(count);
        layuiData.setMsg("数据请求成功");
        layuiData.setData(productList);
        return layuiData;
    }

    /**
     * 去新增商品界面
     * @return
     */
    @RequestMapping("/toProduct")
    public String toProduct (){

        return "productAdd";  //去到productAdd.html
    }

    /**
     * 新增
     * @param productName
     * @param unitPrice
     * @return
     */
    @RequestMapping("/productAdd")
    @Transactional
    @ResponseBody
    public Integer productAdd (String productId, String productName, BigDecimal unitPrice, String unit,Integer categoryId){
        Product product = new Product();
        product.setProductId(productId);
        product.setProductName(productName);
        product.setUnitPrice(unitPrice);
        product.setUnit(unit);
        product.setCategoryId(categoryId);

        int num = productService.addProduct(product);
        return num;
    }


    /**
     * 根据productId删除商品
     * @param productId
     * @return
     */
    @RequestMapping("/deleteProduct")
    @ResponseBody
    public Integer deletePrduct(String productId) {
        int num = productService.deleteProduct(productId);
        return num;
    }

    /**
     * 去查看界面
     * @param productId
     * @param model
     * @return
     */
    @RequestMapping("/toDetail")
    public String toDetail(String productId, Model model) {

        Product product = productService.getProductById(productId);
        model.addAttribute("product",product);
        return "productDetail";
    }


    /**
     * 去修改界面
     * @param productId
     * @param model
     * @return
     */
    @RequestMapping("/toUpdate")
    public String toUpdate(String productId, Model model) {

        Product product = productService.getProductById(productId);
        model.addAttribute("product",product);
        return "productUpdate";
    }


    /**
     * 根据id修改商品信息
     * @return
     */
    @RequestMapping("/productUpdate")
    @Transactional
    @ResponseBody
    public int productUpdate (@RequestParam("productId")String pid, @RequestParam("productName")String pname, @RequestParam("unitPrice")BigDecimal price, @RequestParam("unit") String unit, @RequestParam("categoryId")Integer cid){
        Product product = new Product();
        product.setUnitPrice(price);
        product.setProductId(pid);
        product.setProductName(pname);
        product.setUnit(unit);
        product.setCategoryId(cid);
        int num = productService.updateProduct(product);
        return num;
    }

    /*
    @RequestMapping(value="productUpdate",method= RequestMethod.POST)
    public int productUpdate(@RequestBody Product product) {
        System.out.println("id:"+product.getProductId()+",name:"+product.getProductName()+",price:"+product.getUnitPrice()+",unit:"+product.getUnitPrice()+",cid:"+product.getCategoryId());
        int num = productService.updateProduct(product);
        return num;
    }*/
}
