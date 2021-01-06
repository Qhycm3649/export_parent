<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../base.jsp"%>
<!DOCTYPE html>
<html>

<head>
    <!-- 页面meta -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>数据 - AdminLTE2定制版</title>
    <meta name="description" content="AdminLTE2定制版">
    <meta name="keywords" content="AdminLTE2定制版">
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no" name="viewport">
    <!-- 页面meta /-->
</head>
<body>
<div id="frameContent" class="content-wrapper" style="margin-left:0px;">
    <section class="content-header">
        <h1>
            统计分析
            <small>厂家销量统计</small>
        </h1>
    </section>
    <section class="content">
        <div class="box box-primary">
            <div id="main" style="width: 1000px;height:400px;"></div>
        </div>
    </section>
</div>
</body>

<script src="../plugins/jQuery/jquery-2.2.3.min.js"></script>
<script src="../../plugins/echarts/echarts.min.js"></script>
<script type="text/javascript">
    $.ajax({
        url:"/stat/getSellData.do",
        type:"get",
        dataType:"json",
        success:function(resultData){
            // 3.1 找到div
            var myChart = echarts.init(document.getElementById('main'));
            //定义两个变量接收参数
            var nameData = [];
            var valueData = [];
            //遍历resultData
            for (var i = 0; i < resultData.length; i++) {
                var obj = resultData[i];
                nameData[i] = obj.name;
                valueData[i] = obj.value;
            }
            //3.2 准备报表图的数据
            option = {
                xAxis: {
                    type: 'category',
                    data: nameData
                },
                yAxis: {
                    type: 'value'
                },
                series: [{
                    data: valueData,
                    type: 'bar'
                }]
            };
            // 3.3 把数据与div整合到一块
            myChart.setOption(option);
        }
    });

</script>

</html>