/*===========================
 *作者：dream river (gaven)
 *转发请保留作者信息，谢谢
===========================*/
//===============商品浏览记录=================
var scanProducts = new ArrayList();
function scanProductInfo(id,name,price,imageUrl){
	this.id=id;
	this.name = name;
	this.price = price;
	this.imageUrl = imageUrl;
}

//=====================ArrayList=========================
function ArrayList(){
	this.data=[], 
	this.size=function(){ 
		return this.data.length; 
	}, 
	this.add=function(){ 
	if(arguments.length==1){ 
		this.data.push(arguments[0]); 
	}else if(arguments.length>=2){ 
		var deleteItem=this.data[arguments[0]]; 
		this.data.splice(arguments[0],1,arguments[1],deleteItem) 
	} 
		return this; 
	}, 
	this.get=function(index){ 
		return this.data[index]; 
	}, 
	this.remove=function(index){ 
		this.data.splice(index,1); 
	}, 
	this.removeObj=function(obj){ 
		this.removeIndex(this.indexOf(obj)); 
	}, 
	this.indexOf=function(obj){ 
		for(var i=0;i<this.data.length;i++){ 
			if (this.data[i]===obj) { 
				return i; 
			}; 
		} 
		return -1; 
	}, 
	this.isEmpty=function(){ 
		return this.data.length==0; 
	}, 
	this.clear=function(){ 
		this.data=[]; 
	}, 
	this.contains=function(obj){ 
		return this.indexOf(obj)!=-1; 
	}
	return this; 
}; 
//===================================================