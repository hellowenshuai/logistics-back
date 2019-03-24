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
