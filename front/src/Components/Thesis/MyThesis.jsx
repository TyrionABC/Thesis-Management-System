import axios from "axios";
import {Descriptions, PageHeader, Table, Tabs} from "antd";
import React from "react";

async function FetchThesis(id) {
    let tmp;
    await axios.post('http://localhost:8080/admin/myPaper', { userId: id })
        .then(function(response) {
            console.log(response.data);
            tmp = response.data[0];
        })
        .catch(err => console.log(err));
    return tmp;
}

export default async function MyThesis(props) {
    const {TabPane} = Tabs;
    function callback(key) {
        console.log(key);
    }
    let data;
    await FetchThesis(props.id).then((res) => {
        data = res;
    });
    console.log(data);
    let tmp = data
    const routes = [
        {
            breadcrumbName: '内容管理',
        },
        {
            breadcrumbName: '文章管理',
        }
    ];
    const columns = [
        {
            title: 'ID',
            dataIndex: 'id',
            key: 'id',
            width: '10%',
        },
        {
            title: '标题',
            dataIndex: 'title',
            key: 'title',
            width: '10%',
        },
        {
            title: '作者',
            dataIndex: 'writer',
            key: 'writer',
            width: '10%',
        },
        {
            title: '发布日期',
            dataIndex: 'thesisDate',
            key: 'thesisDate',
            width: '10%',
            sorter: (a, b) => a.date.localeCompare(b.date),
            sortDirections: ['descend', 'ascend'],
        },
        {
            title: '发布人',
            dataIndex: 'publisher',
            key: 'publisher',
            width: '10%',
        },
        {
            title: '论文类型',
            dataIndex: 'thesisType',
            key: 'thesisType',
            width: '10%',
        },
        {
            title: '研究方向',
            dataIndex: 'researchDirection',
            key: 'researchDirection',
            width: '10%',
        },
        {
            title: '发布会议',
            dataIndex: 'meeting',
            key: 'meeting',
            width: '10%',
        },
        {
            title: '点赞数',
            dataIndex: 'like',
            key: 'like',
            width: '10%',
            sorter: (a, b) => a.likes.valueOf() - b.likes.valueOf(),
            sortDirections: ['descend', 'ascend'],
        },
    ];
    return <>
        <PageHeader style={{background: '#fff'}} title="我的文章" breadcrumb={{routes}}>
            <Descriptions>
                <Descriptions.Item label="统计已发布文章数">{123}</Descriptions.Item>
            </Descriptions>
        </PageHeader>
        <div className="site-layout-content">
            <Tabs defaultActiveKey="1" onChange={callback} centered>
                <TabPane tab="草稿箱" key="1">
                    <Table className="site-layout-content" columns={columns} dataSource={[]} />
                </TabPane>
                <TabPane tab="已发布" key="2">
                    <Table className="site-layout-content" columns={columns} dataSource={[]} />
                </TabPane>
            </Tabs>
        </div>
    </>
}