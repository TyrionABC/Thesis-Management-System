import React from 'react';
import {Button, Form, Input, PageHeader, Select, Tabs } from 'antd';
import 'antd/dist/antd.css';
import create from "zustand";
import 'bootstrap/dist/css/bootstrap.min.css';
import {Option} from "antd/es/mentions";
import '../Thesis/Thesis.css';
import axios from "axios";

const { TabPane } = Tabs;

const userInfo = create(set => ({
  id: '',
  name: 'jack',
  permission: 1,
  email: '1822140986@qq.com',
  direction: 'ML',
  image: 'https://images.pexels.com/photos/189349/pexels-photo-189349.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260',
  sex: 'male',
  work: 'ECNU',
  alarmCount: 6,
  changeImage: (url) => set(state => ({ image: url })),
  changeId: (idn) => set(state => ({ id: idn })),
}));

const BasicSet = (id) => {
  const onFinish = (values: any) => {
    console.log('Success:', values);
    axios.post('', values)
        .then(function (response) {
          console.log(response);
        })
  };

  const onFinishFailed = (errorInfo: any) => {
    console.log('Failed:', errorInfo);
  };

  return (
      <Form
          name="basic"
          labelCol={{ span: 4 }}
          wrapperCol={{ span: 8 }}
          initialValues={{ remember: true }}
          onFinish={onFinish}
          onFinishFailed={onFinishFailed}
          autoComplete="off"
      >

        <Form.Item
            label="用户名"
            name="username"
            rules={[{ message: 'Please input your username!' }]}
            initialValue={userInfo(state => state.name)}
        >
          <Input />
        </Form.Item>

        <Form.Item name="gender" label="性别" initialValue={userInfo(state => state.sex)}>
          <Select
              placeholder="请选择"
              allowClear
          >
            <Option value="male">男</Option>
            <Option value="female">女</Option>
            <Option value="other">其他</Option>
          </Select>
        </Form.Item>

        <Form.Item
            label="学校/单位"
            name="work"
            rules={[{ message: 'Please input your school/company!' }]}
            initialValue={userInfo(state => state.work)}
        >
          <Input />
        </Form.Item>

        <Form.Item name="researchDirection" label="主研究方向" initialValue={userInfo(state => state.direction)}>
          <Select
              placeholder="请选择"
              allowClear
          >
            <Option value="FE">前端</Option>
            <Option value="BE">后端</Option>
            <Option value="AND">Android</Option>
            <Option value="IOS">IOS</Option>
            <Option value="ST">软件测试</Option>
            <Option value="AI">人工智能</Option>
            <Option value="ML">机器学习</Option>
            <Option value="DL">深度学习</Option>
            <Option value="DB">数据库</Option>
            <Option value="WS">网络安全</Option>
          </Select>
        </Form.Item>

        <Form.Item wrapperCol={{ offset: 8, span: 16 }}>
          <Button type="primary" htmlType="submit">
            提交
          </Button>
        </Form.Item>
      </Form>
  );
};

const PrivacySet = (id) => {
  // 根据 id 获取用户信息, userInfo(state => state.id);
  const onFinish = (values: any) => {
    console.log('Success:', values);
    axios.post('', values)
        .then(function (response) {
          console.log(response);
        })
        .catch(err => console.log(err));
  };

  const onFinishFailed = (errorInfo: any) => {
    console.log('Failed:', errorInfo);
  };
  return <Form
      labelCol={{ span: 4 }}
      wrapperCol={{ span: 8 }}
      onFinish={onFinish}
      onFinishFailed={onFinishFailed}
  >
    <Form.Item
      name="originPassword"
      label="原密码"
      rules={[
        {
          required: true,
          message: '请输入原密码',
        }
      ]}
      hasFeedback
    >
      <Input.Password/>
    </Form.Item>

    <Form.Item
        name="newPassword"
        label="新密码"
        rules={[
          {
            required: true,
            message: '请输入新密码!',
          },
          ({ getFieldValue }) => ({
            validator(_, value) {
              if(getFieldValue('newPassword').length <= 8) {
                return Promise.reject(new Error('密码的长度至少为8位'));
              }
              else return Promise.resolve();
            }
          })
        ]}
        hasFeedback
    >
      <Input.Password />
    </Form.Item>

    <Form.Item
        name="confirmPassword"
        label="确认密码"
        dependencies={['password']}
        hasFeedback
        rules={[
          {
            required: true,
            message: '请输入确认密码',
          },
          ({ getFieldValue }) => ({
            validator(_, value) {
              if (!value || getFieldValue('newPassword') === value) {
                return Promise.resolve();
              }
              return Promise.reject(new Error('两次输入的密码不一致!'));
            },
          }),
        ]}
    >
      <Input.Password />
    </Form.Item>

    <Form.Item wrapperCol={{ offset: 8, span: 16 }}>
      <Button type="primary" htmlType="submit">
        提交
      </Button>
    </Form.Item>
  </Form>
}

function ChangeInfo(info) {

}

export function BasicInfoSet(props) {
  let thisId = props.id;
  const item = <div className="site-layout-content">
    <Tabs tabPosition={'left'}>
      <TabPane tab="基本设置" key="1">
        <PageHeader title="基本设置"/>
        <BasicSet id={thisId}/>
      </TabPane>
      <TabPane tab="隐私设置" key="2">
        <PageHeader title="修改密码"/>
        <PrivacySet id={thisId}/>
      </TabPane>
    </Tabs>
  </div>

  axios.post('', thisId)
      .then(function (response) {
        ChangeInfo(response);
        return item;
      })
      .catch(err => console.log(err));
}