import {useEffect, useState} from "react";
import axios from "axios";
import {Card, Container, Row} from "react-bootstrap";
import {Link} from "react-router-dom";

const MyPage = () => {
    const [nickname, setNickname] = useState();
    const [name, setName] = useState();
    const [introduce, setIntroduce] = useState();

    useEffect(() => {
        axios.defaults.headers.common["Authorization"] = `Bearer ${localStorage.getItem("accessToken")}`;
        axios
            .get("/api/me")
            .then((res) => {
                setIntroduce(res.data.introduce);
                setName(res.data.name);
                setNickname(res.data.nickname);
            })
            .catch((err) => console.log(err));
    }, [])

    return (
        <Container>
            <Row className="justify-content-center mt-5">
                <Card
                    style={{width: "30rem", height: "15rem"}}
                    className={"text-center"}
                >
                    <Card.Body>
                        <Card.Title>{nickname}</Card.Title>
                        <Card.Subtitle className="mb-2 text-muted">{name}</Card.Subtitle>
                        <Card.Text>
                            {introduce}
                        </Card.Text>
                        <Link to={"/"}>홈으로 돌아가기</Link>
                    </Card.Body>
                </Card>
            </Row>
        </Container>
    );
}

export default MyPage;
