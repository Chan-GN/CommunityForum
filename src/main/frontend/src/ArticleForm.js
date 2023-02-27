import {useState} from "react";
import {useNavigate} from "react-router-dom";
import axios from "axios";
import articleForm from "./ArticleForm";
import CodeHighlighter from "./CodeHighlighter";

const ArticleForm = () => {
    const [title, setTitle] = useState("");
    const [article, setArticle] = useState("");
    const [code, setCode] = useState("");
    let navigate = useNavigate(); // 다른 component 로 이동할 때 사용

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
                method: 'post',
                url: '/api/articles',
                headers: {'Content-Type': 'application/json',
                    'Authorization' : `Bearer ${localStorage.getItem("accessToken")}}`
                },
                data: JSON.stringify(request_data)
            });
            console.log('writeBoard/response: ', response);
            console.log('writeBoard/response.status: ', response.status);
            navigate("/", {});
        } catch (err) {
            console.log('CreateBoard/handleInput/err: ', err);
        }
    }


    return (
        <div style={{padding: "20px", display: "flex", flexDirection: "column"}}>
            <div>
                <label>제목</label><br/>
                <input id='input_title' type="text" placeholder="제목을 입력해주세요" onChange={(e) => setTitle(e.target.value)}
                       value={title}/><br/><br/>
                <label>내용</label><br/>
                <textarea id='textarea_content' type="text" placeholder="내용을 입력해주세요"
                          onChange={(e) => setArticle(e.target.value)} value={article}/><br/>
                <hr/>
                <div>
                    <textarea name="textarea_code" id="textarea_code" cols="30" rows="5"
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
            </div>
            <input
                style={{width: "20%"}}
                type="button" value="게시글 생성" onClick={handleInputClick}/>
        </div>
    );
}

export default ArticleForm;