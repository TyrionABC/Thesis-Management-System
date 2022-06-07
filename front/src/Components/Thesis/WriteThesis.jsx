import React from 'react'
// 引入编辑器组件
import {Input, Space, Form, Select, Col, Row, Cascader, Button, DatePicker, Modal, Table, Tag} from 'antd';
import BraftEditor from 'braft-editor'
// 引入编辑器样式
import 'braft-editor/dist/index.css'
import axios from "axios";
import { AntDesignOutlined, MinusCircleFilled, MinusCircleOutlined, PlusOutlined } from '@ant-design/icons';

const {Option}=Select;
const { Column, ColumnGroup } = Table;

export class WriteThesis extends React.Component {

    state = {
        title:'',
        editorState: BraftEditor.createEditorState(null),
        flag:'',
        isSee:false,
        direction: [],
        arr:[],
    }

    componentDidMount () {
        // 假设此处从服务端获取html格式的编辑器内容
        let that=this;
        axios({
            method: 'get',
            url: 'http://localhost:8080/admin/getAllDirections',
        }).then(function(res) {
            console.log(res.data);
            that.setState({
                direction:res.data,
            })
        });
        const htmlContent = [];
        //await fetchEditorContent()
        // 使用BraftEditor.createEditorState将html字符串转换为编辑器需要的editorStat
        that.setState({
            flag:0,
            editorState: BraftEditor.createEditorState(htmlContent)
        })
    }
    handleTitleChange=(value)=>{
        this.setState({
            title:value,
        })
    }
    onFinish=(value)=>{
        console.log(this.state.flag);
    }
    addReference = () =>{
        this.setState({
            isSee:true,
        })
    } 
    submitContent = () => {
        this.setState({
            flag:1,
        })
    }
    submitFlag = () => {
        // 在编辑器获得焦点时按下ctrl+s会执行此方法
        // 编辑器内容提交到服务端之前，可直接调用editorState.toHTML()来获取HTML格式的内容
        const htmlContent = this.state.editorState.toHTML();
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
        this.setState({ editorState });
    }

    render () {
        /*const direction=[{
            label:'人工智能',
            value:'人工智能',
            children:[
                {label:'深度学习',value:'深度学习'},
                {label:'人机对弈',value:'人机对弈'},
                {label:'机器学习',value:'机器学习'}
            ]
        },{
            label:'语言',
            value:'语言',
            children:[
                {label:'中文',value:'中文'},
                {label:'英文',value:'英文'}
            ]
        }];*/
        const { editorState } = this.state
        return (
            <>
            <Row>
            <Col span={18}>
            <div style={{width:900}}>
                <Space direction='vertical' size="middle">
                <div style={{marginTop:20}}>
                    <Input placeholder='标题' style={{width:700}} onChange={this.handleTitleChange}></Input>
                </div>
                    <div className="my-component" style={{borderStyle:'solid',width:900,backgroundColor:'white' }}>
                    <BraftEditor
                        value={editorState}
                        onChange={this.handleEditorChange}
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
                //labelCol={{span:8,}}
                //wrapperCol={{span:16,}}
                initialValues={{remeber:true,}}
                onFinish={this.onFinish}
                //onFinishFailed={onFinishFailed}
                autoComplete="off"
                >
                    <Form.Item
                    label="论文类型"
                    name="thesisType"
                    >
                        <Select allowClear style={{width:300}} >
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
                        <Cascader allowClear options={this.state.direction} style={{width:300}} multiple />
                    </Form.Item>
                    <Form.List name="writer">
                    {(fields,{add, remove})=>(
                        <>
                            {fields.map(({key, name,...restField})=>(
                                <Space
                                key={key}
                                style={{display:'flex'}}
                                align="baseline"
                                >
                                    <Form.Item
                                    label={"第"+(name+1)+"作者"}
                                    {...restField}
                                    name={name}
                                    >
                                        <Input style={{width:300}} id={name+1} />
                                    </Form.Item>
                                    <MinusCircleOutlined onClick={()=>remove(name)} />
                                </Space>
                            ))}
                            <Form.Item>
                                <Button type="dashed" onClick={()=>add()} style={{width:200}} block icon={<PlusOutlined/>}>
                                    添加作者
                                </Button>
                            </Form.Item>
                        </>
                    )}
                    </Form.List>
                    <Form.Item
                    label="会议"
                    name="publishMeeting">
                        <input style={{width:300}}></input>
                    </Form.Item>
                    <Form.Item
                    label="发表日期"
                    name="publishTime"
                    >
                        <DatePicker style={{width:300}}></DatePicker>
                    </Form.Item>
                    <Form.Item>
                        <Button type="dashed" onClick={this.addReference}>
                            添加引用
                        </Button>
                    </Form.Item>
                    <Modal 
                    title="引用查询"
                    centered
                    visible={this.state.isSee}
                    onOk={()=>this.setState({isSee:false})}
                    onCancel={()=>this.setState({isSee:false})}
                    width={1000}
                    destroyOnClose>
                        <Table dataSource={this.state.arr}>
                        <ColumnGroup title="基本信息">
                        <Column title="ID" dataIndex="id" key="id" />
                        <Column title="标题" dataIndex="title" key="title" />
                        <Column title="作者" dataIndex="writerName" key="writerName" />
                        <Column
                        title="研究方向"
                        dataIndex="path"
                        key="path"
                        render={(tag => (
                                <Tag color="blue" key={tag}>
                                {tag}
                                </Tag>
                                )
                            )}
                        />
                        <Column title="类型" dataIndex="thesisType" key="thesisType" />
                        </ColumnGroup>
                        <ColumnGroup title="发布">
                        <Column title="发布人" dataIndex="publisher" key="publisher" />
                        <Column title="发布会议" dataIndex="publishMeeting" key="publishMeeting" />
                        </ColumnGroup>
                        <Column
                        title="操作"
                        key="action"
                        render={() => (
                            <Space size="middle">
                            <a>添加</ a>
                            </Space>
                        )}
                        />
                        </Table>
                        <Form
                        name="basic"
                        labelCol={{ span: 8 }}
                        wrapperCol={{ span: 16 }}
                        initialValues={{ remember: true }}
                        preserve={false}
                        //onFinish={}
                        //onFinishFailed={}
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
                    </Modal>
                    <Form.Item>
                        <Button type="primary" htmlType="submit" onClick={this.submitContent}>
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