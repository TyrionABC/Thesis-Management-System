import React from "react";

import {
    Layout,
    Menu,
    Avatar,
    Input,
    Form,
    Table,
    Tag,
    Space,
    Modal,
    Descriptions,
    PageHeader,
  } from 'antd';

import {
    UserOutlined,
    FileSearchOutlined,
    ReadOutlined,
    CommentOutlined,
    SmileOutlined,
    MehOutlined
  } from '@ant-design/icons';

import create from 'zustand';
  
import './Admin.css';
import { Link } from 'react-router-dom';
import { useLocation } from "react-router";
import {UserGovern,IllegalUser} from "./User";

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

const Admin = () => {
  let { state } = useLocation();
  if(state === null) window.location.replace("/");
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
  return <AdminMainContent mail={mail}/>;
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
  getItem('用户管理', 'sub1', <UserOutlined />,[
    getItem('正常用户', '1', <SmileOutlined />),
    getItem('违规用户', '2', <MehOutlined />)
  ]),
  getItem('研究方向管理', '3', <FileSearchOutlined />),
  getItem('论文管理', '4', <ReadOutlined /> ),
  getItem('评论管理', '5', <CommentOutlined />),
];

let contents = [<UserGovern />, <IllegalUser />, '', '',''];
function AdminName() {
    const url = useStore(state => state.image);
    const name = useStore(state => state.name);
    return <>
      <Avatar size={'large'} src={url} style={{margin: 5}}/>
      <br/>
      <Link to="/" id="logInfo" style={{color: 'white'}}>signed as: {name}</Link>
    </>
}
class AdminMainContent extends React.Component {
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
      //if (key.key === '8') {
        //this.setState({ isFocus: !this.state.isFocus });
      //}
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
                <AdminName/>
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
          </>
      );
    }
  }
  
export default Admin;