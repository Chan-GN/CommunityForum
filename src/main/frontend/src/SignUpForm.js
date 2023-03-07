import {Container} from "react-bootstrap";
import {Link, useNavigate} from "react-router-dom";
import {useState} from "react";
import axios from "axios";

const SignUpForm = () => {
    const [name, setName] = useState("");
    const [password, setPassword] = useState("");
    const [nickname, setNickname] = useState("");
    const [introduce, setIntroduce] = useState("");
    let navigate = useNavigate(); // 다른 component 로 이동할 때 사용

    const handleSignUpBtn = async () => {
        const signUpInfo = {
            name,
            password,
            nickname,
            introduce
        }
        try{
            let response = await axios({
                method: 'post',
                url: '/api/auth/signup',
                headers: {'Content-Type': 'application/json'},
                data: JSON.stringify(signUpInfo)
            });
            console.log('writeBoard/response: ', response);
            console.log('writeBoard/response.status: ', response.status);
            navigate("/", {});
        } catch (err) {
            console.log('CreateBoard/handleInput/err: ', err);
        }

    }

    return (
        <Container>
            <form style={{marginTop: "100px", width:"50%"}}>
                <div className="form-outline mb-4">
                    <input type="text" id="name" className="form-control"
                        value={name} onChange={(e) => setName(e.target.value)}
                    />
                    <label className="form-label" htmlFor="name">Name</label>
                </div>

                <div className="form-outline mb-4">
                    <input type="password" id="password" className="form-control"
                           value={password} onChange={(e) => setPassword(e.target.value)}
                    />
                    <label className="form-label" htmlFor="password">Password</label>
                </div>

                <div className="form-outline mb-4">
                    <input type="text" id="nickname" className="form-control"
                           value={nickname} onChange={(e) => setNickname(e.target.value)}
                    />
                    <label className="form-label" htmlFor="nickname">Nickname</label>
                </div>

                <div className="form-outline mb-4">
                    <input type="text" id="introduce" className="form-control"
                           value={introduce} onChange={(e) => setIntroduce(e.target.value)}
                    />
                    <label className="form-label" htmlFor="password">Introduce</label>
                </div>

                <button
                    type="button"
                    className="btn btn-primary btn-block mb-4"
                    onClick={handleSignUpBtn}
                >
                    Sign Up
                </button>
            </form>
        </Container>
    )
}

export default SignUpForm;