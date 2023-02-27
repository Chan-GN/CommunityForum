import React, {useEffect, useState} from "react";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import Home from "./Home";
import Article from "./Article";
import ArticleForm from "./ArticleForm";
import UpdateForm from "./UpdateForm";
import LoginForm from "./LoginForm";
import SignUpForm from "./SignUpForm";
import axios from "axios";
import Bookmark from "./Bookmark";


function App() {
    useEffect(() => {
        axios.defaults.headers.common["Authorization"] = `Bearer ${localStorage.getItem("accessToken")}`;
    }, [])

    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Home/>}></Route>
                <Route path="/articles/:id" element={<Article/>}></Route>
                <Route path="/create-article" element={<ArticleForm/>}></Route>
                <Route path="/update-article" element={<UpdateForm/>}></Route>
                <Route path="/login" element={<LoginForm/>}></Route>
                <Route path="/signup" element={<SignUpForm/>}></Route>
                <Route path="/bookmark" element={<Bookmark/>}></Route>
            </Routes>
        </BrowserRouter>
    );
}

export default App;
