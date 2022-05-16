import React from 'react';
import {Button, Form, Input, PageHeader, Select, Upload, message, Tabs } from 'antd';
import { LoadingOutlined, PlusOutlined } from '@ant-design/icons';
import 'antd/dist/antd.css';
import create from "zustand";
import 'bootstrap/dist/css/bootstrap.min.css';
import {Option} from "antd/es/mentions";
import '../Thesis/Thesis.css';

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

function getQueryVariable(variable)
{
  var query = window.location.search.substring(1);
  var vars = query.split("&");
  for (var i=0;i<vars.length;i++) {
    var pair = vars[i].split("=");
    if(pair[0] === variable){return pair[1];}
  }
  return false;
}

function getBase64(img, callback) {
  const reader = new FileReader();
  reader.addEventListener('load', () => callback(reader.result));
  reader.readAsDataURL(img);
}

function beforeUpload(file) {
  const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png';
  if (!isJpgOrPng) {
    message.error('You can only upload JPG/PNG file!');
  }
  const isLt2M = file.size / 1024 / 1024 < 2;
  if (!isLt2M) {
    message.error('Image must smaller than 2MB!');
  }
  return isJpgOrPng && isLt2M;
}

class MyAvatar extends React.Component {
  state = {
    loading: false,
    imageUrl: true,
  };

  handleChange = info => {
    if (info.file.status === 'uploading') {
      this.setState({ loading: true });
      return;
    }
    if (info.file.status === 'done') {
      // Get this url from response in real world.
      getBase64(info.file.originFileObj, imageUrl =>
          this.setState({
            imageUrl, loading: false,
          }),
      );
    }
  };

  render() {
    const { loading, imageUrl } = this.state;
    const uploadButton = (
        <div>
          {loading ? <LoadingOutlined /> : <PlusOutlined />}
          <div style={{ marginTop: 8 }}>Upload</div>
        </div>
    );
    return (
        <Upload
            name="avatar"
            listType="picture-card"
            className="avatar-uploader"
            // 添加图片更新地址
            showUploadList={false}
            beforeUpload={beforeUpload}
            onChange={this.handleChange}
        >
          {imageUrl ? <img src={ this.props.image } alt="avatar" style={{ width: '100%' }} /> : uploadButton}
        </Upload>
    );
  }
}

const BasicSet = () => {
  const onFinish = (values: any) => {
    console.log('Success:', values);
  };

  const onFinishFailed = (errorInfo: any) => {
    console.log('Failed:', errorInfo);
  };
  // 向后端提交，进行更新
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
        <Form.Item label="头像" name="picture">
          <MyAvatar image={userInfo(state => state.image)}/>
        </Form.Item>

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
            label="邮箱(不建议修改)"
            name="email"
            rules={[{ message: 'Please input your email!' }]}
            initialValue={userInfo(state => state.email)}
        >
          <Input />
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

const PrivacySet = () => {
  // 根据 id 获取用户信息, userInfo(state => state.id);
  const onFinish = (values: any) => {
    console.log('Success:', values);
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

export function BasicInfoSet() {
  const id = getQueryVariable("wh");
  const change = userInfo(state => state.changeId);
  change(id);

  return <div className="site-layout-content">
      <Tabs tabPosition={'left'}>
        <TabPane tab="基本设置" key="1">
          <PageHeader title="基本设置"/>
          <BasicSet />
        </TabPane>
        <TabPane tab="隐私设置" key="2">
          <PageHeader title="修改密码"/>
          <PrivacySet />
        </TabPane>

      </Tabs>
    </div>
}