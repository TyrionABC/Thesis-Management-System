import React from 'react';
import ReactDOM from 'react-dom';
import './Components/BeginPage/index.css';
import reportWebVitals from './reportWebVitals';
import Routing from './Router/index';

/* 用来防止 xss 攻击
const title = response.potentiallyMaliciousInput;
// 直接使用是安全的：
const element = <h1>{title}</h1>;
*/

ReactDOM.render(
    <React.StrictMode>
      <Routing/>
    </React.StrictMode>,
    document.getElementById('root')
);

reportWebVitals();
