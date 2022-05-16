import React, {useState} from 'react';
import {
  Layout,
  Menu,
  Affix,
  Button,
  Tooltip,
  Avatar,
  Input,
  Form,
  Table,
  Tag,
  Space,
  Modal,
  Descriptions,
  PageHeader
} from 'antd';
import {
  SearchOutlined,
  BarChartOutlined,
  CloudOutlined,
  CopyOutlined,
  UserOutlined,
  PlusOutlined
} from '@ant-design/icons';
import create from 'zustand';
import { GetContentData, GetUniversalData } from "../Data/DataChart";
import { Latest, MyThesis, MyColumn } from "../Thesis/Thesis";
import { BasicInfoSet } from "./Info";
import './App.css';
import { Link } from 'react-router-dom';
import { useLocation } from "react-router";

const useStore = create(set => ({
  id: 101010,
  name: '',
  state: 'unallowed',
  email: '',
  contentNum: 0,
  image: "https://images.pexels.com/photos/4428279/pexels-photo-4428279.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
  changeContentNum: (num) => set(state => ({ contentNum: num })),
  changeState: (state) => set(state => ({ state: state })),
  changeName: (name) => set(state => ({ name: name })),
  changeEmail: (email) => set(state => ({ email: email })),
}));

function App() {
  let { state } = useLocation();
  let permission = state['permission'];
  let Username = state['name'];
  if(permission !== 'allowed' || Username.length === 0) {
    window.location.replace("/");
  }
  let mail = state['email'];

  // 根据email获取用户信息

  const emailChange = useStore(state => state.changeEmail);
  emailChange(mail);
  const change = useStore(state => state.changeState);
  change(permission);
  const nameChange = useStore(state => state.changeName);
  nameChange(Username);
  return <MainContent mail={mail}/>;
}

const { Content, Footer, Sider } = Layout;

function getItem(label, key, icon, children) {
  return {
    key,
    icon,
    children,
    label,
  };
}

const items = [
  getItem('首页', '1', <CloudOutlined />),
  getItem('内容管理', 'sub1', <CopyOutlined />, [
    getItem('文章管理', '2'),
    getItem('笔记管理', '3'),
  ]),
  getItem('数据中心', 'sub2', <BarChartOutlined />, [
      getItem('内容数据', '4'),
    getItem('全站数据', '5'),
  ]),
    getItem('个人中心', 'sub3', <UserOutlined />, [
        getItem('写文章', '6'),
      getItem('信息设置', '7'),
  ]),
  getItem('文章搜索', '8', <SearchOutlined />),
];

let contents = [<Latest/>, <MyThesis/>, <MyColumn/>, <GetContentData/>, <GetUniversalData/>, '', <BasicInfoSet/>, ''];

function BottomPart() {
  const [bottom, setBottom] = useState(10);
  return <Affix offsetBottom={bottom}>
    <Tooltip title="新文章">
      <Button type="primary" shape="circle" icon={<PlusOutlined />}
              size="large" href="#" style={{ position: "absolute", bottom: 80, right: 80}}/>
    </Tooltip>
  </Affix>
}

function Name() {
  const url = useStore(state => state.image);
  const name = useStore(state => state.name);
  return <>
    <Avatar size={'large'} src={url} style={{margin: 5}}/>
    <br/>
    <Link to="/" id="logInfo" style={{color: 'white'}}>signed as: {name}</Link>
  </>
}

function SearchResult(props) {
  const data = props.lists;
  const { Column, ColumnGroup } = Table;
  const routes = [
    {
      breadcrumbName: '文章搜索',
    },
    {
      breadcrumbName: '搜索结果',
    }
  ];
  return <>
    <PageHeader style={{background: '#fff'}} title="搜索结果" breadcrumb={{ routes }}>
      <Descriptions>
        <Descriptions.Item label="搜索结果总数">{ data.length }</Descriptions.Item>
      </Descriptions>
    </PageHeader>
    <Table dataSource={data}>
      <ColumnGroup title="基本信息">
        <Column title="ID" dataIndex="id" key="id" />
        <Column title="标题" dataIndex="title" key="title" />
        <Column title="作者" dataIndex="writer" key="writer" />
        <Column
            title="研究方向"
            dataIndex="direction"
            key="direction"
            render={tags => (
                <>
                  {tags.map(tag => (
                      <Tag color="blue" key={tag}>
                        {tag}
                      </Tag>
                  ))}
                </>
            )}
        />
        <Column title="类型" dataIndex="type" key="type" />
      </ColumnGroup>
      <ColumnGroup title="发布">
        <Column title="发布人" dataIndex="publish" key="publish" />
        <Column title="发布会议" dataIndex="meeting" key="meeting" />
      </ColumnGroup>
      <Column
          title="操作"
          key="action"
          render={() => (
              <Space size="middle">
                <a>查看</a>
              </Space>
          )}
      />
    </Table>
    </>
}

const InnerForm = () => {
  const onFinish = (values: any) => {
    if(!values['title'] && !values['direction'] && !values['type']
        && !values['abstract'] && !values['writer'] && !values['publish']
        && !values['meeting']) alert("搜索条件不能全为空!");
    console.log('Success:', values);
    // 向后端请求论文列表, 接收论文列表
    const data = [
      {
        id: '10101',
        title: 'Thesis_1',
        writer: 'John',
        direction: ['ML', 'FE'],
        type: '研究型',
        publish: 'Root',
        meeting: '第2次会议',
      },
      {
        id: '12321',
        title: 'Thesis_2',
        writer: 'Jack',
        direction: ['FE', 'DL'],
        type: '研究型',
        publish: 'Root',
        meeting: '第3次会议',
      },
    ];
    contents[7] = <SearchResult lists={data}/>;
  };

  const onFinishFailed = (errorInfo: any) => {
    console.log('Failed:', errorInfo);
  };

  return (
      <Form
          name="basic"
          labelCol={{ span: 8 }}
          wrapperCol={{ span: 16 }}
          initialValues={{ remember: true }}
          onFinish={onFinish}
          onFinishFailed={onFinishFailed}
          autoComplete="off"
      >
        <Form.Item
            label="论文标题"
            name="title"
        >
          <Input />
        </Form.Item>
        <Form.Item
            label="研究方向"
            name="direction"
        >
          <Input />
        </Form.Item>
        <Form.Item
            label="论文类型"
            name="type"
        >
          <Input />
        </Form.Item>
        <Form.Item
            label="论文摘要"
            name="abstract"
        >
          <Input />
        </Form.Item>
        <Form.Item
            label="作者"
            name="writer"
        >
          <Input />
        </Form.Item>
        <Form.Item
            label="发布人"
            name="publish"
        >
          <Input />
        </Form.Item>
        <Form.Item
            label="会议"
            name="meeting"
        >
          <Input />
        </Form.Item>
        <Form.Item wrapperCol={{ offset: 8, span: 16 }}>
          <Button type="primary" htmlType="submit">
            查询
          </Button>
        </Form.Item>
      </Form>
  );
}

class MainContent extends React.Component {
  state = {
    collapsed: false,
    num: 1,
    isFocus: false,
  };

  onCollapse = (collapsed) => {
    console.log(collapsed);
    let ele = document.getElementById('logInfo');
    if(collapsed) ele.style.display='none';
    else ele.style.removeProperty('display');
    this.setState({
      collapsed,
    });
  };

  onClick = (key) => {
    this.setState({num: key.key});
    if (key.key === '8') {
      this.setState({ isFocus: !this.state.isFocus });
    }
  }

  render() {
    const { collapsed, num, isFocus } = this.state;
    return (
        <>
        <Layout
            style={{
              minHeight: '100vh',
            }}
        >
          <Sider collapsible collapsed={collapsed} onCollapse={this.onCollapse}>
            <div className="content">
              <Name/>
            </div>
            <Menu theme="dark" defaultSelectedKeys={['1']} mode="inline" items={items} onClick={this.onClick}/>
          </Sider>
          <Layout className="site-layout" menu>
            <Content
                style={{
                  margin: '8px 16px',
                }}
            >
              { contents[num - 1] }
              <Modal
                  title="论文查询"
                  centered
                  visible={isFocus}
                  onOk={() => this.setState({ isFocus: !isFocus })}
                  onCancel={() => this.setState({ isFocus: !isFocus })}
                  width={1000}
              >
                <InnerForm/>
              </Modal>
            </Content>
            <Footer
                style={{
                  textAlign: 'center',
                }}
            >
              论文In ©2022 Created by ECNUer
            </Footer>
          </Layout>
        </Layout>
        <BottomPart/>
        </>
    );
  }
}

export default App;