<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>email模版</title>
</head>

<body>
<p>你好，${employeeName}童鞋，欢迎加入杭州时划科技大家庭！您的入职信息如下：</p>
<table border="1" cellspacing="0">
    <tr>
        <td><strong style="color: #F00">工号</strong></td>
        <td>${employeeCode}</td>
    </tr>
   <#-- <tr>
        <td><strong style="color: #F00">合同期限</strong></td>
        <td>${contractTerm}年</td>
    </tr>
    <tr>
        <td><strong style="color: #F00">合同起始日期</strong></td>
        <td>${beginContract}</td>
        &lt;#&ndash;?string("yyyy-MM-dd")&ndash;&gt;
    </tr>
    <tr>
        <td><strong style="color: #F00">合同截至日期</strong></td>
        <td>${endContract}</td>
    </tr>-->
    <tr>
        <td><strong style="color: #F00">所属部门</strong></td>
        <td>${department}</td>
    </tr>
    <tr>
        <td><strong style="color: #F00">职位</strong></td>
        <td>${position}</td>
    </tr>
</table>
<p><strong style="color: #F00; font-size: 24px;">希望在未来的日子里，携手共进!</strong></p>
</body>
</html>
