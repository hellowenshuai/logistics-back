1. springboot的相关配置及其链接

[SpringBoot 中的 ServletInitializer](https://blog.csdn.net/qq_28289405/article/details/81279742)

2. java中两个数组并集和差集的处理
 - [两个数组并集和差集的处理](https://www.cnblogs.com/wanying521/p/5179151.html)
 - [两个数组交集](https://www.cnblogs.com/ASPNET2008/p/6034561.html)
 - [元素差集](https://blog.csdn.net/qq_33172029/article/details/83627329)
 ```
String[] as = new String[]{"0","1","2","3"};
String[] bs = new String[]{"1","2"};
List<String> list = Arrays.asList(bs);
//用来装差集
List<String> result = new ArrayList<String>();
for(String a : as){
	//判断是否包含
	if(!list.contains(a)){
		result.add(a);
	}
}
```

3. 中转城市具有多个时，如何处理？
 有一个时，
 司机在中转管理的中转信息中填写中转回执。
 客服在中转回告里填写中转回告
 
 司机才能在到货管理中查看到这个菜单，并可以填写到货回执
 客服填写到货回执和提货回执
 客户在到货管理中可以看到运单信息，并填写 确认到货
 客服在回告管理中填写已提回告
  
