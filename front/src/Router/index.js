import React from "react";
import { Route, BrowserRouter as Router, Routes } from "react-router-dom";
import { createBrowserHistory } from "history";

import Intro from "../Components/BeginPage/Intro";
import Reset from "../Components/BeginPage/Reset";
import App from "../Components/App/App";
import Admin from "../Components/Admin/Admin";
import {WriteThesis} from "../Components/Thesis/WriteThesis";

export default class Routing extends React.Component {
    render() {
        return <Router history={ createBrowserHistory() }>
            <Routes>
                <Route exact path="/" element={ <Intro/> }/>
                <Route path="/reset" element={ <Reset/> }/>
                <Route path="/app" element={ <App/> }/>
                <Route path="/admin" element={ <Admin/>}/>
                <Route path="/writing" element={ <WriteThesis/> }/>
            </Routes>
        </Router>
    }
}