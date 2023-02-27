import React, {useEffect, useState} from "react";
import axios from "axios";
import {Button, Container, Table} from "react-bootstrap";
import {Link} from "react-router-dom";

const Bookmark = () => {
    const [article, setArticle] = useState([]); // 연동 테스트용 state
    let num = 1;

    useEffect(() => {
        axios.defaults.headers.common["Authorization"] = `Bearer ${localStorage.getItem("accessToken")}`;
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

