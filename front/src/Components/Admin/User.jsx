import React from "react";
import { Input, 
        Divider,
        Table,
        Button,
        Space }from 'antd';
import { BulbFilled, SearchOutlined } from '@ant-design/icons';
import { arrayOf } from "prop-types";
import axios from "axios";

/*const user = [{
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
}];*/
const { Search } = Input;
export class UserGovern extends React.Component{
    state = {
        user:[],
        showuser:[],
        id:'',
    }

    constructor(props) {
        super(props);
        this.state = { user:[], show_user:[], id: props.id }
    }

    componentDidMount() {
        let that = this;
        axios({
            method: 'get',
            url: 'http://localhost:8080/admin/getAllUsers',
        }).then(function(res) {
            console.log(res.data);
            let legal_user=[];
            for(var i=0;i<res.data.length;i++){
                if(res.data[i].flag===0){
                    legal_user.push(res.data[i]);
                }
            }
            that.setState({
                user: res.data,
                showuser:legal_user,
            })
        });
    }
    onSearch=(value)=>{
        let mid=[];
        if(value===''){
            for(var i=0;i<this.state.user.length;i++){
                if(this.state.user[i].flag===0){
                    mid.push(this.state.user[i]);
                }
            }
        }else{
            for(var i=0;i<this.state.user.length;i++){
                if((this.state.user[i].name===value||this.state.user[i].userId===value)&&this.state.user[i].flag===0){
                    mid.push(this.state.user[i]);
                }
            }
        }
        this.setState({
            showuser:mid,
        })
    }
    changeUserPermission =(email) =>{
        axios({
            method: 'post',
            url: 'http://localhost:8080/admin/updateAccess',
            data: { userId: email },
        }).then(function(res) {
            console.log(res.data);
        });
        let newuser=[];
        for(var i=0;i<this.state.user.length;i++){
            newuser.push(this.state.user[i]);
            if(newuser[i].userId===email){
                newuser[i].flag=1;
            }
        }
        let mid=[];
        for(var i=0;i<this.state.showuser.length;i++){
            if(this.state.showuser[i].userId!==email){
                mid.push(this.state.showuser[i]);
            }
        }
        this.setState({
            user:newuser,
            showuser:mid,
        })
    }
    lock=(email)=>{
        axios({
            method: 'post',
            url: 'http://localhost:8080/admin/deleteUser',
            data: { userId: email },
        }).then(function(res) {
            console.log(res.data);
        });
        let newuser=[];
        for(var i=0;i<this.state.user.length;i++){
            newuser.push(this.state.user[i]);
            if(newuser[i].userId===email){
                newuser[i].flag=1;
            }
        }
        let mid=[];
        for(var i=0;i<this.state.showuser.length;i++){
            if(this.state.showuser[i].userId!==email){
                mid.push(this.state.showuser[i]);
            }
        }
        this.setState({
            user:newuser,
            showuser:mid,
        })
    }
    render(){
        const columns=[
            {
                title: '邮箱',
                dataIndex: 'userId',
                key: 'userId',
                render: (text)=><a style={{color:'#3366FF'}}>{text}</a>,
            },
            {
                title: '用户名',
                dataIndex: 'name',
                key: 'name',
            },
            {
                title: '单位/学校',
                dataIndex: 'school',
                key: 'school',
            },
            {
                title: '操作',
                key: 'action',
                render: (_, record)=>(
                    <Space size="middle">
                        <a style={{color:"green"}} onClick={()=>this.changeUserPermission(record.userId)}>修改用户权限</a>
                        <a style={{color:"red"}} onClick={()=>this.lock(record.userId)}>封禁用户</a>
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

export class IllegalUser extends React.Component{
    state = {
        user:[],
        showuser:[],
        id:'',
    }

    constructor(props) {
        super(props);
        this.state = { user:[], show_user:[], id: props.id }
    }

    componentDidMount() {
        let that = this;
        axios({
            method: 'get',
            url: 'http://localhost:8080/admin/getAllUsers',
        }).then(function(res) {
            let illegal_user=[];
            for(var i=0;i<res.data.length;i++){
                if(res.data[i].flag===1){
                    illegal_user.push(res.data[i]);
                }
            }
            that.setState({
                user: res.data,
                showuser:illegal_user,
            })
        });
    }
    onSearch=(value)=>{
        let mid=[];
        if(value===''){
            for(var i=0;i<this.state.user.length;i++){
                if(this.state.user[i].flag===1){
                    mid.push(this.state.user[i]);
                }
            }
        }else{
            for(var i=0;i<this.state.user.length;i++){
                if((this.state.user[i].name===value||this.state.user[i].userId===value)&&this.state.user[i].flag===1){
                    mid.push(this.state.user[i]);
                }
            }
        }
        this.setState({
            showuser:mid,
        })
    }
    unlock=(email)=>{
        axios({
            method: 'post',
            url: 'http://localhost:8080/admin/recoverUser',
            data: { userId: email },
        }).then(function(res) {
            console.log(res.data);
        });
        let newuser=[];
        for(var i=0;i<this.state.user.length;i++){
            newuser.push(this.state.user[i]);
            if(newuser[i].userId===email){
                newuser[i].flag=0;
            }
        }
        let mid=[];
        for(var i=0;i<this.state.showuser.length;i++){
            if(this.state.showuser[i].userId!==email){
                mid.push(this.state.showuser[i]);
            }
        }
        this.setState({
            user:newuser,
            showuser:mid,
        })
    }
    render(){
        const columns=[
            {
                title: '邮箱',
                dataIndex: 'userId',
                key: 'userId',
                render: (text)=><a style={{color:'#3366FF'}}>{text}</a>,
            },
            {
                title: '用户名',
                dataIndex: 'name',
                key: 'name',
            },
            {
                title: '单位/学校',
                dataIndex: 'school',
                key: 'school',
            },
            {
                title: '操作',
                key: 'action',
                render: (_, record)=>(
                    <Space size="middle">
                        <a style={{color:"red"}} onClick={()=>this.unlock(record.userId)}>解除封禁</a>
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