import React from "react";
import ReactECharts from 'echarts-for-react';
import myChart from "echarts-for-react";
import '../Thesis/Thesis.css'
import {Descriptions, PageHeader} from "antd";
import axios from "axios";

function CurentTime() {
  var now = new Date();
  var year = now.getFullYear();       //年
  var month = now.getMonth() + 1;     //月
  var day = now.getDate();            //日
  var hh = now.getHours();            //时
  var mm = now.getMinutes();          //分
  var ss = now.getSeconds();          //秒
  var clock = year + "-";
  if(month < 10)
    clock += "0";
  clock += month + "-";
  if(day < 10)
    clock += "0";
  clock += day + " ";
  if(hh < 10)
    clock += "0";
  clock += hh + ":";
  if (mm < 10) clock += '0';
  clock += mm + ":";
  if(ss < 10) clock += '0';
  clock += ss;
  return(clock);
}

export const GetContentData = () => {
  const routes = [
  {
    breadcrumbName: '数据中心',
  },
  {
    breadcrumbName: '内容数据',
  }
];
  return <>
    <PageHeader style={{background: '#fff'}} title="近期文章发布趋势" breadcrumb={{ routes }}>
      <Descriptions>
        <Descriptions.Item label="更新时间">{CurentTime()}</Descriptions.Item>
      </Descriptions>
    </PageHeader>
    <div className="site-layout-content">
      <Counting/>
      <Category/>
    </div>
  </>
}

const Counting: React.FC = () => {
  let data;
  let day;
  axios.post('')
      .then(function (response) {
        console.log(response);
        data = response.data[0];
        day = response.data[0];
      })
      .catch(err => console.log(err));
  const options = {
    title: {
      text: "论文数量"
    },
    xAxis: {
      type: 'category',
      data: day,
    },
    yAxis: {
      type: 'value',
    },
    series: [
      {
        data: data,
        type: 'line',
        smooth: true,
      },
    ],
    tooltip: {
      trigger: 'axis',
    },
  };

  return <ReactECharts option={options} style={{ height: 300 }} opts={{ renderer: 'svg' }}/>
};

const Category: React.FC = () => {
  let thesisCount;
  let likeCount;
  axios.post('')
      .then(function (response) {
        console.log(response);
        thesisCount = response.data[0];
        likeCount = response.data[0];
      })
      .catch(err => console.log(err));
  const option = {
    title: {
      text: '研究方向'
    },
    tooltip: {},
    legend: {
      data:['我的论文', '我的点赞']
    },
    xAxis: {
      data: ['前端', '后端', 'Android', 'IOS', '软件测试', '人工智能', '机器学习', '深度学习', '数据库', '网络安全']
    },
    yAxis: {},
    series: [{
      name: '我的论文',
      type: 'bar',
      data: thesisCount,
    },
      {
        name: '我的点赞',
        type: 'bar',
        data: likeCount,
      }]
  };

  return <ReactECharts
          option={option}
          style={{ height: 300 }}
          opts={{ renderer: 'svg' }}
      />
}

export const GetUniversalData = () => {
  let data = [];
  axios.post('')
      .then(function (response) {
        console.log(response);
        data.push(response.data[0]);
      })
      .catch(err => console.log(err));
  function randomData() {
    now = new Date(+now + oneDay);
    value = value + Math.random() * 21 - 10;
    return {
      name: now.toString(),
      value: [
        [now.getFullYear(), now.getMonth() + 1, now.getDate()].join('/'),
        Math.round(value)
      ]
    };
  }
  let now = new Date(1997, 9, 3);
  let oneDay = 24 * 3600 * 1000;
  let value = Math.random() * 1000;
  for (var i = 0; i < 1000; i++) {
    data.push(randomData());
  }
  let option = {
    title: {
      text: '全站文章发布趋势'
    },
    tooltip: {
      trigger: 'axis',
      formatter: function (params) {
        params = params[0];
        var date = new Date(params.name);
        return (
            date.getDate() +
            '/' +
            (date.getMonth() + 1) +
            '/' +
            date.getFullYear() +
            ' : ' +
            params.value[1]
        );
      },
      axisPointer: {
        animation: false
      }
    },
    xAxis: {
      type: 'time',
      splitLine: {
        show: false
      }
    },
    yAxis: {
      type: 'value',
      boundaryGap: [0, '100%'],
      splitLine: {
        show: false
      }
    },
    series: [
      {
        name: 'Fake Data',
        type: 'line',
        showSymbol: false,
        data: data
      }
    ]
  };
  setInterval(function () {
    for (var i = 0; i < 5; i++) {
      data.shift();
      data.push(randomData());
    }
    myChart.setOption({
      series: [
        {
          data: data
        }
      ]
    });
  }, 1000);
  const routes = [
    {
      breadcrumbName: '数据中心',
    },
    {
      breadcrumbName: '全站数据',
    }
  ];
  return <>
      <PageHeader style={{background: '#fff'}} title="全站文章发布趋势" breadcrumb={{ routes }}>
        <Descriptions>
          <Descriptions.Item label="更新时间">{CurentTime()}</Descriptions.Item>
        </Descriptions>
      </PageHeader>
    <div className="site-layout-content">
        <ReactECharts option={option} style={{ height: 500 }}/>
    </div>
    </>
}