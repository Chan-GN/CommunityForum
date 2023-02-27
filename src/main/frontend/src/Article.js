import {Link, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";
import CodeHighlighter from "./CodeHighlighter";

const Article = () => {
    const {id} = useParams();
    const [article, setArticle] = useState([]);
    const [comment, setComment] = useState("");
    const [comments, setComments] = useState([]);
    const [bookmark, setBookmark] = useState(false);

    useEffect(() => {
        axios.defaults.headers.common["Authorization"] = `Bearer ${localStorage.getItem("accessToken")}`;
        axios
            .get(`/api/articles/${id}`)
            .then((response) => {
                setArticle(response.data.data)
            })
            .catch((error) => console.log(error));
        axios
            .get(`/api/articles/${id}/comments`)
            .then((response) => setComments(response.data.data))
            .catch((error) => console.log(error));
        if (localStorage.getItem("accessToken")) {
            axios
                .get(`/api/articles/${id}/bookmark`)
                .then((response) => setBookmark(response.data))
                .catch((error) => console.log(error));
        }
    }, []);

    const deleteArticle = () => {
        axios
            .delete(`/api/articles/${id}`)
            .then(r => console.log(r))
            .catch((error) => console.log(error));
        window.location.href = "/";
    }

    const unFavBtnClickHandler = async () => {
        if (localStorage.getItem("accessToken")) {
            try {
                let response = await axios({
                    method: "delete",
                    url: `/api/articles/${id}/bookmark`,
                    headers: {'Content-Type': 'application/json'}
                })
                console.log(response);
            } catch (error) {
                console.log(error.response);
            } finally {
                alert("북마크가 취소 되었습니다.");
                window.location.reload();
            }
        } else {
            alert("로그인 후 이용해주세요");
        }
    }

    const favBtnClickHandler = async () => {
        if (localStorage.getItem("accessToken")) {
            try {
                let response = await axios({
                    method: "post",
                    url: `/api/articles/${id}/bookmark`,
                    headers: {'Content-Type': 'application/json'}
                })
                console.log(response);
            } catch (error) {
                console.log(error.response);
            } finally {
                alert("북마크가 되었습니다.");
                window.location.reload();
            }
        } else {
            alert("로그인 후 이용해주세요");
        }
    }

    const createComment = async () => {
        document.getElementById("comment").value = "";
        const data = {body: comment}
        try{
            let response = await axios({
                method: 'post',
                url: `/api/articles/${id}/comments`,
                headers: {'Content-Type': 'application/json'},
                data: JSON.stringify(data)
            });
            window.location.reload();
        } catch (err) {
            console.log('CreateBoard/handleInput/err: ', err);
        }

    }

    function BookmarkButton({bookmark}) {
        if (bookmark) {
            return (
                <button onClick={unFavBtnClickHandler}>
                    북마크 취소
                </button>
            );
        } else {
            return (
                <button onClick={favBtnClickHandler}>
                    북마크
                </button>
            );
        }
    }

    return (
        <div style={{padding: "20px"}}>
            <Link to={"/"}>홈으로 가기</Link>
            <div style={{display: "flex"}}>
                <h1 style={{marginRight: "50px"}}>제목 : {article.title}</h1>
                <BookmarkButton
                    bookmark={bookmark}
                />
            </div>
            <div>
                <p>내용 :{article.content?.article}</p>
                <div style={{width: "50%"}}>
                    {
                        article.content?.code === undefined ?
                            <CodeHighlighter
                                codeString={"Loading ..."}
                            />
                            :
                            <CodeHighlighter
                                codeString={`${article.content?.code}`}
                            />
                    }
                </div>


                <p>조회수 : {article.hits}</p>
                <p>북마크 횟수 : {article.bookmarkHits}</p>
                <p>작성 일자 : {article.postDate}</p>
                <p>작성자 : {article.username}</p>
            </div>
            <div style={{padding: "10px", background: "skyblue"}}>
                {
                    comments.map((c) => (
                        <div key={c.id}>
                            <p>{c.username}</p>
                            <p>{c.body}</p>
                        </div>
                    ))
                }
            </div>
            <div>
                <textarea name="comment" id="comment" cols="30" rows="3"
                          placeholder="댓글을 입력하세요."
                          value={comment}
                          onChange={(e) => setComment(e.target.value)}
                ></textarea>
                <button
                    style={{marginLeft: "10px"}}
                    onClick={createComment}
                >댓글 등록
                </button>
            </div>
            <div>
                <Link to={"/update-article"} state={{article: article}}>게시글 수정</Link>
                <br/>
                <button style={{marginTop: "10px"}} onClick={deleteArticle}>게시글 삭제</button>
            </div>
        </div>
    );
}

export default Article;