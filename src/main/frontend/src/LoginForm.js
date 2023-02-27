import {Container} from "react-bootstrap";
import {Link, useNavigate} from "react-router-dom";
import {useState} from "react";
import axios from "axios";

const LoginForm = () => {
    const [name, setName] = useState("");
    const [password, setPassword] = useState("");
    let navigate = useNavigate(); // 다른 component 로 이동할 때 사용

    const handleLoginBtn = async () => {
        const loginInfo = {
            name: name,
            password: password
        }
        try{
            let response = await axios({
                method: 'post',
                url: '/api/auth/login',
                headers: {'Content-Type': 'application/json'},
                data: JSON.stringify(loginInfo)
            });
            localStorage.setItem('accessToken', response.data.accessToken)
            localStorage.setItem('refreshToken', response.data.refreshToken)
            localStorage.setItem('accessTokenExpiresIn', response.data.accessTokenExpiresIn);
            axios.defaults.headers.common["Authorization"] = `Bearer ${response.data.accessToken}`;
            console.log('writeBoard/response: ', response);
            console.log('writeBoard/response.status: ', response.status);
            navigate("/", {});
        } catch (err) {
            console.log('CreateBoard/handleInput/err: ', err);
        }

    }


    return (
        <Container>
            <form style={{marginTop: "100px", width: "50%"}}>
                <div className="form-outline mb-4">
                    <input type="email" id="form2Example1" className="form-control"
                    value={name} onChange={(e) => setName(e.target.value)}
                    />
                    <label className="form-label" htmlFor="form2Example1">Name</label>
                </div>

                <div className="form-outline mb-4">
                    <input type="password" id="form2Example2" className="form-control"
                           value={password} onChange={(e) => setPassword(e.target.value)}
                    />
                    <label className="form-label" htmlFor="form2Example2">Password</label>
                </div>

                <button type="button" className="btn btn-primary btn-block mb-4"
                    onClick={handleLoginBtn}
                >Sign in</button>

                <div className="text-center">
                    <p>Not a member? <Link to={`/signup`}>Register</Link></p>
                </div>
            </form>
        </Container>
    )
};

export default LoginForm;