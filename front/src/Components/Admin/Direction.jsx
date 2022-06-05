import React from "react";
import { Input, 
    Divider,
    Table,
    Button,
    Space }from 'antd';
import { BulbFilled, SearchOutlined } from '@ant-design/icons';
const direction=[{
    name:语言,
    parent_direction:null,
    level:1
},{
    name:中文,
    parent_direction:语言,
    level:2
},{
    name:普通话,
    parent_direction:中文,
    level:3
}];
export class Direction extends React.Component{

}