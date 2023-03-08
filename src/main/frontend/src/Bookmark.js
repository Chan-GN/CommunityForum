import React, {useEffect, useState} from "react";
import axios from "axios";
import {Button, Container, Table} from "react-bootstrap";
import {Link, useNavigate} from "react-router-dom";

const Bookmark = () => {
    const [article, setArticle] = useState([]);
    const nav = useNavigate();
    let num = 1;

    useEffect(() => {
        if (!localStorage.getItem("accessToken")) { // 로그인 정보(accessToken) 없을 시, Redirect 처리
            alert("로그인이 필요합니다.");
            nav("/");
        }
        axios
            .get("/api/bookmark")
            .then((response) => setArticle(response.data));
    }, []);

    return (
        <Container>
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
    )
};

export default Bookmark;

