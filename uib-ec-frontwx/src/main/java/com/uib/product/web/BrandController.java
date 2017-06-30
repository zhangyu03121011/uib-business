package com.uib.product.web;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uib.base.BaseController;
import com.uib.member.entity.MemMember;
import com.uib.product.entity.Brand;
import com.uib.product.entity.Product;
import com.uib.product.entity.ProductCategory;
import com.uib.product.service.BrandService;
import com.uib.product.service.ProductCategoryService;
import com.uib.product.service.ProductService;

/**
 * 商品品牌Controller
 * @author gaven
 * @version 2015-06-13
 */
@Controller
@RequestMapping(value = "/brand")
public class BrandController extends BaseController {

	@Autowired
	private BrandService brandService;
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductCategoryService productCategoryService;
	
	/**
	 * 查询品牌
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/select")
	private String select(HttpServletRequest request, HttpServletResponse response,ModelMap modelMap){
		MemMember member = (MemMember)request.getSession().getAttribute("member");
		String id=request.getParameter("id");
		String flag=request.getParameter("sort")==null?"":request.getParameter("sort");
		String productcategoryid=request.getParameter("productcategoryid")==null?"":request.getParameter("productcategoryid");
		try {
			id=id==null||"".equals(id)?"":id;
			Brand brand=brandService.get(id);
			List<Product> list=productService.selectProductByBrand(id,flag,productcategoryid); 
			Integer number=productService.getAllProductNum(id);
			//查询所有的产品分类
			List<ProductCategory> list_productCategory=productCategoryService.getCategoryByProduct(id);
			//查询对应品牌和分类的产品数量
			for(ProductCategory productCategory:list_productCategory){
				Integer  num=productService.selectProductByBrandAndCategory(id,productCategory.getId());
				productCategory.setNumber_product(num);
			}
			
			if(flag.equals("desc")){
				flag="asc";
			}else if(flag.equals("asc")){
				flag="desc";
			}
			modelMap.addAttribute("number", number);
			modelMap.addAttribute("brand", brand);
			modelMap.addAttribute("list", list);
			modelMap.addAttribute("flag", flag);
			modelMap.addAttribute("list_productCategory", list_productCategory);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("查询品牌出错");
		}
		return "/product/pinpai";
	}
	/*@ModelAttribute
	public Brand get(@RequestParam(required=false) String id) {
		Brand entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = brandService.get(id);
		}
		if (entity == null){
			entity = new Brand();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(Brand brand, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Brand> page = brandService.findPage(new Page<Brand>(request, response), brand); 
		model.addAttribute("page", page);
		return "modules/product/brandList";
	}

	@RequestMapping(value = "form")
	public String form(Brand brand, Model model) {
		model.addAttribute("brand", brand);
		return "modules/product/brandForm";
	}
	
	@RequestMapping(value = "updateFormView")
	public String updateFormView(Brand brand, Model model) {
		model.addAttribute("brand", brand);
		return "modules/product/brandupdateForm";
	}

	@RequestMapping(value = "save")
	public String save(Brand brand, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, brand)){
			return form(brand, model);
		}
		brandService.save(brand);
		addMessage(redirectAttributes, "保存商品品牌成功");
		return "redirect:"+Global.getAdminPath()+"/product/brand/?repage";
	}
	
	@RequestMapping(value = "update")
	public String update(Brand brand, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, brand)){
			return form(brand, model);
		}
		brandService.update(brand);
		addMessage(redirectAttributes, "修改商品品牌成功");
		return "redirect:"+Global.getAdminPath()+"/product/brand/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(Brand brand, RedirectAttributes redirectAttributes) {
		brandService.delete(brand);
		addMessage(redirectAttributes, "删除商品品牌成功");
		return "redirect:"+Global.getAdminPath()+"/product/brand/?repage";
	}*/

}