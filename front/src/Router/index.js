import React from "react";
import { Route, BrowserRouter as Router, Routes } from "react-router-dom";
import { createBrowserHistory } from "history";

import Intro from "../Components/BeginPage/Intro";
import Reset from "../Components/BeginPage/Reset";
import App from "../Components/App/App";

export default class Routing extends React.Component {
    render() {
        return <Router history={ createBrowserHistory() }>
            <Routes>
                <Route exact path="/" element={ <Intro/> }/>
                <Route path="/reset" element={ <Reset/> }/>
                <Route path="/app" element={ <App/> }/>
            </Routes>
        </Router>
    }
}