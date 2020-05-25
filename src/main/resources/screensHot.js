"use strict";
var page = require('webpage').create(),
    system = require('system'),
    address, output, title,size, pageWidth, pageHeight;
 
page.settings.userAgent = 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36';
page.settings.resourceTimeout = 15000; // 15 seconds
console.log(system.args.length);
if (system.args.length < 3 || system.args.length > 5) {
    console.log('error');
    phantom.exit(1);
} else {
    //String[] cmd = new String[] { cmdPath, jsPath, url, getPicSavePath(imgpath), title };
    //这是传入的参数, 从url位置开始算
    address = system.args[1];     //传入的url，需要截图的url
    output = system.args[2];    //图片保存的路径
    title = system.args[3];    //这里是我的一个小功能，传入的title，如果和网页上的title一样，就可以画个红框
    address = encodeURI(address);     //遇到过传入url和url不匹配的情况，就要先编码一下
    page.viewportSize = { width: 1000, height: 1000 };   //设置大小，一般网页都可以用
	console.log("11111"+address);
    if (system.args.length > 3 && system.args[2].substr(-4) === ".pdf") {
        size = system.args[3].split('*');
        page.paperSize = size.length === 2 ? { width: size[0], height: size[1], margin: '0px' }
                                           : { format: system.args[3], orientation: 'portrait', margin: '1cm' };
    } else if (system.args.length > 3 && system.args[3].substr(-2) === "px") {
        size = system.args[3].split('*');
        if (size.length === 2) {
            var pageWidth = parseInt(size[0], 10),
                pageHeight = parseInt(size[1], 10);
            page.viewportSize = { width: pageWidth, height: pageHeight };
            page.clipRect = { top: 0, left: 0, width: pageWidth, height: pageHeight };
        } else {
            console.log("size:", system.args[3]);
            var pageWidth = parseInt(system.args[3], 10),
                pageHeight = parseInt(pageWidth * 3/4, 10); 
            console.log ("pageHeight:",pageHeight);
            page.viewportSize = { width: pageWidth, height: pageHeight };
        }
    }
    if (system.args.length > 4) {
        page.zoomFactor = system.args[4];
    }
    
    page.open(address, function (status) {
        if (status !== 'success') {
            console.log('Unable to load the address!');
            phantom.exit(1);
        } else {
            //这个函数特殊，因为无法调试，具体的可以在百度，这就是传入的title，对比若一样，则会在截图上画红框
        	 page.evaluate(function(tt){  
					document.body.bgColor = 'white';   //添加背景色为白色，不然会有很多透明的图片
        	      	window.scrollTo(0,1000);// 滚动到底部
        	      	
    	        	var ele = document.getElementById('content_left');
    	            var list = ele.getElementsByClassName('result c-container');
    	            for(var y=0,j = list.length;y < j;y++){
    	            	var ee = list[y];
    	            	var h3 = ee.getElementsByTagName('h3');
    	            	var a = h3[0].getElementsByTagName('a');
    	            	var tit = a[0].innerText;       	       
    	            	if(tit == tt){          	        
    	            		ee.style.border = "2px solid red";       	       
    	            	} 
    	            }      	       
        	    },title);          //添加title
 
            //截图的路径
            window.setTimeout(function () {
                page.render(output);
                phantom.exit();
            }, 8000);
        }
    });
}