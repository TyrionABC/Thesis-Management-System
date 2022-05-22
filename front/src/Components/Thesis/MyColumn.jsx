import axios from "axios";
import {Descriptions, PageHeader, Table, Tabs} from "antd";
import React from "react";

async function FetchNote(id) {
    let tmp;
    await axios.post('http://localhost:8080/admin/myNotes', { userId: id })
        .then(response => {
            console.log(response.data);
            tmp = response.data;
        })
        .catch(err => console.log(err));
    return tmp;
}

export default async function MyColumn(props) {
    const { TabPane } = Tabs;
    function callback(key) {
        console.log(key);
    }
    let data;
    await FetchNote(props.id).then(function(res) {
        data = res;
    });
    const routes = [
        {
            breadcrumbName: '内容管理',
        },
        {
            breadcrumbName: '笔记管理',
        }
    ];
    return <>
        <PageHeader style={{background: '#fff'}} title="我的笔记" breadcrumb={{ routes }}>
            <Descriptions>
                <Descriptions.Item label="统计已发布笔记数">{ data.length }</Descriptions.Item>
            </Descriptions>
        </PageHeader>
        <div className="site-layout-content">
            <Tabs defaultActiveKey="1" onChange={callback} centered>
                <TabPane tab="草稿箱" key="1">
                    <Table dataSource={data}/>
                </TabPane>
                <TabPane tab="已发布" key="2">
                    <Table dataSource={data}/>
                </TabPane>
            </Tabs>
        </div>
    </>
}