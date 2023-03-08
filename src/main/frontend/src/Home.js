import React, {useEffect, useState} from "react";
import axios from "axios";
import {Link} from "react-router-dom";
import {Button, Container, Table} from "react-bootstrap";
import button from "bootstrap/js/src/button";
import Bookmark from "./Bookmark";

const Home = () => {
    const [username, setUsername] = useState("");
    const [article, setArticle] = useState([]); // 연동 테스트용 state
    let num = 1;

    const handleLogoutBtn = () => {
        localStorage.removeItem("accessToken");
        localStorage.removeItem("refreshToken");
        localStorage.removeItem("accessTokenExpiresIn");
        window.location.reload();
    }

    const Bookmark = () => {
        if (localStorage.getItem("accessToken")) {
            return (
                <Link to={"/bookmark"}>북마크한 게시글 보기</Link>
            )
        }
    }
    const Greeting = (username) => {
        if (username === "") {
            return (
                <Link to={`/login`}>로그인</Link>
            )
        } else
            return (
                <>
                    <p>{username}님 환영합니다.</p>
                    <button
                        onClick={handleLogoutBtn}
                    >로그아웃</button>
                    <Link to={"/my-page"}>마이페이지</Link>
                </>
    );

    }

    useEffect(() => {
        axios
            .get("/api/articles")
            .then((response) => setArticle(response.data.data))
            .catch((error) => console.log(error));
        if (localStorage.getItem("accessToken")) {
            axios
                .get("/api/me")
                .then((res) => setUsername(res.data.name))
                .catch((err) => console.log(err));
        }
    }, []);

    return (
        <Container style={{paddingTop: "20px", display: "flex", flexDirection: "column", alignItems: "center"}}>
            {Greeting(username)}
            {Bookmark()}
            <h1>프로그래밍 게시판</h1>
            <Table striped bordered hover style={{minHeight: "450px"}}>
                <thead>
                <tr>
                    <th>#</th>
                    <th>제목</th>
                    <th>작성자</th>
                    <th>작성 시간</th>
                </tr>
                </thead>
                <tbody>
                {
                    article.map(({id, title, postDate, username}) => {
                        return (
                            <tr key={id}>
                                <td>{num++}</td>
                                <td>
                                    <Link to={`/articles/${id}`}
                                          style={{textDecoration: "none", color: "black"}}
                                    >{title}</Link>
                                </td>
                                <td>
                                    {username}
                                </td>
                                <td>
                                    {postDate}
                                </td>
                            </tr>
                        );
                    })
                }
                </tbody>
            </Table>
            <div style={{width: "100%", display: "flex", justifyContent: "right"}}>
                <Button variant="info">
                    <Link to="/create-article"
                          style={{textDecoration: "none", color: "white"}}>글 작성</Link>
                </Button>
            </div>
        </Container>
    );
}

export default Home;
