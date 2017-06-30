/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.product.web;

import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.uib.common.utils.UUIDGenerator;
import com.uib.ecmanager.ServiceUtils.Utils;
import com.uib.ecmanager.common.config.Global;
import com.uib.ecmanager.common.mapper.JsonMapper;
import com.uib.ecmanager.common.persistence.Page;
import com.uib.ecmanager.common.utils.StringUtils;
import com.uib.ecmanager.common.web.BaseController;
import com.uib.ecmanager.modules.mem.entity.MemRank;
import com.uib.ecmanager.modules.mem.service.MemRankService;
import com.uib.ecmanager.modules.product.dao.ProductDao;
import com.uib.ecmanager.modules.product.dao.ProductParameterRefDao;
import com.uib.ecmanager.modules.product.dao.ProductPriceRefDao;
import com.uib.ecmanager.modules.product.dao.ProductPropertyRefDao;
import com.uib.ecmanager.modules.product.dao.ProductSpecificationGroupRefDao;
import com.uib.ecmanager.modules.product.entity.Brand;
import com.uib.ecmanager.modules.product.entity.ParameterGroup;
import com.uib.ecmanager.modules.product.entity.Product;
import com.uib.ecmanager.modules.product.entity.ProductAreaRef;
import com.uib.ecmanager.modules.product.entity.ProductParameter;
import com.uib.ecmanager.modules.product.entity.ProductParameterRef;
import com.uib.ecmanager.modules.product.entity.ProductPriceRef;
import com.uib.ecmanager.modules.product.entity.ProductProperty;
import com.uib.ecmanager.modules.product.entity.ProductPropertyRef;
import com.uib.ecmanager.modules.product.entity.ProductSpecification;
import com.uib.ecmanager.modules.product.entity.ProductSpecificationRef;
import com.uib.ecmanager.modules.product.entity.PropertyGroup;
import com.uib.ecmanager.modules.product.entity.SpecificationGroup;
import com.uib.ecmanager.modules.product.service.BrandService;
import com.uib.ecmanager.modules.product.service.ImageProcessorService;
import com.uib.ecmanager.modules.product.service.ParameterGroupService;
import com.uib.ecmanager.modules.product.service.ProductAreaRefService;
import com.uib.ecmanager.modules.product.service.ProductCategoryService;
import com.uib.ecmanager.modules.product.service.ProductPriceRefService;
import com.uib.ecmanager.modules.product.service.ProductService;
import com.uib.ecmanager.modules.product.service.PropertyGroupService;
import com.uib.ecmanager.modules.product.service.SpecificationGroupService;
import com.uib.ecmanager.modules.supplier.service.SupplierService;
import com.uib.ecmanager.modules.sys.entity.Area;
import com.uib.ecmanager.modules.sys.service.AreaService;

/**
 * 商品Controller
 * 
 * @author gaven
 * @version 2015-06-01
 */
@Controller
@RequestMapping(value = "${adminPath}/product/product")
public class ProductController extends BaseController {

	@Autowired
	private ProductService productService;
	@Autowired
	private BrandService brandService;
	@Autowired
	private PropertyGroupService propertyGroupService;
	@Autowired
	private SpecificationGroupService specificationGroupService;
	@Autowired
	private ParameterGroupService parameterGroupService;
	@Autowired
	private ProductCategoryService productCategoryService;
	@Autowired
	private ProductParameterRefDao productParameterRefDao;
	@Autowired
	private ProductPropertyRefDao productPropertyRefDao;
	@Autowired
	private ProductSpecificationGroupRefDao productSpecificationGroupRefDao;
	@Autowired
	private ProductPriceRefService productPriceRefService;
	@Autowired
	private MemRankService memRankService;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private AreaService areaService;
	@Autowired
	private ProductAreaRefService productAreaRefService;
	@Autowired
	private SupplierService supplierService;
	@Autowired
	private ProductPriceRefDao productPriceRefDao;
	
	@ModelAttribute
	public Product get(@RequestParam(required = false) String id) {
		Product entity = null;
		try {
			if (StringUtils.isNotBlank(id)) {
				entity = productService.get(id);
			}
			if (entity == null) {
				entity = new Product();
			}
		} catch (Exception e) {
			logger.error("根据id获取商品失败", e);
		}
		return entity;
	}

	@RequiresPermissions("product:product:view")
	@RequestMapping(value = { "list", "" })
	public String list(Product product, HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			Page<Product> page = productService.findPage(new Page<Product>(request, response), product);
			model.addAttribute("page", page);
			model.addAttribute("product", product);
		} catch (Exception e) {
			logger.error("获取商品列表失败", e);
		}
		return "modules/product/productList";
	}

	@RequiresPermissions("product:product:view")
	@RequestMapping(value = "form")
	public String form(Product product, Model model) {
		try {
			model.addAttribute("product", product);
			Brand brand = new Brand();
			brand.setDelFlag("0");
			model.addAttribute("brandList", brandService.findList(brand));
			PropertyGroup proGrop = new PropertyGroup();
			proGrop.setDelFlag("0");
			model.addAttribute("propertyGrop", propertyGroupService.findList(proGrop));
			SpecificationGroup specificationGroup = new SpecificationGroup();
			specificationGroup.setDelFlag("0");
			List<SpecificationGroup> specificationGroups = specificationGroupService.findList(specificationGroup);
			model.addAttribute("specificationGroups", specificationGroups);
			model.addAttribute("parameterGroups", parameterGroupService.findList(new ParameterGroup()));
			List<MemRank> memRankList = memRankService.findList(new MemRank());
			model.addAttribute("memRankList", memRankList);
			model.addAttribute("list", areaService.ProductfindAllList(new Area()));
			model.addAttribute("productCategory", productCategoryService.get(product.getProductCategoryId()));
			model.addAttribute("supplier",supplierService.get(product.getSupplierId()));
		} catch (Exception e) {
			logger.error("表单信息获取失败", e);
		}
		// model.addAttribute("specificationGroupsString",
		// JsonMapper.toJsonString(specificationGroups));
		return "modules/product/productForm";
	}
	
	@RequiresPermissions("product:product:view")
	@RequestMapping(value = "updateFormView")
	public String updateFormView(Product product, Model model) {
		
		try {
			String productCategoryId = product.getProductCategoryId();
			String goodsId = product.getGoods();
			List<ProductParameterRef> refs = productParameterRefDao
					.queryProductParameterRersByProductId(product.getId());
			Map<ProductParameter, String> parametersValue = new HashMap<ProductParameter, String>();
			for (ParameterGroup group : product.getParameterGroups()) {
				for (ProductParameter parameter : group.getProductParameterList()) {
					for (ProductParameterRef ref : refs) {
						if (parameter.getId().equals(ref.getParameterId())) {
							parametersValue.put(parameter, ref.getParameterValue());
						}
					}
				}
			}
			product.setParameterValue(parametersValue);
			model.addAttribute("product", product);
			Brand brand = new Brand();
			brand.setDelFlag("0");
			model.addAttribute("brandList", brandService.findList(brand));
			// PropertyGroup proGrop = new PropertyGroup();
			// proGrop.setProductCategoryId(productCategoryId);
			List<PropertyGroup> propertyGrop = propertyGroupService.findPropertyGroupsByCategoryId(productCategoryId,
					Boolean.TRUE);
			model.addAttribute("propertyGrop", propertyGrop);
			ProductPropertyRef properyRef = new ProductPropertyRef();
			properyRef.setProductId(product.getId());
			List<ProductPropertyRef> propertyRefs = productPropertyRefDao.findList(properyRef);
			Map<String, ProductPropertyRef> propertyRefMap = new HashMap<String, ProductPropertyRef>();
			for (PropertyGroup group : propertyGrop) {
				boolean existFlag = false;
				for (ProductProperty property : group.getProductPropertyList()) {
					for (ProductPropertyRef ref : propertyRefs) {
						if (ref.getPropertyId().equals(property.getId())) {
							existFlag = true;
							ref.setProperty(property);
							propertyRefMap.put(group.getId(), ref);
							break;
						}
					}
					if (existFlag) {
						break;
					}
				}
			}
			product.setPropertyRefMap(propertyRefMap);
			SpecificationGroup specificationGroup = new SpecificationGroup();
			specificationGroup.setProductCategoryId(productCategoryId);
			specificationGroup.setDelFlag("0");
			List<SpecificationGroup> specificationGroups = specificationGroupService.findList(productCategoryId,
					goodsId);
			List<Product> products = productService.queryProductsByGoodsId(goodsId);
			// for (SpecificationGroup group : specificationGroups) {
			// group.setSelected(false);
			// for (SpecificationGroup group_ : product
			// .getSpecificationGoups()) {
			// if (StringUtils.isNotBlank(group.getId())
			// && group.getId().equals(group_.getId())) {
			// group.setSelected(true);
			// break;
			// }
			// }
			// }
			
			//获取商品价格
			List<ProductPriceRef> productPriceRefList = productDao.findProductpriceAndMemrank(product.getId()).getProductPriceRefList();
			List<MemRank> memRankList = memRankService.findList(new MemRank());						
			for (MemRank memRank : memRankList) {
				for (ProductPriceRef productPriceRef : productPriceRefList) {
					if (memRank.getId().equals(productPriceRef.getRankId())) {
						memRank.setProductPriceRef(productPriceRef);
					}
				}
			}
			model.addAttribute("memRankList", memRankList);	
			model.addAttribute("products", products);
			model.addAttribute("specificationGroups", specificationGroups);
			model.addAttribute("productCategory", productCategoryService.get(productCategoryId));
			model.addAttribute("supplier",supplierService.get(product.getSupplierId()));
			Area area = new Area();
			area.setProductId(product.getId());
			List<Area> areaList = areaService.ProductfindAllList(area);
			List<ProductAreaRef> parList = productAreaRefService.findProductAreaRefList(product.getId());
			model.addAttribute("list", areaList);
			for (Area area1 : areaList) {
				for (ProductAreaRef par : parList) {
					if(area1.getId().equals(par.getAreaId())){
						area1.setAreaId(par.getAreaId());
					}
				}
			}
			if(parList.size()>0){
				StringBuilder sb = new StringBuilder(); 
				String areaName;
				if(parList.size() > 10){
					for (int i = 0; i < 10; i++) {
						if(parList.get(i).getArea().getType().equals("4")){
							sb.append(parList.get(i).getArea().getName()+",");
						}
					}
					areaName = sb.toString()+"...";
				}else{
					for (ProductAreaRef productAreaRef : parList) {
						sb.append(productAreaRef.getArea().getName()+",");
					}
					areaName = sb.substring(0, sb.length() -1);
				}
				model.addAttribute("areaName", areaName);
			}
			
			
		} catch (Exception e) {
			logger.error("获取商品更新信息失败!", e);
		}
		return "modules/product/productupdateForm";
	}

	@RequiresPermissions("product:product:save")
	@RequestMapping(value = "save")
	public String save(Product product, String specificationGroupIds, Model model,
			RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, product)) {
			return form(product, model);
		}else if (product.getAreaId()== null) {
			addMessage(model, "保存失败,销售区域不能为空!");
			return form(product, model);
		}else if(product.getImage() ==null && product.getImage()==""){
			addMessage(model, "保存失败,展示图片不能为空!");
			return form(product, model);
		}
		try {
			product.setId(UUIDGenerator.getUUID());
			ImageProcessorService.dealImage(product);
			String categoryId = product.getProductCategoryId();
			product.setOriginalName(product.getName());
			Map<String, ProductPropertyRef> propertyRefMap = new HashMap<String, ProductPropertyRef>();
			List<PropertyGroup> propertyGroups = propertyGroupService.findPropertyGroupsByCategoryId(categoryId, false);
			for (PropertyGroup group : propertyGroups) {
				String groupId = group.getId();
				String value = request.getParameter("property_" + groupId);
				if (StringUtils.isNotBlank(value)) {
					ProductPropertyRef ref = new ProductPropertyRef();
					// ref.setProductId(product.getId());
					ref.setPropertyId(value);
					propertyRefMap.put(groupId, ref);
				}
			}
			product.setPropertyRefMap(propertyRefMap);
			Map<ProductParameter, String> parametersValue = product.getParameterValue();
			List<ParameterGroup> parameterGroups = parameterGroupService.findList(new ParameterGroup());
			for (ParameterGroup group : parameterGroups) {
				for (ProductParameter parameter : group.getProductParameterList()) {
					String value = request.getParameter("parameter_" + parameter.getId());
					if (StringUtils.isNotBlank(value)) {
						parametersValue.put(parameter, value);
					}
				}
			}
			product.setParameterValue(parametersValue);
			if (Utils.isBlank(product.getAllocatedStock())) {
				product.setAllocatedStock(0);
			}
			SpecificationGroup specificationGroup = new SpecificationGroup();
			specificationGroup.setProductCategoryId(categoryId);
			List<SpecificationGroup> groups = specificationGroupService.findList(specificationGroup);
			List<List<String>> idsList = new ArrayList<List<String>>();
			boolean firstFlag = false;
			for (SpecificationGroup group : groups) {
				String ids = request.getParameter("specificationGroup_" + group.getId());
				if (StringUtils.isNoneBlank(ids)) {
					String[] idArr = ids.split(",");
					for (int i = 0; i < idArr.length; i++) {
						
						List<String> idList = null;
						if (!firstFlag) {
							idList = new ArrayList<String>();
							idsList.add(idList);
						} else {
							idList = idsList.get(i);
						}
						idList.add(idArr[i]);
					}
					firstFlag = true;
				}
			}
			
			for(int i=0;i<idsList.size();i++){
				for(int j=i+1;j<idsList.size();j++){
					if(idsList.get(i).toString().equals(idsList.get(j).toString())){
						addMessage(model, "修改失败," + "不允许添加相同的规格!");
						return form(product, model);
					}
				}
		}
			if (Utils.isBlank(product.getStock())) {
				product.setStock(0);
			}
			product.setGoods(Utils.generatorUUIDNumber());
			
			
			if (idsList.isEmpty()) {
				//添加商品价格				
				if (!productPriceAdd(product,model)) {
					return form(product, model);
				}
				productService.save(product);
			}
			
			List<String> groupIds = null;
			if (Utils.isNotBlank(specificationGroupIds)) {
				groupIds = Arrays.asList(specificationGroupIds.split(","));
			}
			List<String> productIds = new ArrayList<String>();
			for (List<String> list : idsList) {
				Product product_ = new Product();
				BeanUtils.copyProperties(product_, product);
				product_.setId(UUIDGenerator.getUUID());
				product_.setProductSpecificationIds(list);
				//添加商品价格				
				if (!productPriceAdd(product_,model)) {
					return form(product, model);
				}
				productService.save(product_);				
				productIds.add(product_.getId());
			}
			if (Utils.isNotBlank(groupIds)) {
				if (Utils.isBlank(productIds)) {
					productIds.add(product.getId());
				}
				productSpecificationGroupRefDao.batchSave(groupIds, productIds);
			}			
						
			addMessage(redirectAttributes, "保存商品成功");
		} catch (Exception e) {
			logger.error("保存商品失败", e);
			addMessage(redirectAttributes, "保存商品失败");
			return form(product, model);
		}
		return "redirect:" + Global.getAdminPath() + "/product/product/?repage";
	}
	


	@RequiresPermissions("product:product:edit")
	@RequestMapping(value = "update")
	public String update(Product product,String specificationGroupIds, Model model,
			RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, product)) {
			return updateFormView(product, model);
		}else if (product.getAreaId()== null) {
			addMessage(model, "保存失败,销售区域不能为空!");
			return updateFormView(product, model);
		}else if(StringUtils.isBlank(product.getImage())){
			addMessage(model, "保存失败,展示图片不能为空!");
			return updateFormView(product, model);
		}
		if (null != product.getAllocatedStock()) {
			if (product.getStock() < product.getAllocatedStock()) {
				addMessage(model, "库存不能小于已分配库存" + product.getAllocatedStock());
				return updateFormView(product, model);
			}
		}
		try {
			ImageProcessorService.dealImage(product);
			String categoryId = product.getProductCategoryId();
			Map<ProductParameter, String> parametersValue = product.getParameterValue();
			for (ParameterGroup group : product.getParameterGroups()) {
				for (ProductParameter parameter : group.getProductParameterList()) {
					String value = request.getParameter("parameter_" + parameter.getId());
					if (StringUtils.isNotBlank(value)) {
						parametersValue.put(parameter, value);
					}
				}
			}
			product.setParameterValue(parametersValue);
			Map<String, ProductPropertyRef> propertyRefMap = new HashMap<String, ProductPropertyRef>();
			List<PropertyGroup> propertyGroups = propertyGroupService
					.findPropertyGroupsByCategoryId(categoryId, false);
			for (PropertyGroup group : propertyGroups) {
				String groupId = group.getId();
				String value = request.getParameter("property_" + groupId);
				if (StringUtils.isNotBlank(value)) {
					ProductPropertyRef ref = new ProductPropertyRef();
					ref.setProductId(product.getId());
					ref.setPropertyId(value);
					propertyRefMap.put(groupId, ref);
				}
			}
			product.setPropertyRefMap(propertyRefMap);
			if (product.getAllocatedStock() == null) {
				product.setAllocatedStock(0);
			}
			// 处理规格组
			// SpecificationGroup specificationGroup = new SpecificationGroup();
			// specificationGroup.setProductCategoryId(product
			// .getProductCategoryId());
			List<SpecificationGroup> groups = specificationGroupService.findList(categoryId, null);
 			List<List<String>> idsList = new ArrayList<List<String>>();
			boolean firstFlag = false;
			for (SpecificationGroup group : groups) {
				String ids = request.getParameter("specificationGroup_" + group.getId());
				if (StringUtils.isNoneBlank(ids)) {
					String[] idArr = ids.split(",");
					for (int i = 0; i < idArr.length; i++) {
						
						List<String> idList = null;
						if (!firstFlag) {
							idList = new ArrayList<String>();
							idsList.add(idList);
						} else {
							idList = idsList.get(i);
						}
						idList.add(idArr[i]);
					}
					firstFlag = true;
				}
			}
			for(int i=0;i<idsList.size();i++){
					for(int j=i+1;j<idsList.size();j++){
						if(idsList.get(i).toString().equals(idsList.get(j).toString())){
							addMessage(model, "修改失败," + "不允许添加相同的规格!");
							return updateFormView(product, model);
						}
					}
			}
			productService.updateProduct(idsList, product, specificationGroupIds);
			
			//修改销售区域
			//删除关联该商品的所有区域
			productAreaRefService.deleteByProductId(product.getId());
			//添加选中的区域
			List<ProductAreaRef> parList = new ArrayList<ProductAreaRef>();
			if(product.getAreaId()!=null){
			for (int i = 0; i < product.getAreaId().length; i++) {
				String areaId = product.getAreaId()[i].substring(0,product.getAreaId()[i].length()-1);
					ProductAreaRef par = new ProductAreaRef();
					par.setId(UUIDGenerator.getUUID());
					par.setProductId(product.getId());
					par.setAreaId(areaId);
					par.setIsNewRecord(true);
					parList.add(par);
			}
			productAreaRefService.insertProductAreaRef(parList);
			}
						
			//商品价格			
			List<ProductPriceRef> productPriceRefList = product.getProductPriceRefList();			
			for (ProductPriceRef priceRef : productPriceRefList) {
				BigDecimal b1 = priceRef.getSellPrice();
				BigDecimal b2 = priceRef.getbSupplyPrice();
				if(b1 == null) {
					addMessage(model, "修改失败," + memRankService.get(priceRef.getRankId()).getName() + "的售价不能为空!");
					return updateFormView(product, model);
				} else if (b2 == null) {
					addMessage(model, "修改失败," + memRankService.get(priceRef.getRankId()).getName() + "的B端供货价不能为空!");
					return updateFormView(product, model);
				} else if (b1.subtract(b2).doubleValue() < 0) {
					addMessage(model, "修改失败," + memRankService.get(priceRef.getRankId()).getName() + "的B端供货价不能大于售价!");
					return updateFormView(product, model);
				}
				priceRef.setId(UUIDGenerator.getUUID());
				priceRef.setProductId(product.getId());
				priceRef.setIsNewRecord(true);
			}
			productPriceRefDao.deleteByProductId(product.getId());
			productPriceRefService.insertList(productPriceRefList);
			addMessage(redirectAttributes, "修改商品成功");
		} catch (Exception e) {
			logger.error("修改商品失败", e);
			addMessage(redirectAttributes, "修改商品失败");
			return updateFormView(product, model);
		}
		return "redirect:" + Global.getAdminPath() + "/product/product/?repage";
	}

	@RequiresPermissions("product:product:edit")
	@RequestMapping(value = "delete")
	public String delete(Product product, RedirectAttributes redirectAttributes) {
		try {
			int i = productService.deleteProduct(product);
			String msg = i > 0 ? "成功" : "失败,该商品未下架,或已产生订单";
			addMessage(redirectAttributes, "删除商品" + msg);
		} catch (Exception e) {
			logger.error("删除商品失败", e);
		}
		return "redirect:" + Global.getAdminPath() + "/product/product/?repage";
	}

	/**
	 * @author sl 商品批量上架
	 */
	@RequestMapping("/putaway")
	@RequiresPermissions("product:product:edit")
	public String putaway(String[] pcIds, String[] appIds, String[] wxIds, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes,Model model) {
		Product product = new Product();
		productService.updateMarketable(pcIds, appIds, wxIds, "1");
		addMessage(model, "上架成功");
		return list(product,request,response, model);
	}

	/**
	 * @author sl 商品批量下架
	 */
	@RequestMapping("/soldOut")
	@RequiresPermissions("product:product:edit")
	public String soldOut(String[] pcIds, String[] appIds, String[] wxIds,HttpServletRequest request, HttpServletResponse response,  RedirectAttributes redirectAttributes,Model model) {
		Product product = new Product();
		productService.updateMarketable(pcIds, appIds, wxIds, "0");
		addMessage(model, "下架成功");
		return list(product,request,response, model);
	}

	/**
	 * @author sl 商品批量删除
	 */
	@RequestMapping("/del")
	@RequiresPermissions("product:product:edit")
	public String del(@RequestParam(required = false) String[] ids, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes,Model model) {
		Product product = new Product();
		try {
			int i = productService.del(ids);
			String msg = i > 0 ? "成功" : "失败,该商品未下架,或已产生订单";
			addMessage(model, "删除商品"+msg);
		} catch (Exception e) {
			logger.error("删除商品失败", e);
		}
		return list(product,request,response, model);
	}

	/**
	 * 二维码生成
	 * 
	 * @throws Exception
	 */
	@RequestMapping("/twoDimensionCode")
	public String twoDimensionCode(String id, Model model) {
		String url = Global.getConfig("goodsUrl") + "?id=" + id;
		model.addAttribute("url", url);
		return "/modules/product/twoDimensionCode";
	}

	private static final int BLACK = 0xFF000000;
	private static final int WHITE = 0xFFFFFFFF;

	public static BufferedImage toBufferedImage(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
			}
		}
		return image;
	}

	@RequestMapping("/produceImg")
	public void produceImg(String url, HttpServletResponse response) throws Exception {
		int width = 600; // 图像宽度
		int height = 600; // 图像高度
		String format = "png";// 图像类型
		Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		BitMatrix bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵
		BufferedImage image = toBufferedImage(bitMatrix);
		ImageIO.write(image, format, response.getOutputStream());
		response.flushBuffer();
	}
	
	/**
	 * 添加商品价格方法
	 * @param product 商品
	 * @param model
	 * @return
	 */
	private boolean productPriceAdd(Product product,Model model){
		List<ProductPriceRef> productPriceRefList = product.getProductPriceRefList();
		List<MemRank> memRankList = memRankService.findList(new MemRank());
		boolean flag = true;
		for (int i = 0; i < productPriceRefList.size(); i++) {
			ProductPriceRef prod = productPriceRefList.get(i);					
			MemRank memRank = memRankList.get(i);				
			BigDecimal b1 = prod.getSellPrice();
			BigDecimal b2 = prod.getbSupplyPrice();				
			if (b1 == null) {
				addMessage(model, "保存失败," + memRank.getName() + "的售价不能为空!");
				flag = false;
			} else if (b2 == null) {
				addMessage(model, "保存失败," + memRank.getName() + "的B端供货价不能为空!");
				flag = false;
			} else if (b1.subtract(b2).doubleValue() < 0) {
				addMessage(model, "保存失败," + memRank.getName() + "的B端供货价不能大于售价!");
				flag = false;
			}					
			prod.setId(UUIDGenerator.getUUID());
			prod.setProductId(product.getId());
			prod.setRankId(memRank.getId());
			prod.setIsNewRecord(true);
		}
		productPriceRefService.insertList(productPriceRefList);
		return flag;
	}
	
	
	
}