<%--
  Created by IntelliJ IDEA.
  User: Wenline
  Date: 2021/1/5
  Time: 16:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%--第一步：引入echars的js--%>
    <script src="/plugins/echarts/echarts.min.js"></script>
</head>
<body>
    <%--第二步：预留一个位置显示报表图--%>
    <div id="main" style="width: 600px;height:400px;"></div>
    <%--第三步： 编写js代码把数据与div整合到一块形成一个报表--%>
    <script>
        // 3.1 找到div
        var myChart = echarts.init(document.getElementById('main'));

        //3.2 准备报表图的数据
        var option = {
            title: {
                text: 'ECharts 入门示例'
            },
            tooltip: {},
            legend: {
                data:['销量']
            },
            xAxis: {
                data: ["衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"]
            },
            yAxis: {},
            series: [{
                name: '销量',
                type: 'bar',
                data: [5, 20, 36, 10, 10, 20]
            }]
        };


        // 3.3 把数据与div整合到一块
        myChart.setOption(option);

    </script>
</body>
</html>
