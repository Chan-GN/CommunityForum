import {useLocation, useNavigate} from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";
import CodeHighlighter from "./CodeHighlighter";

const UpdateForm = () => {
    const [title, setTitle] = useState("");
    const [article, setArticle] = useState("");
    const [code, setCode] = useState("");
    let navigate = useNavigate(); // 다른 component 로 이동할 때 사용

    const location = useLocation();

    const handleInputClick = async (e) => {
        const content = {
            article: article,
            code: code
        }
        const request_data = {
            title: title, content: content
        };
        console.log('req_data: ', request_data);
        try{
            let response = await axios({
                method: 'put',
                url: `/api/articles/${location.state.article.id}`,
                headers: {'Content-Type': 'application/json'},
                data: JSON.stringify(request_data)
            });
            console.log('writeBoard/response: ', response);
            console.log('writeBoard/response.status: ', response.status);
            navigate(`/articles/${location.state.article.id}`, {});
        } catch (err) {
            console.log('CreateBoard/handleInput/err: ', err);
        }
    }

    useEffect(()=> {
        setTitle(location.state.article.title);
        setArticle(location.state.article.content.article);
        setCode(location.state.article.content.code);
    }, [])

    return (
        <>
            <div style={{padding: "20px"}}>
                <label>제목</label><br/>
                <input id='input_title' type="text" placeholder="제목을 입력해주세요" onChange={(e) => setTitle(e.target.value)}
                       value={title}/><br/><br/>
                <label>내용</label><br/>
                <textarea id='textarea_content' type="text" placeholder="내용을 입력해주세요"
                          onChange={(e) => setArticle(e.target.value)} value={article}/><br/>
                <div style={{display: "flex"}}>
                    <textarea name="textarea_code" id="textarea_code" cols="30" rows="10"
                              onChange={(e) => setCode(e.target.value)} value={code}
                              onKeyDown={(e) => {
                                  if (e.key === "Tab") {
                                      e.preventDefault();

                                      let start = e.target.selectionStart;
                                      let end = e.target.selectionEnd;

                                      // set textarea value to: text before caret + tab + text after caret
                                      e.target.value = e.target.value.substring(0, start) +
                                          "\t" + e.target.value.substring(end);

                                      // put caret at right position again
                                      e.target.selectionStart =
                                          e.target.selectionEnd = start + 1;
                                  }
                              }
                              }
                    ></textarea>
                    <CodeHighlighter
                        codeString={code}
                    />
                </div>
                <input type="button" value="게시글 수정" onClick={handleInputClick}/>
            </div>
        </>
    );
}

export default UpdateForm;