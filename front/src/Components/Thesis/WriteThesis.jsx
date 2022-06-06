import React from 'react'
// 引入编辑器组件
import {Input, Space, Form, Select, Col, Row, Cascader, Button} from 'antd';
import BraftEditor from 'braft-editor'
// 引入编辑器样式
import 'braft-editor/dist/index.css'
import axios from "axios";
import { AntDesignOutlined } from '@ant-design/icons';

const {Option}=Select;

export class WriteThesis extends React.Component {

    state = {
        title:'',
        type:'',
        link:'',
        direction:[],
        meeting:'',
        editorState: BraftEditor.createEditorState(null)
    }

    componentDidMount () {
        // 假设此处从服务端获取html格式的编辑器内容
        let that = this;
        axios({
            method: 'get',
            url: 'http://localhost:8080/admin/getAllDirections',
        }).then(function(res) {
            console.log(res.data);
            that.setState({
                direction: res.data,
            })
        });
        const htmlContent = [];
        //await fetchEditorContent()
        // 使用BraftEditor.createEditorState将html字符串转换为编辑器需要的editorStat
        that.setState({
            editorState: BraftEditor.createEditorState(htmlContent)
        })
    }

    submitContent = async () => {
        // 在编辑器获得焦点时按下ctrl+s会执行此方法
        // 编辑器内容提交到服务端之前，可直接调用editorState.toHTML()来获取HTML格式的内容
        const htmlContent = this.state.editorState.toHTML()
        //const result = await saveEditorContent(htmlContent)
        /*axios({
            method: 'post',
            url: 'http://localhost:8080/admin/input',
            data: {text: htmlContent}
        }).then(function(res) {
            console.log(res.data);
        });*/
    }

    handleEditorChange = (editorState) => {
        this.setState({ editorState })
    }

    render () {
        console.log(this.state.direction);
        const { editorState } = this.state
        return (
            <>
            <Row>
            <Col span={18}>
            <div style={{width:900}}>
                <Space direction='vertical' size="middle">
                <div style={{marginTop:20}}>
                    <Input placeholder='标题' style={{width:700}}></Input>
                </div>
                    <div className="my-component" style={{borderStyle:'solid',width:900,backgroundColor:'white' }}>
                    <BraftEditor
                        value={editorState}
                        onChange={this.handleEditorChange}
                        onSave={this.submitContent}
                    />
                </div>
                </Space>
            </div>
            </Col>
            <Col span={6}>
            <div style={{width:200,marginTop:20}}>
                <Form 
                name="info"
                layout='vertical'
                labelCol={{span:8,}}
                wrapperCol={{span:16,}}
                initialValues={{remeber:true,}}
                //onFinish={onFinish}
                //onFinishFailed={onFinishFailed}
                autoComplete="off"
                >
                    <Form.Item
                    label="论文类型"
                    name="thesisType"
                    >
                        <Select allowClear style={{width:300}}>
                            <Option value="理论证明型">理论证明型</Option>
                            <Option value="综述性">综述性</Option>
                            <Option value="实验型">实验型</Option>
                            <Option value="工具型">工具型</Option>
                            <Option value="数据集型">数据集型</Option>
                        </Select>
                    </Form.Item>
                    <Form.Item
                    label="研究方向"
                    name="direction">
                        <Cascader allowClear options={this.state.direction} style={{width:300}} multiple  />
                    </Form.Item>
                    <Form.Item
                    label="作者"
                    name="writerName">
                        <input style={{width:300}}></input>    
                    </Form.Item>
                    <Form.Item
                    label="链接"
                    name="literatureLink"
                    rules={[
                        {type:'url',warningOnly:true}
                    ]}>
                        <input style={{width:300}}></input>
                    </Form.Item>
                    <Form.Item
                    label="会议"
                    name="publishMeeting">
                        <input style={{width:300}}></input>
                    </Form.Item>
                    <Form.Item>
                        <Button type="primary" htmlType="submit">
                            提交论文
                        </Button>
                    </Form.Item>
                    <Form.Item>
                        <Button type="primary" htmlType="submit">
                            保存草稿
                        </Button>
                    </Form.Item>
                </Form>
            </div>
            </Col>
            </Row>
            </>
        )

    }

}

export class UpdateThesis extends React.Component {

    state = {
        // 创建一个空的editorState作为初始值
        editorState: BraftEditor.createEditorState(null)
    }

    componentDidMount () {
        // 假设此处从服务端获取html格式的编辑器内容
        const htmlContent = [];
        /*axios({
            method: 'get',
            url: 'http://localhost:8080/admin/getText?id='+props.id,
        }).then(function(res) {
            console.log(res.data);
            htmlContent=res.data.text;
        });*/
        //await fetchEditorContent()
        // 使用BraftEditor.createEditorState将html字符串转换为编辑器需要的editorStat
        this.setState({
            editorState: BraftEditor.createEditorState(htmlContent)
        })
    }

    submitContent = async () => {
        // 在编辑器获得焦点时按下ctrl+s会执行此方法
        // 编辑器内容提交到服务端之前，可直接调用editorState.toHTML()来获取HTML格式的内容
        const htmlContent = this.state.editorState.toHTML()
        //const result = await saveEditorContent(htmlContent)
        /*axios({
            method: 'get',
            url: 'http://localhost:8080/admin/getText?id='+props.id,
        }).then(function(res) {
            console.log(res.data);
            htmlContent=res.data.text;
        });*/
    }

    handleEditorChange = (editorState) => {
        this.setState({ editorState })
    }

    render () {

        const { editorState } = this.state
        return (
            <div className="my-component">
                <BraftEditor
                    value={editorState}
                    onChange={this.handleEditorChange}
                    onSave={this.submitContent}
                />
            </div>
        )

    }

}