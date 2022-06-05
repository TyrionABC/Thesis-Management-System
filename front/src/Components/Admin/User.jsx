import React from "react";
import { Input, 
        Divider,
        Table,
        Button,
        Space }from 'antd';
import { BulbFilled, SearchOutlined } from '@ant-design/icons';
import { arrayOf } from "prop-types";

const user = [{
    user_name:'Liu',
    user_email:'111@qq.com',
    user_belong:'ECNU',
    user_permission:'user',
    flag:'allowed',
},{
    user_name:'Cui',
    user_email:'222@qq.com',
    user_belong:'ECNU',
    user_permission:'user',
    flag:'allowed'
},{
    user_name:'Ma',
    user_email:'333@qq.com',
    user_belong:'ECNU',
    user_permission:'user',
    flag:'allowed'
},{
    user_name:'Kong',
    user_email:'444@qq.com',
    user_belong:'ZZU',
    user_permission:'user',
    flag:'not_allowed'
}];
const legal_user=[];
for(var i=0;i<user.length;i++){
    if(user[i].flag==='allowed'){
        legal_user.push(user[i]);
    }
}
const { Search } = Input;
export class UserGovern extends React.Component{
    constructor(props){
        super(props);
    }
    state={
        showuser:legal_user,
    }
    onSearch=(value)=>{
        let mid=[];
        if(value===''){
            for(var i=0;i<user.length;i++){
                if(user[i].user_permission==="user"&&user[i].flag==='allowed'){
                    mid.push(user[i]);
                }
            }
        }else{
            for(var i=0;i<user.length;i++){
                if((user[i].user_name===value||user[i].user_email===value)&&user[i].user_permission==='user'&&user[i].flag==='allowed'){
                    mid.push(user[i]);
                }
            }
        }
        this.setState({
            showuser:mid,
        })
    }
    changeUserPermission =(email) =>{
        for(var i=0;i<user.length;i++){
            if(user[i].user_email===email){
                user[i].user_permission='admin';
            }
        }
        let mid=[];
        for(var i=0;i<this.state.showuser.length;i++){
            if(this.state.showuser[i].user_email!==email){
                mid.push(this.state.showuser[i]);
            }
        }
        this.setState({
            showuser:mid,
        })
    }
    lock=(email)=>{
        for(var i=0;i<user.length;i++){
            if(user[i].user_email===email){
                user[i].flag='not_allowed';
            }
        }
        let mid=[];
        for(var i=0;i<this.state.showuser.length;i++){
            if(this.state.showuser[i].user_email!==email){
                mid.push(this.state.showuser[i]);
            }
        }
        this.setState({
            showuser:mid,
        })
    }
    render(){
        const columns=[
            {
                title: '邮箱',
                dataIndex: 'user_email',
                key: 'user_email',
                render: (text)=><a style={{color:'#3366FF'}}>{text}</a>,
            },
            {
                title: '用户名',
                dataIndex: 'user_name',
                key: 'user_name',
            },
            {
                title: '单位/学校',
                dataIndex: 'user_belong',
                key: 'user_belong',
            },
            {
                title: '操作',
                key: 'action',
                render: (_, record)=>(
                    <Space size="middle">
                        <a style={{color:"green"}} onClick={()=>this.changeUserPermission(record.user_email)}>修改用户权限</a>
                        <a style={{color:"red"}} onClick={()=>this.lock(record.user_email)}>封禁用户</a>
                    </Space>
                )
            }
        ];
        return(
        <>
            <div style={{marginTop:20,textAlign:"center"}}>
            <Search placeholder="搜索用户" style={{width:600}} allowClear enterButton onSearch={this.onSearch}/>
            </div>
            <Divider>用户信息</Divider>
            <div>
            <Table columns={columns} dataSource={this.state.showuser} />;
            </div>
        </>
        )
    }
}
const illegal_user=[];
for(var i=0;i<user.length;i++){
    if(user[i].flag==='not_allowed'){
        illegal_user.push(user[i]);
    }
}

export class IllegalUser extends React.Component{
    constructor(props){
        super(props);
    }
    state={
        showuser:illegal_user,
    }
    onSearch=(value)=>{
        let mid=[];
        if(value===''){
            for(var i=0;i<user.length;i++){
                if(user[i].user_permission==="user"&&user[i].flag==='not_allowed'){
                    mid.push(user[i]);
                }
            }
        }else{
            for(var i=0;i<user.length;i++){
                if((user[i].user_name===value||user[i].user_email===value)&&user[i].user_permission==='user'&&user[i].flag==='not_allowed'){
                    mid.push(user[i]);
                }
            }
        }
        this.setState({
            showuser:mid,
        })
    }
    unlock=(email)=>{
        for(var i=0;i<user.length;i++){
            if(user[i].user_email===email){
                user[i].flag='allowed';
            }
        }
        let mid=[];
        for(var i=0;i<this.state.showuser.length;i++){
            if(this.state.showuser[i].user_email!==email){
                mid.push(this.state.showuser[i]);
            }
        }
        this.setState({
            showuser:mid,
        })
    }
    render(){
        const columns=[
            {
                title: '邮箱',
                dataIndex: 'user_email',
                key: 'user_email',
                render: (text)=><a style={{color:'#3366FF'}}>{text}</a>,
            },
            {
                title: '用户名',
                dataIndex: 'user_name',
                key: 'user_name',
            },
            {
                title: '单位/学校',
                dataIndex: 'user_belong',
                key: 'user_belong',
            },
            {
                title: '操作',
                key: 'action',
                render: (_, record)=>(
                    <Space size="middle">
                        <a style={{color:"red"}} onClick={()=>this.unlock(record.user_email)}>解除封禁</a>
                    </Space>
                )
            }
        ];
        return(
        <>
            <div style={{marginTop:20,textAlign:"center"}}>
            <Search placeholder="搜索用户" style={{width:600}} allowClear enterButton onSearch={this.onSearch}/>
            </div>
            <Divider>用户信息</Divider>
            <div>
            <Table columns={columns} dataSource={this.state.showuser} />;
            </div>
        </>
        )
    }
}